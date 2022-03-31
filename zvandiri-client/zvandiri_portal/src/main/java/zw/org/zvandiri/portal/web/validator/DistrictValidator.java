/*
 * Copyright 2014 Edward Zengeni.
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
import zw.org.zvandiri.business.domain.District;
import zw.org.zvandiri.business.service.DistrictService;

/**
 * @author Edward Zengeni
 */
@Component
public class DistrictValidator implements Validator {

    @Resource
    private DistrictService districtService;

    @Override
    public boolean supports(Class<?> type) {
        return District.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "field.empty");
        /**
         * @param name district name must always be unique
         */
        District current = (District) o;
        District old = null;
        if (current.getId() != null) {
            old = districtService.get(current.getId());
        }
        if (districtService.checkDuplicate(current, old)) {
            errors.rejectValue("name", "item.duplicate");
        }
        if (current.getProvince() == null) {
            errors.rejectValue("province", "field.empty");
        }
        /**
         * currently no districts with less than three characters so we start @ 4
         */
        if (current.getName() != null && !current.getName().equals("")) {
            if (current.getName().length() <= 3) {
                errors.rejectValue("name", "field.short");
            }
        }
    }
}