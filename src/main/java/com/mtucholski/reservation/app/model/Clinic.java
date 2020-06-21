package com.mtucholski.reservation.app.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mtucholski.reservation.app.json.CustomClinicDeserializer;
import com.mtucholski.reservation.app.json.CustomClinicSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "clinic")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
@JsonDeserialize(using = CustomClinicDeserializer.class)
@JsonSerialize(using = CustomClinicSerializer.class)
public class Clinic extends BaseEntity {

    @Column(name = "clinic_name", nullable = false)
    @NotEmpty
    @Size(min = 3, max = 250)
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String clinicName;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, targetEntity = MedicalDoctor.class )
    @JoinTable(name = "doctors", joinColumns = @JoinColumn(name = "doctor_id"))
    private List<MedicalDoctor> doctors;

    @OneToMany (targetEntity = Address.class, cascade = CascadeType.ALL, mappedBy = "clinic")
    private List<Address> clinicAddress;
}
