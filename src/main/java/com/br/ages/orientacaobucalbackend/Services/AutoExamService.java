package com.br.ages.orientacaobucalbackend.Services;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.supercsv.io.CsvListWriter;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;

@Service
public class AutoExamService {

    private final String S3_URI = "odontogeriatria";
    private final String S3_PREFIX = "autoexam-results";
    private final String KEY_OBJECT_NAME = "objectName";
    private final String STRFMT_OBJECT_NAME = "autoexam-%s.csv";
    private final String TITLE = "Autoexame";
    private final String[] HEADERS = { "Pergunta", "Resposta", "Criticidade" };
    private final S3Service s3Service = new S3Service(S3_URI);

    public ByteArrayInputStream createPDF(String objectName) throws DocumentException, IOException {
        byte[] csvBytes = s3Service.download(objectName, S3_PREFIX);
        String csv = new String(csvBytes, "UTF-8");
        ICsvMapReader mapReader = new CsvMapReader(new StringReader(csv), CsvPreference.STANDARD_PREFERENCE);
        String[] header = mapReader.getHeader(true);
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream pdf = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, pdf);
        Map<String, String> autoexamMap;
        int id = 1;
        document.open();
        document.addTitle(TITLE);
        while ((autoexamMap = mapReader.read(header)) != null) {
            document.add(new Paragraph(
                    id + ". " +
                            autoexamMap.get(HEADERS[0]) + " " +
                            autoexamMap.get(HEADERS[1])));
            id++;
        }
        document.close();
        mapReader.close();
        return new ByteArrayInputStream(pdf.toByteArray());
    }

    public String createCSV(Map<String, ArrayList<String>> map) throws IOException {
        StringWriter output = new StringWriter();
        try (
                ICsvListWriter listWriter = new CsvListWriter(output, CsvPreference.STANDARD_PREFERENCE)) {
            listWriter.write(HEADERS[0], HEADERS[1], HEADERS[2]);
            for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
                listWriter.write(
                        entry.getKey(),
                        entry.getValue().get(0),
                        entry.getValue().get(1));
            }
        }
        byte[] csvBytes = output.toString().getBytes("UTF-8");
        String objectName = String.format(STRFMT_OBJECT_NAME, System.currentTimeMillis());
        s3Service.upload(objectName, S3_PREFIX, csvBytes);
        return String.format("{\"%s\":\"%s\"}", KEY_OBJECT_NAME, objectName);
    }
}
