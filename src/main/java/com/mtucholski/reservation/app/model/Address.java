package com.mtucholski.reservation.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Optional;

@Entity
@Builder
@Data
@Scope()
public class Address {

    @Tolerate
    public Address(){}


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city")
    @NotNull
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String city;

    @Column(name = "street")
   // @NotNull
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String street;

    @Column(name = "street_number")
    @NotNull
    @Pattern(regexp = "[A-Z0-9]+")
    private String streetNumber;

    @Column(name = "zip_code")
    private String zip_code;

    @Column(name = "validation_status", nullable = false)
    @Enumerated (EnumType.STRING)
    private ValidationStatus validationStatus;

    @JsonBackReference
    @ManyToOne(targetEntity = Clinic.class)
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

//    public void setAddressStreet(String street){
//
//        Address address = new Address();
//        address.setStreet(street);
//
//        String a = "mama";
//        String b = "tata";
//        b= "wujek";//  stwórz nowy obiekt tekstowy wujek i przypisz referencje do niego do zmiennej b
//    }
}
