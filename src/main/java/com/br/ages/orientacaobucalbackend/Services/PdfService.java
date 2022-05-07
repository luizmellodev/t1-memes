package com.br.ages.orientacaobucalbackend.Services;

import com.br.ages.orientacaobucalbackend.DataAcess.Repository.PdfRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

@Service
public class PdfService {
    private final PdfRepository pdfRepository;

    @Autowired
    public PdfService(PdfRepository pdfRepository) {
        this.pdfRepository = pdfRepository;
    }

    public String geraPdf(Map<String, ArrayList> map) throws DocumentException, IOException
    {
        String awsLink = "s";
        String paragraphString = "";
        int id = 1;
        Document document = new Document(PageSize.A4);
        File file = File.createTempFile("exame", ".pdf");
        FileOutputStream pdf = new FileOutputStream(file);
        PdfWriter writer;

        try {
            writer = PdfWriter.getInstance(document, pdf);
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
        return awsLink;
    }
}
