package com.targas.erp.controllers;

import com.targas.erp.dao.*;
import com.targas.erp.models.*;
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
public class AffectationController {

    @Autowired
    private IEnseignantRepo enseignantRepo;

    @Autowired
    private ICoursRepo coursRepo;

    @Autowired
    private IAnneeScolaireRepo anneeScolaireRepo;

    @Autowired
    private IAffectationRepo affectationRepo;

    @Autowired
    private IGroupeEtudiantRepo groupeEtudiantRepo;

    @Autowired
    private IGroupeRepo groupeRepo;

    @Autowired
    private INiveauRepo niveauRepo;

    @Autowired
    private IEtudiantRepo etudiantRepo;

    @GetMapping("/affectation/enseignants")
    public String renderAffectationCoursEnseignantPage(Model model) {

        model.addAttribute("affectations", affectationRepo.listAllEnseignantsAff("2019/2020"));
        model.addAttribute("enseignants", enseignantRepo.findAll());
        model.addAttribute("as", anneeScolaireRepo.findAll());

        return "affectationCours";
    }

    @PostMapping("/affectation/save")
    @ResponseBody
    public String saveAffectationCoursEnseignant(@RequestBody String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray coursIds = new JSONArray(jsonObject.get("cours").toString());
            Optional<Enseignant> enseignantOptional = enseignantRepo.findById(Integer.parseInt(jsonObject.get("enseignant").toString()));
            Optional<AnneeScolaire> anneeScolaireOptional = anneeScolaireRepo.findById(Integer.parseInt(jsonObject.get("anneeScolaire").toString()));
            if (enseignantOptional.isPresent() && anneeScolaireOptional.isPresent()) {
                Enseignant enseignant = enseignantOptional.get();
                List<Affectation> affectations = new ArrayList<>();
                for (int i = 0; i < coursIds.length(); i++) {
                    Optional<Cours> coursOptional = coursRepo.findById(Integer.parseInt(coursIds.get(i).toString()));
                    if (coursOptional.isPresent()) {
                        Affectation af = new Affectation();
                        af.setCours(coursOptional.get());
                        af.setEnseignant(enseignant);
                        af.setAnneeScolaire(anneeScolaireOptional.get());
                        affectations.add(af);
                    }
                }
                affectationRepo.saveAll(affectations);
                enseignant.setAffectations(affectations);
                enseignantRepo.save(enseignant);

                return "[\"ok\"]";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "[\"error\"]";
    }

    @PostMapping("/affectation/supprimer")
    @ResponseBody
    public String deleteAffectationCoursEnseignant(@RequestBody String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            Optional<Affectation> affectationOptional = affectationRepo.findById(Integer.parseInt(jsonObject.get("id").toString()));
            if (affectationOptional.isPresent()) {
                affectationRepo.delete(affectationOptional.get());
                return "[\"ok\"]";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "[\"error\"]";
    }

    @PostMapping("/affectation/cours")
    @ResponseBody
    public String getCoursAffectationCoursEnseignant(@RequestBody String jsonString) {

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonCours = new JSONArray();
        try {
            List<Cours> cours = coursRepo.coursRestantEnseignant(jsonObject.getInt("id"));
            System.out.println(cours.size());
            for (int i = 0; i < cours.size(); i++) {
                Cours c = cours.get(i);
                JSONObject jsonObjectcours = new JSONObject();
                jsonObjectcours.put("id", c.getId());
                jsonObjectcours.put("text", c.getNom());
                jsonCours.put(jsonObjectcours);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonCours.toString();
    }

    //affectation des eleves

    @GetMapping("/affectation/eleves")
    public String renderAffectationGroupeEtudiantPage(Model model) {

        model.addAttribute("grpEtudiants", groupeEtudiantRepo.listAllGroupeEtudiant("2019/2020"));
        model.addAttribute("groups", groupeRepo.findAll());
        model.addAttribute("niveaux", niveauRepo.findAll());
        model.addAttribute("as", anneeScolaireRepo.findAll());
        model.addAttribute("etu", etudiantRepo.findAllWithoutGrp());

        return "affectation-eleves";
    }

    @PostMapping("/affectation/eleves/save")
    @ResponseBody
    public String saveAffectationGroupeEtudiant(@RequestBody String jsonString) {
//        as: $('#anneescolaire').val(),
//                nom: $('#nom-grp-affect-etu').val(),
//                grp: $('#grp-affect-etu').val(),
//                niveau: $('#niveau-affect').val(),
//                etudiants:
        System.out.println(jsonString);
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            Integer countGrp = groupeEtudiantRepo.grpExists(Integer.parseInt(jsonObject.get("grp").toString()), jsonObject.get("as").toString(), Integer.parseInt(jsonObject.get("niveau").toString()));
            System.out.println(countGrp);
            if (countGrp.equals(0)) {
                GroupeEtudiant groupeEtudiant = new GroupeEtudiant();
                groupeEtudiant.setNom(jsonObject.get("nom").toString());
                AnneeScolaire anneeScolaire = anneeScolaireRepo.findByNomAnneeScolaire(jsonObject.get("as").toString());
                groupeEtudiant.setAnneeScolaire(anneeScolaire);
                Groupe groupe = groupeRepo.findById(Integer.parseInt(jsonObject.get("grp").toString())).get();
                groupeEtudiant.setGroupe(groupe);
                Niveau niveau = niveauRepo.findById(Integer.parseInt(jsonObject.get("niveau").toString())).get();
                groupeEtudiant.setNiveau(niveau);
                groupeEtudiantRepo.save(groupeEtudiant);
                JSONArray etuJson = jsonObject.getJSONArray("etudiants");
                for (int i = 0; i < etuJson.length(); i++) {
                    Etudiant etudiant = etudiantRepo.findById(Integer.parseInt(etuJson.get(i).toString())).get();
                    etudiant.setGroupeEtudiant(groupeEtudiant);
                    etudiantRepo.save(etudiant);
                }
                return "[\"ok\"]";
            } else
                return "[\"-1\"]";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "[\"error\"]";
    }

    @PostMapping("/affectation/eleves/supprimer")
    @ResponseBody
    public String deleteAffectationGrpEleve(@RequestBody String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            Optional<Etudiant> etudiantOptional = etudiantRepo.findById(Integer.parseInt(jsonObject.get("id").toString()));
            if (etudiantOptional.isPresent()) {
                Etudiant etudiant = etudiantOptional.get();
                etudiant.setGroupeEtudiant(null);
                etudiantRepo.save(etudiant);
                return "[\"ok\"]";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "[\"error\"]";
    }

    @PostMapping("/affectation/eleves/affect")
    @ResponseBody
    public String addEleveAffectation(@RequestBody String jsonString) {
        try {

            JSONObject jsonObject = new JSONObject(jsonString);
            Optional<GroupeEtudiant> optionalGroupeEtudiant = groupeEtudiantRepo.findById(Integer.parseInt(jsonObject.get("id").toString()));
            if (optionalGroupeEtudiant.isPresent()) {
                GroupeEtudiant groupeEtudiant = optionalGroupeEtudiant.get();
                JSONArray etuJson = jsonObject.getJSONArray("etudiants");
                for (int i = 0; i < etuJson.length(); i++) {
                    Etudiant etudiant = etudiantRepo.findById(Integer.parseInt(etuJson.get(i).toString())).get();
                    etudiant.setGroupeEtudiant(groupeEtudiant);
                    etudiantRepo.save(etudiant);
                }
                return "[\"ok\"]";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "[\"error\"]";

    }
}
