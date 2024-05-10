package com.projectservice.projectservice.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.projectservice.projectservice.member_cache.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="mark")
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id", nullable = false)
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private Project project;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;
    public void addProject(Project project) { this.project = project; }
    public void addMember(Member member) {
        this.member = member;
    }
}
