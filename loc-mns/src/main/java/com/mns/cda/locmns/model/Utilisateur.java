package com.mns.cda.locmns.model;


import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    @Email( message = "L'email est invalide'")
    protected String email;

    @Column(nullable = false)
    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    @Size(min = 8, max = 36)
    protected String password;


    @Column(nullable = false)
    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    protected String nom;

    @Column(nullable = false)
    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    protected String prenom;


    @Past
    @NotBlank
    protected LocalDate dateDeNaissance;


}
