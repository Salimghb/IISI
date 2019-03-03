package fr.orleans.miage.iisi.tp1ws.controllers;

import exceptions.MaxNbCoupsException;
import exceptions.MotInexistantException;
import exceptions.PseudoDejaPrisException;
import exceptions.PseudoNonConnecteException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

public interface IDicoController {

        Response connexion(@RequestParam String pseudo) throws PseudoDejaPrisException;

        Response creerPartie(@PathVariable String pseudo, @RequestParam String dicoName) throws PseudoNonConnecteException;

        List listeCoupsPrecedents(@PathVariable String pseudo) throws PseudoNonConnecteException;

        Response jouer(@PathVariable String pseudo, @RequestParam String mot) throws MotInexistantException, MaxNbCoupsException, PseudoNonConnecteException;

        Response deconnexion(@PathVariable String pseudo) throws PseudoNonConnecteException;

        Collection listeDicos();

}
