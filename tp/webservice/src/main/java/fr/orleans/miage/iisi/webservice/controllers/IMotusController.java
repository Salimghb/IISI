package fr.orleans.miage.iisi.webservice.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import fr.orleans.miage.iisi.modele.exceptions.MaxNbCoupsException;
import fr.orleans.miage.iisi.modele.exceptions.MotInexistantException;
import fr.orleans.miage.iisi.modele.exceptions.PseudoDejaPrisException;
import fr.orleans.miage.iisi.modele.exceptions.PseudoNonConnecteException;

import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;

public interface IMotusController {

    Response connexion(@RequestParam String pseudo) throws PseudoDejaPrisException;

    Response creerPartie(@PathVariable String pseudo, @RequestParam String dicoName) throws PseudoNonConnecteException;

    List listeCoupsPrecedents(@PathVariable String pseudo) throws PseudoNonConnecteException;

    Response jouer(@PathVariable String pseudo, @RequestParam String mot) throws MotInexistantException, MaxNbCoupsException, PseudoNonConnecteException;

    Response deconnexion(@PathVariable String pseudo) throws PseudoNonConnecteException;

    Collection listeDicos();

}
