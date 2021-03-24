package com.mtucholski.reservation.app.repository.write;

import com.mtucholski.reservation.app.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author mtucholski
 * spring data repository for Address entity
 */
@Repository
public interface AddressRepositoryWrite extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {
}
