package com.mtucholski.reservation.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity(name = "specialties")
@Table(name = "specialties")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode (callSuper = false)
public class Specialty extends BaseEntity {

    @Column(nullable = false, unique = true)
    @NotEmpty
    private String specialtyName;

    @ManyToOne(optional = false,fetch = FetchType.LAZY, targetEntity = MedicalDoctor.class, cascade = CascadeType.PERSIST)
    private MedicalDoctor medicalDoctors;
}
