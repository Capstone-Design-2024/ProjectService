package com.projectservice.projectservice.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class NFTRegistryProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${topic.NFT-REGISTRY-EVENT}")
    private String NFT_REGISTRY_EVENT;

    public void produceNFTRegistry(String title, String ipfsImgURI, Long makerId, Long projectId, Long price, String description) {
        sendMessage(makeMessage(makeData(title, ipfsImgURI, makerId, projectId, price, description)));
    }

    private void sendMessage(String data) {
        log.info("{} message : {}", NFT_REGISTRY_EVENT, data);
        this.kafkaTemplate.send(NFT_REGISTRY_EVENT, data);
    }

    private Map<String, Object> makeData(String title, String ipfsImgURI, Long makerId, Long projectId, Long price, String description) {
        Map<String, Object> data = new HashMap<>();
        data.put("title", title);
        data.put("image_uri", ipfsImgURI);
        data.put("maker_id", makerId);
        data.put("project_id", projectId);
        data.put("price", price);
        data.put("description", description);

        return data;
    }

    private String makeMessage(Map<String, Object> data) {
        JSONObject obj = new JSONObject();
        obj.put("uuid", "NFTRegistryProducer/" + Instant.now().toEpochMilli());
        obj.put("data", data);
        return obj.toJSONString();
    }
}
