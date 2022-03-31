/*
 * Copyright 2018 jmuzinda.
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

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import zw.org.zvandiri.business.domain.CatActivity;
import zw.org.zvandiri.business.domain.util.YesNo;

/**
 * @author jmuzinda
 */
@Component
public class CatActivityValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(CatActivity.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CatActivity item = (CatActivity) o;

        ValidationUtils.rejectIfEmpty(errors, "certificateNumber", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "dateIssued", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "dateReceivedMentorship", "field.empty");
        if (item.getDateIssued() != null && item.getDateIssued().after(new Date())) {
            errors.rejectValue("dateIssued", "date.aftertoday");
        }
        if (item.getDateReceivedMentorship() != null && item.getDateReceivedMentorship().after(new Date())) {
            errors.rejectValue("dateReceivedMentorship", "date.aftertoday");
        }
        if (item.getCatsMentorship() == null) {
            errors.rejectValue("catsMentorship", "field.empty");
        }
        if (item.getDistrict() == null) {
            errors.rejectValue("district", "field.empty");
        }
        if (item.getAssignedPhone() == null) {
            errors.rejectValue("assignedPhone", "field.empty");
        }
        if (item.getAssignedPhone() != null && item.getAssignedPhone().equals(YesNo.YES)) {
            ValidationUtils.rejectIfEmpty(errors, "phoneModel", "field.empty");
            ValidationUtils.rejectIfEmpty(errors, "serialNumber", "field.empty");
            if (item.getPhoneStatus() == null) {
                errors.rejectValue("phoneStatus", "field.empty");
            }
        }
        if (item.getIssuedBicycle() == null) {
            errors.rejectValue("issuedBicycle", "field.empty");
        }
    }

}
