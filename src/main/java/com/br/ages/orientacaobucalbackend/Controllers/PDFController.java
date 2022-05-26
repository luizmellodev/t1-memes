package com.br.ages.orientacaobucalbackend.Controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.br.ages.orientacaobucalbackend.Services.PdfService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/autoexam")
@CrossOrigin
public class PDFController {

      private final PdfService pdfService;

      @Autowired
      public PDFController(PdfService pdfService) {
            this.pdfService = pdfService;
      }

      @GetMapping("/pdf")
      public ResponseEntity<InputStreamResource> gerarPdf(@RequestBody Map<String, String> map) throws DocumentException, IOException {
            ByteArrayInputStream byteArrayInputStream = pdfService.geraPdf(map);
            HttpHeaders headers = new HttpHeaders();
            headers.add("content-disposition","inline;filename=resultadoAvaliacao.pdf");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(byteArrayInputStream));
      }

      @PostMapping ("/csv")
      public String convertCsv(@RequestBody Map<String, ArrayList> map)throws DocumentException, IOException{
            return new PdfService().convertJsonToCsv(map);
      }
}
