/*
 * Copyright 2017 jmuzinda.
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
package zw.org.zvandiri.portal.web.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import zw.org.zvandiri.business.domain.util.NumberOfANCVisit;

/**
 *
 * @author jmuzinda
 */
public class NumberOfANCVisitConverter implements Converter<String, NumberOfANCVisit> {

    @Override
    public NumberOfANCVisit convert(String s) {
        if(StringUtils.isEmpty(s)) return null;
        return NumberOfANCVisit.get(Integer.valueOf(s));
    }
    
}
