package com.miniproject.demo.domain.post.validation.validator;

import com.miniproject.demo.domain.post.service.PostService;
import com.miniproject.demo.domain.post.validation.annotation.ExistPost;
import com.miniproject.demo.global.response.code.status.ErrorStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PostExistValidator implements ConstraintValidator<ExistPost, Long> {

    private final PostService postService;

    @Override
    public void initialize(ExistPost constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = postService.isExist(aLong);

        if (!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorStatus.POST_NOT_FOUND.toString()).addConstraintViolation();
        }

        return isValid;
    }
}
