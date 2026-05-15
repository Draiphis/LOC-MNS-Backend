package com.mns.cda.locmns.dto;

import com.mns.cda.locmns.model.RoleNom;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRoleDto {

    @NotBlank
    private RoleNom role;

}
