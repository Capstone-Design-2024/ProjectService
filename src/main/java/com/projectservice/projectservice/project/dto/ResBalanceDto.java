package com.projectservice.projectservice.project.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ResBalanceDto {
    private String name;
    private String image;
    private String description;
    private Long price;
    private String uri;

    @Builder
    public ResBalanceDto(String name, String image, String description, Long price, String uri) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.uri = uri;
    }
}
