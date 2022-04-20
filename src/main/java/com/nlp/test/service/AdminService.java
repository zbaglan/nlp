package com.nlp.test.service;

import com.nlp.test.models.Affix;
import com.nlp.test.models.StemWord;
import com.nlp.test.models.StopWord;
import com.nlp.test.parser.impl.AffixFileParser;
import com.nlp.test.parser.impl.StemWordParser;
import com.nlp.test.parser.impl.StopWordParser;
import com.nlp.test.repository.AffixRepository;
import com.nlp.test.repository.StemWordRepository;
import com.nlp.test.repository.StopWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class AdminService {

    private final AffixRepository affixRepository;
    private final StopWordRepository stopWordRepository;
    private final StemWordRepository stemWordRepository;

    private final AffixFileParser affixFileParser;
    private final StemWordParser stemWordParser;
    private final StopWordParser stopWordParser;

    public Set<Affix> parseAffixFile(InputStream bytes) {

        Set<Affix> affixes = affixFileParser.parse(bytes);
        List<String> endings = affixes.stream().map(Affix::getEnding).collect(Collectors.toList());
        List<Affix> allByEndingContaining = affixRepository.findAllByEndingContaining(endings);
        List<String> dbEndings = allByEndingContaining.stream().map(Affix::getEnding).collect(Collectors.toList());

        affixes.removeIf(affix -> dbEndings.contains(affix.getEnding()));

        affixRepository.saveAll(affixes);
        return affixes;
    }

    public Set<StemWord> parseStemFile(InputStream bytes) {
        Set<StemWord> stemWords = stemWordParser.parse(bytes);
        List<String> words = stemWords.stream().map(StemWord::getWord).collect(Collectors.toList());
        List<StemWord> allByWordContaining = stemWordRepository.findAllByWordContaining(words);
        List<String> dbWords = allByWordContaining.stream().map(StemWord::getWord).collect(Collectors.toList());
        stemWords.removeIf(stemWord -> dbWords.contains(stemWord.getWord()));

        stemWordRepository.saveAll(stemWords);
        return stemWords;
    }

    public Set<StopWord> parseStopWord(InputStream is) {
        Set<StopWord> stopWords = stopWordParser.parse(is);
        List<String> words = stopWords.stream().map(StopWord::getWord).collect(Collectors.toList());
        List<StopWord> allByWordContaining = stopWordRepository.findAllByWordContaining(words);
        List<String> dbWords = allByWordContaining.stream().map(StopWord::getWord).collect(Collectors.toList());
        stopWords.removeIf(stopWord -> dbWords.contains(stopWord.getWord()));

        stopWordRepository.saveAll(stopWords);
        return stopWords;
    }
}
