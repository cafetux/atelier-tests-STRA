package com.strator.communaute.vente_de_produits.action;

import com.strator.communaute.catalogue.model.ProduitCatalogue;
import com.strator.communaute.catalogue.repository.CatalogueProduits;
import com.strator.communaute.client.exception.UserInactifException;
import com.strator.communaute.client.model.Client;
import com.strator.communaute.client.repository.ClientsMagasin;
import com.strator.communaute.vente_de_produits.converter.ProduitCatalogueToProduitAVendre;
import com.strator.communaute.vente_de_produits.model.ProduitAVendre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Action pour retrouver les produits à vendre
 */
@Component
public class RetrieveProduitsToSellAction {

    @Autowired
    private CatalogueProduits produitsRepository;

    @Autowired
    private ClientsMagasin clientsMagasin;

    @Autowired
    private ProduitCatalogueToProduitAVendre converter;

    /**
     * Renvoie les produits à vendre et leurs caractéristiques (pour un client donné)
     * @param clientEmail l'email de l'utilisateur (qui l'identifie)
     * @return la liste des produits à vendre
     */
    public List<ProduitAVendre> retrieveAllFor(String clientEmail) {

        Client client = clientsMagasin.retrieveByEmail(clientEmail);
        if(!client.isActif()){
            throw new UserInactifException(client);
        }

        List<ProduitCatalogue> catalogue = produitsRepository.retrieveAll();

        ArrayList<ProduitAVendre> produitAVendres = new ArrayList<ProduitAVendre>();
        for (ProduitCatalogue produitCatalogue : catalogue) {
            if(produitCatalogue.isActif()){
                ProduitAVendre produitAVendre = converter.convert(client, produitCatalogue);
                produitAVendres.add(produitAVendre);
            }
        }

        return produitAVendres;
    }





}
