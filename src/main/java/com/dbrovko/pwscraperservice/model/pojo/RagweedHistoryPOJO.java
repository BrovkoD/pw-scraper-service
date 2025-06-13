package com.dbrovko.pwscraperservice.model.pojo;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "ragweed_history")
@Entity
public class RagweedHistoryPOJO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "ragweed_id", nullable = false)
    private Long ragweedId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "action", nullable = false)
    private String action;

    public RagweedHistoryPOJO() {
    }

    public RagweedHistoryPOJO(Long ragweedId, LocalDate date, String action) {
        this.ragweedId = ragweedId;
        this.date = date;
        this.action = action;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRagweedId() {
        return ragweedId;
    }

    public void setRagweedId(Long ragweedId) {
        this.ragweedId = ragweedId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "RagweedHistoryPOJO{" +
                "id=" + id +
                ", ragweedId=" + ragweedId +
                ", date=" + date +
                ", action='" + action + '\'' +
                '}';
    }
}
