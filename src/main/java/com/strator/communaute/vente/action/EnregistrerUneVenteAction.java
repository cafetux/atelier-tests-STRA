package com.strator.communaute.vente.action;

import com.strator.communaute.client.exception.UserInactifException;
import com.strator.communaute.client.model.Client;
import com.strator.communaute.client.repository.ClientsMagasin;
import com.strator.communaute.vente.model.OperationDeVente;
import com.strator.communaute.vente.model.ProduitToSell;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Permet d'enregistrer une vente effectuée par un client
 */
public class EnregistrerUneVenteAction {

    @Autowired
    private ClientsMagasin clientsMagasin;

    @Autowired

    private TransactionRepository transactionRepository;
    /**
     * Permet d'enregistrer une vente
     * @param clientEmail l'identifiant (email) du client ayant effectué la vente
     * @param produits les produits vendus
     */
    public void enregistrerUneVente(String clientEmail,List<ProduitToSell> produits){

        Client client = clientsMagasin.retrieveByEmail(clientEmail);
        if(!client.isActif()){
            throw new UserInactifException(client);
        }

        OperationDeVente opv= new OperationDeVente(clientEmail);

        for (int i = produits.size(); i > 0 ; i--) {
            opv.addRerenceProduit(produits.get(i).getReference());
        }

        transactionRepository.save(opv);
    }
}
