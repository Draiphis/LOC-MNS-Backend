package com.mns.cda.locmns.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockParTypeDto {

    private Integer id;

    @NotBlank
    private String nom;



    private Long nbModeleEnStock;


}
