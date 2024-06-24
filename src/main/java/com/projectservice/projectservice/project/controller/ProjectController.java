package com.projectservice.projectservice.project.controller;

import com.projectservice.projectservice.common.dto.Message;
import com.projectservice.projectservice.handler.StatusCode;
import com.projectservice.projectservice.project.dto.ReqCreateProjectExceptThumbnailDto;
import com.projectservice.projectservice.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
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

    @PostMapping(value = "/nft/registration/{projectId}", consumes = {"multipart/form-data"})
    public ResponseEntity<Message> registryProjectNFT(@PathVariable Long projectId,@RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        System.out.println("Controller first in");
        projectService.uploadImgToIPFS(getAuthorizer(),image,projectId);
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

    @PostMapping(value = "/s3-proxy")
    public ResponseEntity<ByteArrayResource> s3ImageProxy(@RequestBody HashMap<String, String> s3URL) {
        ResponseEntity<byte[]> responseEntity = projectService.getS3ImageFromURL(getAuthorizer(), s3URL.get("s3_url"));

        ByteArrayResource byteArrayResource = new ByteArrayResource(responseEntity.getBody());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // 이미지를 JPEG로 설정
        headers.setContentLength(byteArrayResource.contentLength());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(responseEntity.getBody().length)
                .body(byteArrayResource);
    }

}
