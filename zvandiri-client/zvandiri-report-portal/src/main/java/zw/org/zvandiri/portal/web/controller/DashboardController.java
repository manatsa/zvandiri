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
import static zw.org.zvandiri.portal.web.controller.IAppTitle.APP_PREFIX;
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
            model.addAttribute("notifications", referralReportAPIService.getNotifications(dto.getInstance(dto)));
            model.addAttribute("contactLevelTrend", "/contact-trend-by-care-level/trend" + dto.getQueryString(dto.getInstance(dto)));
            model.addAttribute("patientAgeGroupDistribution", "/patient-age-group-distribution/pie-chart" + dto.getQueryString(dto.getInstance(dto)));
            model.addAttribute("contactLevelOfCareDistribution", "/contact-distribution-past-six-months/bar-graph" + dto.getQueryString(dto.getInstance(dto)));
            model.addAttribute("patientStatusDistribution", "/patient-status-distribution/pie-chart" + dto.getQueryString(dto.getInstance(dto)));
            return "index";
        }catch (Exception e){
            model.addAttribute("pageTitle", APP_PREFIX + "HOME");
            return "index";
        }

    }

    @RequestMapping(value = "/patient-age-group-distribution/pie-chart", method = RequestMethod.GET)
    public void displayFunctionalityGraph(HttpServletResponse response, SearchDTO dto) {
        response.setContentType("image/png");
        JFreeChart barGraph = null;
        try {
            dto.setStatus(PatientChangeEvent.ACTIVE);
            barGraph = aggregateVisualReportService.getDefaultPieChart(new ChartModelItem("", "", ""), patientReportAPIService.getPieDefaultData(dto), "Counts");
            ChartUtilities.writeChartAsPNG(response.getOutputStream(), barGraph, 520, 320);
        } catch (IOException ex) {
//            ex.printStackTrace();
        }
    }
    
    @RequestMapping(value = "/contact-distribution-past-six-months/bar-graph", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DATA_CLERK') or hasRole('ROLE_M_AND_E_OFFICER') or hasRole('ROLE_HOD_M_AND_E')")
    public void displayChart(HttpServletResponse response, SearchDTO dto) {
        response.setContentType("image/png");
        JFreeChart barGraph = null;
        Integer maxItems = settingsService.getItem().getMaxNumContactIndex();
        try {
            barGraph = aggregateVisualReportService.getDashReport(new ChartModelItem("", "Quarters", "Number", maxItems, true), contactLevelOfCareReportService.getPeriodRange(dto.getInstance(dto)), "Counts");
            ChartUtilities.writeChartAsPNG(response.getOutputStream(), barGraph, 540, 320);
        } catch (IOException ex) {
//            ex.printStackTrace();
        }
    }
    
    @RequestMapping(value = "/contact-trend-by-care-level/trend", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DATA_CLERK') or hasRole('ROLE_M_AND_E_OFFICER') or hasRole('ROLE_HOD_M_AND_E')")
    public void displayTrend(HttpServletResponse response, SearchDTO dto) {
        response.setContentType("image/png");
        JFreeChart barGraph = null;
        Integer maxItems = settingsService.getItem().getMaxNumContactIndex();
        try {
            barGraph = aggregateVisualReportService.getDefaultTrend(new ChartModelItem("", "", "Quarters", maxItems, true), contactLevelOfCareReportService.getTrendReport(dto.getInstance(dto)), "Stable");
            ChartUtilities.writeChartAsPNG(response.getOutputStream(), barGraph, 540, 320);
        } catch (IOException ex) {
            //ex.printStackTrace();
        }
    }
    
}