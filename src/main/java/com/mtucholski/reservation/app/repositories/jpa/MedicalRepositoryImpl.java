package com.mtucholski.reservation.app.repositories.jpa;

import com.mtucholski.reservation.app.exceptions.ClinicException;
import com.mtucholski.reservation.app.model.Doctor;
import com.mtucholski.reservation.app.repositories.MedicalDoctorRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
@NoArgsConstructor
@Profile("jpa")
public class MedicalRepositoryImpl implements MedicalDoctorRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Doctor> findAll() {

        log.info("finding all doctors");
        return this.manager.createQuery("select doctors from doctors").getResultList();
    }

    @Override
    public List<Doctor> findBySpecialtyName(String specialtyName) {

        List<Doctor> returnedDoctors = new ArrayList<>();
        log.info("finding doctors by specialty");
        List<Doctor> doctors = this.manager.createQuery("select doctors from doctors ").getResultList();

        for (Doctor doctor : doctors) {

            if (doctor.getSpecialties().stream().allMatch(specialty1 -> specialty1.getSpecialtyName().equals(specialtyName))){

                returnedDoctors.add(doctor);
            }
        }

        return  returnedDoctors;
    }

    @Override
    public Doctor findByLicenseNumber(int license) {

        Doctor medic = new Doctor();
        log.info("find doctor by license number");
        List<Doctor> doctors = this.manager.createQuery("select doctors  from doctors ").getResultList();

        for (Doctor doctor : doctors){

            if (doctor.getMedicalLicenseNumber().equals(license)){

                medic = doctor;
            }
        }

        return medic;
    }

    @Override
    public Doctor findById(int id) {

        log.info("find doctor by id");
        return this.manager.find(Doctor.class, id);
    }

    @Override

    public void save(Doctor doctor) {

        log.info("checking if id is null");
        if (doctor.getId() == null) {

            log.info("persisting cause id is null");
            this.manager.persist(doctor);
        } else {

            log.info("merging doctor cause id isn't null");
            this.manager.merge(doctor);
        }
    }

    @Override
    public void delete(Doctor doctor) {

        log.info("removing doctor with id:" + "" + doctor.getId());
        this.manager.remove(doctor);
    }

    @Override
    public void update(Doctor doctor) throws ClinicException {

        Doctor oldDoctor = this.manager.find(Doctor.class, doctor.getId());

        if (oldDoctor != null) {

            oldDoctor.setMedicalLicenseNumber(doctor.getMedicalLicenseNumber());
            oldDoctor.setSpecialties(doctor.getSpecialties());
            oldDoctor.setFirstName(doctor.getFirstName());
            oldDoctor.setLastName(doctor.getLastName());
            oldDoctor.setPersonalID(doctor.getPersonalID());
            oldDoctor.setTelephone(doctor.getTelephone());
            this.manager.flush();
        } else {

            throw new ClinicException();
        }
    }
}
