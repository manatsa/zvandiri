/*
 * Copyright 2018 tasu.
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

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import zw.org.zvandiri.business.domain.TbIpt;
import zw.org.zvandiri.business.domain.util.YesNo;

import java.util.Date;

/**
 *
 * @author manatsachinyeruse@gmail.com
 */
@Component
public class TbScreeningValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return TbIpt.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "screenedForTb", "field.empty");
        TbIpt item = (TbIpt) o;
        if (item.getScreenedForTb() != null && item.getScreenedForTb().equals(YesNo.YES)) {
            ValidationUtils.rejectIfEmpty(errors, "dateScreened", "field.empty");
            ValidationUtils.rejectIfEmpty(errors, "identifiedWithTb", "field.empty");
            ValidationUtils.rejectIfEmpty(errors, "eligibleForIpt", "field.empty");
            ValidationUtils.rejectIfEmpty(errors, "onTBTreatment", "field.empty");
            ValidationUtils.rejectIfEmpty(errors, "onIpt", "field.empty");
            ValidationUtils.rejectIfEmpty(errors, "startedOnIpt", "field.empty");
            //completion.date.before.start.date
        }else{
            item.setIdentifiedWithTb(null);
            item.setEligibleForIpt(null);
            item.setScreenedByHcw(null);
            item.setOnTBTreatment(null);
            item.setStartedOnIpt(null);
            item.setOnIpt(null);
            item.setDateCompletedIpt(null);
            item.setDateStartedIpt(null);
            item.setDateScreened(null);
            item.setDateCompletedOnIpt(null);
            item.setDateStartedIpt(null);
            item.setDateCompletedTreatment(null);
            item.setDateStartedTreatment(null);
            item.setEligibleForIpt(null);
            item.setReferredForIpt(null);
            item.setReferredForInvestigation(null);
            item.setTbSymptoms(null);
            item.setDateStartedOnIpt(null);
        }
        if (item.getDateScreened() != null && item.getDateScreened().after(new Date()) ) {
            errors.rejectValue("dateScreened","date.to.be.past");
        }
        /*if(item.getDateScreened()!=null && item.getDateStartedTreatment()!=null && item.getDateStartedTreatment().after(item.getDateScreened())){
            errors.rejectValue("dateStartedTreatment","dates.after.date.screened");
        }*/
        /*if(item.getDateScreened()!=null && item.getDateCompletedTreatment()!=null && item.getDateCompletedTreatment().after(item.getDateScreened())){
            errors.rejectValue("dateStartedTreatment","dates.after.date.screened");
        }*/
        /*if(item.getDateScreened()!=null && item.getDateStartedIpt()!=null && item.getDateStartedIpt().after(item.getDateScreened())){
            errors.rejectValue("dateStartedIpt","dates.after.date.screened");
        }*/
       /* if(item.getDateScreened()!=null && item.getDateStartedOnIpt()!=null && item.getDateStartedOnIpt().before(item.getDateScreened())){
            errors.rejectValue("dateStartedOnIpt","dates.after.date.screened");
        }*/
        if (item.getIdentifiedWithTb() != null && item.getIdentifiedWithTb().equals(YesNo.YES)) {
            ValidationUtils.rejectIfEmpty(errors, "tbSymptoms", "field.empty");
            ValidationUtils.rejectIfEmpty(errors,"referredForInvestigation","field.empty");
            ValidationUtils.rejectIfEmpty(errors, "screenedByHcw", "field.empty");
        }
        if(item.getEligibleForIpt()!=null && item.getEligibleForIpt().equals(YesNo.YES)){
            ValidationUtils.rejectIfEmpty(errors,"referredForIpt","field.empty");
        }
        if(item.getScreenedByHcw()!=null && item.getScreenedByHcw().equals(YesNo.YES)){
            ValidationUtils.rejectIfEmpty(errors,"identifiedWithTbByHcw","field.empty");
        }

        if(item.getOnTBTreatment()!=null && item.getOnTBTreatment().equals(YesNo.YES)){
            ValidationUtils.rejectIfEmpty(errors,"dateStartedTreatment","field.empty");
            if(item.getDateStartedTreatment()!=null && item.getDateStartedTreatment().after(new Date())){
                errors.rejectValue("dateStartedTreatment","date.to.be.past");
            }
            /*if(item.getDateCompletedTreatment()!=null && item.getDateCompletedTreatment().after(new Date())){
                errors.rejectValue("dateCompletedTreatment","date.to.be.past");
            }*/

            if(item.getDateStartedTreatment()!=null && item.getDateCompletedTreatment()!=null && item.getDateStartedTreatment().after(item.getDateCompletedTreatment())){
                errors.rejectValue("dateCompletedTreatment","completion.date.before.start.date");
            }
        }

        if (item.getOnIpt() != null && item.getOnIpt().equals(YesNo.YES)) {
            ValidationUtils.rejectIfEmpty(errors, "dateStartedIpt", "field.empty");
            if(item.getDateStartedIpt()!=null && item.getDateStartedIpt().after(new Date())){
                errors.rejectValue("dateStartedIpt","date.to.be.past");
            }
            if(item.getDateCompletedIpt()!=null && item.getDateCompletedIpt().after(new Date())){
                errors.rejectValue("dateCompletedIpt","date.to.be.past");
            }
            if(item.getDateStartedIpt()!=null && item.getDateCompletedIpt()!=null && item.getDateStartedIpt().after(item.getDateCompletedIpt())){
                errors.rejectValue("dateCompletedIpt","completion.date.before.start.date");
            }
        }

        if (item.getStartedOnIpt() != null && item.getStartedOnIpt().equals(YesNo.YES)) {
            ValidationUtils.rejectIfEmpty(errors, "dateStartedOnIpt", "field.empty");
            if(item.getDateStartedIpt()!=null && item.getDateStartedIpt().after(new Date())){
                errors.rejectValue("dateStartedOnIpt","date.to.be.past");
            }
            /*if(item.getDateCompletedOnIpt()!=null && item.getDateCompletedOnIpt().after(new Date())){
                errors.rejectValue("dateCompletedOnIpt","date.to.be.past");
            }*/
            if(item.getDateStartedOnIpt()!=null && item.getDateCompletedOnIpt()!=null && item.getDateStartedOnIpt().after(item.getDateCompletedOnIpt())){
                errors.rejectValue("dateCompletedOnIpt","completion.date.before.start.date");
            }
        }

        System.err.println(errors);
    }
}
