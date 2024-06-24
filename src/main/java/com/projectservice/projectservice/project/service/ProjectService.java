package com.projectservice.projectservice.project.service;

import com.projectservice.projectservice.project.dto.ReqCreateProjectExceptThumbnailDto;
import com.projectservice.projectservice.project.dto.ResProjectDto;
import com.projectservice.projectservice.security.dto.AuthorizerDto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProjectService {
    Long initProject(AuthorizerDto authorizerDto);

    //    void addThumbnailAddress(); <- S3에 이미지 업로드 하는 과정부터 시작
    void createProject(AuthorizerDto authorizerDto, ReqCreateProjectExceptThumbnailDto reqCreateProjectExceptThumbnailDto);

    List<ResProjectDto> getOwnProject(AuthorizerDto authorizerDto);

    List<ResProjectDto> getAllProjects();
    void updateThumbnailAddress(AuthorizerDto authorizerDto, MultipartFile file, Long projectId);

    void uploadImgToIPFS(AuthorizerDto authorizerDto, MultipartFile file, Long projectId) throws IOException;

    ResponseEntity getS3ImageFromURL(AuthorizerDto authorizerDto, String s3URL);

}
