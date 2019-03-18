package com.targas.erp.controllers;

import com.targas.erp.dao.ICoursRepo;
import com.targas.erp.dao.IFiliereRepo;
import com.targas.erp.dao.IGroupeRepo;
import com.targas.erp.models.Cours;
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

    @Autowired
    private ICoursRepo coursRepo;

    @GetMapping("/groupes")
    public String renderGroupePage(Model model) {
        model.addAttribute("groupes", groupeRepo.listAll());
        model.addAttribute("filieres", filiereRepo.listAll());
        return "groupes";
    }

    @PostMapping("/groupes/enregistrer")
    @ResponseBody
    public String enregistrerGroupe(@RequestBody String jsonString) {

        JSONObject jsonObject = new JSONObject(jsonString);
        Optional<Filiere> filiereOptional = filiereRepo.findById(Integer.parseInt(jsonObject.get("filiere").toString()));
        if (filiereOptional.isPresent()) {
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

    @PostMapping("/groupes/affectation/cours")
    @ResponseBody
    public String getCoursAffectationCoursEnseignant(@RequestBody String jsonString) {

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonCours = new JSONArray();
        try {
            List<Cours> cours = coursRepo.coursRestantGroupe(jsonObject.getInt("id"));
            System.out.println(cours.size());
            for (int i = 0; i < cours.size(); i++) {
                JSONObject jsonObjectcours = new JSONObject();
                Cours c = cours.get(i);
                jsonObjectcours.put("id", c.getId());
                jsonObjectcours.put("text", c.getNom());
                jsonCours.put(jsonObjectcours);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonCours.toString();
    }

    @PostMapping("/groupes/affectation/save")
    @ResponseBody
    public String saveAffectationCoursEnseignant(@RequestBody String jsonString) {


        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            Optional<Groupe> groupeOptional = groupeRepo.findById(Integer.parseInt(jsonObject.get("id").toString()));
            if (groupeOptional.isPresent()) {

                JSONArray jsonCours = new JSONArray(jsonObject.get("cours").toString());
                List<Integer> coursIds = new ArrayList<>();
                for (int i = 0; i < jsonCours.length(); i++) {
                    coursIds.add(Integer.parseInt(jsonCours.get(i).toString()));
                }
                List<Cours> coursList = (List<Cours>) coursRepo.findAllById(coursIds);
                Groupe groupe = groupeOptional.get();
                List<Cours> existingCoursList = groupe.getCours();
                if (!existingCoursList.containsAll(coursList))
                    existingCoursList.addAll(coursList);
                groupeRepo.save(groupe);
                return "[\"ok\"]";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "[\"error\"]";
    }

    @PostMapping("/groupes/affectation/supprimer")
    @ResponseBody
    public String deleteAffectationCoursGroupe(@RequestBody String jsonString) {
        try {
            System.out.println(jsonString);
            JSONObject jsonObject = new JSONObject(jsonString);
            Optional<Groupe> groupeOptional = groupeRepo.findById(Integer.parseInt(jsonObject.get("id").toString()));
            Optional<Cours> coursOptional = coursRepo.findById(Integer.parseInt(jsonObject.get("cours").toString()));
            if (groupeOptional.isPresent() && coursOptional.isPresent()) {
                Groupe groupe = groupeOptional.get();
                Cours cours = coursOptional.get();
                groupe.getCours().remove(cours);
                groupeRepo.save(groupe);
                return "[\"ok\"]";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "[\"error\"]";
    }

}
