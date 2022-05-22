package com.nlp.test.service;

import com.nlp.test.TestApplication;
import com.nlp.test.constants.Constants;
import com.nlp.test.models.Affix;
import com.nlp.test.models.StemWord;
import com.nlp.test.models.StopWord;
import com.nlp.test.repository.AffixRepository;
import com.nlp.test.repository.StemWordRepository;
import com.nlp.test.repository.StopWordRepository;
import com.nlp.test.service.chain.AnalysingMiddlewareService;
import com.nlp.test.service.chain.MiddlewareService;
import com.nlp.test.service.chain.StemWordMiddlewareService;
import com.nlp.test.service.chain.StemmingMiddlewareService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class CommonService {

    private final MiddlewareService middlewareService;
    private final ApplicationContext context;
    private final EntityManager em;
    private final AdminService parserService;

    public String getStemmingText(String text) {
        checkRepositories();
        StringBuilder stemmingText = new StringBuilder();

        middlewareService
                .linkWith(context.getBean(StemWordMiddlewareService.class))
                .linkWith(context.getBean(StemmingMiddlewareService.class));

        String[] split = text.split(Constants.EMPTY);

        for (String word : split) {
            String stemmingWord = middlewareService.analyse(word);
            stemmingText
                    .append(stemmingWord)
                    .append(Constants.EMPTY);
        }

        saveStemmingText(stemmingText.toString());

        return stemmingText.toString();
    }

    public String getAnalysedText(String text) {
        checkRepositories();
        StringBuilder analysedText = new StringBuilder();

        middlewareService
                .linkWith(context.getBean(StemWordMiddlewareService.class))
                .linkWith(context.getBean(AnalysingMiddlewareService.class));

        String[] words = text.split(Constants.EMPTY);

        for (String word : words) {
            String stemmingWord = middlewareService.analyse(word);
            analysedText
                    .append(stemmingWord)
                    .append(Constants.EMPTY);
        }

        saveAnalysedText(analysedText.toString());

        return analysedText.toString();
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

    private void checkRepositories() {
        AffixRepository affixRepository = context.getBean(AffixRepository.class);
        StemWordRepository stemWordRepository = context.getBean(StemWordRepository.class);
        StopWordRepository stopWordRepository = context.getBean(StopWordRepository.class);
        List<Affix> affixes = affixRepository.findAll();
        List<StemWord> stemWords = stemWordRepository.findAll();
        List<StopWord> stopWords = stopWordRepository.findAll();

        if (affixes.isEmpty()) {
            InputStream affixStream = TestApplication.class.getResourceAsStream("/files/affixes.xls");
            parserService.parseAffixFile(affixStream);
        }

        if (stemWords.isEmpty()) {
            InputStream stemWordInputStream = TestApplication.class.getResourceAsStream("/files/stem_words.xls");
            parserService.parseStemFile(stemWordInputStream);
        }

        if (stopWords.isEmpty()) {
            InputStream resourceAsStream = TestApplication.class.getResourceAsStream("/files/stop_words.txt");
            parserService.parseStopWord(resourceAsStream);
        }
    }
}
