package com.github.ArthurSchiavom.shared.entity.converter;

import com.github.ArthurSchiavom.pwassistant.entity.PwiServer;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class PwiServerSetConverter implements AttributeConverter<Set<PwiServer>, String> {
    private static final String SEPARATOR = "|!#|";

    @Override
    public String convertToDatabaseColumn(Set<PwiServer> enums) {
        return enums.stream()
                .map(e -> e.name())
                .collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public Set<PwiServer> convertToEntityAttribute(String enumsAsString) {
        return Set.of(enumsAsString.split(SEPARATOR)).stream()
                .map(s -> PwiServer.valueOf(PwiServer.class, s))
                .collect(Collectors.toSet());
    }
}
