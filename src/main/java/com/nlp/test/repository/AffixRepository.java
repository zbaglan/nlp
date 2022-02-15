package com.nlp.test.repository;

import com.nlp.test.models.Affix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AffixRepository extends JpaRepository<Affix, Long> {

    List<Affix> findByEnding(String ending);
}
