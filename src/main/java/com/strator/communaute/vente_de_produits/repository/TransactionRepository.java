package com.strator.communaute.vente_de_produits.repository;

import com.strator.communaute.vente_de_produits.model.Commande;
import org.springframework.stereotype.Repository;

/**
 * Created by cafetux on 12/05/2015.
 */
@Repository
public class TransactionRepository {


    public void save(Commande operationDeVente){
         System.out.println(operationDeVente);
    }

}
