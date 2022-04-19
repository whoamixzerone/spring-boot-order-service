package com.zerone.springbootorderservice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberRequest {

    @ApiModelProperty(example = "admin1@gmail.com")
    private String email;

    @ApiModelProperty(example = "1234")
    private String password;
}
