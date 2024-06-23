package com.projectservice.projectservice.project.repository;

import com.projectservice.projectservice.member_cache.entity.Member;
import com.projectservice.projectservice.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;


import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByTitle(String memberId);

    Optional<Project> findByMakerAndProjectId(Member maker, Long projectId);

    Optional<Project> findProjectByProjectId(Long projectId);
    List<Project> findAllByMaker(Member maker);


}
