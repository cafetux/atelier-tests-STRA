package com.strator.communaute.catalogue.repository;

import com.strator.communaute.utils.commerce.Prix;
import com.strator.communaute.catalogue.model.ProduitCatalogue;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Repository des clients
 */
@Repository
public class StubCatalogueProduits implements CatalogueProduits {


    private List<ProduitCatalogue> produitCatalogues = Arrays.asList(
            produit("71e8e7e5c52a45f682cd23021931d4ea","Bougie Wengol rouge",445,true,300),
            produit("b8ec935decba42bfb4551b357ef04be1","Bougie Wengol verte",600,true,200),
            produit("fc7e3ef376054109ac34db53ec56961e","Raspberry pi model B",3590,true,1000),
            produit("2ed4a086cee94a8496816d11b9790916","Raspberry pi 2",3660,true,900),
            produit("113346dde361414fb2cdc7d41fc6805f","Cable ethernet",300,false,250),
            produit("8e5cfaee57c942a8b8304bfdb68b6014","Boitier Raspberry 2 noir",1260,true,705)
            );

    private ProduitCatalogue produit(String reference, String libelle, long prixAchatCentime,boolean actif,long marge) {
        return new ProduitCatalogue(reference,libelle,new Prix(prixAchatCentime),actif,marge);
    }

    @Override
    public List<ProduitCatalogue> retrieveAll(){
        return produitCatalogues;
    }

}
