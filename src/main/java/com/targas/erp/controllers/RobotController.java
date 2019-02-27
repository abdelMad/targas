    package com.targas.erp.controllers;

    import com.targas.erp.dao.*;
    import com.targas.erp.models.*;
    import com.targas.erp.utilities.Util;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    import java.sql.*;
    import java.util.*;

    @Controller
    public class RobotController {

        @Autowired
        private IEtudiantRepo iEtudiantRepo;
        @Autowired
        private IEnseignantRepo iEnseignantRepo;
        @Autowired
        private IMbrScolariteRepo iMbrScolariteRepo;
        @Autowired
        private IEtablissementRepo iEtablissementRepo;
        @Autowired
        private ISalleRepo iSalleRepo;

        @Autowired
        private IUtilisateurRepo Iu;

        @GetMapping("/robot")
        public String renderUtilisateurPage(Model model) {
            return "robot/robot";
        }

        @PostMapping("/robot")
        public  String ajoutData() {
            ArrayList<String> prenom = new ArrayList<>();
            prenom.add("Madjid");
            prenom.add("Abdel");
            prenom.add("Abdelmoghit");
            prenom.add("Bunyamin");
            prenom.add("Ousemma");
            prenom.add("Camélia");
            prenom.add("Dan");
            prenom.add("Samia");
            prenom.add("Dyhia");
            prenom.add("Tao");
            prenom.add("Jean");


            //d'apres le cahier de specification - section "Exigences relatives aux tests"
            int nbEtudiant = 4800;
            int nbEnseignant = 320;
            int nbStaff = 30;
            int nbSalle = 100; // salle manufacture

            Random rand = new Random();
            int randomNum;
            String mdp;

            for(int i=0;i<nbEtudiant;i++) {
                Etudiant etudiant = new Etudiant();
                randomNum = rand.nextInt((prenom.size() - 1 - 0) + 1) + 0;
                mdp = Util.generateUniqueToken();
                etudiant.setMdp(Util.hashString(mdp));
                etudiant.setEmail(etudiant.generateName() + "@" + etudiant.generateName() + ".dz");
                etudiant.setAdresse(randomNum + " rue de " + etudiant.generateName());
                etudiant.setNom(etudiant.generateName());
                etudiant.setPrenom(prenom.get(randomNum));
                etudiant.setIdentifiant(etudiant.generateIdentifiant());
                iEtudiantRepo.save(etudiant);
            }

            for(int i=0;i<nbEnseignant;i++){
                Enseignant prof = new Enseignant();
                randomNum = rand.nextInt((prenom.size() - 1 - 0) + 1) + 0;
                mdp = Util.generateUniqueToken();
                prof.setMdp(Util.hashString(mdp));
                prof.setEmail(prof.generateName() + "@" + prof.generateName() + ".fr");
                prof.setAdresse(randomNum + " rue de " + prof.generateName());
                prof.setNom(prof.generateName());
                prof.setPrenom(prenom.get(randomNum));
                prof.setIdentifiant(prof.generateIdentifiant());
                iEnseignantRepo.save(prof);
            }

            for(int i=0;i<nbStaff;i++){
                MbrScolarite staff = new MbrScolarite();
                randomNum = rand.nextInt((prenom.size() - 1 - 0) + 1) + 0;
                mdp = Util.generateUniqueToken();
                staff.setMdp(Util.hashString(mdp));
                staff.setEmail(staff.generateName() + "@" + staff.generateName() + ".biz");
                staff.setAdresse(randomNum + " rue de " + staff.generateName());
                staff.setNom(staff.generateName());
                staff.setPrenom(prenom.get(randomNum));
                staff.setIdentifiant(staff.generateIdentifiant());
                iMbrScolariteRepo.save(staff);
            }

            Etablissement e = new Etablissement();
            e.setNom("Manufacture");
            e.setAdresse("rue du Professeur Benoit Lauras");
            iEtablissementRepo.save(e);

            for(int i=0;i<nbSalle;i++){
                Salle salle = new Salle();
                salle.setEtablissement(e);
                salle.setNom("Salle "+i);
                salle.setCapacite(45);
                salle.setNbrPC(10);
                iSalleRepo.save(salle);
            }


            return "robot/robot";
        }
    }