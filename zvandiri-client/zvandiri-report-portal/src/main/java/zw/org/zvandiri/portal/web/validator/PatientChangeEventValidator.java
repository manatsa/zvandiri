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
import org.springframework.validation.Validator;
import zw.org.zvandiri.business.service.PatientService;
import zw.org.zvandiri.business.util.dto.PatientDTO;

/**
 * @author Judge Muzinda
 */
@Component
public class PatientChangeEventValidator implements Validator {

    @Resource
    private PatientService patientService;

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(PatientDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {

    }

    public void validateChangeSupportGroup(Object o, Errors errors) {
        PatientDTO item = (PatientDTO) o;
        if (item.getPatient().getProvince() == null) {
            errors.rejectValue("patient.province", "field.empty");
        }
        if (item.getPatient().getDistrict() == null) {
            errors.rejectValue("patient.district", "field.empty");
        }
        if (item.getPatient().getSupportGroup() == null) {
            errors.rejectValue("patient.supportGroup", "field.empty");
        }
    }

    public void validateChangeStatus(Object o, Errors errors) {
        PatientDTO item = (PatientDTO) o;
        if (item.getPatient().getStatus() == null) {
            errors.rejectValue("patient.status", "field.empty");
        }
    }

    public void validateChangeFacility(Object o, Errors errors) {
        PatientDTO item = (PatientDTO) o;
        if (item.getPatient().getProvince() == null) {
            errors.rejectValue("patient.province", "field.empty");
        }
        if (item.getPatient().getDistrict() == null) {
            errors.rejectValue("patient.district", "field.empty");
        }
        if (item.getPatient().getPrimaryClinic() == null) {
            errors.rejectValue("patient.primaryClinic", "field.empty");
        }
    }
}
