package com.strator.communaute.vente.action;

import com.strator.communaute.client.repository.ClientsMagasin;
import com.strator.communaute.utils.commerce.Prix;
import com.strator.communaute.catalogue.model.ProduitCatalogue;
import com.strator.communaute.catalogue.repository.CatalogueProduits;
import com.strator.communaute.client.exception.UserInactifException;
import com.strator.communaute.client.model.AccountType;
import com.strator.communaute.client.model.Client;
import com.strator.communaute.utils.math.Percentage;
import com.strator.communaute.vente.model.ProduitToSell;
import com.strator.communaute.vente.model.TvaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.strator.communaute.vente.model.TvaType.*;

/**
 * Action pour retrouver les produits à vendre
 */
@Component
public class RetrieveProduitsToSellAction {

    @Autowired
    private CatalogueProduits produitsRepository;

    @Autowired
    private ClientsMagasin clientsMagasin;

    public List<ProduitToSell> retrieveAllFor(String clientEmail) {

        Client client = clientsMagasin.retrieveByEmail(clientEmail);
        if(!client.isActif()){
            throw new UserInactifException(client);
        }

        List<ProduitCatalogue> catalogue = produitsRepository.retrieveAll();

        ArrayList<ProduitToSell> produitToSells = new ArrayList<ProduitToSell>();
        for (ProduitCatalogue produitCatalogue : catalogue) {
            if(produitCatalogue.isActif()){
                Prix prixDeVenteHT =produitCatalogue.getPrixAchat().plus(produitCatalogue.getMarge());
                TvaType typeTva;
                switch(produitCatalogue.getCategorieProduit()){

                    case PRESERVATIF:
                        typeTva= REDUITE;
                        break;
                    case MEDICAMENT_NON_REMBOURSABLE:
                        typeTva= INTERMEDIAIRE;
                        break;
                    case MEDICAMENT_REMBOURSABLE:
                        typeTva= PARTICULIERE;
                        break;
                    case LIVRE:
                        typeTva= REDUITE;
                        break;
                    case EQUIPEMENT_PERSONNE_DEPENDANTE:
                        typeTva= REDUITE;
                        break;
                    case HYGIENE_DENTAIRE:
                        typeTva= NORMALE;
                        break;
                    case CONFISERIE:
                        typeTva= NORMALE;
                        break;
                    default:
                        throw new IllegalArgumentException("no TVA type found for product of type "+produitCatalogue.getCategorieProduit());
                }
                Percentage tvaRate;
                switch (typeTva){

                    case NORMALE:
                        tvaRate=new Percentage(20);
                        break;
                    case INTERMEDIAIRE:
                        tvaRate=new Percentage(10);
                        break;
                    case REDUITE:
                        tvaRate=new Percentage(5.5);
                        break;
                    case PARTICULIERE:
                        tvaRate=new Percentage(2.5);
                        break;
                    default:
                        throw new IllegalArgumentException("no TVA rate found for this TvaType "+typeTva);
                }
                Prix prixDeVenteTTC = prixDeVenteHT.increaseBy(tvaRate);
                double discountInPercentage = 0.0;
                if(client.getAccountType()== AccountType.PLATINIUM){
                    discountInPercentage+=5;
                }
                Prix prixAPayer = prixDeVenteTTC.decreaseBy(new Percentage(discountInPercentage));
                ProduitToSell produitToSell = new ProduitToSell(produitCatalogue.getReference(),produitCatalogue.getLibelle(),prixDeVenteHT,prixDeVenteTTC,prixAPayer);
                produitToSells.add(produitToSell);
            }
        }

        return produitToSells;
    }


}
