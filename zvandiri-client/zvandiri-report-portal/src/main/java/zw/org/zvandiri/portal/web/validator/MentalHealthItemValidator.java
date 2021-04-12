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
import zw.org.zvandiri.business.domain.MentalHealthItem;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.MentalHealthItemService;

/**
 *
 * @author Judge Muzinda
 */
@Component
public class MentalHealthItemValidator implements Validator {

    @Resource
    private MentalHealthItemService mentalHealthItemService;
    
    @Override
    public boolean supports(Class<?> type) {
        return type.equals(MentalHealthItem.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "past", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "current", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "mentalHistText", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "age", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "professionalCareProvidedBy", "field.empty");        
        MentalHealthItem item = (MentalHealthItem) o;
        MentalHealthItem old = null;
        if (item.getPatient() != null && item.getMentalHealth() != null) {
            if (item.getPatient() != null) {
                old = mentalHealthItemService.getByPatientAndMentalHealth(item.getPatient(), item.getMentalHealth());
            }
            if (mentalHealthItemService.checkDuplicate(item, old)) {
                errors.rejectValue("uuid", "patient.mentalhealthrecord");
            }
        }
        if (item.getMentalHealth() == null) {
            errors.rejectValue("mentalHealth", "field.empty");
        }
        if (item.getReceivedProfessionalHelp() == null) {
            errors.rejectValue("receivedProfessionalHelp", "field.empty");
        }
        if (item.getReceivedProfessionalHelp() != null && item.getReceivedProfessionalHelp().equals(YesNo.YES)) {
            if (item.getProfHelpStart() == null) {
                errors.rejectValue("profHelpStart", "field.empty");
            }
            if (item.getProfHelpEnd() == null) {
                errors.rejectValue("profHelpEnd", "field.empty");
            }
            if (item.getProfHelpStart() != null && item.getProfHelpStart().after(new Date())) {
                errors.rejectValue("profHelpStart", "date.aftertoday");
            }
            if (item.getProfHelpStart() != null && item.getPatient().getDateOfBirth() != null && item.getProfHelpStart().before(item.getPatient().getDateOfBirth())) {
                errors.rejectValue("profHelpStart", "date.beforebirth");
            }
            if (item.getProfHelpEnd() != null && item.getProfHelpEnd().after(new Date())) {
                errors.rejectValue("profHelpEnd", "date.aftertoday");
            }
            if (item.getProfHelpEnd() != null && item.getPatient().getDateOfBirth() != null && item.getProfHelpEnd().before(item.getPatient().getDateOfBirth())) {
                errors.rejectValue("profHelpEnd", "date.beforebirth");
            }
        }
    }

}
