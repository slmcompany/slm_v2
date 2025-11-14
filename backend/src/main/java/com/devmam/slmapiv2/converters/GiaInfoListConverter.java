package com.devmam.slmapiv2.converters;

import com.devmam.slmapiv2.entities.commons.GiaInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.postgresql.util.PGobject;

import java.util.ArrayList;
import java.util.List;

@Converter
public class GiaInfoListConverter implements AttributeConverter<List<GiaInfo>, Object> {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Object convertToDatabaseColumn(List<GiaInfo> attribute) {
        if (attribute == null) return "[]";
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting List<GiaType> to JSON", e);
        }
    }

    @Override
    public List<GiaInfo> convertToEntityAttribute(Object dbData) {
        if (dbData == null) return new ArrayList<>();
        try {
            String json;
            if (dbData instanceof PGobject pgObj) {
                json = pgObj.getValue();
            } else {
                json = dbData.toString();
            }
            return mapper.readValue(json, new TypeReference<List<GiaInfo>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Error reading JSON to List<GiaType>: " + dbData, e);
        }
    }
}
