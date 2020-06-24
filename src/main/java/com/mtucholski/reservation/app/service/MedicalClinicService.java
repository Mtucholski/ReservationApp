package com.mtucholski.reservation.app.service;

import com.mtucholski.reservation.app.exceptions.ClinicException;
import com.mtucholski.reservation.app.model.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * used as a facade so all controllers have a single point of entry
 */
public interface MedicalClinicService {

    Patient findPatientById(int id) throws ClinicException;
    List<Patient> findAllPatients() throws ClinicException;
    List<Patient> findByLastName(String lastName) throws ClinicException;
    void savePatient(Patient patient) throws ClinicException;
    void deletePatient(Patient patient) throws ClinicException;
    void createNewVisit(Visit visit) throws ClinicException;
    void updatePatient(Patient patient) throws ClinicException;

    Visit findVisitsById(int id) throws ClinicException;
    List<Visit> findByDate(LocalDate date) throws ClinicException;
    List<Visit> findAllVisits () throws ClinicException;
    void createVisit(Visit visit) throws ClinicException;
    void delete(Visit visit) throws ClinicException;
    void updateVisit(Visit visit) throws ClinicException;

    MedicalDoctor findDoctorByLicenseNumber(int licenseNumber) throws ClinicException;
    MedicalDoctor findDoctorById(int id) throws ClinicException;
    List<MedicalDoctor> findBySpecialtyName(String specialtyName) throws ClinicException;
    List<MedicalDoctor> findDoctors() throws ClinicException;
    void saveDoctor (MedicalDoctor doctor) throws ClinicException;
    void deleteDoctor(MedicalDoctor doctor) throws ClinicException;
    void updateDoctor(MedicalDoctor doctor) throws ClinicException;

    Specialty findSpecialtyById(int id) throws ClinicException;
    Specialty findSpecialtyByName(String name)throws ClinicException;
    List<Specialty> findAllSpecialties() throws ClinicException;
    void saveSpecialty(Specialty specialty)throws ClinicException;
    void deleteSpecialty(Specialty specialty) throws ClinicException;

    Clinic findByClinicId(int id) throws ClinicException;
    List<Clinic> findAllClinics() throws ClinicException;
    List<Clinic> findByCity(String city) throws ClinicException;
    void updateClinic(Clinic clinic) throws  ClinicException;
    void createClinic(Clinic clinic) throws ClinicException;
    void deleteClinic(Clinic clinic) throws ClinicException;





}
