package com.mtucholski.reservation.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import java.util.*;

@Entity(name ="MedicalDoctor")
@Table(name = "medical_doctor", uniqueConstraints = @UniqueConstraint(columnNames = {"medicalLicenseNumber"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MedicalDoctor extends Person{

    @Column(name = "medical_license_number", unique = true)
    @NotEmpty
    @Size(min = 7, max = 7)
    private Integer medicalLicenseNumber;

    @OneToMany(mappedBy = "medicalDoctors",targetEntity = Specialty.class, orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Specialty> specialties;

    @XmlElement
    public List<Specialty> getSpecialties() {
        List<Specialty> sortedSpecs = new ArrayList<>(getSpecialtiesInternal());
        PropertyComparator.sort(sortedSpecs, new MutableSortDefinition("name", true, true));
        return Collections.unmodifiableList(sortedSpecs);
    }

    @JsonIgnore
    public Set<Specialty> getSpecialtiesInternal() {
        if (this.specialties == null) {
            this.specialties = new HashSet<>();
        }
        return this.specialties;
    }

    @JsonIgnore
    public int getNrOfSpecialties() {
        return getSpecialtiesInternal().size();
    }


    public void addSpecialty(Specialty specialty) {
        getSpecialtiesInternal().add(specialty);
    }

    public void clearSpecialties() {
        getSpecialtiesInternal().clear();
    }
}
