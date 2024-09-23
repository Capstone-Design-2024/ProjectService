package com.projectservice.projectservice.pinata.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResIPFSUploadDto {
    @JsonProperty("IpfsHash")
    private String ipfsHash;
    @JsonProperty("PinSize")
    private String pinSize;
    @JsonProperty("Timestamp")
    private String timestamp;

    @Builder
    public ResIPFSUploadDto(String ipfsHash, String pinSize, String timestamp) {
        this.ipfsHash = ipfsHash;
        this.pinSize = pinSize;
        this.timestamp = timestamp;
    }
    // for test
}
