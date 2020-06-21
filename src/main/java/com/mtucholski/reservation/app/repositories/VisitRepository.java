package com.mtucholski.reservation.app.repositories;

import com.mtucholski.reservation.app.model.Visit;

import java.util.List;

public interface VisitRepository {

    void save(Visit visit);
    void delete(Visit visit);
    List<Visit> findByPatientPersonalId(String personalId);
    List<Visit> findAll();

}
