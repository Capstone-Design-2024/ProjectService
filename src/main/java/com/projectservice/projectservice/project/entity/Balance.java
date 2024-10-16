package com.projectservice.projectservice.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.projectservice.projectservice.member_cache.entity.Member;
import com.projectservice.projectservice.project.dto.ResBalanceDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="balance")
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_id", nullable = false)
    private Long balanceId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private Project project;

    public void addMember(Member member) {
        this.member = member;
    }
    public void addProject(Project project) {
        this.project = project;
    }

    public ResBalanceDto toDto() {
        return ResBalanceDto.builder()
                .name(this.project.getTitle())
                .image(this.project.getThumbnail())
                .description(this.project.getDescription())
                .price((this.project.getPrice()))
                .uri("https://ipfs.io/ipfs/bafkreig3yqlflyfkremizu437aipwmzemybokgg55fuig4xflykrjoqdnq")
                .build();
    }

}
