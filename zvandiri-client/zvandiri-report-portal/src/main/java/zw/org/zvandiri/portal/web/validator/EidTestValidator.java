/*
 * Copyright 2017 jmuzinda.
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
import zw.org.zvandiri.business.domain.EidTest;

/**
 *
 * @author jmuzinda
 */
@Component
public class EidTestValidator implements Validator {
    
    @Override
    public boolean supports(Class<?> type) {
        return type.equals(EidTest.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "dateTaken", "field.empty");
        EidTest item = (EidTest) o;
        if (item.getSource() == null) {
            errors.rejectValue("source", "field.empty");
        }
        if (item.getStatus() == null) {
            errors.rejectValue("status", "field.empty");
        }
        if (item.getDateTaken() != null && item.getDateTaken().after(new Date())) {
            errors.rejectValue("dateTaken", "date.aftertoday");
        }
        if (item.getDateTaken() != null && item.getPatient().getDateOfBirth() != null && item.getDateTaken().before(item.getPatient().getDateOfBirth())) {
            errors.rejectValue("dateTaken", "date.beforebirth");
        }
        if (item.getNextTestDate() == null) {
            errors.rejectValue("nextTestDate", "field.empty");
        }
        if (item.getNextTestDate() != null && item.getNextTestDate().before(new Date())) {
            errors.rejectValue("nextTestDate", "date.future");
        }
    }
}
