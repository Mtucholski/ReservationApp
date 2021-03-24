package com.mtucholski.reservation.app.repository.read;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author mtucholski
 * spring data repository for patient entity
 */
@Repository
public interface PatientRepositoryRead extends JpaRepository<PatientRepositoryRead, Long>, JpaSpecificationExecutor<PatientRepositoryRead> {
}
