package ru.otus.highload.homework.fifth.config;

import io.micrometer.common.lang.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@ReadingConverter
@Component
public class CustomReadingTimestampConverter implements Converter<byte[], Timestamp> {
    @Override
    public Timestamp convert(byte[] source) {
        return new Timestamp(Long.parseLong(new String(source)));
    }
}
