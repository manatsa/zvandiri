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

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import zw.org.zvandiri.business.domain.ArvHist;

/**
 *
 * @author Judge Muzinda
 */
@Component
public class ARVHistValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(ArvHist.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "startDate", "field.empty");
        ArvHist item = (ArvHist) o;
        if(item.getArvMedicine() == null){
            errors.rejectValue("arvMedicine", "field.empty");
        }
        if(item.getArvMedicine2() == null){
            errors.rejectValue("arvMedicine2", "field.empty");
        }
        if (item.getArvMedicine() != null && item.getArvMedicine2() != null) {
        	if (item.getArvMedicine().getName().equals(item.getArvMedicine2().getName())) {
        		errors.rejectValue("arvMedicine2", "medicine.duplicate");
        	}
        }
        if (item.getArvMedicine2() != null && item.getArvMedicine3() != null) {
        	if (item.getArvMedicine2().getName().equals(item.getArvMedicine3().getName())) {
        		errors.rejectValue("arvMedicine3", "medicine.duplicate");
        	}
        }
    }
}