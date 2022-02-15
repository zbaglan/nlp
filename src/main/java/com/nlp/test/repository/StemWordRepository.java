package com.nlp.test.repository;

import com.nlp.test.models.StemWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StemWordRepository extends JpaRepository<StemWord, Long> {

    List<StemWord> findByWord(String word);
}
