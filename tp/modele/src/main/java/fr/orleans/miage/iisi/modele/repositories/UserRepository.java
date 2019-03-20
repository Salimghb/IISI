package fr.orleans.miage.iisi.modele.repositories;


import fr.orleans.miage.iisi.modele.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByNom(String nom);
}
