package com.br.ages.orientacaobucalbackend.Services;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
// import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;

public class S3Service {

    private String uri;
    private S3Client s3;

    public S3Service(String uri) {
        this.uri = uri;
        this.s3 = S3Client.builder()
            .region(Region.US_EAST_2)
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(
                        "AKIAQNTVEQCNGOWL6LLD", 
                        "6amACdViuSTp6zmeZ+nD+61oFNJ2NJbe9NTgmvzV"
                    )
                )
                // EnvironmentVariableCredentialsProvider.create()
            )
            .build();
    }

    public String upload(String objectName, String prefix, byte[] object) {
        try {
            PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(this.uri)
                .key(prefix+"/"+objectName)
                .build();
            PutObjectResponse response = s3.putObject(
                putRequest, 
                RequestBody.fromBytes(object)
            );
            return response.eTag();
        } catch (S3Exception e) {
            System.err.println(e);
        }
        return "";
    }

    public byte[] download(String objectName, String prefix) {
        try {
            GetObjectRequest getRequest = GetObjectRequest.builder()
                .bucket(this.uri)
                .key(prefix+"/"+objectName)
                .build();
            ResponseBytes <GetObjectResponse> objectBytes = s3.getObjectAsBytes(getRequest);
            return objectBytes.asByteArray();
        } catch (S3Exception e) {
            System.err.println(e);
        }
        return new byte[0];
    }
}