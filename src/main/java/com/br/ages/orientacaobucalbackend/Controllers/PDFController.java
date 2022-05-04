package com.br.ages.orientacaobucalbackend.Controllers;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pdf")
@CrossOrigin
public class PDFController {

      Document document = new Document();
      PdfWriter aux =new PdfWriter();

document.open();
      Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
      Chunk chunk = new Chunk("Hello World", font);

document.add(chunk);
document.close();


}
