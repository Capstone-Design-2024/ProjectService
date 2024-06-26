package com.projectservice.projectservice.member_cache.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projectservice.projectservice.baseTime.BaseTimeEntity;
import com.projectservice.projectservice.common.enums.Role;
import com.projectservice.projectservice.project.entity.Mark;
import com.projectservice.projectservice.project.entity.Project;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name="member")
public class Member extends BaseTimeEntity {

    @Id
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "profileURL")
    private String profile_url;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Mark> marks = new ArrayList<>();

    @OneToMany(mappedBy = "maker", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Project> projects = new ArrayList<>();



    @Builder

    public Member(Long memberId, String email, String password, Role role, String name, String address, String profile_url) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.address = address;
        this.profile_url = profile_url;
    }
}