package com.dbrovko.pwscraperservice.service;

import com.dbrovko.pwscraperservice.model.converter.RowToWeatherPOJOConverter;
import com.dbrovko.pwscraperservice.model.pojo.WeatherPOJO;
import com.dbrovko.pwscraperservice.repository.WeatherRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;

@Slf4j
@Service
public class WeatherService extends Scraper<WeatherPOJO> {

    private static final String MAIN_PAGE = "https://rp5.kz/Архів_погоди_в_Києві,_Жулянах_(аеропорт)";
    private static final String ACCESS_POINT = "https://rp5.kz/responses/reFileSynop.php";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final WeatherRepository weatherRepository;
    private final RowToWeatherPOJOConverter rowToWeatherPOJOConverter;

    public WeatherService(WeatherRepository weatherRepository, RowToWeatherPOJOConverter rowToWeatherPOJOConverter) {
        this.weatherRepository = weatherRepository;
        this.rowToWeatherPOJOConverter = rowToWeatherPOJOConverter;
    }

    @Override
    @Scheduled(fixedDelay = 45000)
    void scrape() throws IOException {

        log.info("Obtaining weather data...");

        String cookie;

        try (CloseableHttpResponse response = getRequest(MAIN_PAGE)) {
            Header[] responseHeaders = response.getHeaders("Set-Cookie");

            cookie = Arrays.stream(responseHeaders)
                    .map(Header::getValue)
                    .filter(x -> x.contains("PHPSESSID"))
                    .findFirst()
                    .get();
        } catch (Exception e) {
            log.warn("Error getting PHPSESSID cookie for weather", e);
            throw new RuntimeException("Error getting PHPSESSID cookie for weather", e);
        }

        WeatherPOJO lastSavedWeatherPOPJO = weatherRepository.findFirstByOrderByIdDesc()
                .orElse(null);
        final LocalDate START_DATE = lastSavedWeatherPOPJO == null ?
                LocalDate.of(2017, 1, 1)
                :
                lastSavedWeatherPOPJO.getTime().toLocalDate();

        final LocalDate END_DATE = LocalDate.now();

        StringEntity entity = new StringEntity(
                "wmo_id=33345" +
                        "&a_date1=" + START_DATE.format(DATE_FORMATTER) +
                        "&a_date2=" + END_DATE.format(DATE_FORMATTER) +
                        "&f_ed3=1" +
                        "&f_ed4=1" +
                        "&f_ed5=14" +
                        "&f_pe=1" +
                        "&f_pe1=1" +
                        "&lng_id=3" +
                        "&type=xls");

        Header[] requestHeaders = {
                new BasicHeader("Content-Type", "application/x-www-form-urlencoded"),
                new BasicHeader("X-Requested-With", "XMLHttpRequest"),
                new BasicHeader("Cookie", cookie),
                new BasicHeader("Referer", "https://rp5.ru/")
        };

        String dataURL;

        try (CloseableHttpResponse response = postRequest(ACCESS_POINT, entity, requestHeaders)) {

            String urlResponseString = new String(
                    response.getEntity().getContent().readAllBytes(),
                    StandardCharsets.UTF_8
            );

            dataURL = urlResponseString.substring(
                    urlResponseString.indexOf("http"),
                    urlResponseString.indexOf(" download")
            );
        } catch (Exception e) {
            log.warn("Error getting URL for .xls file with weather data", e);
            throw new RuntimeException("Error getting URL for .xls file with weather data", e);
        }

        try (CloseableHttpResponse response = getRequest(dataURL);
             InputStream inputStream = new GZIPInputStream(response.getEntity().getContent());
             Workbook myWorkBook = new HSSFWorkbook(inputStream)) {

            Sheet sheet = myWorkBook.getSheetAt(0);

            for (int i = sheet.getLastRowNum(); i > 6; i--) {

                Row row = sheet.getRow(i);

                WeatherPOJO weatherPOJO = rowToWeatherPOJOConverter.convert(row);
                if (weatherPOJO == null) {
                    log.warn("Error parsing .xls file");
                    throw new RuntimeException("Error parsing .xls file");
                }

                if (lastSavedWeatherPOPJO != null) {
                    if (weatherPOJO.getTime().compareTo(lastSavedWeatherPOPJO.getTime()) == 0) {
                        lastSavedWeatherPOPJO = null;
                    }
                    continue;
                }

                weatherRepository.save(weatherPOJO);
            }
        } catch (Exception e) {
            log.warn("Error getting .xls file with weather data", e);
            throw new RuntimeException("Error getting .xls file with weather data", e);
        }

        log.info("Weather data fetching is completed");
    }

    void update() {}
}
