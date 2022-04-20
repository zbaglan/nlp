package com.nlp.test.repository;

import com.nlp.test.models.StemWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StemWordRepository extends JpaRepository<StemWord, Long> {

    Optional<StemWord> findByWord(String word);

    @Query("SELECT sw FROM StemWord sw where sw.word in (:words)")
    List<StemWord> findAllByWordContaining(List<String> words);
}
