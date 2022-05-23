package com.br.ages.orientacaobucalbackend.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.Objects;


@Configuration
public class AmazonClient {

    @Value("${cloud.s3.access_key}")
    private String accessKey;
    @Value("${cloud.s3.secret_key}")
    private String secretAccessKey;
    @Value("${cloud.s3.region}")
    private String region;

    @Bean
    public S3Client s3client() {

        Region region = Region.of(Objects.requireNonNull(this.region));
         AwsBasicCredentials credentials = AwsBasicCredentials.create(this.accessKey, this.secretAccessKey);
        return S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }



}