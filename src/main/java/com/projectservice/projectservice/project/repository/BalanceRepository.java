package com.projectservice.projectservice.project.repository;

import com.projectservice.projectservice.member_cache.entity.Member;
import com.projectservice.projectservice.project.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    List<Balance> findAllByMember(Member member);
}
