package com.targas.erp.controllers;

import com.targas.erp.dao.ICoursRepo;
import com.targas.erp.models.Cours;
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
public class CoursController {

    @Autowired
    private ICoursRepo coursRepo;

    @GetMapping("/cours")
    public String renderCoursPage(Model model){
        model.addAttribute("cours",coursRepo.listAll());
        return "cours";
    }

    @PostMapping("/cours/enregistrer")
    @ResponseBody
    public String enregistrerEtablissement(@RequestBody String jsonString) {

        JSONObject jsonObject = new JSONObject(jsonString);
        if(jsonObject.get("id").toString().length()==0){
            Cours cours = new Cours();
            cours.setNom(jsonObject.get("nom").toString());
            cours.setMasseHoraire(Float.parseFloat(jsonObject.get("mh").toString()));
            coursRepo.save(cours);
            return "[\"ok\"]";
        }else {
            Optional<Cours> coursOptional = coursRepo.findById(Integer.parseInt(jsonObject.get("id").toString()));
            if (coursOptional.isPresent()) {
                Cours cours = coursOptional.get();
                cours.setNom(jsonObject.get("nom").toString());
                cours.setMasseHoraire(Float.parseFloat(jsonObject.get("mh").toString()));
                coursRepo.save(cours);
                return "[\"ok\"]";
            }
        }
        return "[\"error\"]";
    }

    @PostMapping("/cours/supprimer")
    @ResponseBody
    public String supprimeretablissement(@RequestBody String jsonString) {

        JSONObject jsonObject = new JSONObject(jsonString);
        Optional<Cours> coursOptional = coursRepo.findById(Integer.parseInt(jsonObject.get("id").toString()));
        if (coursOptional.isPresent()) {
            Cours etablissement = coursOptional.get();
            etablissement.setDeleted(true);
            coursRepo.save(etablissement);
            return "[\"ok\"]";
        }
        return "[\"error\"]";
    }

    @GetMapping("/cours/affectation")
    public String renderAffectationCoursPage(Model model){

        return "affectationCours";
    }
}
