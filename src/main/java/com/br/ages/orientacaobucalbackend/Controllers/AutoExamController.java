package com.br.ages.orientacaobucalbackend.Controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.br.ages.orientacaobucalbackend.Services.AutoExamService;
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
public class AutoExamController {

      private final AutoExamService autoexamService;

      @Autowired
      public AutoExamController(AutoExamService pdfService) {
            this.autoexamService = new AutoExamService();
      }

      @GetMapping("/pdf")
      public ResponseEntity<InputStreamResource> createPDF(@RequestBody Map<String, String> map) throws DocumentException, IOException {
            ByteArrayInputStream byteArrayInputStream = autoexamService.createPDF(map);
            HttpHeaders headers = new HttpHeaders();
            headers.add("content-disposition","inline;filename=resultadoAvaliacao.pdf");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(byteArrayInputStream));
      }

      @PostMapping ("/csv")
      public ResponseEntity<String> createCSV(@RequestBody Map<String, ArrayList> map)throws DocumentException, IOException {
            String objectName = autoexamService.createCSV(map);
            return ResponseEntity
                  .ok()
                  .contentType(MediaType.APPLICATION_JSON)
                  .body(objectName);
      }
}
