package fr.orleans.miage.iisi.modele.facade;

import fr.orleans.miage.iisi.modele.exceptions.MaxNbCoupsException;
import fr.orleans.miage.iisi.modele.exceptions.MotInexistantException;
import fr.orleans.miage.iisi.modele.exceptions.PseudoDejaPrisException;
import fr.orleans.miage.iisi.modele.exceptions.PseudoNonConnecteException;
import fr.orleans.miage.iisi.modele.modele.Dico;
import fr.orleans.miage.iisi.modele.modele.Partie;

import java.util.List;

 public interface IFacadeMotus {
    /**
     * Lance une nouvelle partie avec le pseudo
     *
     * @param pseudo le pseudo du joueur
     * @return true si la connexion est possible (aucun pseudo identique existant) et false sinon
     * @exception PseudoDejaPrisException : lorsque le pseudo est déjà pris
     */
     void connexion(String pseudo) throws PseudoDejaPrisException;

     void initDico(String dicoName);

    /**
     * Deconnecte un joureur
     *
     * @param pseudo le pseudo du joueur
     * @exception PseudoNonConnecteException : le pseudo voulant se déconnecter n'est pas connecté
     */
     void deconnexion(String pseudo) throws PseudoNonConnecteException;

    /**
     * Le joueur pseudo veut jouer le mot "mot" dans la partie en cours
     *
     * @param pseudo
     * @param mot
     * @return les lettres du mots à la bonne position 'X', à la mauvaise position 'm' et '*' pour les autres lettres
     * @throws MotInexistantException   Le mot n'est pas valide (pas dans le dictionnaire)
     * @throws MaxNbCoupsException      Le nombre maximum de coups a été joué
     */
     String jouer(String pseudo, String mot) throws PseudoNonConnecteException, MotInexistantException, MaxNbCoupsException;


    /**
     * Permet de relancer une nouvelle partie pour le pseudo
     * @param pseudo
     * @param dicoName : fichier à charger pour le dictionnaire
     * @exception PseudoNonConnecteException : le pseudo voulant se déconnecter n'est pas connecté
    */
    void nouvellePartie(String pseudo, String dicoName)  throws PseudoNonConnecteException;


    /**
     *
     * @return la liste des dictionnaires disponibles.
     */
    List<Dico> getListeDicos();

    /**
     * Permet d'obtenir la partie du joueur pseudo
     *
     * @param pseudo
     * @return la partie du joueur
     * @exception PseudoNonConnecteException : le pseudo voulant se déconnecter n'est pas connecté
     */
    Partie getPartie(String pseudo)  throws PseudoNonConnecteException;
}
