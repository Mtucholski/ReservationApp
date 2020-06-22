package com.mtucholski.reservation.app.dto;

import com.mtucholski.reservation.app.model.ClinicAddress;
import com.mtucholski.reservation.app.model.MedicalDoctor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClinicDto {

    private String clinicName;
    private List<MedicalDoctor> doctors;
    private ClinicAddress clinicAddress;
}
