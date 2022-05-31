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
package zw.org.zvandiri.portal.web.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import zw.org.zvandiri.business.domain.util.PatientChangeEvent;
import zw.org.zvandiri.business.service.SettingsService;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.report.api.ChartModelItem;
import zw.org.zvandiri.report.api.service.AggregateVisualReportService;
import zw.org.zvandiri.report.api.service.BasicNameNumberReportService;
import zw.org.zvandiri.report.api.service.ContactLevelOfCareReportService;
import zw.org.zvandiri.report.api.service.PatientReportAPIService;
import zw.org.zvandiri.report.api.service.ReferralReportAPIService;

/**
 *
 * @author Judge Muzinda
 */
@Controller
public class DashboardController extends BaseController {

    @Resource
    private PatientReportAPIService patientReportAPIService;
    @Resource
    private AggregateVisualReportService aggregateVisualReportService;
    @Resource
    private ReferralReportAPIService referralReportAPIService;
    @Resource
    private ContactLevelOfCareReportService contactLevelOfCareReportService;
    @Resource
    private BasicNameNumberReportService basicNameNumberReportService;  
    @Resource
    private SettingsService settingsService;
    
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String getIndex(ModelMap model) {
        try{
            SearchDTO dto = getUserLevelObjectState(new SearchDTO());
            model.addAttribute("pageTitle", APP_PREFIX + "HOME");
            model.addAttribute("patientStat", basicNameNumberReportService.getHomeStats(dto.getInstance(dto)));

            return "index";
        }catch (Exception e){
            model.addAttribute("pageTitle", APP_PREFIX + "HOME");
            return "index";
        }

    }

    
}