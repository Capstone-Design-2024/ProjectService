package com.projectservice.projectservice.project.service;

import com.projectservice.projectservice.security.dto.AuthorizerDto;

public interface ProjectService {
    Long initProject(AuthorizerDto authorizerDto);
}
