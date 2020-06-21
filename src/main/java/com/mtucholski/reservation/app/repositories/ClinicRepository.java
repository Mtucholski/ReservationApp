package com.mtucholski.reservation.app.repositories;

import com.mtucholski.reservation.app.model.Clinic;

import java.util.List;

public interface ClinicRepository {

    List<Clinic> findAll();
    List<Clinic> findByCity(String city);
    Clinic findById(int id);
    void update(Clinic clinic);
    void save(Clinic clinic);
    void delete(Clinic clinic);


}
