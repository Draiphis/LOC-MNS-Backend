package com.mns.cda.locmns.model;

import com.fasterxml.jackson.annotation.JsonView;
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
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Le nom ne peut pas être vide")
    @Enumerated(EnumType.STRING)
    @JsonView(UtilisateurView.class)
    protected RoleNom role;

    @ManyToMany(mappedBy = "roles")
    private Set<Utilisateur> utilisateurs;

    @ManyToMany(mappedBy = "rolesAutorises")
    private Set<Type> types;
}
