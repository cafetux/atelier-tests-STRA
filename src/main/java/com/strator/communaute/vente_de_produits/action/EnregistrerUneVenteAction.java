package com.strator.communaute.vente_de_produits.action;

import com.strator.communaute.catalogue.exception.ProduitInconnuException;
import com.strator.communaute.catalogue.model.ProduitCatalogue;
import com.strator.communaute.catalogue.repository.CatalogueProduits;
import com.strator.communaute.client.exception.UserInactifException;
import com.strator.communaute.client.model.Client;
import com.strator.communaute.client.repository.ClientsMagasin;
import com.strator.communaute.programme_fidelite.FideliteDao;
import com.strator.communaute.programme_fidelite.model.ActionRecompensee;
import com.strator.communaute.stocks_produits.repository.StockRepository;
import com.strator.communaute.vente_de_produits.model.Commande;
import com.strator.communaute.vente_de_produits.model.LigneCommande;
import com.strator.communaute.vente_de_produits.model.ProduitVendu;
import com.strator.communaute.vente_de_produits.repository.TransactionRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Permet d'enregistrer une vente effectuée par un client
 */
@Component
public class EnregistrerUneVenteAction {

    @Autowired
    private ClientsMagasin clientsMagasin;

    @Autowired
    private CatalogueProduits catalogueProduits;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private FideliteDao fideliteDao;

    /**
     * Permet d'enregistrer une vente
     * @param clientEmail l'identifiant (email) du client ayant effectué la vente
     * @param produits les produits vendus
     */
    public void enregistrerUneVenteOuUnRetour(String clientEmail, Map<ProduitVendu,Integer> produits, boolean retour){
            BigDecimal cumulCommande =BigDecimal.ZERO;
            Client client = clientsMagasin.retrieveByEmail(clientEmail);
            if (!client.isActif()) {
                throw new UserInactifException(client);
            }

            if (!retour) {
                Commande opv = new Commande(clientEmail);
                for (Map.Entry<ProduitVendu, Integer> produitToSellQuantityEntry : produits.entrySet()) {
                    opv.addRerenceProduit(new LigneCommande(produitToSellQuantityEntry.getKey(),produitToSellQuantityEntry.getValue()));
                }
                int nbArticlesAchetes=0;
                int nbPaliers10Euros=0;
                for (LigneCommande ligneCommande : opv.getProduitsAchetes()) {
                    ProduitCatalogue produit = catalogueProduits.get(ligneCommande.getProduit().getReference());
                    if(produit!=null){
                        stockRepository.enleverDuStock(produit,ligneCommande.getQuantite());
                        nbArticlesAchetes+=ligneCommande.getQuantite();
                        ProduitVendu produitAVendre =null;
                        for (ProduitVendu toSell : produits.keySet()) {
                            if(toSell.getReference().equals(produit.getReference())){
                                produitAVendre =toSell;
                                break;
                            }
                        }
                        cumulCommande = cumulCommande.add(produitAVendre.getPrixPaye().multiply(new BigDecimal(ligneCommande.getQuantite())));
                        while(cumulCommande.compareTo(new BigDecimal("10"))==1) {
                            nbPaliers10Euros+=1;
                            cumulCommande=cumulCommande.subtract(new BigDecimal("10"));
                        }
                    }else {
                        throw new ProduitInconnuException(ligneCommande.getProduit().getReference());
                    }
                }
                ActionRecompensee action01=new ActionRecompensee(clientEmail,opv.getReference(),nbArticlesAchetes*1,new DateTime(), ActionRecompensee.TypeRecompense.ARTICLE);
                fideliteDao.save(action01);
                transactionRepository.save(opv);

                ActionRecompensee action02=new ActionRecompensee(clientEmail,opv.getReference(),nbPaliers10Euros*2,new DateTime(), ActionRecompensee.TypeRecompense.PALIER_COMMANDE);
                fideliteDao.save(action02);

            } else{
                Commande opv = new Commande(clientEmail);
                // on ne peut retourner qu'un seul produit
                Map.Entry<ProduitVendu, Integer> produitRetourne = produits.entrySet().iterator().next();
                opv.addRerenceProduit(new LigneCommande(produitRetourne.getKey(),produitRetourne.getValue()));
                for (LigneCommande ligneCommande : opv.getProduitsAchetes()) {
                    ProduitCatalogue produit = catalogueProduits.get(ligneCommande.getProduit().getReference());

                    if(produit!=null){
                        stockRepository.ajouterAuStock(produit,ligneCommande.getQuantite());
                    }else {
                        throw new ProduitInconnuException(ligneCommande.getProduit().getReference());
                    }
                }
                transactionRepository.save(opv);
            }

    }
}
