package com.dbrovko.pwscraperservice.model.pojo;

import javax.persistence.*;

@Table(name = "ragweed_size")
@Entity
public class RagweedSizePOJO {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "size", nullable = false)
    private String size;

    public RagweedSizePOJO() {
    }

    public RagweedSizePOJO(Long id, String size) {
        this.id = id;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "RagweedSizePOJO{" +
                "id=" + id +
                ", size='" + size + '\'' +
                '}';
    }
}
