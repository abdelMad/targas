package com.targas.erp.schedulers;

import com.targas.erp.dao.IAnneeScolaireRepo;
import com.targas.erp.models.AnneeScolaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class AddAnneeScolaireScheduler {

    @Autowired
    private IAnneeScolaireRepo iAnneeScolaireRepo;

    @Scheduled(cron = "0 0 0 05 02 ?")
    public void process() {
        try {
            int currentYear = LocalDate.now().getYear();
            AnneeScolaire anneeScolaire = new AnneeScolaire();
            anneeScolaire.setNomAnneeScolaire(currentYear+"/"+(currentYear+1));
            iAnneeScolaireRepo.save(anneeScolaire);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
