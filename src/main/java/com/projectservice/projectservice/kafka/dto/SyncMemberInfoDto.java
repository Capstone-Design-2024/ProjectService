package com.projectservice.projectservice.kafka.dto;

import com.projectservice.projectservice.common.enums.Role;
import com.projectservice.projectservice.member_cache.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SyncMemberInfoDto {
    private String name;
    private String email;
    private String password;
    private String address;
    private String profileUrl;
    private Long memberId;
    private Role role;

    @Builder
    public SyncMemberInfoDto(String name, String email, String password, String address, String profileUrl, Long memberId, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.profileUrl = profileUrl;
        this.memberId = memberId;
        this.role = role;
    }

    public Member toEntity() {
        return Member.builder()
                .memberId(this.getMemberId())
                .email(this.getEmail())
                .password(this.getPassword())
                .role(this.getRole())
                .name(this.getName())
                .address(this.getAddress())
                .profile_url(this.getProfileUrl())
                .build();
    }
}
