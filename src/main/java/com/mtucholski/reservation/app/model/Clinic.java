package com.mtucholski.reservation.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity(name = "clinic")
@Table(name = "clinic")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
public class Clinic extends BaseEntity {

    @Column(name = "clinic_name", nullable = false)
    @NotEmpty
    @Size(min = 3, max = 250)
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String clinicName;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, targetEntity = Doctor.class )
    @JoinTable(name = "doctors", joinColumns = @JoinColumn(name = "doctor_id"))
    private List<Doctor> doctors;

    @ManyToOne (targetEntity = ClinicAddress.class, cascade = CascadeType.ALL)
    private ClinicAddress clinicAddress;
}
