package com.mtucholski.reservation.app.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mtucholski.reservation.app.model.Clinic;
import com.mtucholski.reservation.app.model.MedicalDoctor;

import java.io.IOException;
import java.util.Collections;

public class CustomClinicDeserializer extends StdDeserializer<Clinic> {

    public CustomClinicDeserializer(Class<Clinic> clinicClass){

        super(clinicClass);
    }
    @Override
    public Clinic deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        Clinic clinic = new Clinic();
        MedicalDoctor doctor;
        ObjectMapper mapper = new ObjectMapper();

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode doctor_node = node.get("doctors");
        doctor = mapper.treeToValue(doctor_node, MedicalDoctor.class);
        int clinicID = node.get("clinic_id").asInt();
        String name = node.get("clinic_name").asText();

        clinic.setId(clinicID);
        clinic.setClinicName(name);
        clinic.setClinicAddress(clinic.getClinicAddress());
        clinic.setDoctors(Collections.singletonList(doctor));
        return clinic;
    }
}
