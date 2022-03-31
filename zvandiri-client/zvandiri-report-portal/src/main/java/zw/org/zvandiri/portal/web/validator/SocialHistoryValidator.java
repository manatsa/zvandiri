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
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import zw.org.zvandiri.business.domain.SocialHist;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.SocialHistService;

/**
 * @author Judge Muzinda
 */
@Component
public class SocialHistoryValidator implements Validator {

    @Resource
    private SocialHistService socialHistService;

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(SocialHist.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "liveWith", "field.empty");
        SocialHist item = (SocialHist) o;
        SocialHist old = null;
        if (item.getPatient() != null) {
            old = socialHistService.getByPatient(item.getPatient());
        }
        if (item.getAbuse() == null) {
            errors.rejectValue("abuse", "field.empty");
        }
        if (item.getAbuse() != null && item.getAbuse().equals(YesNo.YES)) {
            if (item.getDosclosure() == null) {
                errors.rejectValue("dosclosure", "field.empty");
            }
            if (item.getFeelSafe() == null) {
                errors.rejectValue("feelSafe", "field.empty");
            }
            if (item.getAbuseOutcome() == null) {
                errors.rejectValue("abuseOutcome", "field.empty");
            }
        }
        if (socialHistService.checkDuplicate(item, old)) {
            errors.rejectValue("uuid", "patient.socialhistrecord");
        }
    }
}