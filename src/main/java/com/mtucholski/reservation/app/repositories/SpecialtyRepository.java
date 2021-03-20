package com.mtucholski.reservation.app.repositories;

import com.mtucholski.reservation.app.exceptions.ClinicException;
import com.mtucholski.reservation.app.model.Specialty;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface SpecialtyRepository {

    Specialty findById(int id) throws DataAccessException;
    List<Specialty> findAll() throws DataAccessException;
    Specialty findByName(String name) throws ClinicException;
    void saveSpecialty(Specialty specialty) throws DataAccessException;
    void deleteSpecialty(int id) throws DataAccessException;
}
