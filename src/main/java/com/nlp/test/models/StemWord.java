package com.nlp.test.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "stem_words")
public class StemWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word;
}
