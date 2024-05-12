package com.dbrovko.pwscraperservice.model.pojo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "weather_journal")
@Entity
public class WeatherPOJO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @Column(name = "temperature", nullable = false)
    private BigDecimal temperature;

    @Column(name = "pressure", nullable = false)
    private BigDecimal pressure;

    @Column(name = "humidity", nullable = false)
    private BigDecimal humidity;

    public WeatherPOJO() {
    }

    public WeatherPOJO(LocalDateTime time, BigDecimal temperature, BigDecimal pressure, BigDecimal humidity) {
        this.time = time;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public BigDecimal getPressure() {
        return pressure;
    }

    public void setPressure(BigDecimal pressure) {
        this.pressure = pressure;
    }

    public BigDecimal getHumidity() {
        return humidity;
    }

    public void setHumidity(BigDecimal humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "WeatherPOJO{" +
                "id=" + id +
                ", time=" + time +
                ", temperature=" + temperature +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                '}';
    }
}
