package com.mtucholski.reservation.app.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ClinicCriteria {

    private String key;
    private String operation;
    private Object value;
}
