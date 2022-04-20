package com.nlp.test.service.chain;

import com.nlp.test.models.StemWord;
import com.nlp.test.repository.StemWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StemWordMiddlewareService extends MiddlewareService {

    private final StemWordRepository stemWordRepository;

    @Override
    public String analyse(String word) {
        Optional<StemWord> stemWord = stemWordRepository.findByWord(word);

        if (stemWord.isPresent()) {
            return stemWord.get().getWord();
        }

        return analyseNext(word);
    }
}
