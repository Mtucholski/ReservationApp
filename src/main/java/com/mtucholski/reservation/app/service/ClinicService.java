package com.mtucholski.reservation.app.service;

import com.mtucholski.reservation.app.model.Clinic;

/**
 * @author mtucholski
 * service for managing clinic objects
 */
public interface ClinicService {


    /**
     *
     * @param clinic new created object
     * @return new created clinic object
     */
    Clinic createNewClinic(Clinic clinic);

    /**
     * save clinic object
     * @param clinic object to be saved
     * @return the persisted entity
     */
    Clinic saveClinic(Clinic clinic);


}
