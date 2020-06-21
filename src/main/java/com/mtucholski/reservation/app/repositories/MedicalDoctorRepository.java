package com.mtucholski.reservation.app.repositories;

import com.mtucholski.reservation.app.model.MedicalDoctor;

import java.util.List;

public interface MedicalDoctorRepository {

    List<MedicalDoctor> findAll();
    List<MedicalDoctor> findBySpecialtyName(String specialtyName);
    MedicalDoctor findByLicenseNumber(int license);
    MedicalDoctor findById(int id);
    void save(MedicalDoctor doctor);
    void delete(MedicalDoctor doctor);
}
