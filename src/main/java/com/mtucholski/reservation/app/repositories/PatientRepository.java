package com.mtucholski.reservation.app.repositories;

import com.mtucholski.reservation.app.model.Patient;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PatientRepository {

    List<Patient> findAll() throws DataAccessException;
    List <Patient>findByLastName(String lastName) throws DataAccessException;
    Patient findByPersonalId(String personalId) throws DataAccessException;
    Patient findById(int id) throws DataAccessException;
    void savePatient(Patient patient) throws DataAccessException;
    void deleteByPersonalID(String personalID);
    void updatePatient(Patient patient) throws DataAccessException;
}
