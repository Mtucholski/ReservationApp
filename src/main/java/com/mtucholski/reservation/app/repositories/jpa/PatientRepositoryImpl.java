package com.mtucholski.reservation.app.repositories.jpa;

import com.mtucholski.reservation.app.exceptions.ClinicException;
import com.mtucholski.reservation.app.model.Patient;
import com.mtucholski.reservation.app.repositories.PatientRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@Profile("jpa")
@NoArgsConstructor
public class PatientRepositoryImpl implements PatientRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Patient> findAll() throws DataAccessException {

        log.info("searching for all patients");
        Query query = this.entityManager.createQuery("SELECT patient FROM patients patient");
        log.info("found patients");
        return query.getResultList();
    }

    @Override
    public List<Patient> findByLastName(String lastName) throws DataAccessException {

        List<Patient> patientList = new ArrayList<>();
        log.info("searching for patient with last_name:" + "" + lastName);
        List<Patient> patients = this.entityManager.createQuery("select patients from patients ").getResultList();

        for (Patient patient : patients){

            if (patient.getLastName().equals(lastName)){

                patientList.add(patient);
            }
        }
        log.info("found patient with last name:" + "" + lastName);
        return patientList;
    }

    @Override
    public Patient findByPersonalId(String personalId) throws DataAccessException {

        List<Patient> patients = this.entityManager.createQuery("select patients from patients ").getResultList();

        Optional<Patient> patient = patients.stream().filter(patient1 -> patient1.getPersonalID().equals(personalId)).findFirst();

        if (patient.isPresent()){

            return patient.get();
        }else {

            throw new ClinicException(ClinicException.ExceptionType.NOT_FOUND);
        }
    }

    @Override
    public Patient findById(int id) throws DataAccessException {

        return this.entityManager.find(Patient.class, id);
    }

    @Override
    public void savePatient(Patient patient) throws DataAccessException {

        if (patient.getId() == null) {

            this.entityManager.persist(patient);
        } else {

            this.entityManager.merge(patient);
        }
    }

    @Override
    public void deleteByPersonalID(String personalID) {

        Query query = this.entityManager.createQuery("select patient from patients  patient where patient.personalID =:personalID");
        Patient patient = (Patient) query.getSingleResult();

        if (patient.getPersonalID().equals(personalID)) {

            this.entityManager.remove(patient);
        } else {

            this.entityManager.merge(patient);
        }
    }

    @Override
    public void updatePatient(Patient patient) throws DataAccessException {

        Patient oldPatient = this.entityManager.find(Patient.class, patient.getId());

        if (oldPatient != null) {

            oldPatient.setFirstName(patient.getFirstName());
            oldPatient.setLastName(patient.getLastName());
            oldPatient.setEmail(patient.getEmail());
            oldPatient.setTelephone(patient.getTelephone());
           this.entityManager.flush();

        }
    }
}
