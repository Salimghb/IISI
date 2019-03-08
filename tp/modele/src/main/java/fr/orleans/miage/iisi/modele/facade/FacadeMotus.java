package fr.orleans.miage.iisi.modele.facade;

import fr.orleans.miage.iisi.modele.exceptions.MaxNbCoupsException;
import fr.orleans.miage.iisi.modele.exceptions.MotInexistantException;
import fr.orleans.miage.iisi.modele.exceptions.PseudoDejaPrisException;
import fr.orleans.miage.iisi.modele.exceptions.PseudoNonConnecteException;
import fr.orleans.miage.iisi.modele.modele.Dico;
import fr.orleans.miage.iisi.modele.modele.Joueur;
import fr.orleans.miage.iisi.modele.modele.Partie;
import fr.orleans.miage.iisi.modele.repositories.DicoRepository;
import fr.orleans.miage.iisi.modele.repositories.JoueurRepository;
import fr.orleans.miage.iisi.modele.repositories.PartieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FacadeMotus implements IFacadeMotus {

    @Autowired
    private DicoRepository dicoRepository;
    @Autowired
    private JoueurRepository joueurRepository;
    @Autowired
    private PartieRepository partieRepository;

    public FacadeMotus() {
    }

    public void initDico(String dicoName){
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("model/"+dicoName+".txt");
        BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
        List<String> liste = new ArrayList<>();
        buffer.lines().map(String::toUpperCase).forEach(liste::add);
        Dico dico = new Dico(dicoName);
        dico.setListMots(liste);
        dicoRepository.save(dico);
    }

    private Joueur checkConnecte(String pseudo) throws PseudoNonConnecteException {

        Optional<Joueur> optionalJoueur = joueurRepository.findByNom(pseudo);

        if (!optionalJoueur.isPresent() || !optionalJoueur.get().isEstConnecte()) {
            throw new PseudoNonConnecteException();
        }

        return optionalJoueur.get();

    }

    @Override
    public void connexion(String pseudo) throws PseudoDejaPrisException {

        Optional<Joueur> optionalJoueur = joueurRepository.findByNom(pseudo);

        if (optionalJoueur.isPresent()) {
            throw new PseudoDejaPrisException();
        }

        joueurRepository.save(new Joueur(pseudo, true));

    }

    @Override
    public void deconnexion(String pseudo) throws PseudoNonConnecteException {
        Optional<Joueur> optionalJoueur = joueurRepository.findByNom(pseudo);

        if (!optionalJoueur.isPresent() || !optionalJoueur.get().isEstConnecte()) {
            throw new PseudoNonConnecteException();
        }

        Joueur joueur = optionalJoueur.get();
        joueur.setEstConnecte(false);
        joueur.getPartie().setTerminee(true);

        joueurRepository.save(joueur);
        partieRepository.save(joueur.getPartie());
    }

    //TODO

    @Override
    public String jouer(String pseudo, String mot) throws MotInexistantException, MaxNbCoupsException, PseudoNonConnecteException {
        Joueur joueur = this.checkConnecte(pseudo);
        Partie p = joueur.getPartie();
        String corresp = p.jouer(mot);
        partieRepository.save(p);

        return corresp;
    }


    @Override
    public void nouvellePartie(String pseudo, String dicoName) throws PseudoNonConnecteException {
        Joueur joueur = this.checkConnecte(pseudo);
        partieRepository.save(new Partie(Dico.getInstance(dicoName), joueur));
    }

    @Override
    public List<Dico> getListeDicos(){
        return dicoRepository.findAll();
    }

    @Override
    public Partie getPartie(String pseudo) throws PseudoNonConnecteException {
        Joueur joueur = this.checkConnecte(pseudo);
        return joueur.getPartie();
    }



}
