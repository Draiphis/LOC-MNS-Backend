package com.mns.cda.locmns.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Emprunt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @CreationTimestamp
    @NotNull
    protected LocalDateTime dateDemandeEmprunt;

    @FutureOrPresent
    @NotNull
    protected LocalDate dateDebutEmprunt;

    @FutureOrPresent
    @NotNull
    protected LocalDate dateRetourEmpruntPrevisionelle;

    @FutureOrPresent
    protected LocalDate dateRetourEmpruntReelle;

    @ManyToOne
    @JoinColumn(name = "materiel_id") // FK en base
    private Materiel materiel;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id") // FK en base
    private Utilisateur utilisateur;




}
