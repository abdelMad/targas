package com.targas.erp.controllers;

import com.targas.erp.dao.INiveauRepo;
import com.targas.erp.models.Niveau;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
public class NiveauController {

    @Autowired
    private INiveauRepo niveauRepo;

    @GetMapping("/niveaux")
    public String renderNiveauPage(Model model) {
        model.addAttribute("niveaux", niveauRepo.listAll());
        return "niveau";
    }

    @PostMapping("/niveaux/enregistrer")
    @ResponseBody
    public String enregistrerNiveau(@RequestBody String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        if (jsonObject.get("id").toString().length() == 0) {
            Niveau niveau = new Niveau();
            niveau.setNom(jsonObject.get("nom").toString());
            niveauRepo.save(niveau);
            return "[\"ok\"]";
        } else {
            Optional<Niveau> optionalNiveau = niveauRepo.findById(Integer.parseInt(jsonObject.get("id").toString()));
            if (optionalNiveau.isPresent()) {
                Niveau niveau = optionalNiveau.get();
                niveau.setNom(jsonObject.get("nom").toString());
                niveauRepo.save(niveau);
                return "[\"ok\"]";
            }
        }
        return "[\"error\"]";
    }

    @PostMapping("/niveaux/supprimer")
    @ResponseBody
    public String supprimerNiveau(@RequestBody String jsonString) {

        JSONObject jsonObject = new JSONObject(jsonString);
        Optional<Niveau> niveauOptional = niveauRepo.findById(Integer.parseInt(jsonObject.get("id").toString()));
        if (niveauOptional.isPresent()) {
            Niveau niveau = niveauOptional.get();
            niveau.setDeleted(true);
            niveauRepo.save(niveau);
            return "[\"ok\"]";
        }
        return "[\"error\"]";
    }
}
