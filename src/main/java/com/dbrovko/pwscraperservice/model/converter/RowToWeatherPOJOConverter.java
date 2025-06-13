package com.dbrovko.pwscraperservice.model.converter;

import com.dbrovko.pwscraperservice.model.pojo.WeatherPOJO;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class RowToWeatherPOJOConverter implements Converter<Row, WeatherPOJO> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    public WeatherPOJO convert(Row row) {

        return new WeatherPOJO(
                LocalDateTime.parse(row.getCell(0).getStringCellValue(), DATE_TIME_FORMATTER),
                BigDecimal.valueOf(row.getCell(1).getNumericCellValue()),
                BigDecimal.valueOf(row.getCell(2).getNumericCellValue()),
                BigDecimal.valueOf(row.getCell(5).getNumericCellValue())
        );
    }
}
