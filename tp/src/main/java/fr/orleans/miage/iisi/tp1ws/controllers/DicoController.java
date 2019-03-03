package fr.orleans.miage.iisi.tp1ws.controllers;


import exceptions.MaxNbCoupsException;
import exceptions.MotInexistantException;
import exceptions.PseudoDejaPrisException;
import exceptions.PseudoNonConnecteException;
import facade.FacadeMotus;
import facade.FacadeMotusStatic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity connexion(@RequestParam String pseudo) throws PseudoDejaPrisException {
        this.facade.connexion(pseudo);
        return ResponseEntity.ok(CONNEXIONOK);
    }


    //OK
    @PostMapping("/motus/partie/{pseudo}")
    public ResponseEntity creerPartie(@PathVariable String pseudo, @RequestParam String dicoName) throws PseudoNonConnecteException {
        this.facade.nouvellePartie(pseudo, dicoName);
        return ResponseEntity.ok(CREATIONPARTIEOK);
    }

    //OK
    @GetMapping("/motus/partie/{pseudo}")
    public ResponseEntity listeCoupsPrecedents(@PathVariable String pseudo) throws PseudoNonConnecteException {
        return ResponseEntity.ok().body(facade.getPartie(pseudo).getEssais());
    }

    //OK
    @PutMapping("/motus/partie/{pseudo}")
    public ResponseEntity jouer(@PathVariable String pseudo, @RequestParam String mot) throws MotInexistantException, MaxNbCoupsException, PseudoNonConnecteException {
        facade.jouer(pseudo, mot);
        return ResponseEntity.ok(COUPOK);
    }

    //OK
    @DeleteMapping("/motus/{pseudo}")
    public ResponseEntity deconnexion(@PathVariable String pseudo) throws PseudoNonConnecteException {
        facade.deconnexion(pseudo);
        return ResponseEntity.ok(DECONNEXIONOK);
    }

    //OK
    @GetMapping("/motus/dicos")
    public ResponseEntity listeDicos() {
        return ResponseEntity.ok().body(facade.getListeDicos());
    }

}

