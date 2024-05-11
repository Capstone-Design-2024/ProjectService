package com.projectservice.projectservice.project.service;

import com.projectservice.projectservice.project.dto.ReqCreateProjectExceptThumbnailDto;
import com.projectservice.projectservice.project.entity.Project;
import com.projectservice.projectservice.security.dto.AuthorizerDto;

import java.util.List;

public interface ProjectService {
    Long initProject(AuthorizerDto authorizerDto);

    //    void addThumbnailAddress(); <- S3에 이미지 업로드 하는 과정부터 시작
    void createProject(AuthorizerDto authorizerDto, ReqCreateProjectExceptThumbnailDto reqCreateProjectExceptThumbnailDto);

    List<Project> getOwnProject(AuthorizerDto authorizerDto);
}