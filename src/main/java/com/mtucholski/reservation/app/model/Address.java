package com.mtucholski.reservation.app.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mtucholski.reservation.app.json.CustomAddressDeserializer;
import com.mtucholski.reservation.app.json.CustomAddressSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = CustomAddressSerializer.class)
@JsonDeserialize(using = CustomAddressDeserializer.class)
public class Address extends BaseEntity {

    @Column (name = "city", nullable = false)
    @NotEmpty
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String city;

    @Column(name = "street", nullable = false)
    @NotEmpty
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String street;

    @Column(name = "street_number", nullable = false)
    @NotEmpty
    @Digits(fraction = 0, integer = 4, message = "please give street number")
    private String streetNumber;

    @Column(name = "flat_number")
    @NotEmpty
    @Digits(fraction = 0, integer = 3, message = "please give flat number")
    private String flatNumber;

    @OneToOne(orphanRemoval = true, fetch = FetchType.LAZY, targetEntity = Patient.class, cascade = CascadeType.ALL, mappedBy = "patientAddress")
    private Patient patient;

    @ManyToOne(targetEntity = Clinic.class, cascade =CascadeType.ALL, fetch = FetchType.LAZY)
    private Clinic clinic;
}
