package com.nlp.test.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "affixes")
public class Affix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ending;
    @Column(name = "translate_ending")
    private String translateEnding;
}
