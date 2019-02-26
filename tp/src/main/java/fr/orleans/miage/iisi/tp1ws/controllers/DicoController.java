package fr.orleans.miage.iisi.tp1ws.controllers;


import exceptions.MaxNbCoupsException;
import exceptions.MotInexistantException;
import exceptions.PseudoDejaPrisException;
import exceptions.PseudoNonConnecteException;
import facade.FacadeMotus;
import facade.FacadeMotusStatic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DicoController implements IDicoController {

    private static final String CREATIONPARTIEOK = "Création de la partie réussie !";
    private static final String PASCONNECTE = "Vous n'êtes pas connecté !";
    private static final String PSEUDODEJAPRIS = "Le pseudo est déjà utilisé !";
    private static final String CONNEXIONOK = "Connexion réussie !";
    private static final String MOTINEXISTANT = "Mot inexistant !";
    private static final String LIMITEMAXCOUPS = "Vous avez atteint la limite maximum de coups autorisés !";
    private static final String COUPOK = "Coup joué !";
    private static final String DECONNEXIONOK = "Deconnexion réussie !";

    private FacadeMotus facade;

    DicoController() {
        this.facade = new FacadeMotusStatic();
    }

    //OK
    @PostMapping("/motus")
    public ResponseEntity connexion(@RequestParam String pseudo) {
        ResponseEntity reponse = null;
        try {
            this.facade.connexion(pseudo);
            reponse = ResponseEntity.ok(CONNEXIONOK);
        } catch (PseudoDejaPrisException e) {
            reponse = ResponseEntity.status(HttpStatus.CONFLICT).body(PSEUDODEJAPRIS);
        }
        return reponse;
    }

    //OK
    @PostMapping("/motus/partie/{pseudo}")
    public ResponseEntity creerPartie(@PathVariable String pseudo, @RequestParam String dicoName) {
        ResponseEntity reponse = null;
        try {
            this.facade.nouvellePartie(pseudo, dicoName);
            reponse = ResponseEntity.ok(CREATIONPARTIEOK);
        } catch (PseudoNonConnecteException e) {
            reponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PASCONNECTE);
        }
        return reponse;
    }

    //OK
    @GetMapping("/motus/partie/{pseudo}")
    public ResponseEntity listeCoupsPrecedents(@PathVariable String pseudo) {
        ResponseEntity reponse = null;
        try {
            reponse = ResponseEntity.ok().body(facade.getPartie(pseudo).getEssais());
        } catch (PseudoNonConnecteException e) {
            reponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PASCONNECTE);
        }
        return reponse;
    }

    //OK
    @PutMapping("/motus/partie/{pseudo}")
    public ResponseEntity jouer(@PathVariable String pseudo, @RequestParam String mot) {
        ResponseEntity reponse = null;
        try {
            facade.jouer(pseudo, mot);
            reponse = ResponseEntity.ok(COUPOK);
        } catch (PseudoNonConnecteException e) {
            reponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PASCONNECTE);
        } catch (MotInexistantException e) {
            reponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MOTINEXISTANT);
        } catch (MaxNbCoupsException e) {
            reponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(LIMITEMAXCOUPS);
        }
        return reponse;
    }

    //OK
    @DeleteMapping("/motus/{pseudo}")
    public ResponseEntity deconnexion(@PathVariable String pseudo) {
        ResponseEntity reponse = null;
        try {
            facade.deconnexion(pseudo);
            reponse = ResponseEntity.ok(DECONNEXIONOK);
        } catch (PseudoNonConnecteException e) {
            reponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PASCONNECTE);
        }
        return reponse;
    }

    //OK
    @GetMapping("/motus/dicos")
    public ResponseEntity listeDicos() {
        return ResponseEntity.ok().body(facade.getListeDicos());
    }


}
