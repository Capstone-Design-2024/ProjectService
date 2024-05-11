package com.projectservice.projectservice.project.controller;

import com.projectservice.projectservice.common.dto.Message;
import com.projectservice.projectservice.handler.CustomException;
import com.projectservice.projectservice.handler.StatusCode;
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
    public ResponseEntity<Message> login() {
        return ResponseEntity.ok(new Message(StatusCode.OK, projectService.initProject(getAuthorizer())));
    }

}
