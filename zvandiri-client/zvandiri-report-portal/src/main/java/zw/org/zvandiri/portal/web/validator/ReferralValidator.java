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
import zw.org.zvandiri.business.domain.Referral;

/**
 *
 * @author Judge Muiznda
 */
@Component
public class ReferralValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(Referral.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Referral item = (Referral) o;
        ValidationUtils.rejectIfEmpty(errors, "referralDate", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "organisation", "field.empty");
        if (item.getReferralDate() != null && item.getReferralDate().after(new Date())) {
            errors.rejectValue("referralDate", "date.aftertoday");
        }
        if (item.getReferralDate() != null && item.getPatient().getDateOfBirth() != null && item.getReferralDate().before(item.getPatient().getDateOfBirth())) {
            errors.rejectValue("referralDate", "date.beforebirth");
        }
        if (item.getDateAttended() != null && item.getDateAttended().after(new Date())) {
            errors.rejectValue("dateAttended", "date.aftertoday");
        }
        if (item.getDateAttended() != null && item.getPatient().getDateOfBirth() != null && item.getDateAttended().before(item.getPatient().getDateOfBirth())) {
            errors.rejectValue("dateAttended", "date.beforebirth");
        }
        if ((item.getReferralDate() != null && item.getDateAttended() != null) && item.getDateAttended().before(item.getReferralDate())) {
            errors.rejectValue("dateAttended", "referraldate.after.dateattended");
        }
        // check that @least one section is checked
        /*boolean serviceReq = false;
        if ((item.getHivStiServicesReq() != null && !item.getHivStiServicesReq().isEmpty())){
            serviceReq = true;
        }
        if (!serviceReq && (item.getLaboratoryReq() != null && !item.getLaboratoryReq().isEmpty())){
            serviceReq = true;
        }
        if (!serviceReq && (item.getOiArtReq() != null && !item.getOiArtReq().isEmpty())){
            serviceReq = true;
        }
        if (!serviceReq && (item.getLegalReq() != null && !item.getLegalReq().isEmpty())){
            serviceReq = true;
        }
        if (!serviceReq && (item.getPsychReq() != null && !item.getPsychReq().isEmpty())){
            serviceReq = true;
        }
        if (!serviceReq && (item.getSrhReq() != null && !item.getSrhReq().isEmpty())){
            serviceReq = true;
        }
        if (!serviceReq && (item.getTbReq() != null && !item.getTbReq().isEmpty())){
            serviceReq = true;
        }
        if (!serviceReq) {
            errors.rejectValue("servicesRequestedError", "item.select.one");
        }*/
        if (item.getDateAttended() != null) {
            ValidationUtils.rejectIfEmpty(errors, "attendingOfficer", "field.empty");
            ValidationUtils.rejectIfEmpty(errors, "designation", "field.empty");
            if (item.getActionTaken() == null) {
                errors.rejectValue("actionTaken", "field.empty");
            }
        }
    }
}
