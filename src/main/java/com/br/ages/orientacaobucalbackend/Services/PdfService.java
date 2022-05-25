package com.br.ages.orientacaobucalbackend.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class PdfService {
    private String uri = "saude-velho";
    private String prefix = "autoexam-results";

    public ByteArrayInputStream geraPdf(Map<String, ArrayList> map) throws DocumentException, IOException {
        String paragraphString = "";
        int id = 1;
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream pdf = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, pdf);
        } catch (DocumentException e) {
            throw new RuntimeException("Object creation error.", e);
        }

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

        for (var entry : map.entrySet()) {
            ArrayList alternativeData = entry.getValue();
            String question = entry.getKey();
            String text = alternativeData.get(0).toString();
            String criticalLevel = alternativeData.get(1).toString();
            paragraphString = id + ". " + question + " " + text + " [" + criticalLevel + "]";

            document.add(new Paragraph(paragraphString));
            id++;
        }
        document.close();

        return new ByteArrayInputStream(pdf.toByteArray());
    }

    public String getCriticalColour(Map<String, ArrayList> map) throws DocumentException, IOException {
        String level = "Verde";
        for (var entry : map.entrySet()) {
            ArrayList alternativeData = entry.getValue();
            String criticalLevel = alternativeData.get(1).toString();

            if (criticalLevel.equals("Vermelho") || level.equals("Vermelho"))
                level = "Vermelho";
            else if (criticalLevel.equals("Amarelo"))
                level = "Amarelo";
        }
        return level;
    }

    public String convertJsonToCsv(Map<String, ArrayList> map) throws IOException {

        StringWriter output = new StringWriter();
        try (ICsvListWriter listWriter = new CsvListWriter(output,
                CsvPreference.STANDARD_PREFERENCE)){
            listWriter.write("Pergunta", "Resposta", "Criticidade");
            for (Map.Entry<String, ArrayList> entry : map.entrySet()){
                listWriter.write(entry.getKey(), entry.getValue().get(0), entry.getValue().get(1));
            }
        }
        S3Service s3Service = new S3Service(uri);
        byte[] bytes = output.toString().getBytes("UTF-8");
        s3Service.upload("auto-exam.csv", prefix, bytes );
        return output.toString();
    }
}