package com.zerone.springbootorderservice.controller;

import com.zerone.springbootorderservice.dto.MemberRequest;
import com.zerone.springbootorderservice.dto.TokenResponse;
import com.zerone.springbootorderservice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * 회원 로그인 기능
     * 토큰 accessToken: value 전송
     * @param : MemberRequest
     * @return : ResponseEntity<TokenResponse>
     */
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody MemberRequest memberRequest) {

        return ResponseEntity.ok().body(memberService.doLogin(memberRequest));
    }

    @PostMapping("/verifyTest")
    public Map<String, String> verifyTest() {
        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return result;
    }
}
