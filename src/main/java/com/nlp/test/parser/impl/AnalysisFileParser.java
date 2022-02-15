package com.nlp.test.parser.impl;

import com.nlp.test.parser.Parser;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class AnalysisFileParser implements Parser<String> {
    @Override
    public List<String> parse(InputStream fileBytes) {

        List<String> result = new ArrayList<>();

        try {
            InputStreamReader isr = new InputStreamReader(fileBytes,
                    StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);

            br.lines().forEach(result::add);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
