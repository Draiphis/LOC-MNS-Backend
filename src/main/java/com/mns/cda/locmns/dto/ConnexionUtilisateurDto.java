package com.mns.cda.locmns.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mns.cda.locmns.model.Role;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class ConnexionUtilisateurDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 36)
    private String password;

}
