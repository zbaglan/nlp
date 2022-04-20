package com.nlp.test.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "stop_words")
public class StopWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word;

    public StopWord(String word) {
        this.word = word;
    }

    public StopWord() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StopWord stopWord = (StopWord) o;
        return word != null && Objects.equals(word, stopWord.word);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
