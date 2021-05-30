package com.mtucholski.reservation.app.service;

import com.mtucholski.reservation.app.model.Clinic;
import com.mtucholski.reservation.app.model.ClinicType;

import java.util.*;

import org.springframework.data.domain.Page;

/**
 * @author mtucholski
 * service for managing clinic objects
 */
public interface ClinicService {


    /**
     * @param clinic new created object
     * @return new created clinic object
     */
    Clinic createNewClinic(Clinic clinic);

    /**
     * save clinic object
     *
     * @param clinic object to be saved
     * @return the persisted entity
     */
    Clinic saveClinic(Clinic clinic);

    /**
     * method for validation of object
     *
     * @param clinic clinic to be validated
     * @return validation status
     */
    Clinic validationProcess(Clinic clinic);

    /**
     * @param filter filter
     * @param page   page number
     * @param size   int size
     * @param sort   sorting (Asc, Desc)
     * @return found clinics
     */
    Page<Clinic> findClinics(String filter, int page, int size, String[] sort);

    /**
     * @return a list of clinics with pending validation status
     */
    List<Clinic> findAllClinicsWithPendingValidation();

    /**
     * finds clinics by type
     *
     * @param type enum clinic type
     * @return find all clinics
     */
    List<Clinic> findAllByClinicType(ClinicType type);

}
