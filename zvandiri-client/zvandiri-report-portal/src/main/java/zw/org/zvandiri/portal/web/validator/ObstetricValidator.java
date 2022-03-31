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
import zw.org.zvandiri.business.domain.ObstercHist;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.ObstercHistService;

/**
 * @author Judge Muzinda
 */
@Component
public class ObstetricValidator implements Validator {

    @Resource
    private ObstercHistService obstercHistService;

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(ObstercHist.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ObstercHist item = (ObstercHist) o;
        ObstercHist old = null;
        if (item.getPatient() != null) {
            old = obstercHistService.getByPatient(item.getPatient());
        }
        if (item.getPregnant() == null) {
            errors.rejectValue("pregnant", "field.empty");
        }
        if (item.getPregnant() != null && item.getPregnant().equals(YesNo.YES)) {
            if (item.getChildren() == null) {
                errors.rejectValue("children", "field.empty");
            }
            if (item.getPregCurrent() == null) {
                errors.rejectValue("pregCurrent", "field.empty");
            }
            if (item.getBreafFeedingCurrent() == null) {
                errors.rejectValue("breafFeedingCurrent", "field.empty");
            }
        }
        if (item.getPregCurrent() != null && item.getPregCurrent().equals(YesNo.YES)) {
            if (item.getNumberOfANCVisit() == null) {
                errors.rejectValue("numberOfANCVisit", "field.empty");
            }
            if (item.getGestationalAge() == null) {
                errors.rejectValue("gestationalAge", "field.empty");
            }
            if (item.getArtStarted() == null) {
                errors.rejectValue("artStarted", "field.empty");
            }
        }
        if (obstercHistService.checkDuplicate(item, old)) {
            errors.rejectValue("uuid", "patient.obstetrichistrecord");
        }
    }
}
