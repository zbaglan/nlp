package com.nlp.test.service.chain;

import com.nlp.test.models.Affix;
import com.nlp.test.repository.AffixRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StemmingMiddlewareService extends MiddlewareService {

    private final AffixRepository affixRepository;

    @Override
    public String analyse(String word) {

        int lengthWord = word.length();
        int minLength = 2;

        if (lengthWord <= minLength) {
            return word;
        }

        for (int i = 0; i < lengthWord - minLength; i++) {
            String ending = word.substring(minLength + i);
            String stem = word.substring(0, minLength + i);
            Optional<Affix> affixOptional = affixRepository.findByEnding(ending);

            if (affixOptional.isPresent()) {
                return stem;
            }
        }

        return analyseNext(word);
    }
}
