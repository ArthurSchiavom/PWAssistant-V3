package com.github.ArthurSchiavom.shared.entity.converter;

import com.github.ArthurSchiavom.pwassistant.entity.PwiServer;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;
import java.util.stream.Collectors;

@Converter
public class EnumListConverter implements AttributeConverter<List<PwiServer>, String> {
    private static final String SEPARATOR = "|!#|";

    @Override
    public String convertToDatabaseColumn(List<PwiServer> enums) {
        return enums.stream()
                .map(e -> e.name())
                .collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public List<PwiServer> convertToEntityAttribute(String enumsAsString) {
        return List.of(enumsAsString.split(SEPARATOR)).stream()
                .map(s -> PwiServer.valueOf(PwiServer.class, s))
                .toList();
    }
}
