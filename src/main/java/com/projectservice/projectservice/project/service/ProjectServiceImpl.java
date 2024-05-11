package com.projectservice.projectservice.project.service;

import com.projectservice.projectservice.handler.CustomException;
import com.projectservice.projectservice.handler.StatusCode;
import com.projectservice.projectservice.member_cache.entity.Member;
import com.projectservice.projectservice.member_cache.repository.MemberRepository;
import com.projectservice.projectservice.project.dto.ReqCreateProjectExceptThumbnailDto;
import com.projectservice.projectservice.project.entity.Project;
import com.projectservice.projectservice.project.repository.ProjectRepository;
import com.projectservice.projectservice.security.dto.AuthorizerDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

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
    public List<Project> getOwnProject(AuthorizerDto authorizerDto) {
        Member maker = memberRepository.findById(authorizerDto.getMemberId()).orElseThrow(()->{throw new CustomException(StatusCode.FORBIDDEN);});
        return projectRepository.findAllByMaker(maker);
    }
}
