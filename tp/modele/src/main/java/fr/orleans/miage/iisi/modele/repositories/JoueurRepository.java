package fr.orleans.miage.iisi.modele.repositories;


import fr.orleans.miage.iisi.modele.modele.Joueur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JoueurRepository extends JpaRepository<Joueur, String> {
    Optional<Joueur> findByNom(String nom);
}
