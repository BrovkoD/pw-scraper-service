package com.dbrovko.pwscraperservice.repository;

import com.dbrovko.pwscraperservice.model.pojo.WeatherPOJO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherPOJO, Long> {

    Optional<WeatherPOJO> findFirstByOrderByIdDesc();
}
