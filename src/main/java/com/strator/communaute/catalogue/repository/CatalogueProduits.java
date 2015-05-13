package com.strator.communaute.catalogue.repository;

import com.strator.communaute.catalogue.model.ProduitCatalogue;

import java.util.List;

/**
 * Le catalogue de produits
 */
public interface CatalogueProduits {
    /**
     * Permet de retrouver tout le catalogue
     * @return la liste des produits du catalogue
     */
    List<ProduitCatalogue> retrieveAll() ;
}
