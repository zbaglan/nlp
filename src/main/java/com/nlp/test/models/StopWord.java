package com.nlp.test.models;

import lombok.Data;

import javax.persistence.*;

@Data
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
}
