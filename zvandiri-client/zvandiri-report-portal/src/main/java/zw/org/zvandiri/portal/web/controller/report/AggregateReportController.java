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
package zw.org.zvandiri.portal.web.controller.report;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.util.PatientChangeEvent;
import zw.org.zvandiri.business.util.dto.SearchDTO;

import static zw.org.zvandiri.portal.util.Graph_Prop.*;

import zw.org.zvandiri.portal.web.controller.BaseController;

import static zw.org.zvandiri.portal.web.controller.IAppTitle.APP_PREFIX;

import zw.org.zvandiri.report.api.ChartModelItem;
import zw.org.zvandiri.report.api.service.AggregateVisualReportService;
import zw.org.zvandiri.report.api.service.ContactLevelOfCareReportService;
import zw.org.zvandiri.report.api.service.PatientReportAPIService;

/**
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/report/aggregate")
public class AggregateReportController extends BaseController {

    @Resource
    private PatientReportAPIService patientReportAPIService;
    @Resource
    private AggregateVisualReportService aggregateVisualReportService;
    @Resource
    private ContactLevelOfCareReportService contactLevelOfCareReportService;

    @RequestMapping(value = "/patient-age-group-distribution")
    public String getPatientAgeGroup(ModelMap model, SearchDTO dto) {
        dto = getUserLevelObjectState(dto);
        model.addAttribute("pageTitle", APP_PREFIX + "Patient Age Group Distribution");
        model.addAttribute("item", dto);
        model.addAttribute("graphUrl", "/patient-age-group-distribution/pie-chart" + dto.getQueryString(dto.getInstance(dto)));
        return "report/nationalTrend";
    }

    @RequestMapping(value = "/patient-age-group-distribution/pie-chart", method = RequestMethod.GET)
    public void displayPatientAgeGroup(HttpServletResponse response, SearchDTO dto) {
        response.setContentType("image/png");
        JFreeChart barGraph = null;
        try {
            dto.setStatus(PatientChangeEvent.ACTIVE);
            barGraph = aggregateVisualReportService.getDefaultPieChart(new ChartModelItem("Patient Distribution By Age Group", "", ""), patientReportAPIService.getPieDefaultData(dto), "Counts");
            ChartUtilities.writeChartAsPNG(response.getOutputStream(), barGraph, GRAPH_WIDTH, GRAPH_HEIGHT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/contact-distribution-past-six-months/bar-graph", method = RequestMethod.GET)
    public void displayChart(HttpServletResponse response, SearchDTO dto) {
        response.setContentType("image/png");
        JFreeChart barGraph = null;
        try {
            barGraph = aggregateVisualReportService.getDashReport(new ChartModelItem("Contact Distribution Past 6 Months", "Months", "Number", 1000.0, true), contactLevelOfCareReportService.getPeriodRange(dto.getInstance(dto)), "Counts");
            ChartUtilities.writeChartAsPNG(response.getOutputStream(), barGraph, 540, 320);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/contact-trend-by-care-level/trend", method = RequestMethod.GET)
    public void displayTrend(HttpServletResponse response, SearchDTO dto) {
        response.setContentType("image/png");
        JFreeChart barGraph = null;
        try {
            barGraph = aggregateVisualReportService.getDefaultTrend(new ChartModelItem("Contact Trends By Care Level", "Number", "Months", 150, true), contactLevelOfCareReportService.getTrendReport(dto.getInstance(dto)), "Stable");
            ChartUtilities.writeChartAsPNG(response.getOutputStream(), barGraph, 540, 320);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}