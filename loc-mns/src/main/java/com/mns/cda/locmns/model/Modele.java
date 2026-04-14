package com.mns.cda.locmns.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Modele {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Le nom ne peut pas être vide")
    protected String nom;


    @Column(columnDefinition = "TEXT")
    @Length(max = 500)
    protected String description;

    @ManyToOne
    @JoinColumn(name = "type_id") // FK en base
    private Type type;

    @ManyToOne
    @JoinColumn(name = "marque_id") // FK en base
    private Marque marque;

    @OneToMany(mappedBy = "modele")
    private Set<Materiel> materiaux;

}
