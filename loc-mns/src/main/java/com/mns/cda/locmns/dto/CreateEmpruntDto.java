package com.mns.cda.locmns.dto;

import com.mns.cda.locmns.model.EtatUsure;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateEmpruntDto {

    @FutureOrPresent
    @NotNull
    protected LocalDate dateDebutEmprunt;

    @FutureOrPresent
    @NotNull
    protected LocalDate dateRetourEmpruntPrevisionelle;



}
