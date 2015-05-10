package com.strator.communaute.client.repository;

import com.strator.communaute.client.model.Client;

/**
 * Manipule les clients du Magasin
 */
public interface ClientsMagasin {


    Client retrieveByEmail(String email);

}
