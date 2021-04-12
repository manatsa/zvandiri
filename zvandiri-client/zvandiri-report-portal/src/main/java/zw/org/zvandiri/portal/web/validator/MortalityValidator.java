/*
 * Copyright 2019 jmuzinda.
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
import zw.org.zvandiri.business.domain.Mortality;
import zw.org.zvandiri.business.domain.util.YesNo;

/**
 *
 * @author jmuzinda
 */
@Component
public class MortalityValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(Mortality.class);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Mortality item = (Mortality) o;

        ValidationUtils.rejectIfEmpty(errors, "dateOfDeath", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "causeOfDeath", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "causeOfDeathDetails", "field.empty");
        if (item.getReceivingEnhancedCare() == null) {
            errors.rejectValue("receivingEnhancedCare", "field.empty");
        }
        if (item.getReceivingEnhancedCare() != null && item.getReceivingEnhancedCare().equals(YesNo.YES)) {
            if (item.getDatePutOnEnhancedCare() == null) {
                errors.rejectValue("datePutOnEnhancedCare", "field.empty");
            }
        }
        if (item.getDateOfDeath() != null) {
            if (item.getDateOfDeath().after(new Date())) {
                errors.rejectValue("dateOfDeath", "date.aftertoday");
            }
            if (item.getPatient().getDateOfBirth() != null && item.getDateOfDeath().before(item.getPatient().getDateOfBirth())) {
                errors.rejectValue("dateOfDeath", "date.beforebirth");
            }
        }
        if (item.getDatePutOnEnhancedCare() != null) {
            if (item.getDatePutOnEnhancedCare().after(new Date())) {
                errors.rejectValue("datePutOnEnhancedCare", "date.aftertoday");
            }
            if (item.getPatient().getDateOfBirth() != null && item.getDatePutOnEnhancedCare().before(item.getPatient().getDateOfBirth())) {
                errors.rejectValue("datePutOnEnhancedCare", "date.beforebirth");
            }
        }
        if (item.getContactWithZM() == null) {
            errors.rejectValue("contactWithZM", "field.empty");
        }
        if (item.getContactWithZM() != null && item.getContactWithZM().equals(YesNo.YES)) {
            if (item.getDateOfContactWithZim() == null) {
                errors.rejectValue("dateOfContactWithZim", "field.empty");
            }
        }
    }

}
