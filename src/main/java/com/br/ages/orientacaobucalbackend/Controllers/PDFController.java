package com.br.ages.orientacaobucalbackend.Controllers;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pdf")
@CrossOrigin
public class PDFController {

      public void geraPdf() throws DocumentException, IOException {

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
      }



}
