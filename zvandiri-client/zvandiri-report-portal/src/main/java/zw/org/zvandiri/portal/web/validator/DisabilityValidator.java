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
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import zw.org.zvandiri.business.domain.PatientDisability;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.DisabilityService;

/**
 *
 * @author jmuzinda
 */
@Component
public class DisabilityValidator implements Validator {

    @Resource
    private DisabilityService disabilityService;

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(PatientDisability.class);
    }

    @Override
    public void validate(Object o, Errors errors) {

        PatientDisability item = (PatientDisability) o;
        PatientDisability old = null;
        if (item.getDisabilityCategory() == null) {
            errors.rejectValue("disabilityCategory", "field.empty");
        }
        if (item.getScreened() == null) {
            errors.rejectValue("screened", "field.empty");
        }
        if (item.getSeverity() == null) {
            errors.rejectValue("severity", "field.empty");
        }
        if ((item.getScreened() != null && item.getScreened().equals(YesNo.YES)) && item.getDateScreened() == null) {
            errors.rejectValue("dateScreened", "field.empty");
        }
        if (item.getDateScreened() != null && item.getDateScreened().after(new Date())) {
            errors.rejectValue("dateScreened", "date.aftertoday");
        }
        if (item.getDateScreened() != null && item.getPatient().getDateOfBirth() != null && item.getDateScreened().before(item.getPatient().getDateOfBirth())) {
            errors.rejectValue("dateScreened", "date.beforebirth");
        }
        if (item.getPatient() != null && item.getDisabilityCategory() != null) {
            if (item.getPatient() != null) {
                old = disabilityService.getByPatientAndDisabilityCategory(item.getPatient(), item.getDisabilityCategory());
            }
            if (disabilityService.checkDuplicate(item, old)) {
                errors.rejectValue("uuid", "patient.disabilityrecord");
            }
        }
    }

}
