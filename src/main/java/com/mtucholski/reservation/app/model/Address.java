package com.mtucholski.reservation.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Builder
@Data
public class Address {

    @Tolerate
    public Address(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city")
    @NotNull
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String city;

    @Column(name = "street")
    @NotNull
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String street;

    @Column(name = "street_number")
    @NotNull
    @Pattern(regexp = "[A-Z0-9]+")
    private String streetNumber;

    @Column(name = "zip_code")
    private String zip_code;

    @Column(name = "validation_status", nullable = false)
    @Enumerated (EnumType.STRING)
    private VaildationStatus vaildationStatus;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;
}
