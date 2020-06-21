package com.mtucholski.reservation.app.repositories.jpa;

import com.mtucholski.reservation.app.model.Address;
import com.mtucholski.reservation.app.repositories.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@Profile("jpa")
public class AddressRepositoryImpl implements AddressRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void save(Address address) {

        log.info("saving address");
        if (address.getId() == null) {
            log.info("persisting address cause id is null");
            this.manager.persist(address);
        } else {
            log.info("merging address cause id isn't null");
            this.manager.merge(address);
        }
    }

    @Override
    public void delete(Address address) {

        log.info("removing address from database:" + "" + address.toString());
        this.manager.remove(address);
    }

    @Override
    public void update(Address address) {

        log.info("finding address with id:" + "" + address.getId());
        List<Address> addresses = this.manager.createQuery("select address from Address address").getResultList();
        Optional<Address> optionalAddress = addresses.stream().filter(a -> a.getId().equals(address.getId())).findFirst();

        log.info("checking if address exists");
        if (optionalAddress.isPresent()) {

            log.info("changing address fields");
            Address currentAddress = optionalAddress.get();
            currentAddress.setCity(address.getCity());
            currentAddress.setStreet(address.getStreet());
            currentAddress.setFlatNumber(address.getFlatNumber());
            this.manager.flush();
        }else {

            log.error("cannot find address with id:" +"" + address.getId());
        }
    }
}
