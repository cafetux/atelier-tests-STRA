package com.strator.communaute.run;

import com.strator.communaute.vente_de_produits.action.EnregistrerUneVenteAction;
import com.strator.communaute.vente_de_produits.action.RetrieveProduitsToSellAction;
import com.strator.communaute.vente_de_produits.model.ProduitToSell;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cafetux on 13/05/2015.
 */
public class Main {

    public static void main (String [] arg){
        System.out.println(Arrays.toString(arg));

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");

        // instantiate our spring dao object from the application context
        EnregistrerUneVenteAction enregistrerUneVenteAction = (EnregistrerUneVenteAction)ctx.getBean("enregistrerUneVenteAction");
        RetrieveProduitsToSellAction retrieveProduitsToSellAction = (RetrieveProduitsToSellAction)ctx.getBean("retrieveProduitsToSellAction");
        if(arg[0].equals("GET")) {
            for (ProduitToSell produitToSell : retrieveProduitsToSellAction.retrieveAllFor(arg[1])) {
                System.out.println(produitToSell);
            }
        } else if(arg[0].equals("PUT")) {
            Map<ProduitToSell,Integer> products = new HashMap<ProduitToSell, Integer>();

            for (String row : arg[2].split(",")) {
                String reference = row.substring(0, row.lastIndexOf("x"));
                Integer quantity = Integer.valueOf(row.substring(row.lastIndexOf("x")+1));
                ProduitToSell p = new ProduitToSell(reference,null,null,null,null);
                products.put(p,quantity);
            }
            enregistrerUneVenteAction.enregistrerUneVenteOuUnRetour(arg[1], products,false);
        } else if(arg[0].equals("")) {
            Map<ProduitToSell,Integer> products = new HashMap<ProduitToSell, Integer>();
            String reference = arg[2];
            Integer quantity = Integer.valueOf(arg[3]);
            ProduitToSell p = new ProduitToSell(reference,null,null,null,null);
            products.put(p,quantity);
            enregistrerUneVenteAction.enregistrerUneVenteOuUnRetour(arg[1], products,true);
        }
    }
}
