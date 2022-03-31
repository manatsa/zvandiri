/*
 * Copyright 2018 tasu.
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

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import zw.org.zvandiri.business.domain.TbIpt;
import zw.org.zvandiri.business.domain.util.TbIdentificationOutcome;
import zw.org.zvandiri.business.domain.util.YesNo;

/**
 * @author tasu
 */
@Component
public class TbScreeningValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return TbIpt.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "screenedForTb", "field.empty");
        TbIpt item = (TbIpt) o;
        if (item.getScreenedForTb() != null && item.getScreenedForTb().equals(YesNo.YES)) {
            ValidationUtils.rejectIfEmpty(errors, "dateScreened", "field.empty");
            ValidationUtils.rejectIfEmpty(errors, "identifiedWithTb", "field.empty");
            ValidationUtils.rejectIfEmpty(errors, "tbTreatmentOutcome", "field.empty");
            ValidationUtils.rejectIfEmpty(errors, "referredForIpt", "field.empty");
            ValidationUtils.rejectIfEmpty(errors, "onIpt", "field.empty");
        }
        if (item.getIdentifiedWithTb() != null && item.getIdentifiedWithTb().equals(YesNo.YES)) {
            ValidationUtils.rejectIfEmpty(errors, "tbIdentificationOutcome", "field.empty");
        }
        if (item.getTbIdentificationOutcome() != null && item.getTbIdentificationOutcome().equals(TbIdentificationOutcome.ON_TB_TREATMENT)) {
            ValidationUtils.rejectIfEmpty(errors, "dateStartedTreatment", "field.empty");
        }
        if (item.getOnIpt() != null && item.getOnIpt().equals(YesNo.YES)) {
            ValidationUtils.rejectIfEmpty(errors, "dateStartedIpt", "field.empty");
        }
    }
}
