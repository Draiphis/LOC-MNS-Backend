package com.mns.cda.locmns.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpruntReponseDto {
    private Integer id;
    private String statut;

    private Integer materielId;
    private String materielNom;

    private String demandeurNom;
    private String demandeurPrenom;

    private LocalDate dateDebutEmprunt;
    private LocalDate dateRetourEmpruntPrevisionelle;
}
