package com.mtucholski.reservation.app.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mtucholski.reservation.app.json.CustomPatientDeserializer;
import com.mtucholski.reservation.app.json.CustomPatientSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "patients")
@Table(name = "patients", uniqueConstraints = @UniqueConstraint(columnNames = {"personal_id", "email"}))
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = CustomPatientSerializer.class)
@JsonDeserialize(using = CustomPatientDeserializer.class)
public class Patient extends Person implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer patient_id;

    @Column(name = "email", nullable = false, unique = true)
    @NotEmpty
    @Email
    private String email;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Visit.class, cascade = CascadeType.ALL)
    private Set<Visit> visits;

    @OneToOne(optional = false, targetEntity = PatientAddress.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private PatientAddress patientAddress;


}
