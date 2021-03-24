package com.mtucholski.reservation.app.repository.write;

import com.mtucholski.reservation.app.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author mtucholski
 * spring data for clinic entity
 */
@Repository
public interface ClinicRepositoryWrite extends JpaRepository<Clinic, Long>, JpaSpecificationExecutor<Clinic> {
}
