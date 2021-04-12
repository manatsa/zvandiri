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
import org.apache.log4j.Logger;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import zw.org.zvandiri.business.domain.Contact;
import zw.org.zvandiri.business.domain.TestResult;
import zw.org.zvandiri.business.domain.util.ContactPhoneOption;
import zw.org.zvandiri.business.domain.util.FollowUp;
import zw.org.zvandiri.business.domain.util.Reason;
import zw.org.zvandiri.business.service.ContactService;

/**
 *
 * @author Judge Muzinda
 */
@Component
public class BeneficiaryContactValidator implements Validator {

    private final Logger LOG = Logger.getLogger("BeneficiaryContactValidator");

    @Resource
    private ContactService contactService;

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(Contact.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Contact item = (Contact) o;
        ValidationUtils.rejectIfEmpty(errors, "contactDate", "field.empty");
        if (item.getVisitOutcome() == null) {
            errors.rejectValue("visitOutcome", "field.empty");
        }

        if (item.getCareLevel() == null) {
            errors.rejectValue("careLevel", "field.empty");
        }
        if (item.getLocation() == null) {
            errors.rejectValue("location", "field.empty");
        }
        if (item.getPosition() == null) {
            errors.rejectValue("position", "field.empty");
        }
        if (item.getReason() == null) {
            errors.rejectValue("reason", "field.empty");
        }
        if (item.getReason() != null && item.getReason().equals(Reason.OTHER)) {
            ValidationUtils.rejectIfEmpty(errors, "otherReason", "field.empty");
        }
        if (item.getFollowUp() == null) {
            errors.rejectValue("followUp", "field.empty");
        }
        if (item.getActionTaken() == null) {
            errors.rejectValue("actionTaken", "field.empty");
        }
        if (item.getReason() != null) {
            if (item.getReason().equals(Reason.INTERNAL_REFERRAL) && item.getInternalReferral() == null) {
                errors.rejectValue("internalReferral", "field.empty");
            }
            if (item.getReason().equals(Reason.EXTERNAL_REFERRAL) && item.getExternalReferral() == null) {
                errors.rejectValue("externalReferral", "field.empty");
            }
        }
        if (item.getFollowUp() != null) {
            if (item.getFollowUp().equals(FollowUp.STABLE) && item.getStables() == null) {
                errors.rejectValue("stables", "item.select.one");
            }
            if (item.getFollowUp().equals(FollowUp.ENHANCED) && item.getEnhanceds() == null) {
                errors.rejectValue("enhanceds", "item.select.one");
            }
        }
        if (item.getContactDate() != null && item.getContactDate().after(new Date())) {
            errors.rejectValue("contactDate", "date.aftertoday");
        }
        if (item.getContactDate() != null && item.getPatient().getDateOfBirth() != null && item.getContactDate().before(item.getPatient().getDateOfBirth())) {
            errors.rejectValue("contactDate", "date.beforebirth");
        }
        if (item.getLastClinicAppointmentDate() != null && item.getLastClinicAppointmentDate().after(new Date())) {
            errors.rejectValue("lastClinicAppointmentDate", "date.aftertoday");
        }
        if (item.getLastClinicAppointmentDate() != null && item.getPatient().getDateOfBirth() != null && item.getLastClinicAppointmentDate().before(item.getPatient().getDateOfBirth())) {
            errors.rejectValue("lastClinicAppointmentDate", "date.beforebirth");
        }
        if (item.getLastClinicAppointmentDate() != null && item.getAttendedClinicAppointment() == null) {
            errors.rejectValue("attendedClinicAppointment", "field.empty");
        }
        if (item.getActionTaken() != null && item.getActionTaken().getName().equalsIgnoreCase("Internal Referral")
                && item.getReferredPerson() == null) {
            errors.rejectValue("referredPerson", "field.empty");
        }
        if (item.getLocation() != null) {
            if (item.getLocation().getName().equalsIgnoreCase("Phone")) {
                if (item.getContactPhoneOption() == null) {
                    errors.rejectValue("contactPhoneOption", "field.empty");
                }
                if (item.getContactPhoneOption() != null && item.getContactPhoneOption().equals(ContactPhoneOption.SMS)) {
                    if (item.getNumberOfSms() == null || item.getNumberOfSms() == 0) {
                        errors.rejectValue("numberOfSms", "field.empty");
                    }
                }
            }
        }
        if (item.getNextClinicAppointmentDate() == null) {
            errors.rejectValue("nextClinicAppointmentDate", "field.empty");
        }
        if (item.getNextClinicAppointmentDate() != null && item.getNextClinicAppointmentDate().before(new Date())) {
            errors.rejectValue("nextClinicAppointmentDate", "date.future");
        }
        // validating viral load and cd4 count
        if (item.getViralLoad() != null && item.getViralLoad().getDateTaken() != null) {
            item.getViralLoad().setPatient(item.getPatient());
            validateViralLoad(item.getViralLoad(), errors);
        }
        // validating cd4 count
        if (item.getCd4Count() != null && item.getCd4Count().getDateTaken() != null) {
            item.getCd4Count().setPatient(item.getPatient());
            validateCd4Count(item.getCd4Count(), errors);
        }
    }

    public void validateViralLoad(TestResult item, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "viralLoad.dateTaken", "field.empty");
        if (item.getResult() != null && item.getTnd() != null) {
            errors.rejectValue("viralLoad.result", "tnd.viralload.both");
        }
        if (item.getResult() == null && item.getTnd() == null) {
            errors.rejectValue("viralLoad.result", "tnd.viralload.missing");
        }
        if (item.getSource() == null) {
            errors.rejectValue("viralLoad.source", "field.empty");
        }
        if (item.getNextTestDate() == null) {
            errors.rejectValue("viralLoad.nextTestDate", "field.empty");
        }
        if (item.getNextTestDate() != null && item.getNextTestDate().before(new Date())) {
            errors.rejectValue("viralLoad.nextTestDate", "date.future");
        }
        if (item.getDateTaken() != null && item.getDateTaken().after(new Date())) {
            errors.rejectValue("viralLoad.dateTaken", "date.aftertoday");
        }
        if (item.getDateTaken() != null
                && item.getPatient().getDateOfBirth() != null
                && item.getDateTaken().before(
                        item.getPatient().getDateOfBirth())) {
            errors.rejectValue("viralLoad.dateTaken", "date.beforebirth");
        }
    }

    public void validateCd4Count(TestResult item, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "cd4Count.dateTaken", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "cd4Count.result", "field.empty");
        if (item.getSource() == null) {
            errors.rejectValue("cd4Count.source", "field.empty");
        }
        if (item.getResult() == null && item.getTnd() == null) {
            errors.rejectValue("cd4Count.result", "tnd.viralload.missing");
        }
        if (item.getDateTaken() != null && item.getDateTaken().after(new Date())) {
            errors.rejectValue("cd4Count.dateTaken", "date.aftertoday");
        }
        if (item.getDateTaken() != null && item.getPatient().getDateOfBirth() != null && item.getDateTaken().before(item.getPatient().getDateOfBirth())) {
            errors.rejectValue("cd4Count.dateTaken", "date.beforebirth");
        }
        if (item.getNextTestDate() == null) {
            errors.rejectValue("cd4Count.nextTestDate", "field.empty");
        }
        if (item.getNextTestDate() != null && item.getNextTestDate().before(new Date())) {
            errors.rejectValue("cd4Count.nextTestDate", "date.future");
        }
    }

}
