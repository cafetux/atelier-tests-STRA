package com.strator.communaute.vente_de_produits.action;

import com.strator.communaute.client.exception.UserInactifException;
import com.strator.communaute.test_utils.CleanRepository;
import com.strator.communaute.test_utils.CleanRepositoryListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/context.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,CleanRepositoryListener.class})
public class RetrieveProduitsToSellActionSpringTest {

    @Autowired
    private RetrieveProduitsToSellAction testedObject;


    @Test(expected = UserInactifException.class)
    @CleanRepository
    public void should_reject_client_when_is_inactive() {
        testedObject.retrieveAllFor("inactif@monsite.fr");
    }

}