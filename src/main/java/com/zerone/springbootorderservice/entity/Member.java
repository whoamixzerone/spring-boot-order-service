package com.zerone.springbootorderservice.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id
    private String id;

    private String password;

    private String name;

    private int auth;

    @Builder
    public Member(String id, String password, String name, int auth) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.auth = auth;
    }
}
