package com.mns.cda.locmns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.locmns.view.MarqueView;
import com.mns.cda.locmns.view.ModeleView;
import com.mns.cda.locmns.view.UtilisateurView;
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
public class Marque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({MarqueView.class, ModeleView.class} )
    protected Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Le nom ne peut pas être vide")
    @JsonView({MarqueView.class,ModeleView.class} )
    protected String nom;

    @OneToMany(mappedBy = "marque")
    private Set<Modele> modeles;
}
