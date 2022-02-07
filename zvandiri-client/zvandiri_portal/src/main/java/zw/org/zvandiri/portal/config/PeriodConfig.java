/*
 * Copyright 2015 Judge Muzinda.
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

import java.util.Date;
import javax.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import zw.org.zvandiri.business.service.PeriodService;
import zw.org.zvandiri.business.util.DateUtil;

/**
 *
 * @author Judge Muzinda
 */
@Configuration
public class PeriodConfig {
    
    private static final String SCHEDULE_EXPRESSION = "0 45 20-23 * * MON-FRI";
    
    @Resource
    private PeriodService periodService;

    @Scheduled(cron = SCHEDULE_EXPRESSION)
    public void updateperiod(){
        if(periodService.getByStartDate(DateUtil.getPeriodStart(new Date())) == null){
            periodService.save(periodService.constructPeriod(new Date()));
        }
    }
}