package com.br.ages.orientacaobucalbackend.Controllers;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import com.br.ages.orientacaobucalbackend.Services.PdfService;
import com.itextpdf.text.DocumentException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
      public String geraPdf(@RequestBody Map<String, ArrayList> map) throws DocumentException, IOException {
          return pdfService.geraPdf(map);
      }
}
