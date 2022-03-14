package com.zerone.springbootorderservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {
    private String access_token;
}
