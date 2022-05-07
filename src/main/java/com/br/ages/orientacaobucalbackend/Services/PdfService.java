package com.br.ages.orientacaobucalbackend.Services;

import com.br.ages.orientacaobucalbackend.DataAcess.Repository.PdfRepository;
import com.br.ages.orientacaobucalbackend.DataAcess.Repository.QuestionRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PdfService {
    private final PdfRepository pdfRepository;

    @Autowired
    public PdfService(PdfRepository pdfRepository) {
        this.pdfRepository = pdfRepository;
    }

    public Document geraPdf() throws DocumentException, IOException
    {
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
        Chunk chunk = new Chunk("Hello World", font);

        document.add(chunk);
        document.close();
        return document;
    }
}
