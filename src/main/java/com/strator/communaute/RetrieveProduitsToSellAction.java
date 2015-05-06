package com.strator.communaute;

import com.strator.communaute.catalogue.ProduitCatalogue;
import com.strator.communaute.catalogue.repository.CatalogueProduits;
import com.strator.communaute.client.UserInactifException;

import java.util.ArrayList;
import java.util.List;

/**
 * Action pour retrouver les produits à vendre
 */
public class RetrieveProduitsToSellAction {

    private CatalogueProduits produitsRepository;

    public List<ProduitToSell> retrieveAllFor(Client client) {

        if(!client.isActif()){
            throw new UserInactifException(client);
        }

        List<ProduitCatalogue> catalogue = produitsRepository.retrieveAll();

        for (ProduitCatalogue produitCatalogue : catalogue) {
            if(produitCatalogue.isActif()){
                Prix prixDeVenteHT =produitCatalogue.getPrixAchat().plus(produitCatalogue.getMarge());

                double discount = 0.0;
                if(client.getAccountType()==AccountType.PLATINIUM){
                    discount+=10;
                }
            }
        }

        return new ArrayList<ProduitToSell>();
    }


}
