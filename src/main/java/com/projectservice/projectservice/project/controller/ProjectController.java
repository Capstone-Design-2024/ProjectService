package com.projectservice.projectservice.project.controller;

import com.projectservice.projectservice.common.dto.Message;
import com.projectservice.projectservice.handler.StatusCode;
import com.projectservice.projectservice.project.dto.ReqCreateProjectExceptThumbnailDto;
import com.projectservice.projectservice.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

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

    @GetMapping("/p/other-project")
    public ResponseEntity<Message> listProjects() {
        return ResponseEntity.ok(new Message(StatusCode.OK, projectService.getAllProjects()));
    }

    @PostMapping(value = "/thumbnail/{projectId}", consumes = {"multipart/form-data"})
    public ResponseEntity<Message> uploadProjectThumbnail(@PathVariable Long projectId,@RequestPart(value = "image", required = false) MultipartFile image) {
        projectService.updateThumbnailAddress(getAuthorizer(), image, projectId);
        return ResponseEntity.ok(new Message(StatusCode.OK));
    }

}
