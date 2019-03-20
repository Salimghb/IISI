package fr.orleans.miage.iisi.modele.model;

import fr.orleans.miage.iisi.modele.exceptions.MaxNbCoupsException;
import fr.orleans.miage.iisi.modele.exceptions.MotInexistantException;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La partie d'un user, avec ses essais successifs et le dictionnaire utilisé
 */
@Entity
@Data
@NoArgsConstructor
@Getter
@Setter
public class Partie {

    public static final int MAX_NB_COUPS = 8;

    @Id
    @GeneratedValue
    private long idPartie;


    private String motRecherche;
    private boolean terminee;

    @OneToOne
    private User user;

    @ManyToOne
    private Dico dico;

    @ElementCollection
    private List<String> essais;

    public Partie(Dico dico, User user) {
        this.user = user;
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

}
