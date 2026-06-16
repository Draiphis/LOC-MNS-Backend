package com.mns.cda.locmns.exception;

import com.mns.cda.locmns.dto.ReponseErreurDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GestionnaireException {

    @ExceptionHandler(DatesEmpruntInvalidesException.class)
    public ResponseEntity<ReponseErreurDto> gestionDatesEmpruntInvalides(
            DatesEmpruntInvalidesException ex) {

        return buildError(
                HttpStatus.BAD_REQUEST,
                "DATES_EMPRUNT_INVALIDES",
                ex.getMessage()
        );
    }

    @ExceptionHandler(DatesEmpruntAbsentesException.class)
    public ResponseEntity<ReponseErreurDto> gestionDatesEmpruntAbsentes(
            DatesEmpruntAbsentesException ex) {

        return buildError(
                HttpStatus.BAD_REQUEST,
                "DATES_EMPRUNT_ABSENTES",
                ex.getMessage()
        );
    }

    private ResponseEntity<ReponseErreurDto> buildError(
            HttpStatus status,
            String code,
            String message) {

        ReponseErreurDto erreur = new ReponseErreurDto(
                status.value(),
                code,
                message,
                LocalDateTime.now()
        );

        return ResponseEntity.status(status).body(erreur);
    }
}