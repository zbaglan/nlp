package com.nlp.test.repository;

import com.nlp.test.models.StopWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StopWordRepository extends JpaRepository<StopWord, Long> {
    Optional<StopWord> findByWord(String word);

    @Query("SELECT sw FROM StopWord sw where sw.word in (:words)")
    List<StopWord> findAllByWordContaining(@Param("words") List<String> words);
}
