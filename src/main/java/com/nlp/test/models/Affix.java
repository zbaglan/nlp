package com.nlp.test.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "affixes")
public class Affix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ending;
    @Column(name = "translate_ending")
    private String translateEnding;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Affix affix = (Affix) o;
        return ending != null && Objects.equals(ending, affix.ending);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
