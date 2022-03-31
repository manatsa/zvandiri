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

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import zw.org.zvandiri.business.domain.ArvAdverseEffect;

/**
 * @author Judge Muzinda
 */
@Component
public class ArvAdverseEffectsValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(ArvAdverseEffect.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "event", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "dateCommenced", "field.empty");
        ArvAdverseEffect item = (ArvAdverseEffect) o;
        if (item.getStatus() == null) {
            errors.rejectValue("status", "field.empty");
        }
        if (item.getSource() == null) {
            errors.rejectValue("source", "field.empty");
        }
        if (item.getDateCommenced() != null && item.getDateCommenced().after(new Date())) {
            errors.rejectValue("dateCommenced", "date.aftertoday");
        }
        if (item.getDateCommenced() != null && item.getArvHist().getPatient().getDateOfBirth() != null && item.getDateCommenced().before(item.getArvHist().getPatient().getDateOfBirth())) {
            errors.rejectValue("dateCommenced", "date.beforebirth");
        }
    }

}
