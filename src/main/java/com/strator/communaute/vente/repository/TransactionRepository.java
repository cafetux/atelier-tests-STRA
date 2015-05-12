package com.strator.communaute.vente.repository;

import com.strator.communaute.vente.model.OperationDeVente;
import org.springframework.stereotype.Repository;

/**
 * Created by cafetux on 12/05/2015.
 */
@Repository
public class TransactionRepository {


    public void save(OperationDeVente operationDeVente){
         System.out.println(operationDeVente);
    }

}
