package com.mtucholski.reservation.app.repository.read;

import com.mtucholski.reservation.app.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author mtucholski
 * repository interface for Address
 */
@Repository
public interface AddressRepositoryRead extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {
}
