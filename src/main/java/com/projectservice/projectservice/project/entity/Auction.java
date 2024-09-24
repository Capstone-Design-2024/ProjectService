package com.projectservice.projectservice.project.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.projectservice.projectservice.member_cache.entity.Member;
import com.projectservice.projectservice.project.dto.ResAuctionDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@Table(name="auction")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_id", nullable = false)
    private Long auctionId;
    @Column(name = "price", nullable = false)
    private Long price;
    @Column(name = "type", nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private Project project;

    public void addMember(Member member) {
        this.member = member;
    }
    public void addProject(Project project) {
        this.project = project;
    }

    @Builder
    public Auction(Long auctionId, Long price, String type) {
        this.auctionId = auctionId;
        this.price = price;
        this.type = type;
    }

    public ResAuctionDto toDto() {
        return ResAuctionDto.builder()
                .auctionId(this.auctionId)
                .memberId(this.member.getMemberId())
                .projectId(this.project.getProjectId())
                .name(this.member.getName())
                .priceForProject(this.project.getPrice())
                .priceForAuction(this.price)
                .type(this.type)
                .build();
    }
}
