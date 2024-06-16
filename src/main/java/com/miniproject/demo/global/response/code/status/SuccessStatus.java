package com.miniproject.demo.global.response.code.status;

import com.miniproject.demo.global.response.code.BaseCode;
import com.miniproject.demo.global.response.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    _OK(HttpStatus.OK, "COMMON200", "성공입니다."),
    _CREATED(HttpStatus.CREATED, "COMMON201", "요청 성공 및 리소스 생성됨"),
    CHATBOT_ROOM_CREATE_SUCCESS(HttpStatus.CREATED,"CHATBOT201","상담 채팅방 생성 성공"),
    CHAT_MESSAGE_FETCH_SUCCESS(HttpStatus.OK,"CHATBOT200","채팅메시지 불러오기 성공"),
    CHATBOT_DELETE_SUCCESS(HttpStatus.OK,"CHATBOT200","상담 채팅 종료 성공 "),


    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder().message(message).code(code).isSuccess(true).build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build();
    }
}