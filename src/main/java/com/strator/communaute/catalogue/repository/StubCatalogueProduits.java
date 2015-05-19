package com.strator.communaute.catalogue.repository;

import com.strator.communaute.catalogue.model.ProduitCatalogue;
import com.strator.communaute.data.HardCodedDataBase;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository des clients
 */
@Repository
public class StubCatalogueProduits implements CatalogueProduits {



    @Override
    public List<ProduitCatalogue> retrieveAll() {
        return HardCodedDataBase.getProduits();
    }

    @Override
    public ProduitCatalogue get(String reference) {
        return HardCodedDataBase.getProduit(reference);
    }

}
