package com.miniproject.demo.domain.reply.validation.validator;

import com.miniproject.demo.domain.reply.service.ReplyService;
import com.miniproject.demo.domain.reply.validation.annotation.ExistReply;
import com.miniproject.demo.global.response.code.status.ErrorStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReplyExistValidator implements ConstraintValidator<ExistReply, Long> {

    private final ReplyService replyService;

    @Override
    public void initialize(ExistReply constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = replyService.isExist(aLong);

        if (!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorStatus.POST_NOT_FOUND.toString()).addConstraintViolation();
        }

        return isValid;
    }
}
