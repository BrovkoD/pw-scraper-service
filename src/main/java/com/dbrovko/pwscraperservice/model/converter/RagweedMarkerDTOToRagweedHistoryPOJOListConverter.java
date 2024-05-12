package com.dbrovko.pwscraperservice.model.converter;

import com.dbrovko.pwscraperservice.model.dto.RagweedMarkerDTO;
import com.dbrovko.pwscraperservice.model.pojo.RagweedHistoryPOJO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class RagweedMarkerDTOToRagweedHistoryPOJOListConverter implements Converter<RagweedMarkerDTO, List<RagweedHistoryPOJO>> {

    @Override
    public List<RagweedHistoryPOJO> convert(RagweedMarkerDTO ragweedMarkerDTO) {

        List<RagweedHistoryPOJO> ragweedHistoryPOJOList = new ArrayList<>();

        ragweedHistoryPOJOList.add(
                new RagweedHistoryPOJO(
                        ragweedMarkerDTO.getId(),
                        ragweedMarkerDTO.getDate(),
                        "spotted"
        ));

        ragweedMarkerDTO.getHistory().forEach(x -> {
                    String[] data = x.split(" ", 2);

                    LocalDate localDate;
                    try {
                        localDate = LocalDate.parse(data[0], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                    } catch (Exception e) {
                        return;
                    }

                    String historyDetails = parseHistoryDetails(data[1]);
                    if (historyDetails == null) {
                        return;
                    }

                    ragweedHistoryPOJOList.add(new RagweedHistoryPOJO(
                            ragweedMarkerDTO.getId(),
                            localDate,
                            historyDetails
                    ));
                });

        return ragweedHistoryPOJOList;
    }

    private String parseHistoryDetails(String type) {
        return switch (type) {
            case "Place cleared." -> "cleared";
            case "The place needs to be checked." -> "spotted";
            default -> null;
        };
    }
}
