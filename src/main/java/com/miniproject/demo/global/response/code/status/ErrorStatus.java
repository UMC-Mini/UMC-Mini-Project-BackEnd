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

    // 채팅 에러
    CHATBOT_ROOM_CREATE_FAIL (HttpStatus.BAD_REQUEST, "CHATBOT400", "채팅방 생성에 실패했습니다."),
    CHATBOT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "CHATBOT400", "채팅방을 찾을 수 없습니다."),
    CHATBOT_MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "CHATBOT400", "채팅 메시지를 찾을 수 없습니다."),

    ;

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