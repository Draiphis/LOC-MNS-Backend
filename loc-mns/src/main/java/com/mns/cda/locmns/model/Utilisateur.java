package com.mns.cda.locmns.model;


import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false, unique = true)
    @NotBlank( message = "L'email ne peut pas être vide")
    @Email( message = "L'email est invalide")
    protected String email;

    @Column(nullable = false)
    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    @Size(min = 8, max = 36)
    protected String password;


    @Column(nullable = false)
    @NotBlank(message = "Le nom ne peut pas être vide")
    protected String nom;

    @Column(nullable = false)
    @NotBlank(message = "Le prénom ne peut pas être vide")
    protected String prenom;


    @Past
    @NotNull
    protected LocalDate dateDeNaissance;


    @ManyToMany
    @JoinTable(
            name = "utilisateur_role",
            joinColumns = @JoinColumn(name = "utilisateur_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "demandeur")
    private List<Emprunt> emprunts;

    @OneToMany(mappedBy = "validateurEmprunt")
    private List<Emprunt> empruntsValides;

    @OneToMany(mappedBy = "demandeValidation")
    private List<DemandeUtilisateur> demandesValides;


}
