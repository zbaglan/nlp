package com.nlp.test.controller;

import com.nlp.test.constants.Constants;
import com.nlp.test.models.Affix;
import com.nlp.test.models.StemWord;
import com.nlp.test.models.StopWord;
import com.nlp.test.service.FileParserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.BASE_API)
public class MainController {

    private final FileParserService parserService;

    @PostMapping("/input-text-file")
    public byte[] inputFile(@RequestParam("file") MultipartFile file) throws IOException {
        File result = parserService.parseAnalysisText(file.getInputStream());
        InputStream in = new FileInputStream(result);
        return IOUtils.toByteArray(in);
    }

    @PostMapping("/input-affix-file")
    public ResponseEntity<Map<Integer, List<Affix>>> inputAffixFile(@RequestParam("file") MultipartFile file) throws IOException {

        List<Affix> affixes = parserService.parseAffixFile(file.getInputStream());
        Map<Integer, List<Affix>> map = new HashMap<>();
        map.put(affixes.size(), affixes);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/input-stop-words-file")
    public ResponseEntity inputStopWordsFile(@RequestParam("file") MultipartFile file) throws IOException {

        List<StopWord> stopWordsWords = parserService.parseStopWord(file.getInputStream());
        Map<Integer, List<StopWord>> map = new HashMap<>();
        map.put(stopWordsWords.size(), stopWordsWords);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/input-stem-file")
    public ResponseEntity<Map<Integer, List<StemWord>>> inputStemFile(@RequestParam("file") MultipartFile file) throws IOException {
        List<StemWord> stemWords = parserService.parseStemFile(file.getInputStream());
        Map<Integer, List<StemWord>> map = new HashMap<>();
        map.put(stemWords.size(), stemWords);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/get-result")
    public byte[] getFile() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("result.txt");
        return IOUtils.toByteArray(inputStream);
    }
}
