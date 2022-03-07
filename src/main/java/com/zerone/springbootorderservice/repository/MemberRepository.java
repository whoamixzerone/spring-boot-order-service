package com.zerone.springbootorderservice.repository;

import com.zerone.springbootorderservice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
