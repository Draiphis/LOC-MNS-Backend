package com.mns.cda.locmns.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GestionnaireException {

    @ExceptionHandler(DatesEmpruntInvalidesException.class)
    public ProblemDetail handleDatesInvalides(DatesEmpruntInvalidesException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("DATES_EMPRUNT_INVALIDES");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://api.locmns/errors/dates-invalides"));

        return problem;
    }

    @ExceptionHandler(DatesEmpruntAbsentesException.class)
    public ProblemDetail handleDatesAbsentes(DatesEmpruntAbsentesException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("DATES_EMPRUNT_ABSENTES");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://api.locmns/errors/dates-absentes"));

        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(Exception ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        problem.setTitle("INTERNAL_ERROR");
        problem.setDetail("Une erreur interne est survenue");

        return problem;
    }
}