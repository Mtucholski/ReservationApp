package com.mtucholski.reservation.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mtucholski.reservation.app.json.CustomAddressDeserializer;
import com.mtucholski.reservation.app.json.CustomAddressSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @ManyToOne(optional = false, cascade = CascadeType.ALL, targetEntity = Clinic.class, fetch = FetchType.LAZY)
    private List<Clinic> clinics;

    @JsonIgnore
    public List<Clinic> getClinicsInternal(){

        if (this.clinics == null){

            this.clinics = new ArrayList<>();
        }

        return this.clinics;
    }

    public List<Clinic> getClinics() {

        List<Clinic> sortedClinics = new ArrayList<>(getClinicsInternal());
        PropertyComparator.sort(sortedClinics, new MutableSortDefinition("address",true, true));
        return Collections.unmodifiableList(sortedClinics);
    }

    public void setClinics(List<Clinic> clinics) {

        this.clinics = clinics;
    }

    public void addClinic(Clinic clinic){

        getClinicsInternal().add(clinic);
    }
}
