package com.nlp.test.controller;

import com.nlp.test.constants.Constants;
import com.nlp.test.models.Affix;
import com.nlp.test.models.StemWord;
import com.nlp.test.models.StopWord;
import com.nlp.test.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.BASE_API)
public class AdminController {

    private final AdminService parserService;

    @PostMapping("/input-affix-file")
    public ResponseEntity<Map<Integer, List<Affix>>> inputAffixFile(@RequestParam("file") MultipartFile file) throws IOException {

        Set<Affix> affixes = parserService.parseAffixFile(file.getInputStream());
        Map<Integer, List<Affix>> map = new HashMap<>();
        map.put(affixes.size(), new ArrayList<>(affixes));
        return ResponseEntity.ok(map);
    }

    @PostMapping("/input-stop-words-file")
    public ResponseEntity<Map<Integer, List<StopWord>>> inputStopWordsFile(@RequestParam("file") MultipartFile file) throws IOException {

        Set<StopWord> stopWordsWords = parserService.parseStopWord(file.getInputStream());
        Map<Integer, List<StopWord>> map = new HashMap<>();
        map.put(stopWordsWords.size(), new ArrayList<>(stopWordsWords));
        return ResponseEntity.ok(map);
    }

    @PostMapping("/input-stem-file")
    public ResponseEntity<Map<Integer, List<StemWord>>> inputStemFile(@RequestParam("file") MultipartFile file) throws IOException {
        Set<StemWord> stemWords = parserService.parseStemFile(file.getInputStream());
        Map<Integer, List<StemWord>> map = new HashMap<>();
        map.put(stemWords.size(), new ArrayList<>(stemWords));
        return ResponseEntity.ok(map);
    }
}
