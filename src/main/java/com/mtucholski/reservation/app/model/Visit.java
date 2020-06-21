package com.mtucholski.reservation.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mtucholski.reservation.app.json.CustomVisitSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "MedicalAppointment")
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(using = CustomVisitSerializer.class)
public class Visit extends BaseEntity{

    /**
     * holds value for visit date
     * pattern: dd/MM/yyyy
     */
    @Column(name = "visit_date",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotEmpty
    private LocalDate visitDate;

    @Column(name = "visit_description", nullable = false)
    @Size(min = 20, max = 1000)
    @NotEmpty
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String visitDescription;

    @ManyToOne(targetEntity = Patient.class, cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "patient_id", name = "visit_id")
    private Patient patient;

}
