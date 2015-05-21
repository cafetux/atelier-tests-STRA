package com.strator.communaute.taxes;

import com.strator.communaute.catalogue.model.CategorieProduit;
import com.strator.communaute.utils.math.Percentage;

/**
 * Created by fmaury on 20/05/15.
 */
public interface ITvaService {
    Percentage getTvaRate(CategorieProduit produitCatalogue);
}
