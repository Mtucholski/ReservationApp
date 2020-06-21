package com.mtucholski.reservation.app.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mtucholski.reservation.app.model.Address;

import java.io.IOException;

public class CustomAddressSerializer extends StdSerializer<Address> {


    public CustomAddressSerializer(Class<Address> addressClass) {

        super(addressClass);
    }

    @Override
    public void serialize(Address address, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

        if ((address.getId() == null)) {
            jsonGenerator.writeNullField("id");
        } else {
            jsonGenerator.writeNumberField("address_id", address.getId());
        }

        jsonGenerator.writeStringField("city", address.getCity());
        jsonGenerator.writeStringField("street", address.getStreet());
        jsonGenerator.writeStringField("street_number", address.getStreetNumber());
        jsonGenerator.writeStringField("flat_number", address.getFlatNumber());
        jsonGenerator.writeObject(address.getPatient());

        jsonGenerator.writeObjectField("clinic", address.getClinic());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
    }
}
