package com.mns.cda.locmns.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class Marque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Le nom ne peut pas être vide")
    protected String nom;

    @OneToMany(mappedBy = "marque")
    private Set<Modele> modeles;
}
