package com.strator.communaute.taxes;

import com.strator.communaute.catalogue.model.CategorieProduit;
import com.strator.communaute.utils.math.Percentage;
import com.strator.communaute.vente_de_produits.model.TvaType;
import org.springframework.stereotype.Service;

import static com.strator.communaute.vente_de_produits.model.TvaType.INTERMEDIAIRE;
import static com.strator.communaute.vente_de_produits.model.TvaType.NORMALE;
import static com.strator.communaute.vente_de_produits.model.TvaType.PARTICULIERE;
import static com.strator.communaute.vente_de_produits.model.TvaType.REDUITE;

@Service
public class TvaService implements ITvaService {

    @Override
    public Percentage getTvaRate(CategorieProduit categorieProduit) {
        TvaType typeTva = getTvaType(categorieProduit);
        return typeTva.getRate();
    }

    private TvaType getTvaType(CategorieProduit categorieProduit) {
        TvaType typeTva;
        switch(categorieProduit){

            case PRESERVATIF:
                typeTva= REDUITE;
                break;
            case MEDICAMENT_NON_REMBOURSABLE:
                typeTva= INTERMEDIAIRE;
                break;
            case MEDICAMENT_REMBOURSABLE:
                typeTva= PARTICULIERE;
                break;
            case LIVRE:
                typeTva= REDUITE;
                break;
            case EQUIPEMENT_PERSONNE_DEPENDANTE:
                typeTva= REDUITE;
                break;
            case HYGIENE_DENTAIRE:
                typeTva= NORMALE;
                break;
            case CONFISERIE:
                typeTva= NORMALE;
                break;
            default:
                throw new IllegalArgumentException("no TVA type found for product of type "+categorieProduit);
        }
        return typeTva;
    }

}
