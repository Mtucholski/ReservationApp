package com.mtucholski.reservation.app.repository.write;

import com.mtucholski.reservation.app.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mtucholski
 */
@Repository
public interface VisitRepositoryWrite extends JpaRepository<Visit, Long> {
}
