package org.springframework.data.aerospike.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Mustafa Zidan
 */
final class StringToLocalDateTimeConverter<T extends LocalDateTime> implements Converter<String, T> {

    public T convert(String source) {
        return (T) LocalDateTime.parse(source, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}