package com.targas.erp.controllers.utilisateurs;

import com.targas.erp.dao.IEnseignantRepo;
import com.targas.erp.dao.IEtudiantRepo;
import com.targas.erp.dao.IUtilisateurRepo;
import com.targas.erp.models.Enseignant;
import com.targas.erp.models.Etudiant;
import com.targas.erp.models.Utilisateur;
import com.targas.erp.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

}
