package com.projectservice.projectservice.project.dto;

import com.projectservice.projectservice.project.entity.Auction;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReqCreateAuctionDto {
    private Long price;
    private String type;
    private Long projectId;

    @Builder
    public ReqCreateAuctionDto(Long price, String type, Long projectId) {
        this.price = price;
        this.type = type;
        this.projectId = projectId;
    }

    public Auction toEntity() {
        return Auction.builder()
                .price(this.price)
                .type(this.type)
                .build();
    }
}
