package com.nlp.test.service.chain;

import com.nlp.test.models.StopWord;
import com.nlp.test.repository.StopWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Primary
public class StopWordMiddlewareService extends MiddlewareService{

    private final StopWordRepository stopWordRepository;

    @Override
    public String analyse(String word) {
        Optional<StopWord> stopWord = stopWordRepository.findByWord(word);

        if (stopWord.isPresent()) {
            return stopWord.get().getWord();
        }

        return analyseNext(word);
    }
}
