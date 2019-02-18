package com.targas.erp.controllers;

import com.targas.erp.dao.IAnneeScolaireRepo;
import com.targas.erp.dao.IMbrScolariteRepo;
import com.targas.erp.dao.IUtilisateurRepo;
import com.targas.erp.models.AnneeScolaire;
import com.targas.erp.models.Etudiant;
import com.targas.erp.models.MbrScolarite;
import com.targas.erp.models.Utilisateur;
import com.targas.erp.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
public class LoginController {

    @Autowired
    private IAnneeScolaireRepo iAnneeScolaireRepo;

    @Autowired
    private IMbrScolariteRepo iMbrScolariteRepo;

    @Autowired
    private IUtilisateurRepo utilisateurRepo;

    @GetMapping("/login")
    public String loginPageRender(Model model) {
        Utilisateur utilisateur = new Utilisateur();
        model.addAttribute("utilisateur", utilisateur);
        return "login";
    }

    @PostMapping("/login")
    public String ProcessLogin(@ModelAttribute(value = "utilisateur") Utilisateur utilisateur, HttpServletRequest request) {
        Utilisateur loggedInUser = utilisateurRepo.findByIdentifiantAndMdp(utilisateur.getIdentifiant(), Util.hashString(utilisateur.getMdp()));
        request.getSession().setAttribute("utilisateur", loggedInUser);
        return "redirect:";
    }
    @GetMapping("/init")
    public String init() {
        if (!iMbrScolariteRepo.findById(1).isPresent()) {
            int currentYear = LocalDate.now().getYear();
            AnneeScolaire anneeScolaire = new AnneeScolaire();
            System.out.println(currentYear+"/"+(currentYear+1));
            anneeScolaire.setNomAnneeScolaire(currentYear+"/"+(currentYear+1));
            iAnneeScolaireRepo.save(anneeScolaire);

            MbrScolarite mbrScolarite = new MbrScolarite();
            mbrScolarite.setNom("Admin");
            mbrScolarite.setPrenom("Admin");
            mbrScolarite.setEmail("test@gmail.com");
            mbrScolarite.setMdp(Util.hashString("qwerty123"));
            mbrScolarite.setIdentifiant("admin");
            mbrScolarite.setId(1);
            iMbrScolariteRepo.save(mbrScolarite);
        }
//        Utilisateur utilisateur = iMbrScolariteRepo.findById(1).get();
//        System.out.println(utilisateur instanceof MbrScolarite);
//        System.out.println(utilisateur instanceof Etudiant);
        return "redirect:";
    }

    @GetMapping("/logout")
    public String deconnexion(HttpServletRequest request) {
        request.getSession().removeAttribute("utilisateur");
        return "redirect:/login";
    }




    @GetMapping("/")
    public String renderAccueil(Model model, HttpServletRequest request){

        return "accueil";
    }
}
