package com.strator.communaute.vente.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cafetux on 12/05/2015.
 */
public class OperationDeVente {

    private String clientEmail;
    private LocalDateTime dateDeLaTransaction;
    private List<String> produitsAchetes = new ArrayList<String>();

    public OperationDeVente(String clientEmail){
        this.clientEmail=clientEmail;
        this.dateDeLaTransaction = LocalDateTime.now();
    }

    public void addRerenceProduit(String reference){
        this.produitsAchetes.add(reference);
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public LocalDateTime getDateDeLaTransaction() {
        return dateDeLaTransaction;
    }

    public List<String> getProduitsAchetes() {
        return produitsAchetes;
    }

    @Override
    public String toString() {
        return "OperationDeVente{" +
                "clientEmail='" + clientEmail + '\'' +
                ", dateDeLaTransaction=" + dateDeLaTransaction +
                ", produitsAchetes=" + produitsAchetes +
                '}';
    }
}
