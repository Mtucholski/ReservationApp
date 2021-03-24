package com.mtucholski.reservation.app.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Entity
@Builder
@Data
public class Clinic implements Serializable, Cloneable {

    @Tolerate
    public  Clinic(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clinicId;

    @Column(name = "creation_date", updatable = false, nullable = false)
    @CreationTimestamp
    private Instant creationDate;

    @Column(name = "close_date", nullable = false)
    @CreationTimestamp
    private Instant closeDate;

    @Column(name = "clinic_type", updatable = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private ClinicType clinicType;

    @Column(name = "clinic_status")
    @Enumerated(EnumType.STRING)
    private ClinicStatus clinicStatus;

    @Column(name = "clinic_name", nullable = false)
    @NotEmpty
    @Size(min = 3, max = 250)
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String clinicName;

    @Column(name = "validation_status", nullable = false)
    private VaildationStatus vaildationStatus;

    @JsonManagedReference
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "clinic", orphanRemoval = true, cascade = CascadeType.ALL, targetEntity = Address.class, fetch = FetchType.EAGER)
    private List<Address> addresses;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL, targetEntity = Doctor.class )
    @JoinTable(name = "doctors", joinColumns = @JoinColumn(name = "doctor_id"))
    @JsonManagedReference
    @LazyCollection(LazyCollectionOption.FALSE)
    private Doctor doctors;


    @Override
    public Object clone() throws CloneNotSupportedException {

        return super.clone();
    }
}
