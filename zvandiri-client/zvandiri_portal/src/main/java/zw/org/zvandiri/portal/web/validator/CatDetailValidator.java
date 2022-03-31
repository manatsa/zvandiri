/*
 * Copyright 2016 Judge Muzinda.
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

import java.util.Date;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import zw.org.zvandiri.business.domain.CatDetail;
import zw.org.zvandiri.business.service.CatDetailService;

/**
 * @author Judge Muzinda
 */
@Component
public class CatDetailValidator implements Validator {

    @Resource
    private CatDetailService catDetailService;

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(CatDetail.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CatDetail item = (CatDetail) o;
        ValidationUtils.rejectIfEmpty(errors, "dateAsCat", "field.empty");
        if (item.getProvince() == null) {
            errors.rejectValue("province", "field.empty");
        }
        if (item.getDistrict() == null) {
            errors.rejectValue("district", "field.empty");
        }
        if (item.getPrimaryClinic() == null) {
            errors.rejectValue("primaryClinic", "field.empty");
        }
        /*This validation condition ensures that
         **no CAT details can be saved if the CAT
         **does not have at least one patient
         */
        if (item.getDateAsCat() != null && item.getDateAsCat().after(new Date())) {
            errors.rejectValue("contactDate", "date.aftertoday");
        }
        if (item.getDateAsCat() != null && item.getPatient().getDateOfBirth() != null && item.getDateAsCat().before(item.getPatient().getDateOfBirth())) {
            errors.rejectValue("contactDate", "date.beforebirth");
        }
        if (item.getId() == null) {
            ValidationUtils.rejectIfEmpty(errors, "userName", "field.empty");
            ValidationUtils.rejectIfEmpty(errors, "password", "field.empty");
            ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "field.empty");
            if (!item.getPassword().equals(item.getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "password.confirm");
            }
            if (!passwordMeetsRequirements(item.getPassword())) {
                errors.rejectValue("password", "password.notmeetingrequirements");
            }
            CatDetail old = null;
            if (item.getId() != null) {
                old = catDetailService.get(item.getId());
            }
            if (catDetailService.checkDuplicate(item, old)) {
                errors.rejectValue("userName", "item.duplicate");
            }
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
