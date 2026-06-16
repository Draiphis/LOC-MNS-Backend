package com.mns.cda.locmns.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReponseErreurDto {
    private final int status;
    private final String code;
    private final String message;
    private final LocalDateTime dateErreur;
}


