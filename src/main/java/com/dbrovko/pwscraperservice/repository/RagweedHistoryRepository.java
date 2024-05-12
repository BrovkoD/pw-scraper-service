package com.dbrovko.pwscraperservice.repository;

import com.dbrovko.pwscraperservice.model.pojo.RagweedHistoryPOJO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RagweedHistoryRepository extends JpaRepository<RagweedHistoryPOJO, Long> {
}
