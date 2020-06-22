package com.mtucholski.reservation.app.dto;


import com.mtucholski.reservation.app.model.MedicalDoctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialtyDto {

    private String specialtyName;
    private MedicalDoctor doctor;
}
