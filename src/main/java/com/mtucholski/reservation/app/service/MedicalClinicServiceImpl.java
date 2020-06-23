package com.mtucholski.reservation.app.service;

import com.mtucholski.reservation.app.exceptions.ClinicException;
import com.mtucholski.reservation.app.model.*;
import com.mtucholski.reservation.app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MedicalClinicServiceImpl implements MedicalClinicService {

    private final ClinicRepository clinicRepository;
    private  final MedicalDoctorRepository medicalDoctorRepository;
    private final PatientRepository patientRepository;
    private final SpecialtyRepository specialtyRepository;
    private final VisitRepository visitRepository;


    @Autowired
    public MedicalClinicServiceImpl(ClinicRepository clinicRepository, MedicalDoctorRepository medicalDoctorRepository,
                                    PatientRepository patientRepository, SpecialtyRepository specialtyRepository,
                                    VisitRepository visitRepository) {
        this.clinicRepository = clinicRepository;
        this.medicalDoctorRepository = medicalDoctorRepository;
        this.patientRepository = patientRepository;
        this.specialtyRepository = specialtyRepository;
        this.visitRepository = visitRepository;
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, timeout = 10000)
    public Patient findPatientById(int id) throws ClinicException {

        return patientRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, timeout = 10000, rollbackFor = RuntimeException.class)
    public List<Patient> findAllPatients() throws ClinicException {

        return patientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, timeout = 10000, rollbackFor = RuntimeException.class)
    public List<Patient> findByLastName(String lastName) throws ClinicException {

        return patientRepository.findByLastName(lastName);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 1000, rollbackFor = RuntimeException.class,
    noRollbackFor = ClinicException.class)
    public void savePatient(Patient patient) throws ClinicException {

        patientRepository.savePatient(patient);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 10000, rollbackFor = RuntimeException.class)
    public void deletePatient(Patient patient) throws ClinicException {

        patientRepository.deleteByPersonalID(patient.getPersonalID());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 1000, rollbackFor = RuntimeException.class)
    public void createNewVisit(Visit visit) throws ClinicException {

        visitRepository.save(visit);

    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, noRollbackFor = Error.class, rollbackFor = RuntimeException.class, timeout = 10000)
    public void updatePatient(Patient patient) throws ClinicException {

        patientRepository.updatePatient(patient);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, rollbackFor = ClinicException.class, noRollbackFor = Error.class)
    public Visit findVisitsById(int id) throws ClinicException {

       return  visitRepository.findById(id);
    }

    @Override
    @Transactional(timeout = 10000, readOnly = true, rollbackFor = ClinicException.class,noRollbackFor = Error.class)
    public List<Visit> findByDate(LocalDate date) throws ClinicException {

        return visitRepository.findByDate(date);
    }

    @Override
    @Transactional(timeout = 10000, readOnly = true, rollbackFor = ClinicException.class, noRollbackFor = Error.class)
    public List<Visit> findAllVisits() throws ClinicException {

        return visitRepository.findAll();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 1000, rollbackFor = RuntimeException.class, noRollbackFor = Error.class)
    public void createVisit(Visit visit) throws ClinicException {

        visitRepository.save(visit);
    }

    @Override
    @Transactional(timeout = 10000, isolation = Isolation.SERIALIZABLE, rollbackFor = ClinicException.class, noRollbackFor = Error.class)
    public void delete(Visit visit) throws ClinicException {

        visitRepository.delete(visit);
    }

    @Override
    @Transactional(timeout = 10000, isolation = Isolation.SERIALIZABLE, rollbackFor = RuntimeException.class, noRollbackFor = Error.class)
    public void updateVisit(Visit visit) throws ClinicException {

        visitRepository.update(visit);
    }

    @Override
    @Transactional(timeout = 10000, readOnly = true, rollbackFor = ClinicException.class, noRollbackFor = Error.class)
    public MedicalDoctor findDoctorByLicenseNumber(int licenseNumber) throws ClinicException {

        return medicalDoctorRepository.findByLicenseNumber(licenseNumber);
    }

    @Override
    @Transactional(readOnly = true, timeout = 10000)
    public MedicalDoctor findDoctorById(int id) throws ClinicException {

        return medicalDoctorRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true, timeout = 10000)
    public List<MedicalDoctor> findBySpecialtyName(String specialtyName) throws ClinicException {

        return medicalDoctorRepository.findBySpecialtyName(specialtyName);
    }

    @Override
    @Transactional(readOnly = true, timeout = 10000)
    public List<MedicalDoctor> findDoctors() throws ClinicException {

        return medicalDoctorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true, timeout = 10000, isolation = Isolation.SERIALIZABLE, noRollbackFor = Error.class)
    public void saveDoctor(MedicalDoctor doctor) throws ClinicException {

        medicalDoctorRepository.save(doctor);
    }

    @Override
    @Transactional(timeout = 10000, isolation = Isolation.SERIALIZABLE, rollbackFor = ClinicException.class, noRollbackFor = Error.class)
    public void deleteDoctor(MedicalDoctor doctor) throws ClinicException {

        medicalDoctorRepository.delete(doctor);
    }

    @Override
    @Transactional(timeout = 10000, isolation = Isolation.SERIALIZABLE, rollbackFor = RuntimeException.class, noRollbackFor = Error.class)
    public void updateDoctor(MedicalDoctor doctor) throws ClinicException {

        medicalDoctorRepository.update(doctor);
    }

    @Override
    @Transactional(readOnly = true, timeout = 10000)
    public Specialty findSpecialtyById(int id) throws ClinicException {

        return specialtyRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true, timeout = 10000)
    public Specialty findSpecialtyByName(String name) throws ClinicException {

        return specialtyRepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true, timeout = 10000)
    public List<Specialty> findAllSpecialties() throws ClinicException {

        return specialtyRepository.findAll();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 10000, rollbackFor = RuntimeException.class, noRollbackFor = Error.class)
    public void saveSpecialty(Specialty specialty) throws ClinicException {

        specialtyRepository.saveSpecialty(specialty);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 10000, rollbackFor = RuntimeException.class, noRollbackFor = Error.class)
    public void deleteSpecialty(Specialty specialty) throws ClinicException {

        specialtyRepository.deleteSpecialty(specialty.getId());
    }

    @Override
    @Transactional(readOnly = true, timeout = 10000)
    public Clinic findByClinicId(int id) throws ClinicException {

        return clinicRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true, timeout = 10000)
    public List<Clinic> findAllClinics() throws ClinicException {

        return clinicRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true, timeout = 10000)
    public List<Clinic> findByCity(String city) throws ClinicException {

        return clinicRepository.findByCity(city);
    }

    @Override
    @Transactional(timeout = 10000, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW, noRollbackFor = Error.class, rollbackFor = RuntimeException.class)
    public void updateClinic(Clinic clinic) throws ClinicException {

        clinicRepository.update(clinic);
    }

    @Override
    @Transactional(timeout = 10000, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, noRollbackFor = Error.class, rollbackFor = RuntimeException.class)
    public void createClinic(Clinic clinic) throws ClinicException {

    }

    @Override
    public void deleteClinic(Clinic clinic) throws ClinicException {

        clinicRepository.delete(clinic);
    }
}
