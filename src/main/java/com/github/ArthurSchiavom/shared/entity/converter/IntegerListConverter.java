package com.github.ArthurSchiavom.shared.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class IntegerListConverter implements AttributeConverter<List<Integer>, String> {
    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(final List<Integer> numbers) {
        return numbers.stream().map(i -> Integer.toString(i))
                .collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public List<Integer> convertToEntityAttribute(final String numbersAsString) {
        return Arrays.stream(numbersAsString.split(SEPARATOR))
                .map(s -> Integer.parseInt(s))
                .toList();
    }
}
