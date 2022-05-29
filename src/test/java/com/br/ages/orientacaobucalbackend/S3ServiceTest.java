package com.br.ages.orientacaobucalbackend;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.br.ages.orientacaobucalbackend.Services.S3Service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class S3ServiceTest {

    @Test
    public void testUpload() {
        try {
            S3Service s3 = new S3Service("saude-velho");
            String filePath = "src/test/resources/static/Olho1.bmp";
            File file = new File(filePath);
            byte[] object = Files.readAllBytes(file.toPath());
            String eTag = s3.upload("Olho1.bmp", "autoexam-results", object);
            assertNotEquals("", eTag);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Test
    public void testDownload() {
        try {
            S3Service s3 = new S3Service("saude-velho");
            byte[] object = s3.download("Olho1.bmp", "autoexam-results");
            File outputFile = new File("test/Olho1.bmp");
            Files.write(outputFile.toPath(), object);
            assertNotEquals(0, outputFile.length());
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}