package com.mtucholski.reservation.app.repositories.jpa;

import com.mtucholski.reservation.app.model.Clinic;
import com.mtucholski.reservation.app.repositories.ClinicRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
@Slf4j
@NoArgsConstructor
public class ClinicRepositoryImpl implements ClinicRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Clinic> findAll() {

        log.info("finding all clinics");
        return this.manager.createQuery("select clinic from clinic clinic").getResultList();
    }

    @Override
    public List<Clinic> findByCity(String city) {

        List<Clinic> cityClinics = new ArrayList<>();
        log.info("finding clinics by city");
        List<Clinic> clinics = this.manager.createQuery("select clinic from clinic").getResultList();

        for (Clinic clinic : clinics){

            if (clinic.getClinicAddress().getCity().equals(city)){

                cityClinics.add(clinic);
            }
        }

        return cityClinics;
    }

    @Override
    public Clinic findById(int id) {

        log.info("finding clinic by id");
        return this.manager.find(Clinic.class, id);
    }

    @Override
    public void update(Clinic clinic) {

        log.info("filtering clinics for specific clinic");
        List<Clinic> clinics = this.manager.createQuery("select clinic from clinic clinic").getResultList();

        Optional<Clinic> clinicOptional = clinics.stream().filter(cv -> cv.getId().equals(clinic.getId())).findFirst();

        if (clinicOptional.isPresent()){

            log.info("optional clinic is present. Setting new values for fields");
            Clinic currentClinic = clinicOptional.get();
            currentClinic.setClinicAddress(clinic.getClinicAddress());
            currentClinic.setClinicName(clinic.getClinicName());
            currentClinic.setDoctors(clinic.getDoctors());
            this.manager.flush();
        }
    }

    @Override
    public void save(Clinic clinic) {


        if (clinic.getId() ==null){

            log.info("persisting clinic cause id is null ");
            this.manager.persist(clinic);
        }else {

            log.info("merging clinic cause clinic id isn't null");
            this.manager.merge(clinic);
        }
    }

    @Override
    public void delete(Clinic clinic) {

        log.info("removing clinic with id:" +"" + clinic.getId());
        this.manager.remove(clinic);
    }
}
