package com.mtucholski.reservation.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity(name = "patient_address")
@Table(name = "patient_address")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PatientAddress extends BaseEntity {

    @Column (name = "city", nullable = false)
    @NotEmpty
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String city;

    @Column(name = "street", nullable = false)
    @NotEmpty
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String street;

    @Column(name = "flat_number")
    @NotEmpty
    @Digits(fraction = 0, integer = 3, message = "please give flat number")
    private String flatNumber;

    @OneToOne(orphanRemoval = true, fetch = FetchType.LAZY, targetEntity = Patient.class, cascade = CascadeType.ALL, mappedBy = "patientAddress")
    private Patient patient;

}
