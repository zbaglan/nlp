package com.nlp.test.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "stem_words")
public class StemWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StemWord stemWord = (StemWord) o;
        return word != null && Objects.equals(word, stemWord.word);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
