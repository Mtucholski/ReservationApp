package com.mtucholski.reservation.app.repository.write;

import com.mtucholski.reservation.app.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mtucholski
 * spring data repository for doctor entity
 */
@Repository
public interface DoctorRepositoryWrite extends JpaRepository<Doctor, Long> {
}
