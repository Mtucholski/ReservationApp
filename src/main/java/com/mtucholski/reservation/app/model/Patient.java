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
import java.util.Set;

@Entity(name = "patients")
@Table(name = "patients", uniqueConstraints = @UniqueConstraint(columnNames = {"personal_id", "email"}))
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = CustomPatientSerializer.class)
@JsonDeserialize(using = CustomPatientDeserializer.class)
public class Patient extends Person {


    @Column(name = "email", nullable = false, unique = true)
    @NotEmpty
    @Email
    private String email;

    @ManyToMany(fetch = FetchType.LAZY,targetEntity = Visit.class, cascade = CascadeType.ALL)
    @JoinTable(name="visit", joinColumns = @JoinColumn(name = "visit_id"))
    private Set<Visit> visits;

    @OneToOne(optional = false, targetEntity = Address.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Address address;


    @OneToOne(optional = false, fetch = FetchType.EAGER, targetEntity = Address.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "patient")
    private Address patientAddress;

}
