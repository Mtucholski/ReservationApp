package com.mtucholski.reservation.app.service;

import com.mtucholski.reservation.app.model.Address;

/**
 * service for managing Address
 */
public interface AddressService {

    /**
     * save address
     * @param address validated entity to be saved
     * @return persisted entity
     */

    Address createNewAddress(Address address);


}
