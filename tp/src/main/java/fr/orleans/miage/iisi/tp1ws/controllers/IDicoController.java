package fr.orleans.miage.iisi.tp1ws.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface IDicoController {

        ResponseEntity connexion(@RequestParam String pseudo);

        ResponseEntity creerPartie(@PathVariable String pseudo, @RequestParam String dicoName);

        ResponseEntity listeCoupsPrecedents(@PathVariable String pseudo);

        ResponseEntity jouer(@PathVariable String pseudo, @RequestParam String mot);

        ResponseEntity deconnexion(@PathVariable String pseudo);

        ResponseEntity listeDicos();

}
