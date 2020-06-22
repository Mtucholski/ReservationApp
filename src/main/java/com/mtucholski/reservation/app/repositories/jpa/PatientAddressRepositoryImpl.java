package com.mtucholski.reservation.app.repositories.jpa;

import com.mtucholski.reservation.app.model.PatientAddress;
import com.mtucholski.reservation.app.repositories.PatientAddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@Profile("jpa")
public class PatientAddressRepositoryImpl implements PatientAddressRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void save(PatientAddress patientAddress) {

        log.info("saving patientAddress");
        if (patientAddress.getId() == null) {
            log.info("persisting patientAddress cause id is null");
            this.manager.persist(patientAddress);
        } else {
            log.info("merging patientAddress cause id isn't null");
            this.manager.merge(patientAddress);
        }
    }

    @Override
    public void delete(PatientAddress patientAddress) {

        log.info("removing patientAddress from database:" + "" + patientAddress.toString());
        this.manager.remove(patientAddress);
    }

    @Override
    public void update(PatientAddress patientAddress) {

        log.info("finding patientAddress with id:" + "" + patientAddress.getId());
        List<PatientAddress> patientAddresses = this.manager.createQuery("select address from patient_address address").getResultList();
        Optional<PatientAddress> optionalAddress = patientAddresses.stream().filter(a -> a.getId().equals(patientAddress.getId())).findFirst();

        log.info("checking if patientAddress exists");
        if (optionalAddress.isPresent()) {

            log.info("changing patientAddress fields");
            PatientAddress currentPatientAddress = optionalAddress.get();
            currentPatientAddress.setCity(patientAddress.getCity());
            currentPatientAddress.setStreet(patientAddress.getStreet());
            currentPatientAddress.setFlatNumber(patientAddress.getFlatNumber());
            this.manager.flush();
        }else {

            log.error("cannot find patientAddress with id:" +"" + patientAddress.getId());
        }
    }
}
