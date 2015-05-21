package com.strator.communaute.taxes;

import com.strator.communaute.catalogue.model.CategorieProduit;
import com.strator.communaute.utils.math.Percentage;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TvaServiceTest {

    private TvaService tvaService = new TvaService();

    @Test
    public void preservatif_a_une_tva_reduite() throws Exception {
        Percentage tvaRate = tvaService.getTvaRate(CategorieProduit.PRESERVATIF);
        assertThat(tvaRate).isEqualTo(new Percentage(5.5));
    }

    @Test
    public void confiserie_a_une_tva_normale() throws Exception {
        Percentage tvaRate = tvaService.getTvaRate(CategorieProduit.CONFISERIE);
        assertThat(tvaRate).isEqualTo(new Percentage(20));
    }

    @Test
    public void medicament_non_remboursable_a_une_tva_intermediaire() throws Exception {
        Percentage tvaRate = tvaService.getTvaRate(CategorieProduit.MEDICAMENT_NON_REMBOURSABLE);
        assertThat(tvaRate).isEqualTo(new Percentage(10));
    }

    @Test
    public void medicament_remboursable_a_une_tva_particuliere() throws Exception {
        Percentage tvaRate = tvaService.getTvaRate(CategorieProduit.MEDICAMENT_REMBOURSABLE);
        assertThat(tvaRate).isEqualTo(new Percentage(2.5));
    }
}