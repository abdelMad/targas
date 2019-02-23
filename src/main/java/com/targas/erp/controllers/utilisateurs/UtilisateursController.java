package com.targas.erp.controllers.utilisateurs;

import com.targas.erp.dao.IEnseignantRepo;
import com.targas.erp.dao.IEtudiantRepo;
import com.targas.erp.dao.IUtilisateurRepo;
import com.targas.erp.models.Enseignant;
import com.targas.erp.models.Etudiant;
import com.targas.erp.models.Utilisateur;
import com.targas.erp.utilities.Util;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UtilisateursController {

    @Autowired
    private IEtudiantRepo iEtudiantRepo;
    @Autowired
    private IEnseignantRepo iEnseignantRepo;

    @Autowired
    private IUtilisateurRepo Iu;

    @GetMapping("/utilisateurs")
    public String renderUtilisateurPage(Model model) {
        List<Utilisateur> utilisateurs = new ArrayList<>((List<Etudiant>) iEtudiantRepo.findAll());
        utilisateurs.addAll((List<Enseignant>) iEnseignantRepo.findAll());
        model.addAttribute("uList", utilisateurs);
        return "utilisateur/utilisateurs";

    }

    @GetMapping("/utilisateurs/ajouter")
    public String renderAjoutUtilisateurPage(Model model) {
        Utilisateur utilisateur = new Utilisateur();
        model.addAttribute("u", utilisateur);
        return "utilisateur/ajouterUtilisateur";

    }

    @PostMapping("/utilisateurs/ajouter")
    public String ajouterUtilisateur(@ModelAttribute("u") Utilisateur u, @RequestParam("type") String type, Model model) {
        if ("etudiant".equals(type) || "enseignant".equals(type)) {
            String mdp = Util.generateUniqueToken();
            u.setMdp(Util.hashString(mdp));
            Util.sendEmail(u.getEmail(), "mot de passe", mdp);
            switch (type) {
                case "etudiant":
                    Etudiant etudiant = new Etudiant();
                   // etudiant.setIdentifiant(u.getIdentifiant());
                    etudiant.setIdentifiant(u.generateIdentifiant());
                    etudiant.setEmail(u.getEmail());
                    etudiant.setMdp(u.getMdp());
                    etudiant.setAdresse(u.getAdresse());
                    etudiant.setNom(u.getNom());
                    etudiant.setPrenom(u.getPrenom());
                    iEtudiantRepo.save(etudiant);

                    break;
                case "enseignant":
                    Enseignant enseignant = new Enseignant();
                    enseignant.setIdentifiant(u.getIdentifiant());
                    enseignant.setEmail(u.getEmail());
                    enseignant.setMdp(u.getMdp());
                    enseignant.setAdresse(u.getAdresse());
                    enseignant.setNom(u.getNom());
                    enseignant.setPrenom(u.getPrenom());
                    iEnseignantRepo.save(enseignant);
                    break;
                default:
                    break;
            }
            Util.sendEmail(u.getEmail(), "mot de passe", mdp);
            System.out.println(mdp);
        }
        return "utilisateur/ajouterUtilisateur";

    }

    @GetMapping("/utilisateur/edit/{id}")
    public String modifierUtilisateurRenderPage(@PathVariable("id") Integer id, Model model) {
        System.out.println(id);
        Optional<Utilisateur> optionalUtilisateur = Iu.findById(id);
        if (optionalUtilisateur.isPresent()) {
            Utilisateur utilisateur = optionalUtilisateur.get();
            model.addAttribute("uType", (utilisateur instanceof Etudiant) ? "Etudiant" : (utilisateur instanceof Enseignant ? "Enseignant" : ""));
            model.addAttribute("u", utilisateur);
        } else {
            return "redirect:/utilisateurs";
        }
        return "utilisateur/ajouterUtilisateur";
    }

    @PostMapping("/utilisateur/edit/{id}")
    public String modifierUtilisateur(@PathVariable("id") Integer id, @ModelAttribute("u") Utilisateur u, @RequestParam("type") String type) {
        System.out.println(id);
        Optional<Utilisateur> optionalUtilisateur = Iu.findById(id);
        if (optionalUtilisateur.isPresent()) {
            switch (type) {
                case "etudiant":
                    Etudiant etudiant = new Etudiant();
                    etudiant.setId(id);
                    etudiant.setIdentifiant(u.getIdentifiant());
                    etudiant.setEmail(u.getEmail());
                    etudiant.setMdp(u.getMdp());
                    etudiant.setAdresse(u.getAdresse());
                    etudiant.setNom(u.getNom());
                    etudiant.setPrenom(u.getPrenom());
                    iEtudiantRepo.save(etudiant);

                    break;
                case "enseignant":
                    Enseignant enseignant = new Enseignant();
                    enseignant.setId(id);
                    enseignant.setIdentifiant(u.getIdentifiant());
                    enseignant.setEmail(u.getEmail());
                    enseignant.setMdp(u.getMdp());
                    enseignant.setAdresse(u.getAdresse());
                    enseignant.setNom(u.getNom());
                    enseignant.setPrenom(u.getPrenom());
                    iEnseignantRepo.save(enseignant);
                    break;
                default:
                    break;
            }
        }

        return "redirect:/utilisateurs";
    }

    @GetMapping("/utilisateurs/importer")
    public String renderImportUtilisateurPage(Model model) {
        Utilisateur utilisateur = new Utilisateur();
        model.addAttribute("u", utilisateur);
        return "utilisateur/importerUtilisateur";

    }

    @PostMapping("/utilisateurs/importer")
    public String ImportUtilisateurPage(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
        modelMap.addAttribute("file", file);
        //System.out.println(file.getOriginalFilename());
        /**
         * traitement sur fichier excel a faire ici
         *https://www.baeldung.com/spring-file-upload
         * https://stackoverflow.com/questions/1516144/how-to-read-and-write-excel-file
         */

        try {

            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();

           // POIFSFileSystem fs = new POIFSFileSystem(convFile);
            //HSSFWorkbook wb = new HSSFWorkbook(fs);
            XSSFWorkbook wb = new XSSFWorkbook(convFile);

            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow row;
            XSSFCell cell;

            int rows; // No of rows
            rows = sheet.getPhysicalNumberOfRows();

            int cols = 0; // No of columns
            int tmp = 0;

            // This trick ensures that we get the data properly even if it doesn't start from first few rows
            for(int i = 0; i < 10 || i < rows; i++) {
                row = sheet.getRow(i);
                if(row != null) {
                    tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                    if(tmp > cols) cols = tmp;
                }
            }

            ArrayList<String> infos = new ArrayList<>();

            Statement st;
            Connection c;
            String url = "jdbc:mysql://localhost/targas?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String identifiant = "root";
            String mdp = "4ag2ncarnot";
            PreparedStatement pst;

            String mdpBDD;

            c = DriverManager.getConnection(url, identifiant, mdp);
            st = c.createStatement();
            ResultSet rs2 = st.executeQuery("SELECT COUNT(*) FROM utilisateur");
            int temp = 0;
            if(rs2.next()) {
                temp = rs2.getInt("COUNT(*)");
                //System.out.println("count :" + temp);

            }

            String prenom,nom,mail,adress,id;
            for(int r = 0; r < rows; r++) {
                row = sheet.getRow(r);

                if(row != null) {
                    for(int c0 = 0; c0< cols; c0++) {
                        cell = row.getCell((short)c0);
                        if(cell != null) {

                            infos.add(cell.toString());

                        }
                    }


                    switch (infos.get(0)){
                        case "Etudiant":
                            pst = c.prepareStatement("insert into utilisateur (adresse, description, email, identifiant, mdp, nom, photo, prenom, groupe_etudiant, dtype, id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, 'Etudiant',?)");
                            Etudiant etudiant = new Etudiant();
                            nom=infos.get(1);
                            prenom=infos.get(2);
                            mail=infos.get(3);
                            adress=infos.get(4);
                            etudiant.setNom(infos.get(1));
                            etudiant.setPrenom(infos.get(2));
                            mdpBDD = Util.hashString(Util.generateUniqueToken());
                            id=etudiant.generateIdentifiant();
                            pst.setString(1, adress);
                            pst.setString(2, null);
                            pst.setString(3, mail);
                            pst.setString(4, id);
                            pst.setString(5, mdpBDD);
                            pst.setString(6, nom);
                            pst.setString(7, null);
                            pst.setString(8, prenom);
                            pst.setString(9, null);
                            pst.setString(10, Integer.toString(r + temp + 1));

                            pst.executeUpdate();
                            infos.clear();
                            break;
                            case "Enseignant":

                                System.out.println("prof detected");
                                pst = c.prepareStatement("insert into utilisateur (adresse, description, email, identifiant, mdp, nom, photo, prenom, groupe_etudiant, dtype, id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, 'Enseignant',?)");

                                Enseignant prof = new Enseignant();
                                nom=infos.get(1);
                                prenom=infos.get(2);
                                mail=infos.get(3);
                                adress=infos.get(4);
                                prof.setNom(infos.get(1));
                                prof.setPrenom(infos.get(2));
                                mdpBDD = Util.hashString(Util.generateUniqueToken());
                                id=prof.generateIdentifiant();
                                pst.setString(1, adress);
                                pst.setString(2, null);
                                pst.setString(3, mail);
                                pst.setString(4, id);
                                pst.setString(5, mdpBDD);
                                pst.setString(6, nom);
                                pst.setString(7, null);
                                pst.setString(8, prenom);
                                pst.setString(9, null);
                                pst.setString(10, Integer.toString(r + temp + 1));

                                pst.executeUpdate();

                                infos.clear();
                                break;
                                default:
                                    System.out.println("DEFAULT / ERROR !");
                                    infos.clear();

                    }
                    System.out.println();
                }
            }
        } catch(Exception ioe) {
            ioe.printStackTrace();
        }


        return "utilisateur/importation";
    }

}
