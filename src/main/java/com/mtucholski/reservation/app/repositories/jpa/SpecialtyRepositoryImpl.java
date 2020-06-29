package com.mtucholski.reservation.app.repositories.jpa;

import com.mtucholski.reservation.app.exceptions.ClinicException;
import com.mtucholski.reservation.app.model.Specialty;
import com.mtucholski.reservation.app.repositories.SpecialtyRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Profile("jpa")
@NoArgsConstructor
@Slf4j
public class SpecialtyRepositoryImpl implements SpecialtyRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Specialty findById(int id) throws DataAccessException {

        log.info("searching for specialty by id");
        return this.manager.find(Specialty.class, id);
    }

    @Override
    public List<Specialty> findAll() throws DataAccessException {

        log.info("downloading all specialties");
        return this.manager.createQuery("select specialty from specialties specialty").getResultList();
    }

    @Override
    public Specialty findByName(String name) throws ClinicException {

        return this.manager.find(Specialty.class, name);
    }

    @Override
    public void saveSpecialty(Specialty specialty) throws DataAccessException {

        log.info("saving specialty to database. Checking if id is null");
        if (specialty.getId() == null){

            log.info("id is null. Persisting specialty");
            this.manager.persist(specialty);
        }else {

            log.info("id isn't null. Merging specialty");
            this.manager.merge(specialty);
        }
    }

    @Override
    public void deleteSpecialty(int id) throws DataAccessException {

        Specialty specialty = this.manager.find(Specialty.class, id);

        if (specialty !=null){

            this.manager.remove(specialty);
        }else {

            throw new ClinicException(ClinicException.ExceptionType.NOT_FOUND);
        }
    }
}
