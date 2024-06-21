package com.miniproject.demo.global.error.handler;

import com.miniproject.demo.global.error.exception.GeneralException;
import com.miniproject.demo.global.response.code.BaseErrorCode;

public class ReplyHandler extends GeneralException {
    public ReplyHandler(BaseErrorCode code) {
        super(code);
    }
}
