package com.mtucholski.reservation.app.repository.write;

import com.mtucholski.reservation.app.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mtucholski
 * spring data repository for patient entity
 */
@Repository
public interface PatientRepositoryWrite extends JpaRepository<Patient, Long> {
}
