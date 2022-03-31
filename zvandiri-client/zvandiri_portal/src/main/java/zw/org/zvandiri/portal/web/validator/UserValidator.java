/*
 * Copyright 2014 Edward Zengeni.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package zw.org.zvandiri.portal.web.validator;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import zw.org.zvandiri.business.domain.User;
import zw.org.zvandiri.business.domain.util.UserLevel;
import zw.org.zvandiri.business.service.UserService;
import zw.org.zvandiri.business.util.dto.ChangePasswordDTO;
import zw.org.zvandiri.business.util.dto.ChangePrivilegesDTO;

/**
 * @author Edward Zengeni
 */
@Component
public class UserValidator implements Validator {

    @Resource
    private UserService userService;

    @Override
    public boolean supports(Class<?> type) {
        return User.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        User old = null;
        ValidationUtils.rejectIfEmpty(errors, "firstName", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "userName", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "userRoles", "field.empty");
        if (user.getId() != null) {
            old = userService.get(user.getId());
        }
        if (userService.checkDuplicate(user, old)) {
            errors.rejectValue("userName", "item.duplicate");
        }
        if (user.getId() == null) {
            ValidationUtils.rejectIfEmpty(errors, "password", "field.empty");
            ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "field.empty");
            if (!user.getPassword().equals(user.getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "password.confirm");
            }
        }
        if (user.getUserType() == null) {
            errors.rejectValue("userType", "field.empty");
        }
        if (user.getUserLevel() == null) {
            errors.rejectValue("userLevel", "field.empty");
        }
        if (user.getUserLevel() != null) {
            if (user.getUserLevel().equals(UserLevel.PROVINCE) &&
                    user.getProvince() == null) {
                errors.rejectValue("province", "field.empty");
            }
            if (user.getUserLevel().equals(UserLevel.DISTRICT) &&
                    user.getDistrict() == null) {
                errors.rejectValue("district", "field.empty");
            }
        }
    }

    public void validateChangepassword(Object o, Errors errors) throws Exception {
        ChangePasswordDTO changePassword = (ChangePasswordDTO) o;
        User currentUser = changePassword.getUser();
        ValidationUtils.rejectIfEmpty(errors, "newPassword", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "oldPassword", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "field.empty");
        //String hashedNewPassword = Security.encodeString(changePassword.getOldPassword());
        /*if (!currentUser.getPassword().equals("")) {
         errors.rejectValue("oldPassword", "oldpassword.newpasswordnotequal");
         }*/
        if (changePassword.getNewPassword() != null) {
            if (changePassword.getNewPassword().isEmpty() || !changePassword.getNewPassword().equals("")) {
                if (!passwordMeetsRequirements(changePassword.getNewPassword())) {
                    errors.rejectValue("newPassword", "password.notmeetingrequirements");
                }
            }
            if (!changePassword.getNewPassword().equals(changePassword.getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "password.confirm");
            }
        }
    }

    public void manageChangepassword(Object o, Errors errors) {
        ChangePasswordDTO changePassword = (ChangePasswordDTO) o;
        User currentUser = changePassword.getUser();
        ValidationUtils.rejectIfEmpty(errors, "newPassword", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "field.empty");
        if (changePassword.getNewPassword() != null && changePassword.getConfirmPassword() != null) {
            if (changePassword.getNewPassword().isEmpty() || !changePassword.getNewPassword().equals("")) {
                if (!passwordMeetsRequirements(changePassword.getNewPassword())) {
                    errors.rejectValue("newPassword", "password.notmeetingrequirements");
                }
            }
            if (!changePassword.getNewPassword().equals(changePassword.getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "password.confirm");
            }
        }

    }

    public void validateChangeprivileges(Object o, Errors errors) {
        ChangePrivilegesDTO dto = (ChangePrivilegesDTO) o;
        if (dto.getUser().getUserRoles().isEmpty()) {
            errors.rejectValue("user.userRoles", "field.required");
        }
    }

    private boolean passwordMeetsRequirements(String password) {

        if (password == null || password.trim().equals("")) {
            return false;
        }

        PasswordValidator passwordValidator = new PasswordValidator();
        return passwordValidator.validate(password);

    }
}
