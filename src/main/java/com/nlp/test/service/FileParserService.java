package com.nlp.test.service;

import com.nlp.test.constants.Constants;
import com.nlp.test.models.Affix;
import com.nlp.test.models.StemWord;
import com.nlp.test.models.StopWord;
import com.nlp.test.parser.impl.AffixFileParser;
import com.nlp.test.parser.impl.AnalysisFileParser;
import com.nlp.test.parser.impl.StemWordParser;
import com.nlp.test.parser.impl.StopWordParser;
import com.nlp.test.repository.AffixRepository;
import com.nlp.test.repository.StemWordRepository;
import com.nlp.test.repository.StopWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.*;
import java.util.*;

@Transactional
@RequiredArgsConstructor
@Service
public class FileParserService {

    public static final String EMPTY = " ";
    private final AffixRepository affixRepository;
    private final StopWordRepository stopWordRepository;
    private final StemWordRepository stemWordRepository;

    private final AffixFileParser affixFileParser;
    private final StemWordParser stemWordParser;
    private final StopWordParser stopWordParser;
    private final AnalysisFileParser analysisFileParser;
    private final EntityManager em;

    public List<Affix> parseAffixFile(InputStream bytes) {

        List<Affix> affixes = affixFileParser.parse(bytes);
        affixRepository.saveAll(affixes);
        return affixes;
    }

    public List<StemWord> parseStemFile(InputStream bytes) {
        List<StemWord> stemWords = stemWordParser.parse(bytes);
        stemWordRepository.saveAll(stemWords);
        return stemWords;
    }

    public List<StopWord> parseStopWord(InputStream is) {
        List<StopWord> stopWords = stopWordParser.parse(is);
        stopWordRepository.saveAll(stopWords);
        return stopWords;
    }

    public File parseAnalysisText(InputStream inputStream) {

        List<String> parse = analysisFileParser.parse(inputStream);
        boolean isFound = false;

        StringBuilder builder = new StringBuilder();
        List<String> strings = new ArrayList<>();
        for (String s : parse) {
            String[] split = s.split(" ");
            strings.addAll(Arrays.asList(split));
            strings.add("/n");
        }

        for (String word : strings) {
            if (Objects.equals("/n", word)) {
                builder.append("\n");
                continue;
            }

            Optional<StopWord> stopWord = stopWordRepository.findByWord(word.toLowerCase()).stream().findFirst();
            if (stopWord.isPresent()) {
                builder.append(word).append(EMPTY);
                continue;
            }

            Optional<StemWord> stemWord = stemWordRepository.findByWord(word).stream().findFirst();
            if (stemWord.isPresent()) {
                builder.append(word).append(EMPTY);
                continue;
            }

            int lengthWord = word.length();
            int minLength = 2;

            if (lengthWord <= minLength) {
                builder.append(word).append(EMPTY);
                continue;
            }

            for (int i = 0; i < lengthWord - minLength; i++) {
                String substring = word.substring(minLength + i);
                String start = word.substring(0, minLength + i);
                Optional<Affix> affixOptional = affixRepository.findByEnding(substring).stream().findFirst();
                if (affixOptional.isPresent()) {
                    Affix affix = affixOptional.get();
                    builder.append(start).append(EMPTY);
                    builder.append(affix.getTranslateEnding()).append(EMPTY);
                    isFound = true;
                    break;
                }
            }

            if (!isFound) {
                builder.append(word).append(EMPTY);
            }
        }
        String analysedText = builder.toString();

        saveAnalysedText(analysedText);

        return writeToFile(analysedText);
    }

    private void saveAnalysedText(String analysedText) {
        em.createNativeQuery("INSERT INTO analysed_text(text) VALUES (:analysedText)")
                .setParameter("analysedText", analysedText)
                .executeUpdate();
    }

    private File writeToFile(String data) {

        File file = new File(Constants.FILE_NAME);

        try(FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            //convert string to byte array
            byte[] bytes = data.getBytes();
            //write byte array to file
            bos.write(bytes);
            bos.close();
            fos.close();
            System.out.print("Data written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public InputStream getAnalysedText() {
        Object[] singleResult = (Object[]) em.createNativeQuery("SELECT * FROM analysed_text ORDER BY id DESC LIMIT 1")
                .getSingleResult();
        String text = (String) singleResult[1];
        return new ByteArrayInputStream(text.getBytes());
    }
}
