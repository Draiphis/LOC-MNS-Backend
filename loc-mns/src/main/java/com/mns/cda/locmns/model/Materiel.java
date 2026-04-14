package com.mns.cda.locmns.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Materiel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "La référence ne peut pas être vide")
    protected String reference;

    @ManyToOne
    @JoinColumn(name = "modele_id") // FK en base
    private Modele modele;

    @OneToMany(mappedBy = "materiel")
    private Set<Emprunt> emprunts;
}
