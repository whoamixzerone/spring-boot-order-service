package com.zerone.springbootorderservice.controller;

import com.zerone.springbootorderservice.dto.MemberRequest;
import com.zerone.springbootorderservice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * 회원 로그인 기능
     * 토큰 accessToken: value 전송
     * @param MemberRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody MemberRequest memberRequest) {
        System.out.println("member: " + memberRequest.toString());

        return ResponseEntity.ok().body(memberService.doLogin(memberRequest));
    }
}
