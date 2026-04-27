package com.mns.cda.locmns.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateEmpruntDto {



    @FutureOrPresent
    protected LocalDate dateRetourEmpruntReelle;


}
