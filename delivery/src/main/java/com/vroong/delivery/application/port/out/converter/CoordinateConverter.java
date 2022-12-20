package com.vroong.delivery.application.port.out.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vroong.delivery.domain.Coordinate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class CoordinateConverter implements AttributeConverter<Coordinate, String> {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public String convertToDatabaseColumn(Coordinate attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("fail convert Coordinate object to json string");
        }
    }

    @Override
    public Coordinate convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        try {
            return objectMapper.readValue(dbData, Coordinate.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("fail convert json string to Coordinate object");
        }
    }
}
