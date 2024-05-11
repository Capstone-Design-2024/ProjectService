package com.projectservice.projectservice.aws.s3.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

public interface S3Service {
    String upload(MultipartFile image);
    void deleteImageFromS3(String imageAddress)throws MalformedURLException, UnsupportedEncodingException;
}
