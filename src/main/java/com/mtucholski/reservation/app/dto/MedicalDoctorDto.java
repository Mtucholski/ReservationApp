package com.mtucholski.reservation.app.dto;

import com.mtucholski.reservation.app.model.Person;
import com.mtucholski.reservation.app.model.Specialty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun.security.util.SecurityProviderConstants;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalDoctorDto extends Person {

    private String firstName;
    private String LastName;
    private int personalID;
    private int medicalLicenseNumber;
    private Set<Specialty> specialties;
}
