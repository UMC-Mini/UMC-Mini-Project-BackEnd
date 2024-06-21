package com.miniproject.demo.domain.reply.validation.annotation;

import com.miniproject.demo.domain.reply.validation.validator.ReplyExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ReplyExistValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistReply {

    String message() default "게시글을 찾지 못했습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
