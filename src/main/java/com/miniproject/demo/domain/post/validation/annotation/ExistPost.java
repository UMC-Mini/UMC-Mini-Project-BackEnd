package com.miniproject.demo.domain.post.validation.annotation;

import com.miniproject.demo.domain.post.validation.validator.PostExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PostExistValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistPost {

    String message() default "게시글을 찾지 못했습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
