package com.mns.cda.locmns.dto;

import com.mns.cda.locmns.model.EtatUsure;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEtatDto {

    @NotBlank
    private EtatUsure usure;

}
