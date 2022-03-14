package com.zerone.springbootorderservice.service;

import com.zerone.springbootorderservice.dto.MemberRequest;
import com.zerone.springbootorderservice.dto.TokenResponse;

public interface MemberService {

    public TokenResponse doLogin(MemberRequest memberRequest);
}
