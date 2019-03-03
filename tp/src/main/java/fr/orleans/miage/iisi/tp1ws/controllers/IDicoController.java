package fr.orleans.miage.iisi.tp1ws.controllers;

import exceptions.MaxNbCoupsException;
import exceptions.MotInexistantException;
import exceptions.PseudoDejaPrisException;
import exceptions.PseudoNonConnecteException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface IDicoController {

        ResponseEntity connexion(@RequestParam String pseudo) throws PseudoDejaPrisException;

        ResponseEntity creerPartie(@PathVariable String pseudo, @RequestParam String dicoName) throws PseudoNonConnecteException;

        ResponseEntity listeCoupsPrecedents(@PathVariable String pseudo) throws PseudoNonConnecteException;

        ResponseEntity jouer(@PathVariable String pseudo, @RequestParam String mot) throws MotInexistantException, MaxNbCoupsException, PseudoNonConnecteException;

        ResponseEntity deconnexion(@PathVariable String pseudo) throws PseudoNonConnecteException;

        ResponseEntity listeDicos();

}
