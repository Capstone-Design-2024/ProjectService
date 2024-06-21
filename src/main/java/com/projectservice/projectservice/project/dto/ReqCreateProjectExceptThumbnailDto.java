package com.projectservice.projectservice.project.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class ReqCreateProjectExceptThumbnailDto {
    private Long projectId;
    private String title;
    private String description;
    private String category;
    private Long goalAmount;
    private Timestamp deadLine;
    private String contactPhone;
    private String contactEmail;
    private Long price;

    @Builder
    public ReqCreateProjectExceptThumbnailDto(Long projectId, String title, String description, String category, Long goalAmount, Timestamp deadLine, String contactPhone, String contactEmail, Long price) {
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.goalAmount = goalAmount;
        this.deadLine = deadLine;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.price = price;
    }
}
