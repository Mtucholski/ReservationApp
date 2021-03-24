package com.mtucholski.reservation.app.repository.read;


import com.mtucholski.reservation.app.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * spring data repository for visit entity
 * @author mtucholski
 */
@Repository
public interface VisitRepositoryRead extends JpaRepository<Visit, Long>, JpaSpecificationExecutor<Visit> {
}
