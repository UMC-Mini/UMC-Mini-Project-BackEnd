package com.miniproject.demo.global.error.exception;

import com.miniproject.demo.global.response.code.BaseErrorCode;
import com.miniproject.demo.global.response.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GeneralException extends RuntimeException{
    private BaseErrorCode code;

    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus() {
        return this.code.getReasonHttpStatus();
    }
}
