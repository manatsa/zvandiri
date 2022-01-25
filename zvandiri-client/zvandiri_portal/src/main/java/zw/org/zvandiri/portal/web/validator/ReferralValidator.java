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
import zw.org.zvandiri.business.domain.User;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.UserService;

import javax.annotation.Resource;

/**
 *
 * @author manatsachinyeruse@gmail.com
 */
@Component
public class ReferralValidator implements Validator {

    @Resource
    UserService userService;
    @Override
    public boolean supports(Class<?> type) {
        return type.equals(Referral.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "hasReferred", "field.empty");
        Referral item = (Referral) o;
        if(item.getHasReferred()!=null && item.getHasReferred().equals(YesNo.YES)){
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
            if (item.getDateAttended() != null) {
                ValidationUtils.rejectIfEmpty(errors, "attendingOfficer", "field.empty");
                ValidationUtils.rejectIfEmpty(errors, "designation", "field.empty");
                if (item.getActionTaken() == null) {
                    errors.rejectValue("actionTaken", "field.empty");
                }
            }
        }else{
            item.setReferralDate(null);
            item.setAttendingOfficer(null);
            item.setDateAttended(null);
            item.setExpectedVisitDate(null);
            item.setHivStiServicesReq(null);
            item.setHivStiServicesAvailed(null);
            item.setLaboratoryAvailed(null);
            item.setLaboratoryReq(null);
            item.setLegalAvailed(null);
            item.setLegalReq(null);
            item.setOiArtAvailed(null);
            item.setOiArtReq(null);
            item.setPsychAvailed(null);
            item.setPsychReq(null);
            item.setSrhAvailed(null);
            item.setSrhReq(null);
            item.setTbAvailed(null);
            item.setTbReq(null);
            item.setOrganisation(null);
            item.setDesignation(null);
            item.setActionTaken(null);
        }

        if(errors.hasErrors()){
            User user=userService.getCurrentUser();
            System.err.println(" *** UserName : "+user.getUserName()+", FirstName : "+user.getFirstName()+", LastName : "+user.getLastName()+
                    ", District : "+user.getDistrict()+", Province : "+user.getProvince()+"\n"+errors);
        }

    }
}
