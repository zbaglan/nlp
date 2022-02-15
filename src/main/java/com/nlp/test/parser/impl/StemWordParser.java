package com.nlp.test.parser.impl;

import com.nlp.test.models.StemWord;
import com.nlp.test.parser.Parser;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class StemWordParser implements Parser<StemWord> {
    @Override
    public List<StemWord> parse(InputStream fileBytes) {

        List<StemWord> stemWords = new ArrayList<>();

        try {
            Workbook workbook = WorkbookFactory.create(fileBytes);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell != null && !cell.getStringCellValue().isEmpty()) {
                        StemWord word = new StemWord();
                        word.setWord(cell.getStringCellValue().toLowerCase());
                        stemWords.add(word);
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stemWords;
    }
}
