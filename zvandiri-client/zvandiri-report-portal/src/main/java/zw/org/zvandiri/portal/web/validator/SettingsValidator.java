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
import zw.org.zvandiri.business.domain.Settings;
import zw.org.zvandiri.business.service.SettingsService;

/**
 *
 * @author Judge Muzinda
 */
@Component
public class SettingsValidator implements Validator {
    
    @Resource
    private SettingsService settingsService;

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(Settings.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Settings item = (Settings) o;
        ValidationUtils.rejectIfEmpty(errors, "patientMinAge", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "patientMaxAge", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "catMinAge", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "catMaxAge", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "cd4MinCount", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "viralLoadMaxCount", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "minAgeToGiveBirth", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "patientAutoExpireAfterMaxAge", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "heuMotherMaxAge", "field.empty");
        if(item.getId() == null){
            if(settingsService.getItem() != null){
                errors.rejectValue("uuid", "item.exists");
            }
        }
    }
    
}