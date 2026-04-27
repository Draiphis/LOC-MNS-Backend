package com.mns.cda.locmns.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMaterielDto {

    @NotBlank
    private String reference;

}
