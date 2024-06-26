package com.projectservice.projectservice.project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class ResProjectDto {
    private Long projectId;
    private String title;
    private String description;
    private String category;
    private Long goalAmount;
    private Timestamp deadLine;
    private String contactPhone;
    private String contactEmail;
    private Long price;
    private String makerName;
    private String thumbnail;

    @Builder
    public ResProjectDto(Long projectId, String title, String description, String category, Long goalAmount, Timestamp deadLine, String contactPhone, String contactEmail, Long price, String makerName, String thumbnail) {
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.goalAmount = goalAmount;
        this.deadLine = deadLine;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.price = price;
        this.makerName = makerName;
        this.thumbnail = thumbnail;
    }
}
