package com.br.ages.orientacaobucalbackend.Controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import com.br.ages.orientacaobucalbackend.Services.PdfService;
import com.itextpdf.text.DocumentException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/pdf")
@CrossOrigin
public class PDFController {

      private final PdfService pdfService;

      @Autowired
      public PDFController(PdfService pdfService) {
            this.pdfService = pdfService;
      }

      @PostMapping
      public ResponseEntity<InputStreamResource> geraPdf(@RequestBody Map<String, ArrayList> map) throws DocumentException, IOException {
            HttpHeaders headers = new HttpHeaders();
            ByteArrayInputStream byteArrayInputStream = pdfService.geraPdf(map);
            headers.add("content-disposition","inline;filename=resultadoAvaliacao.pdf");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(byteArrayInputStream));
      }
}