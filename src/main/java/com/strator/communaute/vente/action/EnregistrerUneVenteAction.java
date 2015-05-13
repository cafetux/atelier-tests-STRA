package com.strator.communaute.vente.action;

import com.strator.communaute.client.exception.UserInactifException;
import com.strator.communaute.client.model.Client;
import com.strator.communaute.client.repository.ClientsMagasin;
import com.strator.communaute.vente.model.OperationDeVente;
import com.strator.communaute.vente.model.ProduitToSell;
import com.strator.communaute.vente.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Permet d'enregistrer une vente effectuée par un client
 */
@Component
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
        try {
            Client client = clientsMagasin.retrieveByEmail(clientEmail);
            if (!client.isActif()) {
                throw new UserInactifException(client);
            }

            OperationDeVente opv = new OperationDeVente(clientEmail);

            for (int i = produits.size()-1; i > 0; opv.addRerenceProduit(produits.get(i--).getReference())) {
            }

            transactionRepository.save(opv);
        }catch (Exception e){
            throw null;
        }finally{
            
        }
    }
}
