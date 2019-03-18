package com.targas.erp.controllers;

import com.targas.erp.dao.IAnneeScolaireRepo;
import com.targas.erp.dao.ICoursRepo;
import com.targas.erp.dao.IEmploiRepo;
import com.targas.erp.models.Emploi;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
public class EmploiController {

    @Autowired
    private IEmploiRepo emploiRepo;

    @Autowired
    private ICoursRepo coursRepo;

    @Autowired
    private IAnneeScolaireRepo anneeScolaireRepo;

    @GetMapping("/emploi")
    public String renderEmploi(Model model) {
        model.addAttribute("emplois", emploiRepo.findAll());
        model.addAttribute("as", anneeScolaireRepo.findAll());

        return "emploi";
    }

    @PostMapping("/emploi/add")
    @ResponseBody
    public String creerEmploi(@RequestBody String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dd = sdf.parse(jsonObject.get("dateDebut").toString());
            Date df = sdf.parse(jsonObject.get("dateFin").toString());
            if(emploiRepo.existsEmploiByDates(dd,df) != 0)
                return "[\"exists\"]";
            Emploi emploi = new Emploi();
            emploi.setNom(jsonObject.get("nom").toString());
            emploi.setDateDebut(dd);
            emploi.setDateFin(df);

            emploiRepo.save(emploi);
            return "[\"ok\"]";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "[\"error\"]";
    }

    @GetMapping("/emploi/edit/{id}")
    public String modifierEmploi(Model model, @PathVariable("id") int id, HttpServletResponse response) throws IOException {
        Optional<Emploi> optionalEmploi = emploiRepo.findById(id);
        if(optionalEmploi.isPresent())
            model.addAttribute("emploi",optionalEmploi.get());
        else
            response.sendError(HttpServletResponse.SC_NOT_FOUND);

        return "emploi-edit";
    }
}
