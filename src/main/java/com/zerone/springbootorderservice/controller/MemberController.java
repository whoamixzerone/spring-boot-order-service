package com.zerone.springbootorderservice.controller;

import com.zerone.springbootorderservice.entity.Member;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/member")
public class MemberController {

    /**
     * 회원 로그인 기능
     * 토큰 accessToken: value 전송
     * @param member
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Member member) {
        System.out.println("member: " + member.toString());
        String userId = member.getId();

        return ResponseEntity.ok("OK");
    }
}
