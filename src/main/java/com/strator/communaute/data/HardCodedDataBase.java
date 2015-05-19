package com.strator.communaute.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.strator.communaute.catalogue.model.CategorieProduit;
import com.strator.communaute.catalogue.model.ProduitCatalogue;
import com.strator.communaute.client.model.AccountType;
import com.strator.communaute.client.model.Client;
import com.strator.communaute.fidelite.model.ActionRecompensee;
import com.strator.communaute.utils.commerce.Prix;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.strator.communaute.catalogue.model.CategorieProduit.*;

/**
 * Les données de l'appli stubbée
 */
public class HardCodedDataBase {


    private static Map<String,ProduitCatalogue> produitCatalogues = Maps.newConcurrentMap();
    private static Map<String,Integer> stockProduits = Maps.newConcurrentMap();
    private static List<ActionRecompensee> actionsFidelite = Lists.newArrayList();
    private static Map<String,Client> clients = Maps.newConcurrentMap();


    static{

        add(produit("71e8e7e5c52a45f682cd23021931d4ea", "Durex feeling sensual x10", 1045, true, 400, PRESERVATIF),0);
        add(produit("b8ec935decba42bfb4551b357ef04be1", "Durex Play", 750, true, 150, PRESERVATIF),5);
        add(produit("fc7e3ef376054109ac34db53ec56961e", "Doliprane", 790, true, 100, MEDICAMENT_NON_REMBOURSABLE),6);
        add(produit("2ed4a086cee94a8496816d11b9790916", "Efferalgan", 860, true, 90, MEDICAMENT_NON_REMBOURSABLE),3);
        add(produit("113346dde361414fb2cdc7d41fc6805f", "Actiranox", 1050, false, 321, MEDICAMENT_REMBOURSABLE),2);
        add(produit("dde361414fb2cd113346c7341fc6805f", "Colgate fraisheur Plus", 265, false, 132, HYGIENE_DENTAIRE),10);
        add(produit("61645fb2113346dde3cdc7d41fc6805f", "Bequilles", 5050, false, 3000, EQUIPEMENT_PERSONNE_DEPENDANTE),5);
        add(produit("1fc6846dde361645fb2cd05f1133c7d4", "Se soigner avec les plantes (des pieds)", 1050, true, 400, LIVRE),2);
        add(produit("8e5cfaee57c942a8b8304bfdb68b6014", "Pastilles vichy", 1260, true, 705, CONFISERIE),9);

        clients.put(createInactifUser().getEmail(),createInactifUser());
        clients.put(createStandartClient().getEmail(),createStandartClient());
        clients.put(createVipUser().getEmail(),createVipUser());
    }

    private static void add(ProduitCatalogue produit, int stock) {
        produitCatalogues.put(produit.getReference(), produit);
        stockProduits.put(produit.getReference(),stock);
    }

    private static ProduitCatalogue produit(String reference, String libelle, long prixAchatCentime, boolean actif, long marge, CategorieProduit categorie) {
       return new ProduitCatalogue(reference,libelle,new Prix(prixAchatCentime),actif,marge,categorie);
    }

    public static List<ProduitCatalogue> getProduits() {
        wait_for_connection();
        return new ArrayList(produitCatalogues.values());
    }

    public static ProduitCatalogue getProduit(String reference) {
        wait_for_connection();
        return produitCatalogues.get(reference);
    }

    public static void changeStock(String referenceProduit,int newStock){
        wait_for_connection();
        stockProduits.put(referenceProduit,newStock);
    }

    public static Integer getStock(String reference){
        return stockProduits.get(reference);
    }

    public static Client getClient(String email){
        return clients.get(email);
    }

    private static void wait_for_connection() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static void wait_for_connection(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static Client createStandartClient() {
        Client client = new Client("normal@monsite.fr","LeGrand","Paul", AccountType.STANDART, new DateTime().minusDays(8));
        return client;
    }
    private static Client createVipUser() {
        Client client = new Client("platinium@monsite.fr","Lagare","Jean", AccountType.PLATINIUM, new DateTime().minusDays(4));
        return client;
    }

    private static Client createInactifUser() {
        Client client = new Client("inactif@monsite.fr","Durand","Marc", AccountType.STANDART);
        return client;
    }


}
