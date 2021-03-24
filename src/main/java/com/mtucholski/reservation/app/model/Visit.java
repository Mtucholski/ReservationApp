package com.mtucholski.reservation.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Entity(name = "Visit")
@Table(name = "Visit")
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Visit implements Serializable, Cloneable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "visit_generator")
    @SequenceGenerator(name = "visit_generator", sequenceName = "visit_seq", allocationSize = 1)
    private Long visit_id;

    /**
     * holds value for visit date
     * pattern: dd/MM/yyyy
     */
    @Column(name = "visit_date", nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotEmpty
    private LocalDate visitDate;

    @Column(name = "visit_description", nullable = false)
    @Size(min = 20, max = 1000)
    @NotEmpty
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String visitDescription;

    @Column(name = "validation_status", nullable = false)
    private VaildationStatus vaildationStatus;

    @JsonBackReference
    @ManyToOne(targetEntity = Patient.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", updatable = false)
    private Patient patient;

}
