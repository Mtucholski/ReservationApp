package com.mtucholski.reservation.app.specification;

import com.mtucholski.reservation.app.model.Clinic;
import com.mtucholski.reservation.app.model.ClinicCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClinicSpecificationBuilder {

    private final  List<ClinicCriteria> params;

    public ClinicSpecificationBuilder (){

        params = new ArrayList<>();
    }

    public ClinicSpecificationBuilder with(String key, String operation, Object value){

        params.add(new ClinicCriteria(key, operation, value));
        return this;
    }

    public Specification<Clinic> build() {

        if (params.size() == 0){

            return null;
        }

        List<Specification> specs = params.stream().map(ClinicSpecification::new).collect(Collectors.toList());
        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
