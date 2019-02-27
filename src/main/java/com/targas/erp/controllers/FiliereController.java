package com.targas.erp.controllers;

import com.targas.erp.dao.IEtablissementRepo;
import com.targas.erp.dao.IFiliereRepo;
import com.targas.erp.models.Etablissement;
import com.targas.erp.models.Filiere;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class FiliereController {

    @Autowired
    private IFiliereRepo filiereRepo;
    @Autowired
    private IEtablissementRepo etablissementRepo;

    @GetMapping("/filieres")
    public String renderCoursPage(Model model) {
        model.addAttribute("filieres", filiereRepo.listAll());
        model.addAttribute("etablissements", etablissementRepo.listAll());

        return "filieres";
    }

    @PostMapping("/filieres/enregistrer")
    @ResponseBody
    public String enregistrerEtablissement(@RequestBody String jsonString) {

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray etablissementJsonArray = new JSONArray(jsonObject.get("etabs").toString());
        List<Etablissement> etablissements = new ArrayList<>();
        Filiere filiere = new Filiere();
        for (int i = 0; i < etablissementJsonArray.length(); i++) {
            Optional<Etablissement> etablissementOptional = etablissementRepo.findById(Integer.parseInt(etablissementJsonArray.get(i).toString()));
            etablissementOptional.ifPresent(etablissements::add);
        }
        if (jsonObject.get("id").toString().length() == 0) {
            filiere.setNomFiliere(jsonObject.get("nom").toString());
            filiere.setEtablissementList(etablissements);
            filiereRepo.save(filiere);
            return "[\"ok\"]";
        } else {
            Optional<Filiere> filiereOptional = filiereRepo.findById(Integer.parseInt(jsonObject.get("id").toString()));
            if (filiereOptional.isPresent()) {
                filiere = filiereOptional.get();
                filiere.setNomFiliere(jsonObject.get("nom").toString());
                filiere.setEtablissementList(etablissements);
                filiereRepo.save(filiere);
                return "[\"ok\"]";
            }
        }
        return "[\"error\"]";
    }

    @PostMapping("/filieres/supprimer")
    @ResponseBody
    public String supprimeretablissement(@RequestBody String jsonString) {

        JSONObject jsonObject = new JSONObject(jsonString);
        Optional<Filiere> filiereOptional = filiereRepo.findById(Integer.parseInt(jsonObject.get("id").toString()));
        if (filiereOptional.isPresent()) {
            Filiere filiere = filiereOptional.get();
            filiere.setDeleted(true);
            filiereRepo.save(filiere);
            return "[\"ok\"]";
        }
        return "[\"error\"]";
    }

}
