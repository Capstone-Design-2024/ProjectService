package com.projectservice.projectservice.project.service;

import com.projectservice.projectservice.handler.CustomException;
import com.projectservice.projectservice.handler.StatusCode;
import com.projectservice.projectservice.member_cache.entity.Member;
import com.projectservice.projectservice.member_cache.repository.MemberRepository;
import com.projectservice.projectservice.project.dto.ReqCreateAuctionDto;
import com.projectservice.projectservice.project.dto.ResAuctionDto;
import com.projectservice.projectservice.project.entity.Auction;
import com.projectservice.projectservice.project.entity.Project;
import com.projectservice.projectservice.project.repository.AuctionRepository;
import com.projectservice.projectservice.project.repository.ProjectRepository;
import com.projectservice.projectservice.security.dto.AuthorizerDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService{
    private final AuctionRepository auctionRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;

    /**
     * @ToDo
     * 판매 등록시 NFT를 실제로 가지고 있어야 함. 이 부분에 대한 확인 필요
     * */
    @Override
    @Transactional
    public void createAuction(ReqCreateAuctionDto reqCreateAuctionDto, AuthorizerDto authorizerDto) {
        Member member = memberRepository.findById(authorizerDto.getMemberId()).orElseThrow(() -> {
            throw new CustomException(StatusCode.FORBIDDEN);
        });
        Project project = projectRepository.findProjectByProjectId(reqCreateAuctionDto.getProjectId()).orElseThrow(() -> {
            throw new CustomException(StatusCode.NOT_FOUND);
        });
        Auction auction = reqCreateAuctionDto.toEntity();
        auction.addMember(member);
        auction.addProject(project);
        auctionRepository.save(auction);
    }

    @Override
    public List<ResAuctionDto> getAuctions(Long projectId) {
        return auctionRepository.findAll()
                .stream()
                .filter(auction -> auction.getProject().getProjectId() == projectId)
                .map(auction -> auction.toDto())
                .collect(Collectors.toList());
    }
}
