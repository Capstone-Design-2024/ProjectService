package com.projectservice.projectservice.project.controller;

import com.projectservice.projectservice.common.dto.Message;
import com.projectservice.projectservice.handler.StatusCode;
import com.projectservice.projectservice.project.dto.ReqCreateProjectExceptThumbnailDto;
import com.projectservice.projectservice.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import static com.projectservice.projectservice.security.JwtInfoExtractor.getAuthorizer;

@RequiredArgsConstructor
@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("/init")
    public ResponseEntity<Message> getEmptyProjectId() {
        return ResponseEntity.ok(new Message(StatusCode.OK, projectService.initProject(getAuthorizer())));
    }

    @PostMapping("/registration")
    public ResponseEntity<Message> registryProject(@RequestBody ReqCreateProjectExceptThumbnailDto reqCreateProjectExceptThumbnailDto) {
        projectService.createProject(getAuthorizer(),reqCreateProjectExceptThumbnailDto);
        return ResponseEntity.ok(new Message(StatusCode.OK));
    }

    @GetMapping("/own")
    public ResponseEntity<Message> listOwnProject() {
        return ResponseEntity.ok(new Message(StatusCode.OK, projectService.getOwnProject(getAuthorizer())));
    }

}