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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.Settings;
import zw.org.zvandiri.business.domain.User;
import zw.org.zvandiri.business.domain.util.Gender;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.PatientService;
import zw.org.zvandiri.business.service.SettingsService;
import zw.org.zvandiri.business.service.UserService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.PatientHeuDTO;
import zw.org.zvandiri.portal.util.MobileNumberFormat;

/**
 * @author Judge Muzinda
 */
@Component
public class PatientValidator implements Validator {

    @Resource
    UserService userService;
    @Resource
    private PatientService patientService;
    private final String STRING_PATTERN = "[a-zA-Z]+";
    private Pattern pattern;
    private Matcher matcher;
    @Resource
    private SettingsService settingsService;

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(Patient.class);
    }

    public void validatePatientDemographic(Object o, Errors errors) {
        Patient item = (Patient) o;
        Patient old = null;
        if (item.getId() != null) {
            old = patientService.get(item.getId());
        }
        Settings settings = settingsService.getItem();
        ValidationUtils.rejectIfEmpty(errors, "firstName", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "dateOfBirth", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "oINumber", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "IDNumber", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "haveBirthCertificate", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "maritalStatus", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "orphanStatus", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "clientType", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "artRegimen", "field.empty");
        if (item.getFirstName() != null && !item.getFirstName().isEmpty()) {
            pattern = Pattern.compile(STRING_PATTERN);
            matcher = pattern.matcher(item.getFirstName());
            if (!matcher.matches()) {
                errors.rejectValue("firstName", "item.containNoChar");
            }
        }
        if (item.getLastName() != null && !item.getLastName().isEmpty()) {
            pattern = Pattern.compile(STRING_PATTERN);
            matcher = pattern.matcher(item.getLastName());
            if (!matcher.matches()) {
                errors.rejectValue("lastName", "item.containNoChar");
            }
        }
        if (item.getMiddleName() != null && !item.getMiddleName().isEmpty()) {
            pattern = Pattern.compile(STRING_PATTERN);
            matcher = pattern.matcher(item.getMiddleName());
            if (!matcher.matches()) {
                errors.rejectValue("middleName", "item.containNoChar");
            }
        }
        if (item.getGender() == null) {
            errors.rejectValue("gender", "field.empty");
        }
        if (item.getDateOfBirth() != null) {
            if (item.getDateOfBirth().after(new Date())) {
                errors.rejectValue("dateOfBirth", "date.aftertoday");
            }
            //mother.max.age
            if (item.getGender() != null) {
                if (item.getGender().equals(Gender.FEMALE) && item.getDateOfBirth().before(DateUtil.getDateFromAge(settings.getHeuMotherMaxAge()))) {
                    errors.rejectValue("dateOfBirth", "patient.max.age");
                } else if (!item.getGender().equals(Gender.FEMALE) && item.getDateOfBirth().before(DateUtil.getDateFromAge(settings.getPatientMaxAge()))) {
                    errors.rejectValue("dateOfBirth", "patient.max.age");
                }
            }
        }

        //System.err.println(">>>>> "+item.getMaritalStatus()+"\t*****"+item.getOrphanStatus());


    }

    public void validatePatientContactDetails(Object o, Errors errors) {
        Patient item = (Patient) o;
        Patient old = null;
        if (item.getHei().equals(YesNo.NO)) {
            if (item.getId() != null) {
                old = patientService.get(item.getId());
            }
            //ValidationUtils.rejectIfEmpty(errors, "mobileNumber", "field.empty");
            if (item.getMobileNumber() != null && !item.getMobileNumber().matches(MobileNumberFormat.ZIMBABWE)) {
                errors.rejectValue("mobileNumber", "mobileNumber.format");
            }
            if (item.getMobileNumber() != null && item.getMobileOwner() == null) {
                errors.rejectValue("mobileOwner", "field.empty");
            }
            if (item.getMobileOwner() != null && item.getMobileOwner().equals(YesNo.NO) && item.getOwnerName() == null) {
                errors.rejectValue("ownerName", "field.empty");
            }
            if (item.getMobileOwner() != null && item.getMobileOwner().equals(YesNo.NO) && item.getMobileOwnerRelation() == null) {
                errors.rejectValue("mobileOwnerRelation", "field.empty");
            }
            if (item.getSecondaryMobileNumber() != null && !item.getSecondaryMobileNumber().matches(MobileNumberFormat.ZIMBABWE)) {
                errors.rejectValue("secondaryMobileNumber", "mobileNumber.format");
            }
            if ((item.getOwnSecondaryMobile() != null && item.getOwnSecondaryMobile().equals(YesNo.YES)) && item.getSecondaryMobileNumber() == null) {
                errors.rejectValue("secondaryMobileNumber", "field.empty");
            }
            if (item.getSecondaryMobileNumber() != null && item.getOwnSecondaryMobile() == null) {
                errors.rejectValue("ownSecondaryMobile", "field.empty");
            }
            if (item.getOwnSecondaryMobile() != null && item.getOwnSecondaryMobile().equals(YesNo.NO) && item.getSecondaryMobileOwnerName() == null) {
                errors.rejectValue("secondaryMobileOwnerName", "field.empty");
            }
            if ((item.getOwnSecondaryMobile() != null && item.getOwnSecondaryMobile().equals(YesNo.NO)) && item.getSecondaryMobileownerRelation() == null) {
                errors.rejectValue("secondaryMobileownerRelation", "field.empty");
            }
            if ((item.getOwnSecondaryMobile() != null && item.getOwnSecondaryMobile().equals(YesNo.NO)) && item.getSecondaryMobileOwnerName() == null) {
                errors.rejectValue("secondaryMobileOwnerName", "field.empty");
            }
        }
    }

    public void validatePatientAddress(Object o, Errors errors) {
        Patient item = (Patient) o;
        ValidationUtils.rejectIfEmpty(errors, "address", "field.empty");
        if (item.getProvince() == null) {
            errors.rejectValue("province", "field.empty");
        }
        if (item.getDistrict() == null) {
            errors.rejectValue("district", "field.empty");
        }
        if (item.getPrimaryClinic() == null) {
            errors.rejectValue("primaryClinic", "field.empty");
        }
        if (item.getHei() == null || (item.getHei().equals(YesNo.NO))) {
            if (item.getSupportGroup() == null) {
                errors.rejectValue("supportGroup", "field.empty");
            }
        }
        if (item.getFirstName() != null && item.getLastName() != null && item.getDateOfBirth() != null && item.getPrimaryClinic() != null) {
            if (patientService.checkDuplicate(item, null)) {
                errors.rejectValue("patientExist", "patient.exists");
            }
        }
    }

    public void validatePatientEducationAndZvandiriDetails(Object o, Errors errors) {
        Patient item = (Patient) o;
        if (item.getHei().equals(YesNo.NO)) {
            ValidationUtils.rejectIfEmpty(errors, "dateJoined", "field.empty");
            ValidationUtils.rejectIfEmpty(errors, "isKeypopulation", "field.empty");
            if (item.getEducation() == null) {
                errors.rejectValue("education", "field.empty");
            }
            if (item.getEducationLevel() == null) {
                errors.rejectValue("educationLevel", "field.empty");
            }
           /* if (item.getEducation() != null && item.getEducation().getName().equalsIgnoreCase("Out of School")) {
                if ((item.getEducationLevel() != null
                        && (item.getEducationLevel().getName().equalsIgnoreCase("N/A")
                        || item.getEducationLevel().getName().equalsIgnoreCase("Primary School")))
                        && item.getReasonForNotReachingOLevel() == null) {
                    errors.rejectValue("reasonForNotReachingOLevel", "field.empty");
                }
            }*/
            if (item.getReferer() == null) {
                errors.rejectValue("referer", "field.empty");
            }
            if (item.getDateJoined() != null && item.getDateJoined().after(new Date())) {
                errors.rejectValue("dateJoined", "date.aftertoday");
            }
            if (item.getDateJoined() != null && item.getDateOfBirth() != null && item.getDateJoined().before(item.getDateOfBirth())) {
                errors.rejectValue("dateJoined", "date.beforebirth");
            }
            if (item.getIsKeypopulation() != null && item.getIsKeypopulation().equals(YesNo.YES)) {
                if (item.getKeyPopulation() == null) {
                    errors.rejectValue("keyPopulation", "field.empty");
                }
            } else {
                // item.setKeyPopulation(null);
            }

        }
    }

    public void validatePatientHivAndHealth(Object o, Errors errors) {
        Patient item = (Patient) o;
        if (item.getHivStatusKnown() == null) {
            errors.rejectValue("hivStatusKnown", "field.empty");
        }
        if (item.getDisability() == null) {
            errors.rejectValue("disability", "field.empty");
        }
        if (item.getHei().equals(YesNo.NO)) {
            if (item.getHivStatusKnown() != null && item.getHivStatusKnown().equals(YesNo.YES) && item.getTransmissionMode() == null) {
                errors.rejectValue("transmissionMode", "field.empty");
            }
            if (item.getDateTested() != null && item.getDateTested().after(new Date())) {
                errors.rejectValue("dateTested", "date.aftertoday");
            }
            if (item.getDateTested() != null && item.getDateOfBirth() != null && item.getDateTested().before(item.getDateOfBirth())) {
                errors.rejectValue("dateTested", "date.beforebirth");
            }
            if (item.getHivStatusKnown() != null && item.getHivStatusKnown().equals(YesNo.YES)) {
                if (item.gethIVDisclosureLocation() == null) {
                    errors.rejectValue("hIVDisclosureLocation", "field.empty");
                    errors.rejectValue("disclosureType", "field.empty");
                }
            }
            if (item.getDisability() != null && item.getDisability().equals(YesNo.YES)) {
                errors.rejectValue("disablityType", "field.empty");
            }
        }

    }

    public void validatePrimaryCareGiver(Object o, Errors errors) {
        Patient item = (Patient) o;
        if (item.getSelfPrimaryCareGiver() == null) {
            errors.rejectValue("selfPrimaryCareGiver", "field.empty");
        }
    }

    public void validatePatientZvandiriDetails(Object o, Errors errors) {
        Patient item = (Patient) o;
        if (item.getHei().equals(YesNo.NO)) {
            if (item.getYoungMumGroup() != null && item.getYoungMumGroup().equals(YesNo.YES) && (item.getGender() != null && item.getGender().equals(Gender.MALE))) {
                errors.rejectValue("youngMumGroup", "females.only");
            }
            if ((item.getGender() != null && item.getGender().equals(Gender.FEMALE)) && item.getYoungMumGroup() != null && item.getYoungMumGroup().equals(YesNo.YES) && (item.getDateOfBirth() != null && item.getAge() <= 10)) {
                errors.rejectValue("youngMumGroup", "female.too.young");
            }
            if (item.getCat() != null && item.getCat().equals(YesNo.YES) && (item.getDateOfBirth() != null && item.getAge() <= 10)) {
                errors.rejectValue("youngMumGroup", "cat.too.young");
            }
        }
    }

    @Override
    public void validate(Object o, Errors errors) {

    }

    public void validateHeu(Object o, Errors errors) {
        PatientHeuDTO item = (PatientHeuDTO) o;
        if (item.getMotherOfHeu() == null) {
            errors.rejectValue("motherOfHeu", "field.empty");
        }
    }

    public void validateAll(Object o, Errors errors) {
        validatePatientHivAndHealth(o, errors);
        validatePatientEducationAndZvandiriDetails(o, errors);
        validatePatientAddress(o, errors);
        validatePatientContactDetails(o, errors);
        validatePatientDemographic(o, errors);

        if (errors.hasErrors()) {
            User user = userService.getCurrentUser();
            System.err.println(" *** UserName : " + user.getUserName() + ", FirstName : " + user.getFirstName() + ", LastName : " + user.getLastName() +
                    ", District : " + user.getDistrict() + ", Province : " + user.getProvince() + "\n" + errors);
        }
    }
}
