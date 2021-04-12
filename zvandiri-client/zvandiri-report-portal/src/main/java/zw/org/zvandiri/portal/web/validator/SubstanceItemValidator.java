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
import zw.org.zvandiri.business.domain.SubstanceItem;
import zw.org.zvandiri.business.service.SubstanceItemService;

/**
 *
 * @author Judge Muzinda
 */
@Component
public class SubstanceItemValidator implements Validator {

    @Resource
    private SubstanceItemService substanceItemService;

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(SubstanceItem.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        SubstanceItem item = (SubstanceItem) o;
        SubstanceItem old = null;
        ValidationUtils.rejectIfEmpty(errors, "startDate", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "howOften", "field.empty");
        if (item.getPatient() != null && item.getSubstance() != null) {
            if (item.getPatient() != null) {
                old = substanceItemService.getByPatientAndSubstance(item.getPatient(), item.getSubstance());
            }
            if (substanceItemService.checkDuplicate(item, old)) {
                errors.rejectValue("uuid", "patient.substancerecord");
            }
        }
        if (item.getSubstance() == null) {
            errors.rejectValue("substance", "field.empty");
        }
        if (item.getCurrent() == null) {
            errors.rejectValue("current", "field.empty");
        }
        if (item.getPast() == null) {
            errors.rejectValue("past", "field.empty");
        }
        if (item.getDrugIntervention() == null) {
            errors.rejectValue("drugIntervention", "field.empty");
        }
    }
}
