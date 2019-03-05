package modelemotus.modele;

import modelemotus.exceptions.MaxNbCoupsException;
import modelemotus.exceptions.MotInexistantException;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La partie d'un joueur, avec ses essais successifs et le dictionnaire utilisé
 */
@Entity
@Data
@NoArgsConstructor
public class Partie {

    public static final int MAX_NB_COUPS = 8;

    @Id
    @GeneratedValue
    private long idPartie;


    private String motRecherche;
    private boolean terminee;

    @OneToOne
    private Joueur joueur;

    @ManyToOne
    private Dico dico;

    @ElementCollection
    private List<String> essais;

    public Partie(Dico dico, Joueur joueur) {
        this.joueur = joueur;
        essais = new ArrayList<>();
        this.dico = dico;
        motRecherche = dico.getRandomMot();
    }

    public String jouer(String mot) throws MotInexistantException, MaxNbCoupsException {
        if (essais.size()>= MAX_NB_COUPS) throw new MaxNbCoupsException();
        String motMaj = mot.toUpperCase();
        essais.add(motMaj);
        // le mot n'existe pas
        if (!dico.isMot(motMaj)) throw new MotInexistantException(mot);

        return correspondance(motMaj);
    }

    public List<String> getEssais() {
        return essais;
    }

    public int getNbEssais() {
        return essais.size();
    }

    /*
    fonction qui recherche la correspondance des lettres entre le mot proposé et le mot recherché

    elle renvoie une chaine avec le résultat pour chaque lettre du mot proposé,
    avec un X pour une lettre à la bonne place
         un m pour une lettre mal placée
         un * pour les mauvaises lettres

    Exemples : si le mot recherché est CITRON, on a :
            le mot proposé  CASTOR
            renvoie         X**mXm
            le mot proposé  CINEMA
            renvoie         XXm***
            le mot proposé  CYPRES
            renvoie         X**X**
            le mot proposé  CITRON
            renvoie         XXXXXX
     */
    public String correspondance(String mot) {
        char[] resultat = new char[mot.length()];
        boolean[] match = new boolean[motRecherche.length()];
        // marque les lettres à la bonne place
        for(int i = 0; i<mot.length();i++) {
            char c = mot.charAt(i);
            if (c==motRecherche.charAt(i)) {
                resultat[i]='X';
                match[i] = true;
            } else {
                resultat[i]='*';
                match[i] = false;
            }
        }
        // les lettres à la mauvaise place
        for(int i = 0; i<mot.length();i++) {
            if (resultat[i]!='X') {
                char c = mot.charAt(i);
                int start = 0;
                while (start>=0&&start<motRecherche.length()) {
                    int j = motRecherche.indexOf(c,start);
                    if (j>=0 && !match[j]) {
                        resultat[i] = 'm';
                        match[j]=true;
                        start = -1;
                    } else {
                        start = (j>=0?j+1:j);
                    }
                }
            }
        }
        return new String(resultat);
    }

    public String getMotRecherche() {
        return motRecherche;
    }

    public void setMotRecherche(String motRecherche) {
        this.motRecherche = motRecherche;
    }


    public static int getMaxNbCoups() {
        return MAX_NB_COUPS;
    }

    public long getIdPartie() {
        return idPartie;
    }

    public void setIdPartie(long idPartie) {
        this.idPartie = idPartie;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public Dico getDico() {
        return dico;
    }

    public void setDico(Dico dico) {
        this.dico = dico;
    }

    public void setEssais(List<String> essais) {
        this.essais = essais;
    }

    public boolean isTerminee() {
        return terminee;
    }

    public void setTerminee(boolean terminee) {
        this.terminee = terminee;
    }

}
