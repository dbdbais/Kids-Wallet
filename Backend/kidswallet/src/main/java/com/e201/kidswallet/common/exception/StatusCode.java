package com.e201.kidswallet.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StatusCode {
    SUCCESS(HttpStatus.OK, null),
    WRONG_PW(HttpStatus.UNAUTHORIZED, "비밀번호가 잘못되었습니다. 다시 시도해 주세요."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    UNAUTHORIZED_REQUEST(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "권한이 없는 사용자입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT FOUND."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 Request Method 호출입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류"),
    DUPLICATE_USER(HttpStatus.UNAUTHORIZED,"중복된 사용자입니다."),
    NO_USER(HttpStatus.METHOD_NOT_ALLOWED,"허용되지 않은 사용자입니다."),
    NO_PARENTS(HttpStatus.BAD_REQUEST,"부모가 없습니다."),
    OVERFLOW_IMAGE_SIZE(HttpStatus.BAD_REQUEST,"이미지 파일의 크기가 너무 큽니다(4GB이하 허용)"),
    NOT_ENOUGH_MONEY(HttpStatus.BAD_REQUEST,"돈이 부족합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
