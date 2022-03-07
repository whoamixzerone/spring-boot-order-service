package com.zerone.springbootorderservice.repository;

import com.zerone.springbootorderservice.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMembers() {
        IntStream.rangeClosed(1, 3).forEach(i -> {
            Member member = Member.builder()
                    .id("user" + i)
                    .password("1234")
                    .name("test" + i)
                    .build();

            memberRepository.save(member);
        });
    }
}