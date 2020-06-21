package com.mtucholski.reservation.app.repositories.jpa;

import com.mtucholski.reservation.app.model.Visit;
import com.mtucholski.reservation.app.repositories.VisitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.time.LocalDate;
import java.util.List;

@Repository
@Slf4j
@Profile("jpa")
public class VisitRepositoryImpl implements VisitRepository {

@PersistenceContext
private EntityManager manager;

    @Override
    public void save(Visit visit) {

        log.info("checking if visit id is null");
        if (visit.getId() == null){

            log.info("persisting cause visit id is null");
            this.manager.persist(visit);
        }else {

            log.info("merging cause visit id isn't null");
            this.manager.merge(visit);
        }

    }

    @Override
    public void delete(Visit visit) {

        log.info("removing visit with id:" + ""+ visit.getId());
        this.manager.remove(visit);
    }

    @Override
    public List<Visit> findByDate(LocalDate date) {

        log.info("searching for visit by date");
        Query query = this.manager.createQuery("select visitDate from Visit where visitDate = :date");
        query.setParameter("date", date);
        return query.getResultList();

    }

    @Override
    public List<Visit> findAll() {

        log.info("finding all visits for report");
        return (List<Visit>) this.manager.createQuery("select visitDate from Visit");
    }

    @Override
    public Visit findById(int id) {

        log.info("finding visit by id");
        return this.manager.find(Visit.class, id);
    }
}
