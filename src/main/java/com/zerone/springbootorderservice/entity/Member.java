package com.zerone.springbootorderservice.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Member extends BaseEntity {

    @ApiModelProperty(example = "admin")
    @Id
    private String id;

    @ApiModelProperty(example = "1234")
    private String password;

    @ApiModelProperty(example = "관리자1")
    private String name;

    @Builder
    public Member(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }
}
