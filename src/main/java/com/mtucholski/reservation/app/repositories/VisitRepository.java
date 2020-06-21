package com.mtucholski.reservation.app.repositories;

import com.mtucholski.reservation.app.model.Visit;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;
import java.util.List;

public interface VisitRepository {

    void save(Visit visit) throws DataAccessException;
    void delete(Visit visit) throws DataAccessException;
    List<Visit> findByDate(LocalDate date) throws DataAccessException;
    List<Visit> findAll() throws DataAccessException;
    Visit findById(int id)throws DataAccessException;

}
