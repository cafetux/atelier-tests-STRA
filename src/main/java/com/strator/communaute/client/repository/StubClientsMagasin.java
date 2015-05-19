package com.strator.communaute.client.repository;

import com.strator.communaute.client.model.Client;
import com.strator.communaute.data.HardCodedDataBase;
import org.springframework.stereotype.Repository;

/**
 * Un stub renvoyant des clients prédéfinis
 */
@Repository
public class StubClientsMagasin implements ClientsMagasin{


    @Override
    public Client retrieveByEmail(String email) {
        wait_for_connection();
        return HardCodedDataBase.getClient(email);
    }

    private void wait_for_connection() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
