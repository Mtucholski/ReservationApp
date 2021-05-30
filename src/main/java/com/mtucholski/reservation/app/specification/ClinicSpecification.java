package com.mtucholski.reservation.app.specification;

import com.mtucholski.reservation.app.model.Clinic;
import com.mtucholski.reservation.app.model.ClinicCriteria;
import com.mtucholski.reservation.app.model.ClinicStatus;
import com.mtucholski.reservation.app.model.ClinicType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class ClinicSpecification implements Specification<Clinic> {

    private static final long serialVersionUID = 1L;
    private final ClinicCriteria criteria;

    public ClinicSpecification(final ClinicCriteria criteria) {

        super();
        this.criteria = criteria;
    }

    public ClinicCriteria getCriteria() {

        return criteria;
    }

    @Override
    public Predicate toPredicate(Root<Clinic> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        switch (criteria.getKey()) {

            case "creationDate":
            case "closeDate":

                if (criteria.getKey().equals(":Null")) {

                    return criteriaBuilder.isNull(root.get(criteria.getKey()));
                }
                return criteriaBuilder.equal(root.get(criteria.getKey()), getParsedInstantDate());

            case "clinicStatus":
                if (criteria.getOperation().equals("!:")) {

                    return criteriaBuilder.notEqual(root.get(criteria.getKey()), ClinicStatus.valueOf((String) criteria.getValue()));
                }

                return criteriaBuilder.equal(root.get(criteria.getKey()), ClinicStatus.valueOf((String) criteria.getValue()));

            case "clinicType":
                if (criteria.getOperation().equals("!:")) {

                    return criteriaBuilder.notEqual(root.get(criteria.getKey()), ClinicType.valueOf((String) criteria.getValue()));
                }

                return criteriaBuilder.notEqual(root.get(criteria.getKey()), ClinicType.valueOf((String) criteria.getValue()));
        }

        return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
    }

    private Instant getParsedInstantDate() {
        return LocalDate.parse((String) criteria.getValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .atStartOfDay().toInstant(ZoneOffset.UTC);
    }
}
