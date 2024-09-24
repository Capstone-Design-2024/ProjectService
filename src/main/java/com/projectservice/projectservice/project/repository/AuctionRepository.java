package com.projectservice.projectservice.project.repository;

import com.projectservice.projectservice.project.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AuctionRepository extends JpaRepository<Auction, Long> {
}
