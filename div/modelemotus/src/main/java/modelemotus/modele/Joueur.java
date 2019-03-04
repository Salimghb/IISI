package modelemotus.modele;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
public class Joueur {

    @Id
    private String nom;
    private boolean estConnecte;

    @OneToOne
    private Partie partie;

    public Joueur(String nom) {
        this.nom = nom;
    }

    public Joueur(String nom, boolean estConnecte) {
        this.nom = nom;
        this.estConnecte = estConnecte;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
