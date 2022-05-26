package com.br.ages.orientacaobucalbackend.Services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

@Service
public class PdfService {

    private final String URI = "saude-velho";
    private final String PREFIX = "autoexam-results";

    private S3Service s3Service;

    public PdfService() {
        this.s3Service = new S3Service(URI);
    }

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
        byte[] csvBytes = output.toString().getBytes("UTF-8");
        System.out.println();
        s3Service.upload("autoexam.csv", PREFIX, csvBytes);
        return output.toString();
    }
}