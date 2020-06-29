package com.mtucholski.reservation.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "doctors")
@Table(name = "doctors", uniqueConstraints = @UniqueConstraint(columnNames = {"license_number"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor extends Person{

    @Column(name = "license_number", unique = true)
    @NotNull
    private Integer medicalLicenseNumber;

    @OneToMany(mappedBy = "doctors",targetEntity = Specialty.class, orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Specialty> specialties;

}
