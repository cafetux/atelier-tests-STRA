package com.pharmashopping.catalogue.repository;

import com.pharmashopping.catalogue.model.ProduitCatalogue;
import com.pharmashopping.data.HardCodedDataBase;
import com.pharmashopping.data.IDataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository des clients
 */
@Repository
public class StubCatalogueProduits implements CatalogueProduits {

    private IDataBase database;

    public StubCatalogueProduits(){
        database = new HardCodedDataBase();
    }

    @Override
    public List<ProduitCatalogue> retrieveAll() {
        return database.getProduits();
    }

    @Override
    public ProduitCatalogue get(String reference) {
        return database.getProduit(reference);
    }

}
