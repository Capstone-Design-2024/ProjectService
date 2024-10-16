package com.projectservice.projectservice.pinata.service;

import com.projectservice.projectservice.pinata.dto.ResIPFSJsonDto;
import com.projectservice.projectservice.pinata.dto.ResIPFSUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


@Service
@RequiredArgsConstructor
public class PinataService {
    @Value("${pinata.key}")
    private String pinataJwt;
    @Value("${pinata.url}")
    private String pinataURL;

    private final RestTemplate restTemplate;

    public ResIPFSUploadDto upload(MultipartFile file, String fileName) {
        try {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", convert(file));
            body.add("pinataMetadata", metaDataAppender("name", fileName));
            body.add("pinataOptions", metaDataAppender("cidVersion","1"));

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, pinataHeader());

            ResponseEntity<ResIPFSUploadDto> response = restTemplate.exchange(
                    pinataURL,
                    HttpMethod.POST,
                    requestEntity,
                    ResIPFSUploadDto.class
            );
            return response.getBody();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public ResIPFSJsonDto tokenResolver(String tokenURI) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ResIPFSJsonDto> response = restTemplate.exchange(
                tokenURI,
                HttpMethod.GET,
                entity,
                ResIPFSJsonDto.class
        );
        return response.getBody();
    }


    private ByteArrayResource convert(MultipartFile file) throws IOException {
        byte[] fileBytes = file.getBytes();
        ByteArrayResource fileResource = new ByteArrayResource(fileBytes) {
            @Override
            public String getFilename() {
                return "nft.jpg"; // 파일 이름 설정
            }
        };
        return fileResource;
    }

    private String metaDataAppender(String key, String value) {
        String pinataMetadata = "{\""+key+"\": \""+value+"\"}";
        return pinataMetadata;
    }

    private HttpHeaders pinataHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + pinataJwt);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return headers;
    }

}
