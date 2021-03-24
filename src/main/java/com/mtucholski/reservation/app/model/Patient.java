package com.mtucholski.reservation.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@Entity()
@Table(name = "patients", uniqueConstraints = @UniqueConstraint(columnNames = {"personal_id", "email"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient implements Serializable, Cloneable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String lastName;

    @Column(name = "personal_id", unique = true, updatable = false)
    @Size(min = 11, max = 11)
    private Long personalId;

    @Column(name = "social_security_number", updatable = true)
    private Long socialSecurityNumber;

    @Column(name = "identity_card_type")
    @Enumerated(EnumType.STRING)
    private IdentityCardType identityCardType;

    @Column(name = "email", nullable = false, unique = true)
    @NotEmpty
    @Email
    private String email;

    @Column(name = "validation_status", nullable = false)
    private VaildationStatus vaildationStatus;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Visit.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "patients")
    private List<Visit> visits;

}
