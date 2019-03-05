package modelemotus.facade;

import modelemotus.exceptions.MaxNbCoupsException;
import modelemotus.exceptions.MotInexistantException;
import modelemotus.exceptions.PseudoDejaPrisException;
import modelemotus.exceptions.PseudoNonConnecteException;
import modelemotus.modele.Dico;
import modelemotus.modele.Joueur;
import modelemotus.modele.Partie;
import modelemotus.repositories.DicoRepository;
import modelemotus.repositories.JoueurRepository;
import modelemotus.repositories.PartieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacadeMotusJpa implements FacadeMotus {

    private DicoRepository dicoRepository;
    private JoueurRepository joueurRepository;
    private PartieRepository partieRepository;

    @Autowired
    public FacadeMotusJpa(DicoRepository dicoRepository, JoueurRepository joueurRepository, PartieRepository partieRepository) {
        this.dicoRepository = dicoRepository;
        this.joueurRepository = joueurRepository;
        this.partieRepository = partieRepository;
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
