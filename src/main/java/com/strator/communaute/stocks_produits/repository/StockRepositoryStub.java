package com.strator.communaute.stocks_produits.repository;

import com.strator.communaute.catalogue.model.ProduitCatalogue;
import com.strator.communaute.data.HardCodedDataBase;
import org.springframework.stereotype.Repository;

/**
 * Interractions avec la couche persistence des stocks
 */
@Repository
public class StockRepositoryStub implements StockRepository {

    @Override
    public void ajouterAuStock(ProduitCatalogue produit, int quantite){
        System.out.println("Stock: Ajout de " + quantite + " "+produit);
        int stock=HardCodedDataBase.getStock(produit.getReference());
        int newStock = stock + quantite;
        HardCodedDataBase.changeStock(produit.getReference(), newStock);

        System.out.println("Stock: stock mis à jour pour le produit " +produit +" : "+stock+quantite);
    }

    @Override
    public void enleverDuStock(ProduitCatalogue produit, int quantite){
        System.out.println("Stock: On enleve " + quantite + " "+produit);
        int stock=HardCodedDataBase.getStock(produit.getReference());
        int newStock = stock - quantite;
        HardCodedDataBase.changeStock(produit.getReference(), newStock);

        System.out.println("Stock: stock mis à jour pour le produit " +produit +" : "+stock+quantite);
    }

}
