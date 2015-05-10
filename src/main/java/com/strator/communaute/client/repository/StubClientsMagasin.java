package com.strator.communaute.client.repository;

import com.strator.communaute.client.model.AccountType;
import com.strator.communaute.client.model.Client;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

/**
 * Un stub renvoyant des clients prédéfinis
 */
@Repository
public class StubClientsMagasin implements ClientsMagasin{


    @Override
    public Client retrieveByEmail(String email) {
        if(email.contains("inactif")){
            return inactifUser();
        }
        if(email.contains("platinium")){
            return vipUser();
        }
        return standartClient();
    }

    private Client standartClient() {
        Client client = new Client("normal@monsite.fr","LeGrand","Paul", AccountType.STANDART, new DateTime().minusDays(8));
        return client;
    }
    private Client vipUser() {
        Client client = new Client("platinium@monsite.fr","Lagare","Jean", AccountType.PLATINIUM, new DateTime().minusDays(4));
        return client;
    }

    private Client inactifUser() {
        Client client = new Client("inactif@monsite.fr","Durand","Marc", AccountType.STANDART);
        return client;
    }
}
