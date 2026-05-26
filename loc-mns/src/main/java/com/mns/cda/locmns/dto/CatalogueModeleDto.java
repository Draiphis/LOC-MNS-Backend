package com.mns.cda.locmns.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CatalogueModeleDto {

    private Integer id;

    @NotBlank
    private String nom;

    @NotBlank
    private String description;

    @NotBlank
    private String image;

    private int stockDisponible;

    private boolean estDisponible;

}
