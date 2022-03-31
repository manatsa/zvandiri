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
package zw.org.zvandiri.portal.web.controller.process;

import java.util.Map;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.User;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.DatePropertyService;
import zw.org.zvandiri.business.service.UserService;

/**
 * @author Judge Muzinda
 */
@Repository("sysPropService")
public class AppPropServiceImpl implements AppPropService {

    @Resource
    private UserService userService;
    @Resource
    private DatePropertyService datePropertyService;

    @Override
    public User getUserName() {
        return userService.getCurrentUser();
    }

    @Override
    public Map<String, String> getDateYearRanges() {
        return datePropertyService.getAllYearRanges();
    }

    @Override
    public String processTitle(String name) {
        return APP_PREFIX + name;
    }

    @Override
    public Boolean getHeu(Patient patient) {
        return patient.getHei().equals(YesNo.YES);
    }

}