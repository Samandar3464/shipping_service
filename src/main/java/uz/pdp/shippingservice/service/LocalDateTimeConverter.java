package uz.pdp.shippingservice.service;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private final DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDateTime convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(source, formatter);
    }

    public LocalDate convertOnlyDate(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return LocalDate.parse(source, formatter);
    }

//    public LocalDateTime convert2(String source) {
//        if (source == null || source.isEmpty()) {
//            return null;
//        }
//        String s1 = source.substring(0,19);
//        return LocalDateTime.parse(s1, formatter);
//    }
    public LocalDateTime convertDate(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(source + "T00:00:00", formatter);
    }
}