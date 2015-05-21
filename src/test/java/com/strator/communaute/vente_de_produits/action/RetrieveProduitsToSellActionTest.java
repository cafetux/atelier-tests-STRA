package com.strator.communaute.vente_de_produits.action;

import com.strator.communaute.catalogue.model.ProduitCatalogue;
import com.strator.communaute.catalogue.repository.CatalogueProduits;
import com.strator.communaute.client.exception.UserInactifException;
import com.strator.communaute.client.model.AccountType;
import com.strator.communaute.client.model.Client;
import com.strator.communaute.client.repository.ClientsMagasin;
import com.strator.communaute.vente_de_produits.converter.ProduitCatalogueToProduitAVendre;
import com.strator.communaute.vente_de_produits.model.ProduitAVendre;
import org.assertj.core.api.Condition;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.easymock.EasyMockUnitils;
import org.unitils.easymock.annotation.Mock;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class RetrieveProduitsToSellActionTest {

    public static final Condition<ProduitCatalogue> ACTIF = new Condition<ProduitCatalogue>() {
        @Override
        public boolean matches(ProduitCatalogue value) {
            return value.isActif() && value.getReference().equals("activeProduct");
        }
    };
    @TestedObject
    private RetrieveProduitsToSellAction testedObject;

    @Mock
    @InjectIntoByType
    private ClientsMagasin clientMagasin;

    @Mock
    @InjectIntoByType
    private CatalogueProduits catalogueProduits;

    @Mock
    @InjectIntoByType
    private ProduitCatalogueToProduitAVendre converter;

    private Capture<ProduitCatalogue> produitsConvertis = new Capture<ProduitCatalogue>();
    private List<ProduitAVendre> produitAVendres;

    @Test(expected = UserInactifException.class)
    public void should_reject_client_when_is_inactive() {

        EasyMock.expect(clientMagasin.retrieveByEmail("muchmuch@gmail.com")).andReturn(inactiveUser());
        EasyMockUnitils.replay();
        testedObject.retrieveAllFor("muchmuch@gmail.com");
    }

    private Client inactiveUser() {
        Client client = new Client("muchmuch@gmail.com","","", AccountType.STANDARD);
        return client;
    }

    @Test
    public void should_only_return_active_product() {
        for_any_client();
        given_list_with_inactive_product();

        when_retrieve_product();
        then_we_have_only_active_product();

    }

    private void then_we_have_only_active_product() {
        assertThat(produitAVendres).as("on ne doit retrouver que 2 produits").hasSize(2);
        for (ProduitCatalogue produitCatalogue : produitsConvertis.getValues()) {
            assertThat(produitCatalogue).as("Seuls les produits actifs doivent être convertis").is(ACTIF);
        }
    }

    private void when_retrieve_product() {
        EasyMock.expect(converter.convert(anyObject(Client.class), capture(produitsConvertis))).andStubReturn(anyProduct());

        EasyMockUnitils.replay();


        produitAVendres = testedObject.retrieveAllFor("muchmuch@gmail.com");
    }

    private void for_any_client() {
        EasyMock.expect(clientMagasin.retrieveByEmail(anyObject(String.class))).andStubReturn(activeUser());
    }

    private void given_list_with_inactive_product() {
        ArrayList<ProduitCatalogue> produitCatalogues = new ArrayList<ProduitCatalogue>();

        produitCatalogues.add(activeProduct());
        produitCatalogues.add(inactiveProduct());
        produitCatalogues.add(activeProduct());

        EasyMock.expect(catalogueProduits.retrieveAll()).andReturn(produitCatalogues);
    }

    private ProduitAVendre anyProduct() {
        return new ProduitAVendre("", "", null, null, null);
    }

    private ProduitCatalogue inactiveProduct() {
        return new ProduitCatalogue("inactiveProduct", "", null, false, 0, null);
    }

    private ProduitCatalogue activeProduct() {
        return new ProduitCatalogue("activeProduct", "", null, true, 0, null);
    }

    private Client activeUser() {
        return new Client("muchmuch@gmail.com","","", AccountType.STANDARD, new DateTime());
    }

}