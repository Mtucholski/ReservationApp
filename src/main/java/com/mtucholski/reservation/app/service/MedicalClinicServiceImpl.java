package com.mtucholski.reservation.app.service;

import com.mtucholski.reservation.app.exceptions.ClinicException;
import com.mtucholski.reservation.app.exceptions.RESTApiClientWrapper;
import com.mtucholski.reservation.app.model.*;
import com.mtucholski.reservation.app.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class MedicalClinicServiceImpl implements MedicalClinicService {

    private final ClinicRepository clinicRepository;
    private final MedicalDoctorRepository medicalDoctorRepository;
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

        log.info("searching for patient with id" +" " + id);
        Patient patient = RESTApiClientWrapper.request(() -> patientRepository.findById(id))
                .messageDataIsNull("Server internal error")
                .messageDataNotFound("no patient with given id")
                .messageError("Some error please contact with admin")
                .execute();

        if (patient == null){

            log.error("error patient is null:");
            throw new ClinicException(ClinicException.ExceptionType.BAD_ENTRY_DATA);
        }

        log.info("patient found");
        return patient;
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, timeout = 10000, rollbackFor = RuntimeException.class)
    public List<Patient> findAllPatients() throws ClinicException {

        log.info("looking for all patients");
        List<Patient> patients = RESTApiClientWrapper.request(patientRepository::findAll)
                .messageDataIsNull("there isn't any patients. Something wrong or clinic is new")
                .messageDataNotFound("Data not found")
                .messageError("Server error.Please contact with admin")
                .execute();

        log.info("patients found. Number of found patients:" + " " + patients.size());
        return patients;

    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, timeout = 10000, rollbackFor = RuntimeException.class)
    public List<Patient> findByLastName(String lastName) throws ClinicException {

        if (lastName.isEmpty()){

            log.error("last name is empty");
            throw new ClinicException(ClinicException.ExceptionType.BAD_ENTRY_DATA);
        }

        log.info("searching for patients with last name:" + "" + lastName);

        return RESTApiClientWrapper.request(() -> patientRepository.findByLastName(lastName))
                .messageDataIsNull("wrong entry data")
                .messageDataNotFound("no patient with given last name:" + "" + lastName)
                .messageError("something gone wrong. Please contact with admin")
                .execute();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 1000, rollbackFor = RuntimeException.class,
            noRollbackFor = ClinicException.class)
    public void savePatient(Patient patient) throws ClinicException {

        if (patient == null){

            log.error("patient is null");
            throw new ClinicException(ClinicException.ExceptionType.BAD_ENTRY_DATA);
        }

        patientRepository.savePatient(patient);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 10000, rollbackFor = RuntimeException.class)
    public void deletePatient(Patient patient) throws ClinicException {

        if (patient ==null){

            log.error("patient is null so cannot be deleted");
            throw new ClinicException(ClinicException.ExceptionType.BAD_ENTRY_DATA);
        }
        patientRepository.deleteByPersonalID(patient.getPersonalID());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, noRollbackFor = Error.class, rollbackFor = RuntimeException.class, timeout = 10000)
    public void updatePatient(Patient patient) throws ClinicException {

        log.info("checking if patient is null");
        if (patient == null){

            log.error("patient is null");
            throw new ClinicException(ClinicException.ExceptionType.BAD_ENTRY_DATA);
        }

        patientRepository.updatePatient(patient);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, rollbackFor = ClinicException.class, noRollbackFor = Error.class)
    public Visit findVisitsById(int id) throws ClinicException {

        log.info("looking for visit with id:" +" "+ id);
        Visit visit = RESTApiClientWrapper.request(()-> visitRepository.findById(id))
                .messageDataIsNull(" data is null")
                .messageDataNotFound(" no visit with given id")
                .messageError("something wrong please contact with admin")
                .execute();

        if (visit == null){

            log.error("error when trying to find visit");
            throw new ClinicException(ClinicException.ExceptionType.UNEXPECTED_RESULT);
        }

        log.info("visit found with id" + ": " + id);
        return visit;
    }

    @Override
    @Transactional(timeout = 10000, readOnly = true, rollbackFor = ClinicException.class, noRollbackFor = Error.class)
    public List<Visit> findByDate(LocalDate date) throws ClinicException {

        if (date == null ){

            log.error("wrong data. Date is null");
            throw new ClinicException(ClinicException.ExceptionType.BAD_ENTRY_DATA);
        }

        return RESTApiClientWrapper.request(()-> visitRepository.findByDate(date))
                .messageDataIsNull("bad entry")
                .messageDataNotFound("no visits in given date")
                .messageError("something wrong. Please contact with admin")
                .execute();
    }

    @Override
    @Transactional(timeout = 10000, readOnly = true, rollbackFor = ClinicException.class, noRollbackFor = Error.class)
    public List<Visit> findAllVisits() throws ClinicException {

        log.info("searching for all visits");
        List<Visit> visits = RESTApiClientWrapper.request(visitRepository::findAll)
                .messageError("wrong and error")
                .messageDataNotFound("service didn't find any visit. Please contact with your IT support")
                .execute();

        log.info("found number of visits:" + "" + visits.size());
        return visits;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 1000, rollbackFor = RuntimeException.class, noRollbackFor = Error.class)
    public void createVisit(Visit visit) throws ClinicException {

       if (visit == null){

           log.error("visit is null and cannot be saved");
           throw new ClinicException(ClinicException.ExceptionType.BAD_ENTRY_DATA);
       }

       visitRepository.save(visit);
    }

    @Override
    @Transactional(timeout = 10000, isolation = Isolation.SERIALIZABLE, rollbackFor = ClinicException.class, noRollbackFor = Error.class)
    public void delete(Visit visit) throws ClinicException {


        if (visit == null){

            log.error("visit is null ");
            throw new ClinicException(ClinicException.ExceptionType.BAD_ENTRY_DATA);
        }

        visitRepository.delete(visit);
    }

    @Override
    @Transactional(timeout = 10000, isolation = Isolation.SERIALIZABLE, rollbackFor = RuntimeException.class, noRollbackFor = Error.class)
    public void updateVisit(Visit visit) throws ClinicException {

        if (visit == null){

            log.error("visit is null");
            throw new ClinicException(ClinicException.ExceptionType.BAD_ENTRY_DATA);
        }

        visitRepository.update(visit);
    }

    @Override
    @Transactional(timeout = 10000, readOnly = true, rollbackFor = ClinicException.class, noRollbackFor = Error.class)
    public MedicalDoctor findDoctorByLicenseNumber(int licenseNumber) throws ClinicException {

        log.info("looking for doctor with license number" + "" + licenseNumber);
        MedicalDoctor doctor = RESTApiClientWrapper.request(()-> medicalDoctorRepository.findByLicenseNumber(licenseNumber))
                .messageDataIsNull("data is null. No doctor with given license number")
                .messageDataNotFound("data not found")
                .messageError("error")
                .execute();
        if (doctor == null){

            throw new ClinicException(ClinicException.ExceptionType.UNEXPECTED_RESULT);
        }
        return doctor;
    }

    @Override
    @Transactional(readOnly = true, timeout = 10000)
    public MedicalDoctor findDoctorById(int id) throws ClinicException {

        log.info("looking for doctor with id:" + " "+ id);
        MedicalDoctor doctor = RESTApiClientWrapper.request(()-> medicalDoctorRepository.findById(id))
                .messageError("something wrong")
                .messageDataNotFound("data not found")
                .messageError("something wrong please contact with IT support")
                .execute();
        log.info("found doctor with id:" + " " + id +" doctor:" + " " + doctor.toString());
        return doctor;
    }

    @Override
    @Transactional(readOnly = true, timeout = 10000)
    public List<MedicalDoctor> findBySpecialtyName(String specialtyName) throws ClinicException {

        if (specialtyName.isEmpty()){

            log.error("specialty name is empty cannot proceed");
            throw new ClinicException(ClinicException.ExceptionType.BAD_ENTRY_DATA);
        }

        return RESTApiClientWrapper.request(()-> medicalDoctorRepository.findBySpecialtyName(specialtyName))
                .messageDataIsNull("data is null")
                .messageDataNotFound(" data not found. No doctors with given specialty:" + " " + specialtyName)
                .messageError("error")
                .execute();
    }

    @Override
    @Transactional(readOnly = true, timeout = 10000)
    public List<MedicalDoctor> findDoctors() throws ClinicException {

        log.info("looking for all doctors");

        return RESTApiClientWrapper.request(medicalDoctorRepository::findAll)
                .messageDataIsNull("data is null")
                .messageDataNotFound("data not found. Please contact with admin")
                .messageError("internal server error")
                .execute();
    }

    @Override
    @Transactional(readOnly = true, timeout = 10000, isolation = Isolation.SERIALIZABLE, noRollbackFor = Error.class)
    public void saveDoctor(MedicalDoctor doctor) throws ClinicException {

        if (doctor == null){

            log.error("error doctor is null");
            throw new ClinicException(ClinicException.ExceptionType.BAD_ENTRY_DATA);
        }

        medicalDoctorRepository.save(doctor);
    }

    @Override
    @Transactional(timeout = 10000, isolation = Isolation.SERIALIZABLE, rollbackFor = ClinicException.class, noRollbackFor = Error.class)
    public void deleteDoctor(MedicalDoctor doctor) throws ClinicException {

        if (doctor == null){

            log.error("doctor is null and cannot be deleted");
            throw new ClinicException(ClinicException.ExceptionType.BAD_ENTRY_DATA);
        }

        medicalDoctorRepository.delete(doctor);
    }

    @Override
    @Transactional(timeout = 10000, isolation = Isolation.SERIALIZABLE, rollbackFor = RuntimeException.class, noRollbackFor = Error.class)
    public void updateDoctor(MedicalDoctor doctor) throws ClinicException {

        if (doctor == null){

            log.error("doctor is null and cannot be proceed");
            throw new ClinicException(ClinicException.ExceptionType.BAD_ENTRY_DATA);
        }
        medicalDoctorRepository.update(doctor);
    }

    @Override
    @Transactional(readOnly = true, timeout = 10000)
    public Specialty findSpecialtyById(int id) throws ClinicException {

        return RESTApiClientWrapper.request(()-> specialtyRepository.findById(id))
                .messageDataIsNull("specialty data null")
                .messageDataNotFound("specialty not found")
                .messageError("error when executing findBySpecialtyId")
                .execute();
    }

    @Override
    @Transactional(readOnly = true, timeout = 10000)
    public Specialty findSpecialtyByName(String name) throws ClinicException {

        if (name.isEmpty() || !(name.matches("^[\\p{L} .'-]+$"))){

            throw new ClinicException(ClinicException.ExceptionType.BAD_ENTRY_DATA);
        }

        return RESTApiClientWrapper.request(()-> specialtyRepository.findByName(name))
                .messageDataIsNull("specialty data null")
                .messageDataNotFound("specialty not found")
                .messageError("error when executing findBySpecialtyId")
                .execute();
    }

    @Override
    @Transactional(readOnly = true, timeout = 10000)
    public List<Specialty> findAllSpecialties() throws ClinicException {

        return RESTApiClientWrapper.request(specialtyRepository::findAll)
                .messageDataIsNull("data is null")
                .messageDataNotFound("data not found")
                .messageError("error occurred")
                .execute();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 10000, rollbackFor = RuntimeException.class, noRollbackFor = Error.class)
    public void saveSpecialty(Specialty specialty) throws ClinicException {

        if (specialty == null){

            throw new ClinicException(ClinicException.ExceptionType.BAD_ENTRY_DATA);
        }
        specialtyRepository.saveSpecialty(specialty);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 10000, rollbackFor = RuntimeException.class, noRollbackFor = Error.class)
    public void deleteSpecialty(Specialty specialty) throws ClinicException {

        if (specialty == null){

            throw new ClinicException(ClinicException.ExceptionType.BAD_ENTRY_DATA);
        }

        specialtyRepository.deleteSpecialty(specialty.getId());
    }

    @Override
    @Transactional(readOnly = true, timeout = 10000)
    public Clinic findByClinicId(int id) throws ClinicException {


        return RESTApiClientWrapper.request(()-> clinicRepository.findById(id))
                .messageDataIsNull("data is null")
                .messageDataNotFound("data not found")
                .messageError("error please contact with admin")
                .execute();
    }

    @Override
    @Transactional(readOnly = true, timeout = 10000)
    public List<Clinic> findAllClinics() throws ClinicException {

        return RESTApiClientWrapper.request(clinicRepository::findAll)
                .messageDataIsNull("data is null")
                .messageDataNotFound("data not found")
                .messageError("error")
                .execute();
    }

    @Override
    @Transactional(readOnly = true, timeout = 10000)
    public List<Clinic> findByCity(String city) throws ClinicException {

        return clinicRepository.findByCity(city);
    }

    @Override
    @Transactional(timeout = 10000, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW, noRollbackFor = Error.class, rollbackFor = RuntimeException.class)
    public void updateClinic(Clinic clinic) throws ClinicException {

        if (clinic == null){

            throw new ClinicException(ClinicException.ExceptionType.BAD_ENTRY_DATA);
        }

        clinicRepository.update(clinic);
    }

    @Override
    @Transactional(timeout = 10000, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, noRollbackFor = Error.class, rollbackFor = RuntimeException.class)
    public void createClinic(Clinic clinic) throws ClinicException {

        if (clinic == null){

            throw new ClinicException(ClinicException.ExceptionType.BAD_ENTRY_DATA);
        }

        clinicRepository.save(clinic);
    }

    @Override
    public void deleteClinic(Clinic clinic) throws ClinicException {

        if (clinic == null){

            throw new ClinicException(ClinicException.ExceptionType.BAD_ENTRY_DATA);
        }

        clinicRepository.delete(clinic);
    }
}
