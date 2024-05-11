package com.projectservice.projectservice.project.service;

import com.projectservice.projectservice.handler.CustomException;
import com.projectservice.projectservice.handler.StatusCode;
import com.projectservice.projectservice.member_cache.repository.MemberRepository;
import com.projectservice.projectservice.project.entity.Project;
import com.projectservice.projectservice.project.repository.ProjectRepository;
import com.projectservice.projectservice.security.dto.AuthorizerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
