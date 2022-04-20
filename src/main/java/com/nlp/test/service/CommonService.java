package com.nlp.test.service;

import com.nlp.test.constants.Constants;
import com.nlp.test.parser.impl.AnalysisFileParser;
import com.nlp.test.service.chain.AnalysingMiddlewareService;
import com.nlp.test.service.chain.MiddlewareService;
import com.nlp.test.service.chain.StemWordMiddlewareService;
import com.nlp.test.service.chain.StemmingMiddlewareService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CommonService {

    private final MiddlewareService middlewareService;
    private final ApplicationContext context;
    private final AnalysisFileParser analysisFileParser;
    private final EntityManager em;

    public String getStemmingText(InputStream inputStream) {
        StringBuilder stemmingText = new StringBuilder();

        middlewareService
                .linkWith(context.getBean(StemWordMiddlewareService.class))
                .linkWith(context.getBean(StemmingMiddlewareService.class));

        List<String> words = getWordsFromParsedText(inputStream);

        for (String word : words) {
            String stemmingWord = middlewareService.analyse(word);
            stemmingText
                    .append(stemmingWord)
                    .append(Constants.EMPTY);
        }

        saveStemmingText(stemmingText.toString());

        return stemmingText.toString();
    }

    public String getAnalysedText(InputStream inputStream) {
        StringBuilder analysedText = new StringBuilder();

        middlewareService
                .linkWith(context.getBean(StemWordMiddlewareService.class))
                .linkWith(context.getBean(AnalysingMiddlewareService.class));

        List<String> words = getWordsFromParsedText(inputStream);

        for (String word : words) {
            String stemmingWord = middlewareService.analyse(word);
            analysedText
                    .append(stemmingWord)
                    .append(Constants.EMPTY);
        }

        saveAnalysedText(analysedText.toString());

        return analysedText.toString();
    }

    private List<String> getWordsFromParsedText(InputStream inputStream) {
        Set<String> parsedFileText = analysisFileParser.parse(inputStream);
        List<String> words = new ArrayList<>();

        for (String parsedLine : parsedFileText) {
            String[] split = parsedLine.split(Constants.EMPTY);
            words.addAll(Arrays.asList(split));
        }

        return words;
    }

    private void saveAnalysedText(String analysedText) {
        em.createNativeQuery("INSERT INTO analysed_text(text) VALUES (:analysedText)")
                .setParameter("analysedText", analysedText)
                .executeUpdate();
    }

    private void saveStemmingText(String stemmingText) {
        em.createNativeQuery("INSERT INTO stemming_text(text) VALUES (:analysedText)")
                .setParameter("analysedText", stemmingText)
                .executeUpdate();
    }

    public InputStream getResultText() {
        Object[] singleResult = (Object[]) em.createNativeQuery("SELECT * FROM analysed_text ORDER BY id DESC LIMIT 1")
                .getSingleResult();
        String text = (String) singleResult[0];
        return new ByteArrayInputStream(text.getBytes());
    }
}
