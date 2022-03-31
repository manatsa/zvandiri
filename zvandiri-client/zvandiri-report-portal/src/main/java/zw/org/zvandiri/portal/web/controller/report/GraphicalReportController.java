/*
 * Copyright 2017 User.
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.util.PatientChangeEvent;
import zw.org.zvandiri.business.domain.util.Result;
import zw.org.zvandiri.business.domain.util.TestType;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.service.SettingsService;
import zw.org.zvandiri.business.service.TbScreeningService;
import zw.org.zvandiri.business.util.dto.SearchDTO;

import static zw.org.zvandiri.portal.util.Graph_Prop.*;

import zw.org.zvandiri.portal.web.controller.BaseController;

import static zw.org.zvandiri.portal.web.controller.IAppTitle.APP_PREFIX;

import zw.org.zvandiri.report.api.ChartModelItem;
import zw.org.zvandiri.report.api.service.AggregateVisualReportService;
import zw.org.zvandiri.report.api.service.ContactLevelOfCareReportService;
import zw.org.zvandiri.report.api.service.PatientContactReportService;
import zw.org.zvandiri.report.api.service.PatientGenderReportService;
import zw.org.zvandiri.report.api.service.PatientReportAPIService;
import zw.org.zvandiri.report.api.service.PatientStatusReportService;
import zw.org.zvandiri.report.api.service.PatientViralLoadReportService;
import zw.org.zvandiri.report.api.service.ReferralReportAPIService;
import zw.org.zvandiri.report.api.service.TbScreeningReportReportService;

/**
 * @author User
 */
@Controller
@RequestMapping("/report/graphs")
public class GraphicalReportController extends BaseController {

    @Resource
    private ReferralReportAPIService referralReportAPIService;
    @Resource
    private AggregateVisualReportService aggregateVisualReportService;
    @Resource
    private PatientGenderReportService patientGenderReportService;
    @Resource
    private PatientStatusReportService patientStatusReportService;
    @Resource
    private ContactLevelOfCareReportService contactLevelOfCareReportService;
    @Resource
    private PatientReportAPIService patientReportAPIService;
    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private FacilityService facilityService;
    @Resource
    private SettingsService settingsService;
    @Resource
    private PatientContactReportService patientContactReportService;
    @Resource
    private PatientViralLoadReportService patientViralLoadReportService;
    @Resource
    private TbScreeningReportReportService tbScreeningReportReportService;
    @Resource
    private TbScreeningService screeningService;

    public void setUpModel(ModelMap map, SearchDTO dto) {
        dto = getUserLevelObjectState(dto);
        map.addAttribute("item", dto.getInstance(dto));
        map.addAttribute("provinces", provinceService.getAll());
        if (dto.getProvince() != null) {
            map.addAttribute("districts", districtService.getDistrictByProvince(dto.getProvince()));
        }
        if (dto.getDistrict() != null) {
            map.addAttribute("facilities", facilityService.getOptByDistrict(dto.getDistrict()));
        }
    }

