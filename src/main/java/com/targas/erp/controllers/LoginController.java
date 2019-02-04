package com.targas.erp.controllers;

import com.targas.erp.dao.IMbrScolariteRepo;
import com.targas.erp.models.Etudiant;
import com.targas.erp.models.MbrScolarite;
import com.targas.erp.models.Utilisateur;
import com.targas.erp.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired
    private IMbrScolariteRepo iMbrScolariteRepo;

    @GetMapping("/login")
    public String loginPageRender() {

        return "login";
    }

    @GetMapping("/init")
    public String init() {
        if (!iMbrScolariteRepo.findById(1).isPresent()) {
            MbrScolarite mbrScolarite = new MbrScolarite();
            mbrScolarite.setNom("Admin");
            mbrScolarite.setPrenom("Admin");
            mbrScolarite.setEmail("test@gmail.com");
            mbrScolarite.setMdp(Util.hashString("qwerty123"));
            mbrScolarite.setIdentifiant("admin");
            mbrScolarite.setId(1);
            iMbrScolariteRepo.save(mbrScolarite);
        }
        Utilisateur utilisateur = iMbrScolariteRepo.findById(1).get();
        System.out.println(utilisateur instanceof MbrScolarite);
        System.out.println(utilisateur instanceof Etudiant);
        return "login";
    }

    @GetMapping("/")
    public String renderAccueil(Model model, HttpServletRequest request){

        return "accueil";
    }
}
