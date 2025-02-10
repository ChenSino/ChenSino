package com.chensino.core.api.validate;

import com.chensino.core.api.dto.UserLoginDTO;
import com.chensino.core.api.validate.group.PhoneLogin;
import com.chensino.core.api.validate.group.UserNameLogin;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * @Description TODO
 * @Date 2023/5/4 上午9:23
 * @Created by chenxk
 */
public class UserLoginDTOValidator implements Validator {
    @Override
    public boolean supports(@Nonnull Class<?> clazz) {
        return Objects.equals(UserLoginDTO.class, clazz);
    }

    @Override
    public void validate(@Nonnull Object target,@Nonnull Errors errors) {
    }

    /**
     * 分组校验
     *
     * @param target 被校验对象
     * @param errors 错误信息
     * @param group 分组校验
     */
    public void validate(Object target, Errors errors, Class<?> group) {
        UserLoginDTO userLoginDTO = (UserLoginDTO) target;
        //手机号验证码登录校验
        if (PhoneLogin.class.isAssignableFrom(group)) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "手机号不能为空");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneCode", "验证码不能为空");
            if (userLoginDTO.getPhoneCode() != null && userLoginDTO.getPhoneCode().length() != 6) {
                errors.rejectValue("phoneCode", "phoneCode.length.require.six", "验证码长度为6");
            }
            if (userLoginDTO.getPhone() != null && userLoginDTO.getPhone().length() != 11) {
                errors.rejectValue("phone", "phone.length.require.six", "手机号长度为11");
            }
        }
        //用户名密码登录校验
        if (UserNameLogin.class.isAssignableFrom(group)) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "field.required", "用户名不能为空");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required", "密码不能为空");
        }
    }
}
