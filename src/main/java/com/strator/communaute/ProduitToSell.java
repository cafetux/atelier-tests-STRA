package com.strator.communaute;

/**
 * Un produit à vendre
 */
public class ProduitToSell {


    /**
     * le référence du produit en magasin
     */
    private String reference;
    /**
     *  la dénomination commerciale du produit
     */
    private String libelle;
    /**
     * le prix de vente hors taxe du produit
     */
    private Prix prixHT;
    /**
     * le prix de vente (taxes incluses) du produit
     */
    private Prix prixTTC;

    /**
     *  La remise à appliquer sur le prix de vente TTC du produit
     */
    private Prix remiseSurLeTTC;



    public String getReference() {
        return reference;
    }

    public String getLibelle() {
        return libelle;
    }

    public Prix getPrixHT() {
        return prixHT;
    }

    public Prix getPrixTTC() {
        return prixTTC;
    }

    public Prix getRemiseSurLeTTC() {
        return remiseSurLeTTC;
    }
}
