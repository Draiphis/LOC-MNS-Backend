package com.mns.cda.locmns.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterielDisponibleDto {

    @NotNull
    private Integer id;

    @NotBlank
    private String reference;

    @NotNull
    private Integer modeleId;

    @NotBlank
    private String modeleNom;

}
