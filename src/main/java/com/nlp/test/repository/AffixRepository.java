package com.nlp.test.repository;

import com.nlp.test.models.Affix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AffixRepository extends JpaRepository<Affix, Long> {

    Optional<Affix> findByEnding(String ending);

    @Query("SELECT affix FROM Affix affix where affix.ending in (:endings)")
    List<Affix> findAllByEndingContaining(@Param("endings") List<String> endings);
}
