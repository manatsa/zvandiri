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

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import zw.org.zvandiri.business.domain.SrhHist;
import zw.org.zvandiri.business.domain.util.Gender;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.SrhHistService;

/**
 * @author Judge Muzinda
 */
@Component
public class SrhHistValidator implements Validator {

    @Resource
    private SrhHistService srhHistService;

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(SrhHist.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        SrhHist item = (SrhHist) o;
        SrhHist old = null;
        if (item.getPatient() != null) {
            old = srhHistService.getByPatient(item.getPatient());
        }
        if (item.getPatient().getGender() != null && item.getPatient().getGender().equals(Gender.FEMALE)) {
            ValidationUtils.rejectIfEmpty(errors, "ageStartMen", "field.empty");
            ValidationUtils.rejectIfEmpty(errors, "bleedHowOften", "field.empty");
            ValidationUtils.rejectIfEmpty(errors, "bleeddays", "field.empty");
        }
        if (item.getSexualIntercourse() == null) {
            errors.rejectValue("sexualIntercourse", "field.empty");
        }
        if (item.getSexuallyActive() == null) {
            errors.rejectValue("sexuallyActive", "field.empty");
        }
        if (item.getSexuallyActive() != null && item.getSexuallyActive().equals(YesNo.YES) && item.getCondomUse() == null) {
            errors.rejectValue("condomUse", "field.empty");
        }
        if (item.getSexuallyActive() != null && item.getSexuallyActive().equals(YesNo.YES) && item.getBirthControl() == null) {
            errors.rejectValue("birthControl", "field.empty");
        }
        if (srhHistService.checkDuplicate(item, old)) {
            errors.rejectValue("uuid", "patient.srhhistrecord");
        }
    }
}
