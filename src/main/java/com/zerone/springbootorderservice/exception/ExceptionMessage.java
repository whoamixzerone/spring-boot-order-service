package com.zerone.springbootorderservice.exception;

import lombok.Getter;

@Getter
public class ExceptionMessage {
    public static final String USER_ALREADY_EXIST = "사용자가 이미 존재합니다.";
    public static final String USER_NOT_EXIST = "사용자가 존재하지 않습니다.";
}
