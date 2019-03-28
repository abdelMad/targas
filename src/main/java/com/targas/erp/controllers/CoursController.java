package com.targas.erp.controllers;

import com.targas.erp.dao.IContenuCoursRepo;
import com.targas.erp.dao.ICoursRepo;
import com.targas.erp.dao.IEnseignantRepo;
import com.targas.erp.models.ContenuCours;
import com.targas.erp.models.Cours;
import com.targas.erp.models.Utilisateur;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Optional;

@Controller
public class CoursController {

    @Autowired
    private ICoursRepo coursRepo;

    @Autowired
    private IEnseignantRepo enseignantRepo;

    @Autowired
    private IContenuCoursRepo contenuCoursRepo;

    @GetMapping("/cours")
    public String renderCoursPage(Model model, HttpServletRequest request) {
        Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
        if (utilisateur.isAdmin())
            model.addAttribute("cours", coursRepo.listAll());
        else if (utilisateur.isEnseignant())
            model.addAttribute("cours", coursRepo.coursEnseignant(utilisateur.getId()));
        else if (utilisateur.isEtudiant())
            model.addAttribute("cours", coursRepo.coursEtudiant(utilisateur.getId()));

        return "cours";
    }

    @PostMapping("/cours/enregistrer")
    @ResponseBody
    public String enregistrerEtablissement(@RequestBody String jsonString) {

        JSONObject jsonObject = new JSONObject(jsonString);
        if (jsonObject.get("id").toString().length() == 0) {
            Cours cours = new Cours();
            cours.setNom(jsonObject.get("nom").toString());
            cours.setMasseHoraire(Float.parseFloat(jsonObject.get("mh").toString()));
            coursRepo.save(cours);
            return "[\"ok\"]";
        } else {
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

    @GetMapping("/cours/{id}")
    public String detailCours(@PathVariable("id") Integer id, Model model) {
        Optional<Cours> coursOptional = coursRepo.findById(id);
        if (coursOptional.isPresent()) {
            Cours cours = coursOptional.get();
            model.addAttribute("cours", cours);
            model.addAttribute("cc", new ContenuCours());
        }
        return "cours-detail";
    }

    @PostMapping("/cours/{id}/element/add")
    public String ajouterElement(@PathVariable("id") Integer id, @ModelAttribute("cc") ContenuCours cc, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            Optional<Cours> coursOptional = coursRepo.findById(id);
            if (coursOptional.isPresent()) {
                Cours cours = coursOptional.get();
                Part filePart = request.getPart("file");
                ClassPathResource cpr = new ClassPathResource("/static/uploads/");
                String filename = cc.getNomContenu() + "." + filePart.getContentType().substring(filePart.getContentType().indexOf("/") + 1);
                String fullpath = cpr.getFile().getPath() + "/" + filename;
                InputStream imageProfileInputStream = filePart.getInputStream();
                byte[] buffer = new byte[imageProfileInputStream.available()];
                imageProfileInputStream.read(buffer);
                System.out.println(cpr.exists());
                System.out.println(fullpath);
                cc.setUrl("/uploads/" + filename);
                File targetFile = new File(fullpath);
                if (!targetFile.exists())
                    targetFile.createNewFile();
                OutputStream outStream = new FileOutputStream(targetFile);
                outStream.write(buffer);
                cc.setCours(cours);
                contenuCoursRepo.save(cc);
                redirectAttributes.addFlashAttribute("message", "Modification reussie");

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return "redirect:/cours/" + id;
    }

}
