package com.dbrovko.pwscraperservice.repository;

import com.dbrovko.pwscraperservice.model.pojo.DistrictPOJO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<DistrictPOJO, Long> {
}
