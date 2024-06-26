package com.projectservice.projectservice.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projectservice.projectservice.member_cache.entity.Member;
import com.projectservice.projectservice.project.dto.ReqCreateProjectExceptThumbnailDto;
import com.projectservice.projectservice.project.dto.ResProjectDto;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name="project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false)
    private Long projectId;
    @Column(name = "title", unique = true)
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "category")
    private String category;
    @Column(name = "goal_amount")
    private Long goalAmount;
    @Column(name = "dead_line")
    private Timestamp deadLine;
    @Column(name = "thumbnail")
    private String thumbnail;
    @Column(name = "contact_phone")
    private String contactPhone;
    @Column(name = "contact_email")
    private String contactEmail;
    @Column(name = "price")
    private Long price;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Mark> marks = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "maker_id")
    @JsonBackReference
    private Member maker;

    public void addMaker(Member maker) { this.maker = maker; }

    @Builder
    public Project(Long projectId, String title, String description, String category, Long goalAmount, Timestamp deadLine, String thumbnail, String contactPhone, String contactEmail, Long price) {
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.goalAmount = goalAmount;
        this.deadLine = deadLine;
        this.thumbnail = thumbnail;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.price = price;
    }

    @Transactional
    public Project updateProjectExceptThumbnail(ReqCreateProjectExceptThumbnailDto reqCreateProjectExceptThumbnailDto) {
        this.title = reqCreateProjectExceptThumbnailDto.getTitle();
        this.description = reqCreateProjectExceptThumbnailDto.getDescription();
        this.category = reqCreateProjectExceptThumbnailDto.getCategory();
        this.goalAmount = reqCreateProjectExceptThumbnailDto.getGoalAmount();
        this.deadLine = reqCreateProjectExceptThumbnailDto.getDeadLine();
        this.contactPhone = reqCreateProjectExceptThumbnailDto.getContactPhone();
        this.contactEmail = reqCreateProjectExceptThumbnailDto.getContactEmail();
        this.price = reqCreateProjectExceptThumbnailDto.getPrice();
        return this;
    }

    @Transactional
    public void updateThumbnailUrl(String fileUrl) {
        this.thumbnail = fileUrl;
    }

    public ResProjectDto toResProjectDto() {
        return ResProjectDto.builder()
                .makerName(this.maker.getName())
                .projectId(this.projectId)
                .title(this.title)
                .description(this.description)
                .category(this.category)
                .goalAmount(this.goalAmount)
                .deadLine(this.deadLine)
                .contactEmail(this.contactEmail)
                .contactPhone(this.contactPhone)
                .price(this.price)
                .thumbnail(this.thumbnail)
                .build();
    }
}
