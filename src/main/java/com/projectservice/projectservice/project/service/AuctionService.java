package com.projectservice.projectservice.project.service;

import com.projectservice.projectservice.project.dto.ReqCreateAuctionDto;
import com.projectservice.projectservice.project.dto.ResAuctionDto;
import com.projectservice.projectservice.project.entity.Auction;
import com.projectservice.projectservice.security.dto.AuthorizerDto;

import java.util.List;

public interface AuctionService {
    void createAuction(ReqCreateAuctionDto reqCreateAuctionDto, AuthorizerDto authorizerDto);
    List<ResAuctionDto> getAuctions(Long projectId);
}
