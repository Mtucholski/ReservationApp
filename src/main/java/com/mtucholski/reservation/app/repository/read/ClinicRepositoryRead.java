package com.mtucholski.reservation.app.repository.read;

import com.mtucholski.reservation.app.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author mtucholski
 * clinic repository spring data
 */
@Repository
public interface ClinicRepositoryRead extends JpaRepository<Clinic, Long>, JpaSpecificationExecutor<Clinic> {
}
