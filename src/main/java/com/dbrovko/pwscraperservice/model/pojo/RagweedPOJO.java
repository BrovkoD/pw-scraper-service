package com.dbrovko.pwscraperservice.model.pojo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Table(name = "ragweed_journal")
@Entity
public class RagweedPOJO {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "latitude", nullable = false)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false)
    private BigDecimal longitude;

    @ManyToOne
    @JoinColumn(name = "size_id", referencedColumnName = "id")
    private RagweedSizePOJO size;

    @ManyToOne
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private DistrictPOJO district;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<RagweedHistoryPOJO> history;

    public RagweedPOJO() {
    }

    public RagweedPOJO(Long id, BigDecimal latitude, BigDecimal longitude, boolean active) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public RagweedSizePOJO getSize() {
        return size;
    }

    public void setSize(RagweedSizePOJO size) {
        this.size = size;
    }

    public DistrictPOJO getDistrict() {
        return district;
    }

    public void setDistrict(DistrictPOJO district) {
        this.district = district;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<RagweedHistoryPOJO> getHistory() {
        return history;
    }

    public void setHistory(List<RagweedHistoryPOJO> history) {
        this.history = history;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RagweedPOJO that)) return false;
        return getId().equals(that.getId()) && getLatitude().equals(that.getLatitude()) && getLongitude().equals(that.getLongitude());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLatitude(), getLongitude());
    }

    @Override
    public String toString() {
        return "RagweedPOJO{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", size=" + size +
                ", district=" + district +
                ", active=" + active +
                ", deleted=" + deleted +
                ", history=" + history +
                '}';
    }
}
