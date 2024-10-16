package com.projectservice.projectservice.project.controller;

import com.projectservice.projectservice.common.dto.Message;
import com.projectservice.projectservice.handler.StatusCode;
import com.projectservice.projectservice.project.dto.ReqCreateAuctionDto;
import com.projectservice.projectservice.project.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static com.projectservice.projectservice.security.JwtInfoExtractor.getAuthorizer;

@RequiredArgsConstructor
@RestController
@RequestMapping("/project/auction")
public class AuctionController {
    private final AuctionService auctionService;

    @PostMapping("/")
    public ResponseEntity<Message> registryAuction(@RequestBody ReqCreateAuctionDto reqCreateAuctionDto) {
        auctionService.createAuction(reqCreateAuctionDto, getAuthorizer());
        return ResponseEntity.ok(new Message(StatusCode.OK));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Message> listUpAuction(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(new Message(StatusCode.OK, auctionService.getAuctions(projectId)));
    }

    @PostMapping("/buy")
    public ResponseEntity<Message> buyFromBid(@RequestBody HashMap<String, Long> auctionId) {
        auctionService.buyBidAuction(auctionId.get("auctionId"), getAuthorizer());
        return ResponseEntity.ok(new Message(StatusCode.OK));
    }

    @PostMapping("/sell")
    public ResponseEntity<Message> sellFromAsk(@RequestBody HashMap<String, Long> auctionId) {
        auctionService.sellAskAuction(auctionId.get("auctionId"), getAuthorizer());
        return ResponseEntity.ok(new Message(StatusCode.OK));
    }

    @GetMapping("/balance")
    public ResponseEntity<Message> checkBalance() {
        return ResponseEntity.ok(new Message(StatusCode.OK, auctionService.getBalance(getAuthorizer())));
    }
}
