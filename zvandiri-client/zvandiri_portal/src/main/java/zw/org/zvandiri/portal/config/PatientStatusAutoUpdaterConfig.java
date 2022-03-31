/*
 * Copyright 2017 Judge Muzinda.
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
package zw.org.zvandiri.portal.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import zw.org.zvandiri.business.domain.util.PatientChangeEvent;
import zw.org.zvandiri.business.service.PatientReportService;
import zw.org.zvandiri.business.service.PatientService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;

/**
 * @author Judge Muzinda
 */
@Configuration
public class PatientStatusAutoUpdaterConfig {

    private static final String SCHEDULE_EXPRESSION = "0 45 20-23 * * SUN";

    @Resource
    private PatientReportService patientReportService;
    @Resource
    private PatientService patientService;

    @Scheduled(cron = SCHEDULE_EXPRESSION)
    public void autoGraduatePatientsAbove24() {

        SearchDTO dto = new SearchDTO();
        dto.setStatus(PatientChangeEvent.ACTIVE);
        dto.setEndDate(DateUtil.getDateDiffDate(-14, DateUtil.getDateFromAge(24)));
        dto.setStartDate(DateUtil.getDateFromAge(45));
        patientService.updatePatientStatus(patientReportService.getPatientAboutToGraduateList(dto));
    }
}
