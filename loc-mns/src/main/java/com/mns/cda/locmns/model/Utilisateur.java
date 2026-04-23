package com.mns.cda.locmns.model;


import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.locmns.view.UtilisateurView;
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

    public interface OnUpdate{};
    public interface  OnCreate{};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(UtilisateurView.class )
    protected Integer id;

    @Column(nullable = false, unique = true)
    @NotBlank( groups = {OnCreate.class}, message = "L'email ne peut pas être vide")
    @Email( message = "L'email est invalide")
    @JsonView(UtilisateurView.class)
    protected String email;

    @Column(nullable = false)
    @NotBlank( groups = {OnCreate.class,OnUpdate.class},message = "Le mot de passe ne peut pas être vide")
    @Size(min = 8, max = 36)
    protected String password;


    @Column(nullable = false)
    @NotBlank(groups = {OnCreate.class,OnUpdate.class}, message = "Le nom ne peut pas être vide")
    @JsonView(UtilisateurView.class)
    protected String nom;

    @Column(nullable = false)
    @NotBlank(groups = {OnCreate.class,OnUpdate.class}, message = "Le prénom ne peut pas être vide")
    @JsonView(UtilisateurView.class)
    protected String prenom;


    @Past
    @NotNull(groups = {OnCreate.class,OnUpdate.class})
    @JsonView(UtilisateurView.class)
    protected LocalDate dateDeNaissance;


    @ManyToMany()
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
