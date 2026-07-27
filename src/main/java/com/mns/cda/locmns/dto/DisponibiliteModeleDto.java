package com.mns.cda.locmns.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class DisponibiliteModeleDto {

    private boolean reservable;
    private LocalDate dateDisponibleAPartirDe;
    private long nombreExemplaires;
    private String message;
    private List<LocalDate> datesIndisponibles;
}
