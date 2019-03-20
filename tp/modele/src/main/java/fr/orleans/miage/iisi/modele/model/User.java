package fr.orleans.miage.iisi.modele.model;

import fr.orleans.miage.iisi.modele.model.security.Authority;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@Getter
@Setter
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    @Column(name = "USERNAME", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String nom;

    @Column(name = "PASSWORD", length = 100)
    @NotNull
    @Size(min = 4, max = 100)
    private String password;


    private boolean estConnecte;

    @OneToOne
    private Partie partie;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Authority> authorities;

    public User(String nom) {
        this.nom = nom;
    }

    public User(String nom, boolean estConnecte) {
        this.nom = nom;
        this.estConnecte = estConnecte;
    }

}
