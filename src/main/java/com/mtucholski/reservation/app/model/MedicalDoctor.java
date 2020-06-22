package com.mtucholski.reservation.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity(name = "doctors")
@Table(name = "doctors", uniqueConstraints = @UniqueConstraint(columnNames = {"medical_license_number"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalDoctor extends Person{

    @Column(name = "medical_license_number", unique = true)
    @NotEmpty
    @Size(min = 7, max = 7)
    private Integer medicalLicenseNumber;

    @OneToMany(mappedBy = "medicalDoctors",targetEntity = Specialty.class, orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Specialty> specialties;

}
