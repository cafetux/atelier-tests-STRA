package com.strator.communaute.vente_de_produits.converter;

import com.strator.communaute.catalogue.model.ProduitCatalogue;
import com.strator.communaute.client.model.AccountType;
import com.strator.communaute.client.model.Client;
import com.strator.communaute.taxes.ITvaService;
import com.strator.communaute.utils.commerce.Prix;
import com.strator.communaute.utils.math.Percentage;
import com.strator.communaute.vente_de_produits.model.ProduitAVendre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by fmaury on 20/05/15.
 */
@Component
public class ProduitCatalogueToProduitAVendre {

    @Autowired
    private ITvaService tvaService;

    public ProduitAVendre convert(Client client, ProduitCatalogue produitCatalogue){
            Prix prixDeVenteHT =produitCatalogue.getPrixAchat().plus(produitCatalogue.getMarge());
            Percentage tvaRate = tvaService.getTvaRate(produitCatalogue.getCategorieProduit());
            Prix prixDeVenteTTC = prixDeVenteHT.increaseBy(tvaRate);
            double discountInPercentage = 0.0;
            if(client.getAccountType()== AccountType.PLATINIUM){
                discountInPercentage+=5;
            }
            Prix prixAPayer = prixDeVenteTTC.decreaseBy(new Percentage(discountInPercentage));
            return new ProduitAVendre(produitCatalogue.getReference(), produitCatalogue.getLibelle(), prixDeVenteHT, prixDeVenteTTC, prixAPayer);
    }
}
