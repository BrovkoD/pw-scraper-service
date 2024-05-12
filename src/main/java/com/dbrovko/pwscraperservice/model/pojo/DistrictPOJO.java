package com.dbrovko.pwscraperservice.model.pojo;

import javax.persistence.*;

@Table(name = "district_journal")
@Entity
public class DistrictPOJO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public DistrictPOJO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DistrictPOJO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