    @RequestMapping(value = "/referral-distribution", method = RequestMethod.GET)
    public String showReferralChart(ModelMap map) {
        SearchDTO dto = new SearchDTO();
        setUpModel(map, dto);
        map.addAttribute("pageTitle", APP_PREFIX + "Number Of External Referrals Past 6 Months");
        map.addAttribute("report", "/referral-distribution-past-six-months/bar-graph" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/referral-distribution", method = RequestMethod.POST)
    public String showReferralChartPost(ModelMap map, @ModelAttribute("item") SearchDTO dto) {
        setUpModel(map, dto);
        map.addAttribute("pageTitle", APP_PREFIX + "Number Of External Referrals Past 6 Months");
        map.addAttribute("report", "/referral-distribution-past-six-months/bar-graph" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/referral-distribution-past-six-months/bar-graph", method = RequestMethod.GET)
    public void displayReferralChart(HttpServletResponse response, SearchDTO dto) {
        response.setContentType("image/png");
        JFreeChart barGraph = null;
        try {
            barGraph = aggregateVisualReportService.getDashReport(new ChartModelItem("", "Months", "Total", 1000.0, true), referralReportAPIService.getPeriodRange(dto), "Counts");
            ChartUtilities.writeChartAsPNG(response.getOutputStream(), barGraph, GRAPH_WIDTH, GRAPH_HEIGHT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/patient-gender-distribution", method = RequestMethod.GET)
    public String showPatientGenderChart(ModelMap map) {
        SearchDTO dto = new SearchDTO();
        setUpModel(map, dto);
        map.addAttribute("pageTitle", APP_PREFIX + "Distribution Of Patients By Gender");
        map.addAttribute("report", "/patient-gender-distribution/pie-chart" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/patient-gender-distribution", method = RequestMethod.POST)
    public String showPatientGenderChartPost(ModelMap map, @ModelAttribute("item") SearchDTO dto) {
        setUpModel(map, dto);
        map.addAttribute("pageTitle", APP_PREFIX + "Distribution Of Patients By Gender");
        map.addAttribute("report", "/patient-gender-distribution/pie-chart" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/patient-gender-distribution/pie-chart", method = RequestMethod.GET)
    public void displayPatientGenderPieChart(HttpServletResponse response, SearchDTO dto) {
        response.setContentType("image/png");
        JFreeChart barGraph = null;
        try {
            dto.setStatus(PatientChangeEvent.ACTIVE);
            barGraph = aggregateVisualReportService.getDefaultPieChart(new ChartModelItem("", "", ""), patientGenderReportService.getDefaultPieData(dto), "Counts");
            ChartUtilities.writeChartAsPNG(response.getOutputStream(), barGraph, GRAPH_WIDTH, GRAPH_HEIGHT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/patient-status-distribution", method = RequestMethod.GET)
    public String showPatientStatusChart(ModelMap map) {
        SearchDTO dto = new SearchDTO();
        setUpModel(map, dto);
        map.addAttribute("pageTitle", APP_PREFIX + "Distribution Of Patients By Status");
        map.addAttribute("report", "/patient-status-distribution/pie-chart" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/patient-status-distribution", method = RequestMethod.POST)
    public String showPatientStatusChartPost(ModelMap map, @ModelAttribute("item") SearchDTO dto) {
        setUpModel(map, dto);
        map.addAttribute("pageTitle", APP_PREFIX + "Distribution Of Patients By Status");
        map.addAttribute("report", "/patient-status-distribution/pie-chart" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/patient-status-distribution/pie-chart", method = RequestMethod.GET)
    public void displayPatientStatusPieChart(HttpServletResponse response, SearchDTO dto) {
        response.setContentType("image/png");
        JFreeChart barGraph = null;
        try {
            barGraph = aggregateVisualReportService.getDefaultPieChart(new ChartModelItem("", "", ""), patientStatusReportService.getDefaultPieData(dto), "Counts");
            ChartUtilities.writeChartAsPNG(response.getOutputStream(), barGraph, GRAPH_WIDTH, GRAPH_HEIGHT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/contact-level-of-care", method = RequestMethod.GET)
    public String showContactLevelOfCareChart(ModelMap map) {
        SearchDTO dto = new SearchDTO();
        setUpModel(map, dto);
        map.addAttribute("pageTitle", APP_PREFIX + "Trends Of Contacts By Care Level");
        map.addAttribute("report", "/contact-trend-by-care-level/trend" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/contact-level-of-care", method = RequestMethod.POST)
    public String showContactLevelOfCareChartPost(ModelMap map, @ModelAttribute("item") SearchDTO dto) {
        setUpModel(map, dto);
        map.addAttribute("pageTitle", APP_PREFIX + "Trends Of Contacts By Care Level");
        map.addAttribute("report", "/contact-trend-by-care-level/trend" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/contact-trend-by-care-level/trend", method = RequestMethod.GET)
    public void displayTrend(HttpServletResponse response, SearchDTO dto) {
        response.setContentType("image/png");
        JFreeChart barGraph = null;
        Integer maxItems = settingsService.getItem().getMaxNumContactIndex();
        try {
            barGraph = aggregateVisualReportService.getDefaultTrend(new ChartModelItem("", "", "Quarters", maxItems, true), contactLevelOfCareReportService.getTrendReport(dto.getInstance(dto)), "Stable");
            ChartUtilities.writeChartAsPNG(response.getOutputStream(), barGraph, GRAPH_WIDTH, GRAPH_HEIGHT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/contact-distribution", method = RequestMethod.GET)
    public String showContactDistributionChart(ModelMap map) {
        SearchDTO dto = new SearchDTO();
        setUpModel(map, dto);
        map.addAttribute("pageTitle", APP_PREFIX + "Number Of Contacts Past 6 Months");
        map.addAttribute("report", "/contact-distribution-past-six-months/bar-graph" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/contact-distribution", method = RequestMethod.POST)
    public String showContactDistributionChartPost(ModelMap map, @ModelAttribute("item") SearchDTO dto) {
        setUpModel(map, dto);
        map.addAttribute("pageTitle", APP_PREFIX + "Number Of Contacts Past 6 Months");
        map.addAttribute("report", "/contact-distribution-past-six-months/bar-graph" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/contact-distribution-past-six-months/bar-graph", method = RequestMethod.GET)
    public void displayChart(HttpServletResponse response, SearchDTO dto) {
        response.setContentType("image/png");
        JFreeChart barGraph = null;
        Integer maxItems = settingsService.getItem().getMaxNumContactIndex();
        try {
            barGraph = aggregateVisualReportService.getDashReport(new ChartModelItem("", "Quarters", "Number", maxItems, true), contactLevelOfCareReportService.getPeriodRange(dto.getInstance(dto)), "Counts");
            ChartUtilities.writeChartAsPNG(response.getOutputStream(), barGraph, GRAPH_WIDTH, GRAPH_HEIGHT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/patient-age-group-distribution", method = RequestMethod.GET)
    public String showPatientAgeGroupDistributionChart(ModelMap map) {
        SearchDTO dto = new SearchDTO();
        setUpModel(map, dto);
        map.addAttribute("pageTitle", APP_PREFIX + "Distribution Of Patients By Age Group");
        map.addAttribute("report", "/patient-age-group-distribution/pie-chart" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/patient-age-group-distribution", method = RequestMethod.POST)
    public String showPatientAgeGroupDistributionChartPost(ModelMap map, @ModelAttribute("item") SearchDTO dto) {
        setUpModel(map, dto);
        map.addAttribute("pageTitle", APP_PREFIX + "Distribution Of Patients By Age Group");
        map.addAttribute("report", "/patient-age-group-distribution/pie-chart" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/patient-age-group-distribution/pie-chart", method = RequestMethod.GET)
    public void displayFunctionalityGraph(HttpServletResponse response, SearchDTO dto) {
        response.setContentType("image/png");
        JFreeChart barGraph = null;
        try {
            dto.setStatus(PatientChangeEvent.ACTIVE);
            barGraph = aggregateVisualReportService.getDefaultPieChart(new ChartModelItem("", "", ""), patientReportAPIService.getPieDefaultData(dto), "Counts");
            ChartUtilities.writeChartAsPNG(response.getOutputStream(), barGraph, GRAPH_WIDTH, GRAPH_HEIGHT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/contact-care-level-distribution", method = RequestMethod.GET)
    public String showContactCareLevelChart(ModelMap map) {
        SearchDTO dto = new SearchDTO();
        setUpModel(map, dto);
        map.addAttribute("pageTitle", APP_PREFIX + "Distribution Of Contacts By Care Level");
        map.addAttribute("report", "/contact-care-level-distribution/pie-chart" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/contact-care-level-distribution", method = RequestMethod.POST)
    public String showContactCareLevelChartPost(ModelMap map, @ModelAttribute("item") SearchDTO dto) {
        setUpModel(map, dto);
        map.addAttribute("pageTitle", APP_PREFIX + "Distribution Of Contacts By Care Level");
        map.addAttribute("report", "/contact-care-level-distribution/pie-chart" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/contact-care-level-distribution/pie-chart", method = RequestMethod.GET)
    public void displayContactCareLevelPieChart(HttpServletResponse response, SearchDTO dto) {
        response.setContentType("image/png");
        JFreeChart barGraph = null;
        try {
            dto.setStatus(PatientChangeEvent.ACTIVE);
            barGraph = aggregateVisualReportService.getDefaultPieChart(new ChartModelItem("", "", ""), contactLevelOfCareReportService.getDefaultPieData(dto), "Counts");
            ChartUtilities.writeChartAsPNG(response.getOutputStream(), barGraph, GRAPH_WIDTH, GRAPH_HEIGHT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/patient-contact-distribution", method = RequestMethod.GET)
    public String showPatientContactChart(ModelMap map) {
        SearchDTO dto = new SearchDTO();
        setUpModel(map, dto);
        map.addAttribute("pageTitle", APP_PREFIX + "Distribution Of Patients By Contact");
        map.addAttribute("report", "/patient-contact-distribution/pie-chart" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/patient-contact-distribution", method = RequestMethod.POST)
    public String showPatientContactChartPost(ModelMap map, @ModelAttribute("item") SearchDTO dto) {
        setUpModel(map, dto);
        map.addAttribute("pageTitle", APP_PREFIX + "Distribution Of Patients By Contact");
        map.addAttribute("report", "/patient-contact-distribution/pie-chart" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/patient-contact-distribution/pie-chart", method = RequestMethod.GET)
    public void displayPatientContactPieChart(HttpServletResponse response, SearchDTO dto) {
        response.setContentType("image/png");
        JFreeChart barGraph = null;
        try {
            dto.setStatus(PatientChangeEvent.ACTIVE);
            barGraph = aggregateVisualReportService.getDefaultPieChart(new ChartModelItem("", "", ""), patientContactReportService.getDefaultPieData(dto), "Counts");
            ChartUtilities.writeChartAsPNG(response.getOutputStream(), barGraph, GRAPH_WIDTH, GRAPH_HEIGHT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/patient-viral-load-distribution", method = RequestMethod.GET)
    public String showPatientViralLoadChart(ModelMap map) {
        SearchDTO dto = new SearchDTO();
        setUpModel(map, dto);
        map.addAttribute("pageTitle", APP_PREFIX + "Distribution Of Patients By Viral Load");
        map.addAttribute("report", "/patient-viral-load-distribution/pie-chart" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/patient-viral-load-distribution", method = RequestMethod.POST)
    public String showPatientViralLoadChartPost(ModelMap map, @ModelAttribute("item") SearchDTO dto) {
        setUpModel(map, dto);
        dto.setTestType(TestType.VIRAL_LOAD);
        map.addAttribute("pageTitle", APP_PREFIX + "Distribution Of Patients By Viral Load");
        map.addAttribute("report", "/patient-viral-load-distribution/pie-chart" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/patient-viral-load-distribution/pie-chart", method = RequestMethod.GET)
    public void displayPatientViralLoadPieChart(HttpServletResponse response, SearchDTO dto) {
        response.setContentType("image/png");
        JFreeChart barGraph = null;
        try {
            barGraph = aggregateVisualReportService.getDefaultPieChart(new ChartModelItem("", "", ""), patientViralLoadReportService.getDefaultPieData(dto), "Counts");
            ChartUtilities.writeChartAsPNG(response.getOutputStream(), barGraph, GRAPH_WIDTH, GRAPH_HEIGHT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/patient-viral-suppression-distribution", method = RequestMethod.GET)
    public String showPatientViralSuppressionChart(ModelMap map) {
        SearchDTO dto = new SearchDTO();
        setUpModel(map, dto);
        dto.setTestType(TestType.VIRAL_LOAD);
        map.addAttribute("pageTitle", APP_PREFIX + "Distribution Of Patients By Viral Suppression");
        map.addAttribute("report", "/patient-viral-suppression-distribution/pie-chart" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/patient-viral-suppression-distribution", method = RequestMethod.POST)
    public String showPatientViralSuppressionChartPost(ModelMap map, @ModelAttribute("item") SearchDTO dto) {
        setUpModel(map, dto);
        map.addAttribute("pageTitle", APP_PREFIX + "Distribution Of Patients By Viral Suppression");
        map.addAttribute("report", "/patient-viral-suppression-distribution/pie-chart" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/patient-viral-suppression-distribution/pie-chart", method = RequestMethod.GET)
    public void displayPatientViralSuppressionPieChart(HttpServletResponse response, SearchDTO dto) {
        response.setContentType("image/png");
        JFreeChart barGraph = null;
        try {
            barGraph = aggregateVisualReportService.getDefaultPieChart(new ChartModelItem("", "", ""), patientViralLoadReportService.getViralSuppressionPieData(dto), "Counts");
            ChartUtilities.writeChartAsPNG(response.getOutputStream(), barGraph, GRAPH_WIDTH, GRAPH_HEIGHT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/tb-screening-treatment-status", method = RequestMethod.GET)
    public String showTbScreeningTreatmentStatusChart(ModelMap map) {
        SearchDTO dto = new SearchDTO();
        setUpModel(map, dto);
        map.addAttribute("pageTitle", APP_PREFIX + "Trends Of TB Screening By Treatment Status");
        map.addAttribute("report", "/tb-screening-treatment-status/trend" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/tb-screening-treatment-status", method = RequestMethod.POST)
    public String showTbScreeningTreatmentStatusPost(ModelMap map, @ModelAttribute("item") SearchDTO dto) {
        setUpModel(map, dto);
        map.addAttribute("pageTitle", APP_PREFIX + "Trends Of TB Screening By Treatment Status");
        map.addAttribute("report", "/tb-screening-treatment-status/trend" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/tb-screening-treatment-status/trend", method = RequestMethod.GET)
    public void displayTreatmentStatusTrend(HttpServletResponse response, SearchDTO dto) {
        response.setContentType("image/png");
        JFreeChart barGraph = null;
        Integer maxItems = screeningService.getAll().size();
        try {
            barGraph = aggregateVisualReportService.getTbStatusTrend(new ChartModelItem("", "", "Quarters", maxItems, true), tbScreeningReportReportService.getTbTreatmentStatusTrendReport(dto.getInstance(dto)), "Active On Tb Treatment");
            ChartUtilities.writeChartAsPNG(response.getOutputStream(), barGraph, GRAPH_WIDTH, GRAPH_HEIGHT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/tb-screening-treatment-outcome", method = RequestMethod.GET)
    public String showTbScreeningTreatmentOutcomeChart(ModelMap map) {
        SearchDTO dto = new SearchDTO();
        setUpModel(map, dto);
        map.addAttribute("pageTitle", APP_PREFIX + "Trends Of TB Screening By Treatment Outcome");
        map.addAttribute("report", "/tb-screening-treatment-outcome/trend" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/tb-screening-treatment-outcome", method = RequestMethod.POST)
    public String showTbScreeningTreatmentOutcomePost(ModelMap map, @ModelAttribute("item") SearchDTO dto) {
        setUpModel(map, dto);
        map.addAttribute("pageTitle", APP_PREFIX + "Trends Of TB Screening By Treatment Outcome");
        map.addAttribute("report", "/tb-screening-treatment-outcome/trend" + dto.getQueryString(dto.getInstance(dto)));
        return "report/graphs";
    }

    @RequestMapping(value = "/tb-screening-treatment-outcome/trend", method = RequestMethod.GET)
    public void displayTreatmentOutcomeTrend(HttpServletResponse response, SearchDTO dto) {
        response.setContentType("image/png");
        JFreeChart barGraph = null;
        Integer maxItems = screeningService.getAll().size();
        try {
            barGraph = aggregateVisualReportService.getTbOutcomeTrend(new ChartModelItem("", "", "Quarters", maxItems, true), tbScreeningReportReportService.getTbTreatmentOutcomeTrendReport(dto.getInstance(dto)), "Successful");
            ChartUtilities.writeChartAsPNG(response.getOutputStream(), barGraph, GRAPH_WIDTH, GRAPH_HEIGHT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
