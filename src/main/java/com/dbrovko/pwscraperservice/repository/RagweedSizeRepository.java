package com.dbrovko.pwscraperservice.repository;

import com.dbrovko.pwscraperservice.model.pojo.RagweedSizePOJO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RagweedSizeRepository extends JpaRepository<RagweedSizePOJO, Long> {

    Optional<RagweedSizePOJO> findBySize(String size);
}