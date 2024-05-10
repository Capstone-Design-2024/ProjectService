package com.projectservice.projectservice.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projectservice.projectservice.member_cache.entity.Member;
import jakarta.persistence.*;
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
    private List<Tag> tage = new ArrayList<>();

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
}
