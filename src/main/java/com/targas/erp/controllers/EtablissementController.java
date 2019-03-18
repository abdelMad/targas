package com.targas.erp.controllers;

import com.targas.erp.dao.IEtablissementRepo;
import com.targas.erp.dao.ISalleRepo;
import com.targas.erp.models.Etablissement;
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
public class EtablissementController {

    @Autowired
    private ISalleRepo salleRepo;

    @Autowired
    private IEtablissementRepo etablissementRepo;

    @GetMapping("/etablissements")
    public String sallesPageRender(Model model) {
        model.addAttribute("etablissements", etablissementRepo.listAll());
        return "etablissements";
    }

    @PostMapping("/etablissements/enregistrer")
    @ResponseBody
    public String enregistrerEtablissement(@RequestBody String jsonString) {

        JSONObject jsonObject = new JSONObject(jsonString);
        if(jsonObject.get("id").toString().length()==0){
            Etablissement etablissement = new Etablissement();
            etablissement.setNom(jsonObject.get("nom").toString());
            etablissement.setAdresse(jsonObject.get("adresse").toString());
            etablissementRepo.save(etablissement);
            return "[\"ok\"]";
        }else {
            Optional<Etablissement> etablissementOptional = etablissementRepo.findById(Integer.parseInt(jsonObject.get("id").toString()));
            if (etablissementOptional.isPresent()) {
                Etablissement etablissement = etablissementOptional.get();
                etablissement.setNom(jsonObject.get("nom").toString());
                etablissement.setAdresse(jsonObject.get("adresse").toString());
                etablissementRepo.save(etablissement);
                return "[\"ok\"]";
            }
        }
        return "[\"error\"]";
    }

    @PostMapping("/etablissements/supprimer")
    @ResponseBody
    public String supprimerEtablissement(@RequestBody String jsonString) {

        JSONObject jsonObject = new JSONObject(jsonString);
        Optional<Etablissement> etablissementOptional = etablissementRepo.findById(Integer.parseInt(jsonObject.get("id").toString()));
        if (etablissementOptional.isPresent()) {
            Etablissement etablissement = etablissementOptional.get();
            etablissement.setDeleted(true);
            etablissementRepo.save(etablissement);
            return "[\"ok\"]";
        }
        return "[\"error\"]";
    }

}
