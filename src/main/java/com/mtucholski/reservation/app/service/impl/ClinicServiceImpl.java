package com.mtucholski.reservation.app.service.impl;

import com.mtucholski.reservation.app.exceptions.ClinicException;
import com.mtucholski.reservation.app.model.Clinic;
import com.mtucholski.reservation.app.model.ClinicStatus;
import com.mtucholski.reservation.app.model.ClinicType;
import com.mtucholski.reservation.app.model.ValidationStatus;
import com.mtucholski.reservation.app.repository.read.ClinicRepositoryRead;
import com.mtucholski.reservation.app.repository.write.ClinicRepositoryWrite;
import com.mtucholski.reservation.app.service.ClinicService;
import com.mtucholski.reservation.app.specification.ClinicSpecificationBuilder;
import com.mtucholski.reservation.app.validation.ClinicValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mtucholski
 * service implementation for managing clinic
 */
@Service
@Slf4j
public class ClinicServiceImpl implements ClinicService {

    @Autowired
    private ClinicRepositoryRead repositoryRead;

    @Autowired
    private ClinicRepositoryWrite repositoryWrite;

    @Autowired
    private ClinicValidation clinicValidation;


    @Override
    @Transactional(transactionManager = "transactionManagerWrite", propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE, rollbackFor = ClinicException.class)
    public Clinic createNewClinic(Clinic clinic) {

        clinicValidation.validateClinic(clinic);

        if (clinic.getIsValid()) {

            return repositoryWrite.save(clinic);
        } else {

            throw new ClinicException(ClinicException.ExceptionType.BAD_ENTRY_DATA);
        }
    }

    @Override
    @Transactional(transactionManager = "transactionManagerWrite", propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = ClinicException.class)
    public Clinic saveClinic(Clinic clinic) {

        if (clinic.getIsValid() && clinic.getClinicStatus().equals(ClinicStatus.Open)) {

            return repositoryWrite.save(clinic);

        } else {

            throw new ClinicException(ClinicException.ExceptionType.BAD_ENTRY_DATA);
        }
    }

    @Override
    public Clinic validationProcess(Clinic clinic) {

        if (clinic.getValidationStatus().equals(ValidationStatus.PendingValidation)) {

            clinicValidation.checkIfClinicCanBeOpen(clinic);
        } else if (clinic.getValidationStatus().equals(ValidationStatus.Invalid)) {

            clinicValidation.validateClinic(clinic);
        } else {

            clinic.setClinicStatus(ClinicStatus.Open);
        }

        return clinic;
    }

    @Override
    @Transactional(transactionManager = "transactionManagerRead", readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.REPEATABLE_READ)
    public Page<Clinic> findClinics(String filter, int page, int size, String[] sort) {

        return repositoryRead.findAll(getClinicSpecification(filter), PageRequest.of(page, size, Sort.by(getSortOrders(sort))));
    }


    @Override
    @Transactional(transactionManager = "transactionManagerRead", readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.REPEATABLE_READ, rollbackFor = ClinicException.class)
    public List<Clinic> findAllClinicsWithPendingValidation() {

        return repositoryRead.findAllByValidationStatus(ValidationStatus.PendingValidation);
    }

    @Override
    @Transactional(transactionManager = "transactionManagerRead", readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.REPEATABLE_READ, rollbackFor = ClinicException.class)
    public List<Clinic> findAllByClinicType(ClinicType type) {
        return repositoryRead.findAllByClinicType(type);
    }


    /**
     * Apply filters from request and get the specificastion
     *
     * @param filter filter
     * @return ClinicSpecification built object;
     */
    private Specification<Clinic> getClinicSpecification(String filter) {

        ClinicSpecificationBuilder builder = new ClinicSpecificationBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|!:)((((\\w|\\W))+?)|(\\d{4}-\\d{2}-\\d{2})),");
        Matcher matcher = pattern.matcher(filter + ",");

        while (matcher.find()) {

            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        return builder.build();
    }


    /**
     * Get Sort Order for multiple sorts.
     *
     * @param sort array of sorting attributes
     * @return sorted orders
     */
    private List<Sort.Order> getSortOrders(String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();

        if (sort != null && sort.length > 0) {
            // else-if is used because when the request comes from client, it modifies the string array, the if,else covers the normal case.
            if (sort[0].contains(",")) {
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else if (sort.length % 2 == 0) {
                for (int i = 0; i < sort.length; i = i + 2) {
                    orders.add(new Sort.Order(getSortDirection(sort[i + 1]), sort[i]));
                }
            } else {
                orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            }
        }
        return orders;
    }

    /**
     * Get Sort Direction.
     *
     * @param direction direction can be asc or desc
     * @return direction of sorting
     */
    private Sort.Direction getSortDirection(String direction) {
        return direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
    }
}
