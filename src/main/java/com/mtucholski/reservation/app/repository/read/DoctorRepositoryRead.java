package com.mtucholski.reservation.app.repository.read;

import com.mtucholski.reservation.app.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author mtucholski
 * doctor repository spring data
 */
@Repository
public interface DoctorRepositoryRead extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor> {
}
