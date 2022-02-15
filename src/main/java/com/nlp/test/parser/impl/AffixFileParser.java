package com.nlp.test.parser.impl;

import com.nlp.test.models.Affix;
import com.nlp.test.parser.Parser;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AffixFileParser implements Parser<Affix> {

    @Override
    public List<Affix> parse(InputStream fileBytes) {

        List<Affix> affixes = new ArrayList<>();

        try {

            Workbook workbook = WorkbookFactory.create(fileBytes);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                Cell endingCell = row.getCell(0);
                Cell translateEndingCell = row.getCell(1);

                Affix affix = new Affix();

                if (endingCell != null) {
                    affix.setEnding(endingCell.getStringCellValue().toLowerCase());
                }

                if (translateEndingCell != null) {
                    affix.setTranslateEnding(translateEndingCell.getStringCellValue());
                }

                affixes.add(affix);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return affixes.stream()
                .filter(affix -> affix.getEnding() != null && !affix.getEnding().isEmpty())
                .collect(Collectors.toList());

    }
}
