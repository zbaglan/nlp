package com.nlp.test.parser;

import java.io.InputStream;
import java.util.List;

public interface Parser<T> {

    List<T> parse(InputStream fileBytes);
}
