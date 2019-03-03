package fr.orleans.miage.iisi.tp1ws.controllers;


import exceptions.MaxNbCoupsException;
import exceptions.MotInexistantException;
import exceptions.PseudoDejaPrisException;
import exceptions.PseudoNonConnecteException;
import facade.FacadeMotus;
import facade.FacadeMotusStatic;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;

@RestController
public class DicoController implements IDicoController {

    private static final String CREATIONPARTIEOK = "Création de la partie réussie !";
    private static final String CONNEXIONOK = "Connexion réussie !";
    private static final String COUPOK = "Coup joué !";
    private static final String DECONNEXIONOK = "Deconnexion réussie !";

    private FacadeMotus facade;

    DicoController() {
        this.facade = new FacadeMotusStatic();
    }

    //OK
    @PostMapping("/motus")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response connexion(String pseudo) throws PseudoDejaPrisException {
        this.facade.connexion(pseudo);
        return Response.ok(CONNEXIONOK).build();
    }

    //OK
    @PostMapping("/motus/partie/{pseudo}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response creerPartie(@PathVariable String pseudo, String dicoName) throws PseudoNonConnecteException {
        this.facade.nouvellePartie(pseudo, dicoName);
        return Response.ok(CREATIONPARTIEOK).build();
    }

    //OK
    @GetMapping("/motus/partie/{pseudo}")
    @Produces(MediaType.APPLICATION_JSON)
    public List listeCoupsPrecedents(@PathVariable String pseudo) throws PseudoNonConnecteException {
        return facade.getPartie(pseudo).getEssais();
    }

    //OK
    @PutMapping("/motus/partie/{pseudo}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response jouer(@PathVariable String pseudo, String mot) throws MotInexistantException, MaxNbCoupsException, PseudoNonConnecteException {
        facade.jouer(pseudo, mot);
        return Response.ok(COUPOK).entity(mot).build();
    }

    //OK
    @DeleteMapping("/motus/{pseudo}")
    public Response deconnexion(@PathVariable String pseudo) throws PseudoNonConnecteException {
        facade.deconnexion(pseudo);
        return Response.ok(DECONNEXIONOK).build();
    }

    //OK
    @GetMapping("/motus/dicos")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection listeDicos() {
        return facade.getListeDicos();
    }

}

