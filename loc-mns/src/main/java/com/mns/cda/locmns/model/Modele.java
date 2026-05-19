package com.mns.cda.locmns.model;


import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.locmns.view.MaterielView;
import com.mns.cda.locmns.view.ModeleView;
import com.mns.cda.locmns.view.UtilisateurView;
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
    @JsonView({ModeleView.class, MaterielView.class})
    protected Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Le nom ne peut pas être vide")
    @JsonView({ModeleView.class, MaterielView.class} )
    protected String nom;

    @Column()
    @JsonView({ModeleView.class, MaterielView.class} )
    protected String image;


    @Column(columnDefinition = "TEXT")
    @Length(max = 500)
    @JsonView({ModeleView.class, MaterielView.class} )
    protected String description;

    @ManyToOne
    @JoinColumn(name = "type_id") // FK en base
    @JsonView(ModeleView.class )
    private Type type;

    @ManyToOne
    @JoinColumn(name = "marque_id") // FK en base
    @JsonView(ModeleView.class )
    private Marque marque;

    @OneToMany(mappedBy = "modele")
    private Set<Materiel> materiaux;

}
