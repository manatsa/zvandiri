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

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import zw.org.zvandiri.business.domain.MentalHealthScreening;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.MentalHealthScreeningService;

import java.util.Date;

/**
 *
 * @author manatsachinyeruse@gmail.com
 */
@Component
public class MentalHealthScreeningValidator implements Validator{
    
    @Resource
    private MentalHealthScreeningService service;
    
    @Override
    public boolean supports(Class<?> type){
        return type.equals(MentalHealthScreening.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "screenedForMentalHealth", "field.empty");
        MentalHealthScreening item = (MentalHealthScreening) o;
        if(item.getScreenedForMentalHealth() != null && item.getScreenedForMentalHealth().equals(YesNo.YES)) {
            ValidationUtils.rejectIfEmpty(errors, "risk", "field.empty");
            ValidationUtils.rejectIfEmpty(errors, "support", "field.empty");
            ValidationUtils.rejectIfEmpty(errors, "dateScreened", "field.empty");

            if(item.getDateScreened()!=null && item.getDateScreened().after(new Date())){
                errors.rejectValue("dateScreened","date.to.be.past");
            }

            if(item.getRisk() != null && item.getRisk().equals(YesNo.YES)) {
                if(item.getIdentifiedRisks() == null) {
                    ValidationUtils.rejectIfEmpty(errors, "identifiedRisks", "item.select.one");
                }

            }
            if(item.getSupport()!= null && item.getSupport().equals(YesNo.YES)) {
                if(item.getSupports() == null) {
                    ValidationUtils.rejectIfEmpty(errors, "supports", "item.select.one");
                }
            }

        }else{
            item.setIdentifiedRisks(null);
            item.setDateScreened(null);
            item.setRisk(null);
            item.setSupport(null);
            item.setSupports(null);
        }
    }



    
}
