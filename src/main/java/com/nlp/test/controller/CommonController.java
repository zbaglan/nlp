package com.nlp.test.controller;

import com.nlp.test.constants.Constants;
import com.nlp.test.dto.RequestDto;
import com.nlp.test.dto.ResponseDto;
import com.nlp.test.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.BASE_API + "/v1")
public class CommonController {

    private final CommonService commonService;

    @PostMapping(value = "/stemming", consumes = "application/json")
    public ResponseEntity<String> inputForStemming(@RequestBody RequestDto dto) {
        String stemmingText = commonService.getStemmingText(dto.getValue());
        return ResponseEntity.ok(stemmingText);
    }

    @PostMapping("/analyse")
    public ResponseEntity<String> inputForAnalysing(@RequestBody RequestDto dto) throws IOException {
        String analysedText = commonService.getAnalysedText(dto.getValue());
        return ResponseEntity.ok(analysedText);
    }

    @GetMapping("/get-result")
    public byte[] getFile() throws IOException {
        InputStream in = commonService.getResultText();
        return IOUtils.toByteArray(in);
    }
}
