package com.projectservice.projectservice.project.service;

import com.projectservice.projectservice.aws.s3.service.S3Service;
import com.projectservice.projectservice.handler.CustomException;
import com.projectservice.projectservice.handler.StatusCode;
import com.projectservice.projectservice.kafka.producer.NFTRegistryProducer;
import com.projectservice.projectservice.member_cache.entity.Member;
import com.projectservice.projectservice.member_cache.repository.MemberRepository;
import com.projectservice.projectservice.pinata.dto.ResIPFSUploadDto;
import com.projectservice.projectservice.pinata.service.PinataService;
import com.projectservice.projectservice.project.dto.ReqCreateProjectExceptThumbnailDto;
import com.projectservice.projectservice.project.dto.ResProjectDto;
import com.projectservice.projectservice.project.entity.Project;
import com.projectservice.projectservice.project.repository.ProjectRepository;
import com.projectservice.projectservice.security.dto.AuthorizerDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;
    private final PinataService pinataService;
    private final NFTRegistryProducer nftRegistryProducer;
    private final RestTemplate restTemplate;

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
        Project project = projectRepository.findProjectByProjectId(reqCreateProjectExceptThumbnailDto.getProjectId()).orElseThrow(()->{throw new CustomException(StatusCode.NOT_INITIATED_PROJECT);});
        if (!project.getTitle().equals(authorizerDto.getMemberId().toString())) throw new CustomException(StatusCode.ALREADY_CREATED_PROJECT);
        projectRepository.findByTitle(reqCreateProjectExceptThumbnailDto.getTitle()).ifPresent(p -> {throw new CustomException(StatusCode.DUPLICATED_PROJECT_TITLE);});

        project.updateProjectExceptThumbnail(reqCreateProjectExceptThumbnailDto);
        project.addMaker(maker);
    }

    @Override
    public List<ResProjectDto> getOwnProject(AuthorizerDto authorizerDto) {
        Member maker = memberRepository.findById(authorizerDto.getMemberId()).orElseThrow(()->{throw new CustomException(StatusCode.FORBIDDEN);});

        return projectRepository.findAllByMaker(maker)
                .stream()
                .map(p -> p.toResProjectDto())
                .collect(Collectors.toList());
    }

    @Override
    public List<ResProjectDto> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .filter((p) -> p.getMaker() != null)
                .map(p -> p.toResProjectDto())
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

    @Override
    public void uploadImgToIPFS(AuthorizerDto authorizerDto, MultipartFile file, Long projectId) {
        Member maker = memberRepository.findById(authorizerDto.getMemberId()).orElseThrow(() -> {
            throw new CustomException(StatusCode.FORBIDDEN);
        });
        Project project = projectRepository.findByMakerAndProjectId(maker, projectId).orElseThrow(() -> {
            throw new CustomException(StatusCode.FORBIDDEN);
        });
        ResIPFSUploadDto imgHashValue = pinataService.upload(file, project.getTitle());

        nftRegistryProducer.produceNFTRegistry(project.getTitle(), ipfsURIEncoder(imgHashValue.getIpfsHash()), maker.getMemberId(), project.getProjectId(), project.getPrice(), project.getDescription());
    }

    @Override
    public ResponseEntity getS3ImageFromURL(AuthorizerDto authorizerDto, String s3URL) {
        memberRepository.findById(authorizerDto.getMemberId()).orElseThrow(() -> {throw new CustomException(StatusCode.FORBIDDEN);});
        ResponseEntity<byte[]> responseEntity = restTemplate.getForEntity(s3URL, byte[].class);

        return responseEntity;
    }

    private String ipfsURIEncoder(String ipfsHash) {
        return "ipfs://" + ipfsHash;
    }
}
