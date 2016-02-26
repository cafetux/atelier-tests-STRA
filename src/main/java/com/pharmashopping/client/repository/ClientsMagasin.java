package com.pharmashopping.client.repository;

import com.pharmashopping.client.model.Client;

/**
 * Manipule les clients du Magasin
 */
public interface ClientsMagasin {


    Client retrieveByEmail(String email);

}
