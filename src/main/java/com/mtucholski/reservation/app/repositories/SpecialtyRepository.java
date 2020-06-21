package com.mtucholski.reservation.app.repositories;

import com.mtucholski.reservation.app.model.Specialty;
import org.springframework.dao.DataAccessException;

public interface SpecialtyRepository {

    Specialty findById(int id) throws DataAccessException;
    Specialty findAll() throws DataAccessException;
    void saveSpecialty(Specialty specialty) throws DataAccessException;
    void deleteSpecialty(int id) throws DataAccessException;
}
