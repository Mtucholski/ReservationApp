package com.mtucholski.reservation.app.repositories;

import com.mtucholski.reservation.app.model.Address;

public interface AddressRepository {

    void save(Address address);
    void delete(Address address);
    void update(Address address);
}
