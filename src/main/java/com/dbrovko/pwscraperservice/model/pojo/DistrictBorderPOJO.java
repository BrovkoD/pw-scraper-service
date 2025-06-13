package com.dbrovko.pwscraperservice.model.pojo;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "district_border")
@Entity
public class DistrictBorderPOJO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "district_id", nullable = false)
    private Long districtId;

    @Column(name = "latitude", nullable = false)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false)
    private BigDecimal longitude;

    public DistrictBorderPOJO() {
    }

    public DistrictBorderPOJO(Long districtId, BigDecimal latitude, BigDecimal longitude) {
        this.districtId = districtId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
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

    @Override
    public String toString() {
        return "DistrictBorderPOJO{" +
                "id=" + id +
                ", districtId=" + districtId +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
