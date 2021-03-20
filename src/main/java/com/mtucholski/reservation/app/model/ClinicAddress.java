package com.mtucholski.reservation.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "clinic_address")
@Table(name = "clinic_address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClinicAddress extends AddressEntity {

    @Column(name = "zip_code", nullable = false, table = "clinic_address")
    private String zipCode;
}
