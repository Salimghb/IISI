package modelemotus.facade;

import modelemotus.exceptions.MaxNbCoupsException;
import modelemotus.exceptions.MotInexistantException;
import modelemotus.exceptions.PseudoDejaPrisException;
import modelemotus.exceptions.PseudoNonConnecteException;
import modelemotus.modele.Joueur;
import modelemotus.modele.Partie;
import modelemotus.repositories.DicoRepository;
import modelemotus.repositories.JoueurRepository;
import modelemotus.repositories.PartieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
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

    private void checkConnecte(String pseudo) throws PseudoNonConnecteException {

        Optional<Joueur> optionalJoueur = this.joueurRepository.findByNom(pseudo);

        if (!optionalJoueur.isPresent() || !optionalJoueur.get().isEstConnecte()) {
            throw new PseudoNonConnecteException();
        }

    }

    @Override
    public void connexion(String pseudo) throws PseudoDejaPrisException {

        Optional<Joueur> optionalJoueur = this.joueurRepository.findByNom(pseudo);

        if (optionalJoueur.isPresent()) {
            throw new PseudoDejaPrisException();
        }

        this.joueurRepository.save(new Joueur(pseudo, true));

    }

    @Override
    public void deconnexion(String pseudo) throws PseudoNonConnecteException {
        Optional<Joueur> optionalJoueur = this.joueurRepository.findByNom(pseudo);

        if (!optionalJoueur.isPresent() || !optionalJoueur.get().isEstConnecte()) {
            throw new PseudoNonConnecteException();
        }

        Joueur joueur = optionalJoueur.get();
        joueur.setEstConnecte(false);

        this.joueurRepository.save(joueur);
    }

    //TODO
    @Override
    public String jouer(String pseudo, String mot) throws PseudoNonConnecteException, MotInexistantException, MaxNbCoupsException {
        return null;
    }

    @Override
    public void nouvellePartie(String pseudo, String dicoName) throws PseudoNonConnecteException {

    }

    @Override
    public Collection<String> getListeDicos() {
        return null;
    }

    @Override
    public Partie getPartie(String pseudo) throws PseudoNonConnecteException {
        return null;
    }
}
