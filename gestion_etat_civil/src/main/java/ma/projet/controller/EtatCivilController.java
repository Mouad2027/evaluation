package ma.projet.controller;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/etat-civil")
public class EtatCivilController {

    @Autowired
    private HommeService hommeService;

    @Autowired
    private FemmeService femmeService;

    @Autowired
    private MariageService mariageService;

    @GetMapping("/hommes")
    public List<Homme> getAllHommes() {
        return hommeService.findAll();
    }

    @GetMapping("/femmes")
    public List<Femme> getAllFemmes() {
        return femmeService.findAll();
    }

    @GetMapping("/femmes/plus-agee")
    public ResponseEntity<Femme> getFemmePlusAgee() {
        Femme femme = femmeService.getFemmeLaPlusAgee();
        return femme != null ? ResponseEntity.ok(femme) : ResponseEntity.notFound().build();
    }

    @GetMapping("/femmes/mariees-deux-fois")
    public List<Femme> getFemmesMarieesDeuxFois() {
        return femmeService.getFemmesMarieesAuMoinsDeuxFois();
    }

    @GetMapping("/hommes/{hommeId}/mariages")
    public ResponseEntity<?> getMariagesHomme(@PathVariable Long hommeId) {
        try {
            hommeService.afficherMariagesHomme(hommeId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }
}