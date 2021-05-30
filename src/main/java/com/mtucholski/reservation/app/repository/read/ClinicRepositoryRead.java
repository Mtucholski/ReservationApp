package com.mtucholski.reservation.app.repository.read;

import com.mtucholski.reservation.app.model.Clinic;
import com.mtucholski.reservation.app.model.ClinicType;
import com.mtucholski.reservation.app.model.ValidationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author mtucholski
 * clinic repository spring data
 */
@Repository
public interface ClinicRepositoryRead extends JpaRepository<Clinic, Long>, JpaSpecificationExecutor<Clinic> {

    List<Clinic> findAllByValidationStatus(ValidationStatus validationStatus);
    List<Clinic> findAllByClinicType(ClinicType type);
}
