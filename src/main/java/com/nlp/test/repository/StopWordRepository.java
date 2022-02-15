package com.nlp.test.repository;

import com.nlp.test.models.StopWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StopWordRepository extends JpaRepository<StopWord, Long> {

    List<StopWord> findByWord(String word);
}
