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
import zw.org.zvandiri.business.domain.HivConInfectionItem;
import zw.org.zvandiri.business.service.HivConInfectionItemService;

/**
 * @author Judge Muzinda
 */
@Component
public class HivConInfectionItemValidator implements Validator {

    @Resource
    private HivConInfectionItemService hivConInfectionItemService;

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(HivConInfectionItem.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "infectionDate", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "medication", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "resolution", "field.empty");
        HivConInfectionItem item = (HivConInfectionItem) o;
        HivConInfectionItem old = null;
        if (item.getPatient() != null && item.getHivCoInfection() != null) {
            if (item.getPatient() != null) {
                old = hivConInfectionItemService.getByPatientAndHivConInfection(item.getPatient(), item.getHivCoInfection());
            }
            if (hivConInfectionItemService.checkDuplicate(item, old)) {
                errors.rejectValue("uuid", "patient.hivcoinfectionrecord");
            }
        }
        if (item.getHivCoInfection() == null) {
            errors.rejectValue("hivCoInfection", "field.empty");
        }
        if (item.getInfectionDate() != null && item.getInfectionDate().after(new Date())) {
            errors.rejectValue("infectionDate", "date.aftertoday");
        }
        if (item.getInfectionDate() != null && item.getPatient().getDateOfBirth() != null && item.getInfectionDate().before(item.getPatient().getDateOfBirth())) {
            errors.rejectValue("infectionDate", "date.beforebirth");
        }
    }
}
