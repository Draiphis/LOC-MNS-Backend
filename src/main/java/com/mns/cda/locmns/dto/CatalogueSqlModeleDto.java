package com.mns.cda.locmns.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CatalogueSqlModeleDto {

    private Integer id;

    @NotBlank
    private String nom;

    @NotBlank
    private String description;

    @NotBlank
    private String image;

    private Long stockDisponible;


}
