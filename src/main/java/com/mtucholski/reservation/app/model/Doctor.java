package com.mtucholski.reservation.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode()
@Entity(name = "doctors")
@Table(name = "doctors", uniqueConstraints = @UniqueConstraint(columnNames = {"license_number"}))
@Data
@Builder
public class Doctor implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotEmpty
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String specialtyName;

    @Column(name = "license_number", unique = true, updatable = false)
    @NotNull
    @Size(min = 11, max=11)
    private Integer medicalLicenseNumber;

    @Column(name = "validation_status", nullable = false)
    private VaildationStatus vaildationStatus;

    @Tolerate
    public Doctor(){

    }

    @JsonBackReference
    @OneToMany(mappedBy = "clinic", targetEntity = Clinic.class, orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Clinic> clinicList;
}
