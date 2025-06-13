package com.dbrovko.pwscraperservice.repository;

import com.dbrovko.pwscraperservice.model.pojo.RagweedPOJO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RagweedRepository extends JpaRepository<RagweedPOJO, Long> {

    RagweedPOJO findFirstByDeletedFalseOrderByIdDesc();

    List<RagweedPOJO> findByDeletedFalse();
}
