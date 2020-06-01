package com.mtucholski.reservation.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "personalID")
    private Patient patient;

}
