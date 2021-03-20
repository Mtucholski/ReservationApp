package com.mtucholski.reservation.app.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.*;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
public class Person extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    @NotEmpty
    @Size(min = 3, max = 25, message = "first_name must be between 3 up to 25 letters")
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    @NotEmpty(message = "Cannot be empty")
    @Size(min = 3, max = 30, message = "last_name must be between 3 up to 30 letters")
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String lastName;

    @Column(name = "personal_id", unique = true, nullable = false)
    @NotBlank
    @Size(min = 11, max = 11, message = "must contain 11 digits")
    @Digits(message = "Must contain only digits", integer = 11, fraction = 0)
    private String personalID;

    @Column(name = "telephone")
    @NotEmpty
    @Pattern(regexp= "(?<!\\w)(\\(?(\\+|00)?48\\)?)?[ -]?\\d{3}[ -]?\\d{3}[ -]?\\d{3}(?!\\w)")
    private String telephone;
}
