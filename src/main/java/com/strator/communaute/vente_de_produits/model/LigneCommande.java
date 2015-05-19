package com.strator.communaute.vente_de_produits.model;

/**
 * Une operation de vente pour un produit donné
 */
public class LigneCommande {

    private String reference;
    private int quantite;

    public LigneCommande(String reference, int quantite) {
        this.reference = reference;
        this.quantite = quantite;
    }

    public String getReference() {
        return reference;
    }

    public int getQuantite() {
        return quantite;
    }

    @Override
    public String toString() {
        return "LigneCommande{" +
                "'" + reference + '\'' +
                " x " + quantite +
                '}';
    }
}
