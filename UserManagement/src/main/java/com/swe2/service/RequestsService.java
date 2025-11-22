package com.swe2.service;

import com.swe2.model.dto.UserCreateRequest;
import com.swe2.model.dto.UserRegisterDTO;
import org.apache.coyote.Request;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class RequestsService {
    public UserCreateRequest converToDto(UserRegisterDTO requestDTO){
        UserCreateRequest request = new UserCreateRequest();
        request.setName(requestDTO.getName());
        request.setEmail(requestDTO.getEmail());
        request.setRoleId(requestDTO.getRoleId());
        request.setPassword(requestDTO.getPassword());
        return request;
    }
    public UserCreateRequest Validate(@Valid UserCreateRequest req){
        return req;
    }

}
