package com.mtucholski.reservation.app.dto;

import com.mtucholski.reservation.app.model.Specialty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalDoctorDto {

    private String firstName;
    private String LastName;
    private int personal_id;
    private int medicalLicenseNumber;
    private Set<Specialty> specialties;
}
