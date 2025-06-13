package com.dbrovko.pwscraperservice.service;

import com.dbrovko.pwscraperservice.model.converter.RagweedMarkerDTOToRagweedHistoryPOJOListConverter;
import com.dbrovko.pwscraperservice.model.dto.RagweedDTO;
import com.dbrovko.pwscraperservice.model.dto.RagweedMarkerDTO;
import com.dbrovko.pwscraperservice.model.pojo.RagweedHistoryPOJO;
import com.dbrovko.pwscraperservice.model.pojo.RagweedPOJO;
import com.dbrovko.pwscraperservice.repository.DistrictRepository;
import com.dbrovko.pwscraperservice.repository.RagweedHistoryRepository;
import com.dbrovko.pwscraperservice.repository.RagweedRepository;
import com.dbrovko.pwscraperservice.repository.RagweedSizeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class RagweedService extends Scraper<RagweedPOJO> {

    private static final String ALL_POINTS = "https://www.ambrozii.net/api/marker/all";
    private static final String DATA_PER_POINT = "https://www.ambrozii.net/api/points/";

    private final RagweedRepository ragweedRepository;
    private final RagweedSizeRepository ragweedSizeRepository;
    private final RagweedHistoryRepository ragweedHistoryRepository;
    private final RagweedMarkerDTOToRagweedHistoryPOJOListConverter ragweedMarkerDTOToRagweedHistoryPOJOListConverter;
    private final DistrictRepository districtRepository;

    public RagweedService(RagweedRepository ragweedRepository, RagweedSizeRepository ragweedSizeRepository, RagweedHistoryRepository ragweedHistoryRepository, RagweedMarkerDTOToRagweedHistoryPOJOListConverter ragweedMarkerDTOToRagweedHistoryPOJOListConverter, DistrictRepository districtRepository) {
        this.ragweedRepository = ragweedRepository;
        this.ragweedSizeRepository = ragweedSizeRepository;
        this.ragweedHistoryRepository = ragweedHistoryRepository;
        this.ragweedMarkerDTOToRagweedHistoryPOJOListConverter = ragweedMarkerDTOToRagweedHistoryPOJOListConverter;
        this.districtRepository = districtRepository;
    }

    @Override
    void scrape() {

        log.info("Obtaining ragweed data...");

        ArrayList<RagweedPOJO> ragweedData = getNewData();

        if (ragweedRepository.count() > 0) {
            ragweedData.subList(0, ragweedData.indexOf(ragweedRepository.findFirstByDeletedFalseOrderByIdDesc()) + 1).clear();
        }

        ragweedRepository.saveAll(ragweedData);

        log.info("Ragweed data fetching is completed");
    }

    private ArrayList<RagweedPOJO> getNewData() {

        ArrayList<RagweedPOJO> newRagweedData;

        try (CloseableHttpResponse response = getRequest(ALL_POINTS)) {

            newRagweedData = new ObjectMapper().readValue(
                            response.getEntity().getContent(),
                            new TypeReference<ArrayList<RagweedDTO>>() {}
                    )
                    .stream()
                    .filter(RagweedDTO -> RagweedDTO.getMarkType() != 1) // get all marks except locations
                    .map(x -> new RagweedPOJO(
                            x.getIndex(),
                            x.getLatitude(),
                            x.getLongitude(),
                            x.getMarkType() == 2))
                    .collect(Collectors.toCollection(ArrayList::new));

            Collections.reverse(newRagweedData);
        } catch (Exception e) {
            log.warn("Error getting ragweed data", e);
            throw new RuntimeException("Error getting ragweed data", e);
        }

        return newRagweedData;
    }

    void update() throws IOException, InterruptedException {

//        log.info("Updating ragweed data...");
//
////        UPDATE INFO
//        ArrayList<RagweedPOJO> newRagweedData = getNewData();
//        List<RagweedPOJO> oldRagweedData = ragweedRepository.findByDeletedFalse();
//
//        int i = 0;
//
//        for (RagweedPOJO oldRagweedPOJO : oldRagweedData) {
//
//            RagweedPOJO newRagweedPOJO = newRagweedData.get(i);
//
//            if (!oldRagweedPOJO.getId().equals(newRagweedPOJO.getId())) {
//
//                oldRagweedPOJO.setDeleted(true);
//                ragweedRepository.save(oldRagweedPOJO);
//
//                ragweedHistoryRepository.save(
//                        new RagweedHistoryPOJO(
//                                oldRagweedPOJO.getId(),
//                                LocalDate.now(),
//                                "deleted"
//                        ));
//
//                continue;
//            }
//
//            checkChanges(oldRagweedPOJO, newRagweedPOJO);
//
//            i++;
//        }
//
//        for (int x = i; x < newRagweedData.size(); x++) {
//            save(newRagweedData.get(x));
//        }
//
//        log.info("Ragweed data updated");
    }

//    private void checkChanges(RagweedPOJO oldRagweedPOJO, RagweedPOJO newRagweedPOJO) throws IOException {
//
//        boolean save = false;
//        if (!oldRagweedPOJO.getSize().equals(newRagweedPOJO.getSize())) {
//            oldRagweedPOJO.setSize();
//        }
//        RagweedMarkerDTO ragweedMarkerDTO = getMarker(oldRagweedPOJO);
//        if (oldRagweedPOJO.isActive() != newIsActive) {
//
//            log.info(format("Mismatch on log: %s", oldRagweedPOJO));
//
//
//            oldRagweedPOJO.setActive(newIsActive);
//
//            ragweedHistoryRepository.save(
//                    new RagweedHistoryPOJO(
//                            ragweedPOJO.getId(),
//                            ragweedPOJO.isActive() ? "add" : "remove",
//                            ragweedMarkerDTO.getDate()
//                    ));
//
//            if (newIsActive) {
////                куди дівати дати активації після виривання???
//            } else {
////                    oldRagweedPOJO.setClearTime(ragweedMarkerDTO.getDate());
//            }
//
//            ragweedRepository.save(oldRagweedPOJO);
//
//            log.info(format("Updated data: %s", oldRagweedPOJO));
//        }
//    }

    void getDetails(RagweedPOJO ragweedPOJO) throws InterruptedException {

        RagweedMarkerDTO ragweedMarkerDTO = getMarker(ragweedPOJO.getId());

        String ragweedSize = ragweedMarkerDTO.getSize();
        ragweedPOJO.setSize(ragweedSizeRepository.findBySize(ragweedSize).orElse(null));

        ragweedPOJO.setHistory(ragweedMarkerDTOToRagweedHistoryPOJOListConverter.convert(ragweedMarkerDTO));

        if (ragweedPOJO.getHistory().isEmpty()) {
            ragweedPOJO.getHistory().add(new RagweedHistoryPOJO(
                    ragweedPOJO.getId(),
                    ragweedMarkerDTO.getDate(),
                    ragweedPOJO.isActive() ? "spotted" : "cleared"
            ));
        }

        ragweedRepository.save(ragweedPOJO);
    }

    private RagweedMarkerDTO getMarker(Long id) throws InterruptedException {

        RagweedMarkerDTO ragweedMarkerDTO = null;

        do {
            try (CloseableHttpResponse response = getRequest(DATA_PER_POINT + id)) {
                ragweedMarkerDTO = new ObjectMapper().readValue(
                                response.getEntity().getContent(),
                                new TypeReference<RagweedDTO>() {}
                        ).getMarker();
            } catch (Exception e) {
                log.warn("Error getting ragweed marker data, id = {}. Resolving...", id);
                synchronized (this) {
                    wait(45000);
                }
            }
        } while (ragweedMarkerDTO == null);

        return ragweedMarkerDTO;
    }
}
