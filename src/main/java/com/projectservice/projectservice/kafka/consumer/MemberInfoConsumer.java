package com.projectservice.projectservice.kafka.consumer;

import com.projectservice.projectservice.common.enums.Role;
import com.projectservice.projectservice.kafka.dto.SyncMemberInfoDto;
import com.projectservice.projectservice.member_cache.entity.Member;
import com.projectservice.projectservice.member_cache.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@Slf4j
@RequiredArgsConstructor
public class MemberInfoConsumer {
    private final MemberRepository memberRepository;
    @Value("${topic.SIGNUP-EVENT}")
    private String SIGNUP_EVENT;

    @Transactional
    @KafkaListener(topics = "${topic.SIGNUP-EVENT}", groupId = "project-memberInfoConsumers", containerFactory = "kafkaListenerContainerFactory")
    public void memberInfoConsumer(ConsumerRecord<String, String> record) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(record.value());
        JSONObject data = ((JSONObject) jsonObject.get("data"));

        SyncMemberInfoDto syncMemberInfoDto = SyncMemberInfoDto.builder()
                                                                .memberId(Long.parseLong(data.get("member_id").toString()))
                                                                .address(data.get("address").toString())
                                                                .role(Role.valueOf(data.get("role").toString()))
                                                                .email(data.get("email").toString())
                                                                .password(data.get("password").toString())
                                                                .profileUrl(data.get("profile_url").toString())
                                                                .name(data.get("name").toString())
                                                                .build();
        Member member = syncMemberInfoDto.toEntity();
        memberRepository.save(member);
    }
}
