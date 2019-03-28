package com.targas.erp.controllers;

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
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Controller
public class UtilisateursController {

    @Autowired
    private IEtudiantRepo iEtudiantRepo;
    @Autowired
    private IEnseignantRepo iEnseignantRepo;

    @Autowired
    private IUtilisateurRepo utilisateurRepo;
    @Autowired
    private IUtilisateurRepo Iu;

    @GetMapping("/utilisateurs")
    public String renderUtilisateurPage(Model model) {
        Pageable pageable = PageRequest.of(0, 10);
//        List<Utilisateur> utilisateurs = new ArrayList<>((List<Etudiant>) iEtudiantRepo.findAll());
//        utilisateurs.addAll((List<Enseignant>) iEnseignantRepo.findAll());
        List<Utilisateur> utilisateurs = utilisateurRepo.findAll(pageable);
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
            switch (type) {
                case "etudiant":
                    Etudiant etudiant = new Etudiant();
                    // etudiant.setIdentifiant(u.getIdentifiant());
                    etudiant.setEmail(u.getEmail());
                    etudiant.setMdp(u.getMdp());
                    etudiant.setAdresse(u.getAdresse());
                    etudiant.setNom(u.getNom());
                    etudiant.setPrenom(u.getPrenom());
                    etudiant.setIdentifiant(etudiant.generateIdentifiant());
                    iEtudiantRepo.save(etudiant);
                    Util.sendEmail(u.getEmail(), "identifiant et mot de passe", "Identifiant: " + etudiant.getIdentifiant() + " mot de passe: " + mdp);

                    break;
                case "enseignant":
                    Enseignant enseignant = new Enseignant();
                    enseignant.setEmail(u.getEmail());
                    enseignant.setMdp(u.getMdp());
                    enseignant.setAdresse(u.getAdresse());
                    enseignant.setNom(u.getNom());
                    enseignant.setPrenom(u.getPrenom());
                    enseignant.setIdentifiant(enseignant.generateIdentifiant());
                    iEnseignantRepo.save(enseignant);
                    Util.sendEmail(u.getEmail(), "identifiant et mot de passe", "Identifiant: " + enseignant.getIdentifiant() + " mot de passe: " + mdp);

                    break;
                default:
                    break;
            }
            System.out.println(mdp);
        }
        return "redirect:/utilisateurs";

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
                    etudiant.setIdentifiant(optionalUtilisateur.get().getIdentifiant());
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
                    enseignant.setIdentifiant(optionalUtilisateur.get().getIdentifiant());
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
            for (int i = 0; i < 10 || i < rows; i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                    if (tmp > cols) cols = tmp;
                }
            }

            ArrayList<String> infos = new ArrayList<>();
            String prenom, nom, mail, adress, id;
            for (int r = 0; r < rows; r++) {
                row = sheet.getRow(r);

                if (row != null) {
                    for (int c0 = 0; c0 < cols; c0++) {
                        cell = row.getCell((short) c0);
                        if (cell != null) {

                            infos.add(cell.toString());

                        }
                    }

                    Random rand = new Random();
                    int randomNum;
                    String mdp;

                    switch (infos.get(0)) {
                        case "Etudiant":
                            Etudiant etudiant = new Etudiant();
                            mdp = Util.generateUniqueToken();
                            etudiant.setMdp(Util.hashString(mdp));
                            etudiant.setEmail(infos.get(3));
                            etudiant.setAdresse(infos.get(4));
                            etudiant.setNom(infos.get(1));
                            etudiant.setPrenom(infos.get(2));
                            etudiant.setIdentifiant(etudiant.generateIdentifiant());
                            String m = etudiant.generateIdentifiant();
                            System.out.println("Import étudiant mdp : " + mdp + " id : " + m);
                            iEtudiantRepo.save(etudiant);
                            infos.clear();
                            break;
                        case "Enseignant":
                            Enseignant prof = new Enseignant();
                            mdp = Util.generateUniqueToken();
                            prof.setMdp(Util.hashString(mdp));
                            prof.setEmail(infos.get(3));
                            prof.setAdresse(infos.get(4));
                            prof.setNom(infos.get(1));
                            prof.setPrenom(infos.get(2));

                            m = prof.generateIdentifiant();
                            prof.setIdentifiant(m);
                            System.out.println("Import enseignant mdp : " + mdp + " id " + m);
                            iEnseignantRepo.save(prof);
                            infos.clear();
                            break;
                        default:
                            System.out.println("DEFAULT / ERROR !");
                            infos.clear();

                    }
                    System.out.println();
                }
            }
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }


        return "utilisateur/importation";
    }

    @GetMapping("/profil")
    public String editProfil(Model model, HttpServletRequest request) {

        model.addAttribute("u", request.getSession().getAttribute("utilisateur"));
        model.addAttribute("upwd", new Utilisateur());
        return "utilisateur/profil";

    }

    @PostMapping("/profil/general-infos")
    public String setGeneralInfos(@ModelAttribute("u") Utilisateur u, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Utilisateur currU = (Utilisateur) request.getSession().getAttribute("utilisateur");
        currU.setNom(u.getNom());
        currU.setPrenom(u.getPrenom());
        currU.setNumTel(u.getNumTel());
        currU.setDescription(u.getDescription());
        utilisateurRepo.save(currU);
        redirectAttributes.addFlashAttribute("message", "Modification reussie");
        return "redirect:/profil";
    }

    @PostMapping("/profil/image")
    public String setImage(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            Utilisateur utilisateurCourant = (Utilisateur) request.getSession().getAttribute("utilisateur");
            Part imageProfile = request.getPart("imageProfile");
            boolean check = true;
            ClassPathResource cpr = new ClassPathResource("/static/images/");
            if (utilisateurCourant.getPhoto() != null && !utilisateurCourant.getPhoto().isEmpty()) {
                String path = cpr.getFile().getPath() + "/" + utilisateurCourant.getIdentifiant() + ".png";
                File file = new File(path);
                System.out.println("fileExists:=" + file.exists());
                check = file.delete();
                System.out.println("check:=" + check);
            }
            if (check) {
                String fileName = utilisateurCourant.getIdentifiant() + ".png";
                InputStream imageProfileInputStream = imageProfile.getInputStream();
                byte[] buffer = new byte[imageProfileInputStream.available()];
                imageProfileInputStream.read(buffer);
                System.out.println(cpr.exists());
                System.out.println(cpr.getFile().getPath() + "/" + fileName);
                File targetFile = new File(cpr.getFile().getPath() + "/" + fileName);
                OutputStream outStream = new FileOutputStream(targetFile);
                outStream.write(buffer);
                utilisateurCourant.setPhoto("/images/" + fileName);
                utilisateurRepo.save(utilisateurCourant);
                redirectAttributes.addFlashAttribute("message", "Modification reussie");

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return "redirect:/profil";
    }

    @PostMapping("/profil/mdp")
    public String setMdp(@ModelAttribute("u") Utilisateur u, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Utilisateur currU = (Utilisateur) request.getSession().getAttribute("utilisateur");
        if (Util.hashString(u.getOldMdp()).equals(currU.getMdp())) {
            if (u.getMdp().equals(u.getConfirmMdp())) {
                currU.setMdp(Util.hashString(u.getMdp()));
                utilisateurRepo.save(currU);
            }
        }
        redirectAttributes.addFlashAttribute("message", "Modification reussie");
        return "redirect:/profil";
    }

    @GetMapping("/profil/{id}")
    public String profilPublic(@PathVariable("id") Integer id,Model model) {

        Optional<Utilisateur> optionalUtilisateur = utilisateurRepo.findById(id);
        if(optionalUtilisateur.isPresent())
            model.addAttribute("u",optionalUtilisateur.get());
        else
            return "redirect:/profil";

        return "utilisateur/profil-public";
    }

}
