package com.mtucholski.reservation.app.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
public class Person extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    @NotEmpty
    @Size(min = 3, max = 25)
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    @NotEmpty
    @Size(min = 3, max = 30)
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String lastName;

    @Column(name = "personal_id", unique = true, nullable = false)
    @NotEmpty(message = "It cannot be empty")
    @Size(min = 11, max = 11, message = "must contain 11 digists")
    private String personalID;

    @Column(name = "telephone")
    @NotEmpty
    @Digits(fraction = 0, integer = 10)
    private String telephone;
}
