package com.miniproject.demo.global.response.code.status;

import com.miniproject.demo.global.response.code.BaseErrorCode;
import com.miniproject.demo.global.response.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 기본 에러
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // User 에러
    _NOT_FOUND_USER(HttpStatus.NOT_FOUND, "USER400", "user를 찾을 수 없습니다."),
    _PASSWORD_NOT_EQUAL(HttpStatus.NOT_FOUND, "USER401", "password가 일치하지 않습니다."),

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST404", "게시글을 찾을 수 없습니다."),
    POST_SECRET(HttpStatus.BAD_REQUEST, "POST401", "게시글의 비밀 설정이 잘못되었습니다."),
    POST_INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "POST401", "게시글의 비밀번호가 없습니다."),
    POST_INCORRECT_PASSWORD(HttpStatus.BAD_REQUEST, "POST401", "게시글의 비밀번호가 틀렸습니다."),
    POST_NOT_WRITER(HttpStatus.BAD_REQUEST, "POST401", "게시글의 작성자가 아닙니다."),
    REPLY_NOT_FOUND(HttpStatus.NOT_FOUND, "REPLY404", "댓글을 찾을 수 없습니다."),
    REPLY_NOT_WRITER(HttpStatus.BAD_REQUEST, "POST401", "댓글의 작성자가 아닙니다."),

    // auth 관련 에러
    _TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "AUTH400", "토큰을 찾을 수 없습니다."),
    _AUTH_EXPIRE_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH401", "토큰이 만료되었습니다."),
    _AUTH_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH402", "토큰이 유효하지 않습니다."),
    _AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "AUTH403", "인증 실패");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder().message(message).code(code).isSuccess(false).build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}