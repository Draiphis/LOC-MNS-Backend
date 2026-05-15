package com.mns.cda.locmns.model;


import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.locmns.view.TypeView;
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
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(TypeView.class )
    protected Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Le nom ne peut pas être vide")
    @JsonView(TypeView.class )
    protected String nom;

    @ManyToMany
    @JoinTable(
            name = "type_role",
            joinColumns = @JoinColumn(name = "type_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> rolesAutorises;

    @OneToMany(mappedBy = "type")
    private Set<Modele> modeles;
}
