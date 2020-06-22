package com.mtucholski.reservation.app.dto;

import com.mtucholski.reservation.app.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitDto {

    private LocalDate date;
    private String visitDescription;
    private Patient patient;
}
