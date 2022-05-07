package com.br.ages.orientacaobucalbackend.Controllers;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.br.ages.orientacaobucalbackend.Entity.Question;
import com.br.ages.orientacaobucalbackend.Services.PdfService;
import com.br.ages.orientacaobucalbackend.Services.QuestionService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pdf")
@CrossOrigin
public class PDFController {

      private final PdfService pdfService;

      @Autowired
      public PDFController(PdfService pdfService) {
            this.pdfService = pdfService;
      }

      @GetMapping
      public void geraPdf() throws DocumentException, IOException {
           pdfService.geraPdf();
      }
}
