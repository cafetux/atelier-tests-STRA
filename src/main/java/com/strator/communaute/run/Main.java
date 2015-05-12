package com.strator.communaute.run;

import com.strator.communaute.vente.action.EnregistrerUneVenteAction;
import com.strator.communaute.vente.action.RetrieveProduitsToSellAction;
import com.strator.communaute.vente.model.ProduitToSell;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        if(arg[0].equals("GET")){
            for (ProduitToSell produitToSell : retrieveProduitsToSellAction.retrieveAllFor(arg[1])) {
                System.out.println(produitToSell);
            }
        }else if(arg[0].equals("PUT")){
            List<ProduitToSell> products = new ArrayList<ProduitToSell>();
            for (String references : arg[2].split(",")) {
                ProduitToSell p = new ProduitToSell(references,null,null,null,null);
                products.add(p);
            }
            enregistrerUneVenteAction.enregistrerUneVente(arg[1],products);
        }
    }
}
