package com.mtucholski.reservation.app.repositories;

import com.mtucholski.reservation.app.exceptions.ClinicException;
import com.mtucholski.reservation.app.model.Doctor;

import java.util.List;

public interface MedicalDoctorRepository {

    List<Doctor> findAll();
    List<Doctor> findBySpecialtyName(String specialtyName);
    Doctor findByLicenseNumber(int license);
    Doctor findById(int id);
    void save(Doctor doctor);
    void delete(Doctor doctor);
    void update(Doctor doctor) throws ClinicException;
}
