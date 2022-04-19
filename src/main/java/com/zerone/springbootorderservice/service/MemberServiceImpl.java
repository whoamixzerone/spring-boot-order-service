package com.zerone.springbootorderservice.service;

import com.zerone.springbootorderservice.dto.MemberRequest;
import com.zerone.springbootorderservice.dto.TokenResponse;
import com.zerone.springbootorderservice.entity.Member;
import com.zerone.springbootorderservice.exception.UserNotFoundException;
import com.zerone.springbootorderservice.repository.MemberRepository;
import com.zerone.springbootorderservice.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public TokenResponse doLogin(MemberRequest memberRequest) {
        Optional<Member> member = memberRepository.findByEmail(memberRequest.getEmail());
        if(!member.isPresent()) {
            throw new UserNotFoundException();
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.get());

        return TokenResponse.builder()
                .access_token(accessToken)
                .build();
    }
}
