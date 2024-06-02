package com.miniproject.demo.global.error.handler;


import com.miniproject.demo.global.response.code.BaseErrorCode;
import com.miniproject.demo.global.error.exception.GeneralException;

public class TestHandler extends GeneralException {

    public TestHandler(BaseErrorCode code) {
        super(code);
    }
}
