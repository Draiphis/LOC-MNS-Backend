package com.mns.cda.locmns.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

public class Etat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotBlank
    protected EtatUsure usure;
}
