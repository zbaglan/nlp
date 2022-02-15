package com.nlp.test.parser.impl;

import com.nlp.test.models.StopWord;
import com.nlp.test.parser.Parser;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class StopWordParser implements Parser<StopWord> {
    @Override
    public List<StopWord> parse(InputStream fileBytes) {

        List<StopWord> stopWords = new ArrayList<>();

        try {
             InputStreamReader isr = new InputStreamReader(fileBytes,
                     StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr);

            br.lines().forEach(line -> stopWords.add(new StopWord(line.toLowerCase())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stopWords;
    }
}
