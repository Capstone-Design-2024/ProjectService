package com.projectservice.projectservice.project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResAuctionDto {
    private Long auctionId;
    private Long memberId;
    private Long projectId;
    private String name;
    private Long priceForProject;
    private Long priceForAuction;
    private String type;

    @Builder
    public ResAuctionDto(Long auctionId, Long memberId, Long projectId, String name, Long priceForProject, Long priceForAuction, String type) {
        this.auctionId = auctionId;
        this.memberId = memberId;
        this.projectId = projectId;
        this.name = name;
        this.priceForProject = priceForProject;
        this.priceForAuction = priceForAuction;
        this.type = type;
    }
}
