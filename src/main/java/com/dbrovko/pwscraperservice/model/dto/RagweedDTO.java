package com.dbrovko.pwscraperservice.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RagweedDTO {

    @JsonProperty("i")
    private Long index;

    @JsonProperty("a")
    private BigDecimal latitude;

    @JsonProperty("o")
    private BigDecimal longitude;

    @JsonProperty("c")
    private int markType; // (1) location; (2) active; (3) cleared; (4) cleared by gov

    private RagweedMarkerDTO marker;

    public RagweedDTO() {
    }

    public RagweedDTO(Long index, BigDecimal latitude, BigDecimal longitude, int markType, RagweedMarkerDTO marker) {
        this.index = index;
        this.latitude = latitude;
        this.longitude = longitude;
        this.markType = markType;
        this.marker = marker;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public int getMarkType() {
        return markType;
    }

    public void setMarkType(int markType) {
        this.markType = markType;
    }

    public RagweedMarkerDTO getMarker() {
        return marker;
    }

    public void setMarker(RagweedMarkerDTO marker) {
        this.marker = marker;
    }

    @Override
    public String toString() {
        return "RagweedDTO{" +
                "index=" + index +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", markType=" + markType +
                ", marker=" + marker +
                '}';
    }
}
