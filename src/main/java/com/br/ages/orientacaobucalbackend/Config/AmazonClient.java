package com.br.ages.orientacaobucalbackend.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

//@Configuration
public class AmazonClient {

    //@Bean
    //public AmazonS3 s3client() {

    //    BasicAWSCredentials awsCreds = new BasicAWSCredentials("", "");
    //    AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
    //            .withRegion(Regions.SA_EAST_1)
    //            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
    //            .build();

    //    return s3Client;
    //}



}