package com.mtucholski.reservation.app.repositories.jpa;

import com.mtucholski.reservation.app.model.Patient;
import com.mtucholski.reservation.app.repositories.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Slf4j
public class PatientRepositoryImpl implements PatientRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Patient> findAll() throws DataAccessException {

        log.info("searching for all patients");
        Query query = this.entityManager.createQuery("SELECT patient FROM Patient patient");
        log.info("found patients");
        return query.getResultList();
    }

    @Override
    public List<Patient> findByLastName(String lastName) throws DataAccessException {

        log.info("searching for patient with last_name:" + "" + lastName);
        Query query = this.entityManager.createQuery(
                "SELECT patient from Patient patient left join fetch patient.address where patient.lastName LIKE :lastName");

        query.setParameter("lastName", lastName + "%");
        log.info("found patient with last name:" + "" + lastName);
        return query.getResultList();
    }

    @Override
    public Patient findByPersonalId(String personalId) throws DataAccessException {

        Query query = this.entityManager.createQuery("select patient from Patient patient where patient.personalID = :personalId");
        query.setParameter("personalId", personalId);
        return (Patient) query.getSingleResult();
    }

    @Override
    public Patient findById(int id) throws DataAccessException {

        Query query = this.entityManager.createQuery("select patient from Patient patient where patient.id = :id");
        query.setParameter("id", id);
        return (Patient) query.getSingleResult();
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

        Query query = this.entityManager.createQuery("select patient from Patient  patient where patient.personalID =:personalID");
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
