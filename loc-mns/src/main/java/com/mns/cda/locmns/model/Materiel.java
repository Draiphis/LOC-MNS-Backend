package com.mns.cda.locmns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.locmns.view.MaterielView;
import com.mns.cda.locmns.view.ModeleView;
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
    @JsonView(MaterielView.class )
    protected Integer id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "La référence ne peut pas être vide")
    @JsonView(MaterielView.class )
    protected String reference;

    @ManyToOne
    @JoinColumn(name = "modele_id") // FK en base
    @JsonView(MaterielView.class )
    private Modele modele;

    @OneToMany(mappedBy = "materiel")
    private Set<Emprunt> emprunts;

    @OneToMany(mappedBy = "materiel")
    private Set<Documentation> documents;
}
