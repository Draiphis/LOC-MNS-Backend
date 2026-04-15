package com.mns.cda.locmns.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DemandeUtilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @FutureOrPresent
    protected LocalDate dateRetourAnticipe;

    @FutureOrPresent
    protected LocalDate dateProlongation;

    protected Boolean dysfonctionnement;

    @Column(columnDefinition = "TEXT")
    @Length(max = 1500)
    protected String messageDescription;

    @FutureOrPresent
    protected LocalDate dateValidation;

    @Column(columnDefinition = "TEXT")
    @Length(max = 1500)
    protected String messageValidation;

    protected Boolean valide;


    @ManyToOne
    @JoinColumn(name = "emprunt_id") // FK en base
    private Emprunt demandeConcernantEmprunt;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id") // FK en base
    private Emprunt demandeValidation;

}
