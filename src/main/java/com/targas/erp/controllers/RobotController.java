    package com.targas.erp.controllers.utilisateurs;

    import com.targas.erp.dao.IEnseignantRepo;
    import com.targas.erp.dao.IEtudiantRepo;
    import com.targas.erp.dao.IMbrScolariteRepo;
    import com.targas.erp.dao.IUtilisateurRepo;
    import com.targas.erp.models.Enseignant;
    import com.targas.erp.models.Etudiant;
    import com.targas.erp.models.MbrScolarite;
    import com.targas.erp.models.Utilisateur;
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
        private IUtilisateurRepo Iu;

        @GetMapping("/robot")
        public String renderUtilisateurPage(Model model) {

            return "robot/robot";

        }

        /*
        public int limite = 0;
        public int limiteP = 0;
        public int limiteS = 0;
        boolean prof = false;
        boolean staffB = false;
        */
        @PostMapping("/robot")
        public  String ajoutData() {

            int nbrEtudiant = 4800;
            int nbrProf = 500;
            int nbrStaff = 100;
            Statement st;
            Connection c;
            ArrayList<String> prenom = new ArrayList<>();
            ArrayList<String> nom = new ArrayList<>();
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
            String url = "jdbc:mysql://localhost/targas?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String identifiant = "root";
            String mdp = "";
            PreparedStatement pst;
            Etudiant etudiant = new Etudiant();

            int err = 0;
            Random rand = new Random();
            int randomNum = rand.nextInt((prenom.size() - 1) + 1);
            try {
                c = DriverManager.getConnection(url, identifiant, mdp);
                st = c.createStatement();
                ResultSet rs2 = st.executeQuery("SELECT COUNT(*) FROM utilisateur");
                int temp = 0;
                if(rs2.next()) {
                    temp = rs2.getInt("COUNT(*)");
                    //System.out.println("count :" + temp);

                }
                pst = c.prepareStatement("insert into utilisateur (adresse, description, email, identifiant, mdp, nom, photo, prenom, groupe_etudiant, dtype, id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, 'Etudiant',?)");

                int titan = 0;
                for (titan = 0; titan < nbrEtudiant; titan++) {
                    try {
                        String nomBDD = etudiant.generateName();
                        String prenomBDD = prenom.get((randomNum+titan)%prenom.size());
                        String mailBDD = nomBDD + "@" + etudiant.generateName() + ".dz";
                        String adresseBDD = (titan%42) + " rue de " + etudiant.generateName();
                        String mdpBDD = Util.hashString(Util.generateUniqueToken());
                        etudiant.setNom(nomBDD);
                        etudiant.setPrenom(prenomBDD);
                        String idBDD = etudiant.generateIdentifiant();
                        pst.setString(1, adresseBDD);
                        pst.setString(2, null);
                        pst.setString(3, mailBDD);
                        pst.setString(4, idBDD);
                        pst.setString(5, mdpBDD);
                        pst.setString(6, nomBDD);
                        pst.setString(7, null);
                        pst.setString(8, prenomBDD);
                        pst.setString(9, null);
                        pst.setString(10, Integer.toString(titan + temp + 1));
                       // System.out.println(pst.toString());
                        int omega = pst.executeUpdate();
                        //System.out.println(omega + " enregistrement(s)");
                    }catch (SQLException s){
                        System.out.println("Erreu sql interne" + s);
                        titan--;
                        err++;
                    }
                }


                Enseignant prof = new Enseignant();

                pst = c.prepareStatement("insert into utilisateur (adresse, description, email, identifiant, mdp, nom, photo, prenom, groupe_etudiant, dtype, id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, 'Enseignant',?)");


                int j;
                for (j=titan; j < nbrEtudiant + nbrProf; j++) {
                    try {
                        String prenomBDD = prof.generateName();
                        String nomBDD = prenom.get((randomNum+j)%prenom.size());
                        String mailBDD = nomBDD + "@" + prof.generateName() + ".fr";
                        String adresseBDD = (j%42) + " avenue de " + etudiant.generateName();
                        String mdpBDD = Util.hashString(Util.generateUniqueToken());
                        prof.setNom(nomBDD);
                        prof.setPrenom(prenomBDD);
                        String idBDD = prof.generateIdentifiant();
                        pst.setString(1, adresseBDD);
                        pst.setString(2, null);
                        pst.setString(3, mailBDD);
                        pst.setString(4, idBDD);
                        pst.setString(5, mdpBDD);
                        pst.setString(6, nomBDD);
                        pst.setString(7, null);
                        pst.setString(8, prenomBDD);
                        pst.setString(9, null);
                        pst.setString(10, Integer.toString(j + temp + 1));
                        //System.out.println(pst.toString());
                        int omega = pst.executeUpdate();
                        //System.out.println(omega + " enregistrement(s)");
                    }catch (SQLException s){
                        System.out.println("Erreu sql interne" + s);
                        j--;
                        err++;
                    }
                }


                MbrScolarite staff = new MbrScolarite();

                pst = c.prepareStatement("insert into utilisateur (adresse, description, email, identifiant, mdp, nom, photo, prenom, groupe_etudiant, dtype, id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, 'MdrScolarite',?)");


                for (int k=j; k < nbrEtudiant + nbrProf + nbrStaff; k++) {
                    try {
                        String prenomBDD = staff.generateName();
                        String nomBDD = prenom.get((randomNum+k)%prenom.size());
                        String mailBDD = nomBDD + "@" + staff.generateName() + ".biz";
                        String adresseBDD = (k%42) + " allee de " + staff.generateName();
                        String mdpBDD = Util.hashString(Util.generateUniqueToken());
                        staff.setNom(nomBDD);
                        staff.setPrenom(prenomBDD);
                        String idBDD = staff.generateIdentifiant();
                        pst.setString(1, adresseBDD);
                        pst.setString(2, null);
                        pst.setString(3, mailBDD);
                        pst.setString(4, idBDD);
                        pst.setString(5, mdpBDD);
                        pst.setString(6, nomBDD);
                        pst.setString(7, null);
                        pst.setString(8, prenomBDD);
                        pst.setString(9, null);
                        pst.setString(10, Integer.toString(k + temp + 1));
                        //System.out.println(pst.toString());
                        int omega = pst.executeUpdate();
                        //System.out.println(omega + " enregistrement(s)");
                    }catch (SQLException s){
                        System.out.println("Erreu sql interne" + s);
                        k--;
                        err++;
                    }
                }
            }catch (SQLException e){
                System.out.println("Sql ERROR" + e);
            }
            //System.out.println("err "+err);

            /*
            if (!prof && !staffB) {
                Etudiant etudiant = new Etudiant();

                Random rand = new Random();
                int randomNum = rand.nextInt((prenom.size() - 1 - 0) + 1) + 0;
                String mdp = Util.generateUniqueToken();
                etudiant.setMdp(Util.hashString(mdp));
                etudiant.setEmail(etudiant.generateName() + "@" + etudiant.generateName() + ".dz");
                etudiant.setAdresse(randomNum + " rue de " + etudiant.generateName());
                etudiant.setNom(etudiant.generateName());
                etudiant.setPrenom(prenom.get(randomNum));
                etudiant.setIdentifiant(etudiant.generateIdentifiant());
                try {
                    iEtudiantRepo.save(etudiant);
                } catch (Exception e) {
                    System.out.println("erreur est survenue dans insertion ...");
                }
                System.out.println("Eleve ajoute : ");

                limite++;
                if (limite < 1000) {
                    ajoutData();
                }else{
                    prof=true;
                    ajoutData();
                }
            } else if (!staffB){
                limite = 0;
                Enseignant prof = new Enseignant();
                Random rand =new Random();
                int randomNum = rand.nextInt((prenom.size() - 1 - 0) + 1) + 0;
                String mdp = Util.generateUniqueToken();
                prof.setMdp(Util.hashString(mdp));
                prof.setEmail(prof.generateName() + "@" + prof.generateName() + ".fr");
                prof.setAdresse(randomNum + " rue de " + prof.generateName());
                prof.setNom(prof.generateName());
                prof.setPrenom(prenom.get(randomNum));
                prof.setIdentifiant(prof.generateIdentifiant());
                try {
                    iEnseignantRepo.save(prof);
                } catch (Exception e) {
                    System.out.println("erreur est survenue dans insertion ...");
                }
                System.out.println("prof ajoute  ");
                limiteP++;
                if (limiteP<62){
                    ajoutData();

            }else{
                    staffB=true;
                    ajoutData();
                }
        }if(staffB){

                MbrScolarite staff = new MbrScolarite();
                Random rand =new Random();
                int randomNum = rand.nextInt((prenom.size() - 1 - 0) + 1) + 0;
                String mdp = Util.generateUniqueToken();
                staff.setMdp(Util.hashString(mdp));
                staff.setEmail(staff.generateName() + "@" + staff.generateName() + ".biz");
                staff.setAdresse(randomNum + " rue de " + staff.generateName());
                staff.setNom(staff.generateName());
                staff.setPrenom(prenom.get(randomNum));
                staff.setIdentifiant(staff.generateIdentifiant());
                try {
                    iMbrScolariteRepo.save(staff);
                } catch (Exception e) {
                    System.out.println("erreur est survenue dans insertion ...");
                }
                System.out.println("satff ajoute  ");
                limiteS++;
                if (limiteS<6){
                    ajoutData();
                }else{

                    limite = 0;
                    limiteP = 0;
                    limiteS = 0;
                    prof = false;
                    staffB = false;
                }

            }
            */
            return "robot/robot";
        }
    }