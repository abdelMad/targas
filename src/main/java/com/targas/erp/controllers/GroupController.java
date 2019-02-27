package com.targas.erp.controllers;

import com.targas.erp.dao.IFiliereRepo;
import com.targas.erp.dao.IGroupeRepo;
import com.targas.erp.models.Filiere;
import com.targas.erp.models.Groupe;
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
public class GroupController {

    @Autowired
    private IGroupeRepo groupeRepo;

    @Autowired
    private IFiliereRepo filiereRepo;
    @GetMapping("/groupes")
    public String renderGroupePage(Model model){
        model.addAttribute("groupes", groupeRepo.listAll());
        model.addAttribute("filieres", filiereRepo.listAll());
        return "groupes";
    }
    @PostMapping("/groupes/enregistrer")
    @ResponseBody
    public String enregistrerGroupe(@RequestBody String jsonString) {

        JSONObject jsonObject = new JSONObject(jsonString);
        Optional<Filiere> filiereOptional = filiereRepo.findById(Integer.parseInt(jsonObject.get("filiere").toString()));
        if(filiereOptional.isPresent()) {
            Groupe groupe = new Groupe();
            if (jsonObject.get("id").toString().length() == 0) {
                groupe.setNomGroupe(jsonObject.get("nom").toString());
                groupe.setAbbreviation(jsonObject.get("abbreviation").toString());
                groupe.setFiliere(filiereOptional.get());
                groupeRepo.save(groupe);
                return "[\"ok\"]";
            } else {
                Optional<Groupe> groupeOptional = groupeRepo.findById(Integer.parseInt(jsonObject.get("id").toString()));
                if (groupeOptional.isPresent()) {
                    groupe = groupeOptional.get();
                    groupe.setNomGroupe(jsonObject.get("nom").toString());
                    groupe.setAbbreviation(jsonObject.get("abbreviation").toString());
                    groupe.setFiliere(filiereOptional.get());
                    groupeRepo.save(groupe);
                    return "[\"ok\"]";
                }
            }
        }
        return "[\"error\"]";
    }

    @PostMapping("/groupes/supprimer")
    @ResponseBody
    public String supprimerGroupe(@RequestBody String jsonString) {

        JSONObject jsonObject = new JSONObject(jsonString);
        Optional<Groupe> groupeOptional = groupeRepo.findById(Integer.parseInt(jsonObject.get("id").toString()));
        if (groupeOptional.isPresent()) {
            Groupe groupe = groupeOptional.get();
            groupe.setDeleted(true);
            groupeRepo.save(groupe);
            return "[\"ok\"]";
        }
        return "[\"error\"]";
    }

}
