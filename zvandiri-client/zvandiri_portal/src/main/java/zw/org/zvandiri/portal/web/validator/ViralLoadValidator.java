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
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import zw.org.zvandiri.business.domain.InvestigationTest;
import zw.org.zvandiri.business.domain.User;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.UserService;

import javax.annotation.Resource;

/**
 *
 * @author manatsachinyeruse@gmail.com
 */
@Component
public class ViralLoadValidator implements Validator {

    @Resource
    UserService userService;

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(InvestigationTest.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "testDone", "field.empty");
        InvestigationTest item = (InvestigationTest) o;

        if(item.getTestDone()!=null && item.getTestDone().equals(YesNo.YES))
        {
            ValidationUtils.rejectIfEmpty(errors,"dateTaken", "field.empty");
            ValidationUtils.rejectIfEmpty(errors,"testType", "field.empty");
            ValidationUtils.rejectIfEmpty(errors,"source", "field.empty");
            ValidationUtils.rejectIfEmpty(errors,"nextTestDate", "field.empty");
            ValidationUtils.rejectIfEmpty(errors,"haveResult", "field.empty");

        }

        if(item.getHaveResult()!=null && item.getHaveResult().equals(YesNo.YES)) {
            if (item.getResult() != null && item.getTnd() != null && item.getTnd() != "") {
                errors.rejectValue("result", "tnd.viralload.both");
                errors.rejectValue("tnd", "tnd.viralload.both");
            }
            if(item.getResult()==null && (item.getTnd()==null || item.getTnd()=="")){
                errors.rejectValue("result", "tnd.viralload.missing");
                errors.rejectValue("tnd", "tnd.viralload.missing");
            }
        }


            if(item.getNextTestDate() != null && item.getNextTestDate().before(item.getDateTaken())){
                errors.rejectValue("nextTestDate","date.to.be.future");
            }

        if (item.getDateTaken() != null && item.getDateTaken().after(new Date())) {
            errors.rejectValue("dateTaken", "date.to.be.past");
        }
//        if (item.getDateTaken() != null && item.getPatient().getDateOfBirth() != null && item.getDateTaken().before(item.getPatient().getDateOfBirth())) {
//            errors.rejectValue("dateTaken", "date.beforebirth");
//        }

        if(errors.hasErrors()){
            User user=userService.getCurrentUser();
            System.err.println(" *** UserName : "+user.getUserName()+", FirstName : "+user.getFirstName()+", LastName : "+user.getLastName()+
                    ", District : "+user.getDistrict()+", Province : "+user.getProvince()+"\n"+errors);
        }
    }
}
