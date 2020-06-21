package com.mtucholski.reservation.app.repositories.jpa;

import com.mtucholski.reservation.app.model.MedicalDoctor;
import com.mtucholski.reservation.app.repositories.MedicalDoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Slf4j
@Profile("jpa")
public class MedicalRepositoryImpl implements MedicalDoctorRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<MedicalDoctor> findAll() {

        log.info("finding all doctors");
        return this.manager.createQuery("select doctors from MedicalDoctor doctors").getResultList();
    }

    @Override
    public List<MedicalDoctor> findBySpecialtyName(String specialtyName) {

        log.info("finding doctors by specialty");
        return this.manager.createQuery("select doctors from MedicalDoctor doctors where doctors.specialties =: specialtyName").getResultList();
    }

    @Override
    public MedicalDoctor findByLicenseNumber(int license) {

        log.info("find doctor by license number");
        return (MedicalDoctor) this.manager.createQuery("select doctors from MedicalDoctor doctors where doctors.medicalLicenseNumber =: license").getSingleResult();
    }

    @Override
    public MedicalDoctor findById(int id) {

        log.info("find doctor by id");
        return this.manager.find(MedicalDoctor.class, id);
    }

    @Override

    public void save(MedicalDoctor doctor) {

        log.info("checking if id is null");
        if (doctor.getId() == null){

            log.info("persisting cause id is null");
            this.manager.persist(doctor);
        } else {

            log.info("merging doctor cause id isn't null");
            this.manager.merge(doctor);
        }
    }

    @Override
    public void delete(MedicalDoctor doctor) {

        log.info("removing doctor with id:" + "" + doctor.getId());
        this.manager.remove(doctor);
    }
}
