package com.targas.erp.controllers;

import com.targas.erp.dao.IEtablissementRepo;
import com.targas.erp.dao.ISalleRepo;
import com.targas.erp.models.Etablissement;
import com.targas.erp.models.Salle;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/salles")
public class SalleController {

    @Autowired
    private ISalleRepo salleRepo;

    @Autowired
    private IEtablissementRepo etablissementRepo;

    @GetMapping("")
    public String sallesPageRender(Model model) {
        model.addAttribute("salles", salleRepo.listAll());
        model.addAttribute("etablissements", etablissementRepo.listAll());

        return "salles";
    }


    @PostMapping("/enregistrer")
    @ResponseBody
    public String modifierSalle(@RequestBody String jsonString) {

        JSONObject jsonObject = new JSONObject(jsonString);
        Optional<Etablissement> etablissementOptional = etablissementRepo.findById(Integer.parseInt(jsonObject.get("etablissement").toString()));
        if (etablissementOptional.isPresent()) {
            if (jsonObject.get("id").toString().length() == 0) {
                Salle salle = new Salle();
                salle.setNom(jsonObject.get("nom").toString());
                salle.setCapacite(Integer.parseInt(jsonObject.get("capacite").toString()));
                salle.setNbrPC(Integer.parseInt(jsonObject.get("nbrPc").toString()));
                salle.setEtablissement(etablissementOptional.get());

                salleRepo.save(salle);
                return "[\"ok\"]";
            } else {
                Optional<Salle> salleOptional = salleRepo.findById(Integer.parseInt(jsonObject.get("id").toString()));
                if (salleOptional.isPresent()) {
                    Salle salle = salleOptional.get();
                    salle.setNom(jsonObject.get("nom").toString());
                    salle.setCapacite(Integer.parseInt(jsonObject.get("capacite").toString()));
                    salle.setNbrPC(Integer.parseInt(jsonObject.get("nbrPc").toString()));
                    salle.setEtablissement(etablissementOptional.get());
                    salleRepo.save(salle);
                    return "[\"ok\"]";
                }
            }
        }
        return "[\"error\"]";
    }

    @PostMapping("/supprimer")
    @ResponseBody
    public String supprimerSalle(@RequestBody String jsonString) {

        JSONObject jsonObject = new JSONObject(jsonString);
        Optional<Salle> salleOptional = salleRepo.findById(Integer.parseInt(jsonObject.get("id").toString()));
        if (salleOptional.isPresent()) {
            Salle salle = salleOptional.get();
            salle.setDeleted(true);
            salleRepo.save(salle);
            return "[\"ok\"]";
        }
        return "[\"error\"]";
    }

}
