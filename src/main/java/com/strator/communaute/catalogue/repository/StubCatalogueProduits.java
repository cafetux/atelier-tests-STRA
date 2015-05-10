package com.strator.communaute.catalogue.repository;

import com.strator.communaute.catalogue.model.CategorieProduit;
import com.strator.communaute.utils.commerce.Prix;
import com.strator.communaute.catalogue.model.ProduitCatalogue;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

import static com.strator.communaute.catalogue.model.CategorieProduit.*;

/**
 * Repository des clients
 */
@Repository
public class StubCatalogueProduits implements CatalogueProduits {


    private List<ProduitCatalogue> produitCatalogues = Arrays.asList(
            produit("71e8e7e5c52a45f682cd23021931d4ea","Durex feeling sensual x10",1045,true,400, PRESERVATIF),
            produit("b8ec935decba42bfb4551b357ef04be1","Durex Play",750,true,150, PRESERVATIF),
            produit("fc7e3ef376054109ac34db53ec56961e","Doliprane",790,true,100, MEDICAMENT_NON_REMBOURSABLE),
            produit("2ed4a086cee94a8496816d11b9790916","Efferalgan",860,true,90, MEDICAMENT_NON_REMBOURSABLE),
            produit("113346dde361414fb2cdc7d41fc6805f","Actiranox",1050,false,321, MEDICAMENT_REMBOURSABLE),
            produit("dde361414fb2cd113346c7341fc6805f","Colgate fraisheur Plus",265,false,132, HYGIENE_DENTAIRE),
            produit("61645fb2113346dde3cdc7d41fc6805f","Bequilles",5050,false,3000, EQUIPEMENT_PERSONNE_DEPENDANTE),
            produit("1fc6846dde361645fb2cd05f1133c7d4","Se soigner avec les plantes (des pieds)",1050,true,400, LIVRE),
            produit("8e5cfaee57c942a8b8304bfdb68b6014","Pastilles vichy",1260,true,705, CONFISERIE)
            );

    private ProduitCatalogue produit(String reference, String libelle, long prixAchatCentime, boolean actif, long marge, CategorieProduit categorie) {
        return new ProduitCatalogue(reference,libelle,new Prix(prixAchatCentime),actif,marge,categorie);
    }

    @Override
    public List<ProduitCatalogue> retrieveAll(){
        return produitCatalogues;
    }

}
