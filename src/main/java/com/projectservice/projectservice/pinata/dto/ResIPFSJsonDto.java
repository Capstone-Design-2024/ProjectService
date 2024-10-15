package com.projectservice.projectservice.pinata.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResIPFSJsonDto {
    private String name;
    private String description;
    private String image;
    private String external_url;
    private List<Attribute> attributes;

    @Data
    @Builder
    public static class Attribute {
        private String trait_type;
        private int value;
    }
}
