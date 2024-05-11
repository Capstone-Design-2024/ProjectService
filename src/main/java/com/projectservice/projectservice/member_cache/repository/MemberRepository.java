package com.projectservice.projectservice.member_cache.repository;


import com.projectservice.projectservice.member_cache.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
