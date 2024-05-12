package com.dbrovko.pwscraperservice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RagweedMarkerDTO {

    @JsonProperty("id")
    private Long id;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;

    @JsonProperty("filter")
    private String size;

    private List<String> history;

    public RagweedMarkerDTO() {
    }

    public RagweedMarkerDTO(Long id, LocalDate date, String size, List<String> history) {
        this.id = id;
        this.date = date;
        this.size = size;
        this.history = history;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<String> getHistory() {
        return history;
    }

    public void setHistory(List<String> history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return "RagweedMarkerDTO{" +
                "id=" + id +
                ", date=" + date +
                ", size='" + size + '\'' +
                ", history=" + history +
                '}';
    }
}
