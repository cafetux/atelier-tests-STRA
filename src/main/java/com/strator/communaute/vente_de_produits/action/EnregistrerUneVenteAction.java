package com.strator.communaute.vente_de_produits.action;

import com.strator.communaute.catalogue.exception.ProduitInconnuException;
import com.strator.communaute.catalogue.model.ProduitCatalogue;
import com.strator.communaute.catalogue.repository.CatalogueProduits;
import com.strator.communaute.client.exception.UserInactifException;
import com.strator.communaute.client.model.Client;
import com.strator.communaute.client.repository.ClientsMagasin;
import com.strator.communaute.stocks_produits.repository.StockRepository;
import com.strator.communaute.vente_de_produits.model.Commande;
import com.strator.communaute.vente_de_produits.model.LigneCommande;
import com.strator.communaute.vente_de_produits.model.ProduitToSell;
import com.strator.communaute.vente_de_produits.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    /**
     * Permet d'enregistrer une vente
     * @param clientEmail l'identifiant (email) du client ayant effectué la vente
     * @param produits les produits vendus
     */
    public void enregistrerUneVenteOuUnRetour(String clientEmail, Map<ProduitToSell,Integer> produits, boolean retour){
            Client client = clientsMagasin.retrieveByEmail(clientEmail);
            if (!client.isActif()) {
                throw new UserInactifException(client);
            }

            if (!retour) {
                Commande opv = new Commande(clientEmail);
                for (Map.Entry<ProduitToSell, Integer> produitToSellQuantityEntry : produits.entrySet()) {
                    opv.addRerenceProduit(new LigneCommande(produitToSellQuantityEntry.getKey().getReference(),produitToSellQuantityEntry.getValue()));
                }
                for (LigneCommande ligneCommande : opv.getProduitsAchetes()) {
                    ProduitCatalogue produit = catalogueProduits.get(ligneCommande.getReference());
                    if(produit!=null){
                        stockRepository.enleverDuStock(produit,ligneCommande.getQuantite());
                    }else {
                        throw new ProduitInconnuException(ligneCommande.getReference());
                    }
                }
                transactionRepository.save(opv);

            } else{
                Commande opv = new Commande(clientEmail);
                // on ne peut retourner qu'un seul produit
                Map.Entry<ProduitToSell, Integer> produitRetourne = produits.entrySet().iterator().next();
                opv.addRerenceProduit(new LigneCommande(produitRetourne.getKey().getReference(),produitRetourne.getValue()));
                for (LigneCommande ligneCommande : opv.getProduitsAchetes()) {
                    ProduitCatalogue produit = catalogueProduits.get(ligneCommande.getReference());

                    if(produit!=null){
                        stockRepository.ajouterAuStock(produit,ligneCommande.getQuantite());
                    }else {
                        throw new ProduitInconnuException(ligneCommande.getReference());
                    }
                }
                transactionRepository.save(opv);
            }

    }
}
