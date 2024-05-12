package com.projectservice.projectservice.project.service;

import com.projectservice.projectservice.aws.s3.service.S3Service;
import com.projectservice.projectservice.handler.CustomException;
import com.projectservice.projectservice.handler.StatusCode;
import com.projectservice.projectservice.member_cache.entity.Member;
import com.projectservice.projectservice.member_cache.repository.MemberRepository;
import com.projectservice.projectservice.project.dto.ReqCreateProjectExceptThumbnailDto;
import com.projectservice.projectservice.project.dto.ResOwnProjectDto;
import com.projectservice.projectservice.project.entity.Project;
import com.projectservice.projectservice.project.repository.ProjectRepository;
import com.projectservice.projectservice.security.dto.AuthorizerDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    @Override
    public Long initProject(AuthorizerDto authorizerDto) {
        memberRepository.findById(authorizerDto.getMemberId()).orElseThrow(()->{throw new CustomException(StatusCode.FORBIDDEN);});
        String temporalTitle = authorizerDto.getMemberId().toString();
        return projectRepository.findByTitle(temporalTitle)
                .orElseGet(() -> {
                    Project newProject = Project.builder()
                            .title(temporalTitle)
                            .build();
                    projectRepository.save(newProject);
                    return newProject;
                }).getProjectId();
    }

    @Override
    @Transactional
    public void createProject(AuthorizerDto authorizerDto,ReqCreateProjectExceptThumbnailDto reqCreateProjectExceptThumbnailDto) {
        Member maker = memberRepository.findById(authorizerDto.getMemberId()).orElseThrow(()->{throw new CustomException(StatusCode.FORBIDDEN);});
        Project project = projectRepository.findById(reqCreateProjectExceptThumbnailDto.getProjectId()).orElseThrow(()->{throw new CustomException(StatusCode.NOT_INITIATED_PROJECT);});
        if (!project.getTitle().equals(authorizerDto.getMemberId().toString())) throw new CustomException(StatusCode.ALREADY_CREATED_PROJECT);

        project.updateProjectExceptThumbnail(reqCreateProjectExceptThumbnailDto);
        project.addMaker(maker);
    }

    @Override
    public List<ResOwnProjectDto> getOwnProject(AuthorizerDto authorizerDto) {
        Member maker = memberRepository.findById(authorizerDto.getMemberId()).orElseThrow(()->{throw new CustomException(StatusCode.FORBIDDEN);});

        return projectRepository.findAllByMaker(maker)
                .stream()                           // Convert collection to Stream
                .map(p -> p.toResOwnProjectDto())   // Transform each project to ResOwnProjectDto
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateThumbnailAddress(AuthorizerDto authorizerDto, MultipartFile file, Long projectId) {
        Member maker = memberRepository.findById(authorizerDto.getMemberId()).orElseThrow(()->{throw new CustomException(StatusCode.FORBIDDEN);});
        Project project = projectRepository.findByMakerAndProjectId(maker, projectId).orElseThrow(()->{throw new CustomException(StatusCode.NOT_INITIATED_PROJECT);});
        String uploadedUrl = s3Service.upload(file);
        project.updateThumbnailUrl(uploadedUrl);
    }
}
