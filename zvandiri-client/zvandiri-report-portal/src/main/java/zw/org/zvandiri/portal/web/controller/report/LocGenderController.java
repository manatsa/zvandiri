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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.util.PeriodType;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.HalfYearPeriodService;
import zw.org.zvandiri.business.service.PeriodService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.service.QuarterPeriodService;
import zw.org.zvandiri.business.service.YearPeriodService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.service.LocGenderReportService;
import zw.org.zvandiri.report.api.service.OfficeExportService;

/**
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/report/aggregate")
public class LocGenderController extends BaseController {

    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private PeriodService periodService;
    @Resource
    private OfficeExportService officeExportService;
    @Resource
    private LocGenderReportService locGenderReportService;
    @Resource
    private YearPeriodService yearPeriodService;
    @Resource
    private QuarterPeriodService quarterPeriodService;
    @Resource
    private HalfYearPeriodService halfYearPeriodService;

    public void setUpModel(ModelMap model, SearchDTO item) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "Location Gender Cross Tabulation Report");
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("periods", periodService.getAll());
        if (item.getPeriodType() == null) {
            item.setPeriod(null);
            item.setQuarterPeriod(null);
            item.setHalfYearPeriod(null);
            item.setYearPeriod(null);
            item.setPeriodType(PeriodType.MONTHLY);
        }
        if (item.getPeriodType() != null) {
            if (item.getPeriodType().equals(PeriodType.MONTHLY)) {
                model.addAttribute("periods", periodService.getAll());
                if (item.getPeriod() == null) {
                    item.setPeriod(periodService.getLatestPeriod());
                }
            } else if (item.getPeriodType().equals(PeriodType.QUARTERLY)) {
                model.addAttribute("quarters", quarterPeriodService.getAll());
                if (item.getQuarterPeriod() == null) {
                    item.setQuarterPeriod(quarterPeriodService.getLatestPeriod());
                }
            } else if (item.getPeriodType().equals(PeriodType.HALF_YEARLY)) {
                model.addAttribute("halfyears", halfYearPeriodService.getAll());
                if (item.getHalfYearPeriod() == null) {
                    item.setHalfYearPeriod(halfYearPeriodService.getLatestPeriod());
                }
            } else if (item.getPeriodType().equals(PeriodType.YEARLY)) {
                model.addAttribute("years", yearPeriodService.getAll());
                if (item.getYearPeriod() == null) {
                    item.setYearPeriod(yearPeriodService.getLatestPeriod());
                }
            }
        }
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
        }
        model.addAttribute("item", item.getInstance(item));
        model.addAttribute("excelExport", "/report/aggregate/loc-gender-healthcenter/export/excel" + item.getQueryString(item.getInstance(item)));
        model.addAttribute("items", locGenderReportService.getDefaultReport(item.getInstance(item)));
    }

    @RequestMapping(value = "/loc-gender-healthcenter", method = RequestMethod.GET)
    public String getReportIndex(ModelMap model) {
        setUpModel(model, new SearchDTO());
        return "report/table";
    }

    @RequestMapping(value = "/loc-gender-healthcenter", method = RequestMethod.POST)
    public String getReportResult(ModelMap model, @ModelAttribute("item") SearchDTO item) {
        setUpModel(model, item);
        return "report/table";
    }

    public void setUpModelB(ModelMap model, SearchDTO item) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "Location Gender Cross Tabulation Report");
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("periods", periodService.getAll());
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
        }
        model.addAttribute("item", item.getInstance(item));
        model.addAttribute("excelExport", "/report/aggregate/loc-gender-support-group/export/excel" + item.getQueryString(item.getInstance(item)));
        model.addAttribute("items", locGenderReportService.getDefaultReportB(item.getInstance(item)));
    }

    @RequestMapping(value = "/loc-gender-support-group", method = RequestMethod.GET)
    public String getReportSupportGroup(ModelMap model) {
        setUpModelB(model, new SearchDTO());
        return "report/table";
    }

    @RequestMapping(value = "/loc-gender-support-group", method = RequestMethod.POST)
    public String getReportSupportGroup(ModelMap model, @ModelAttribute("item") SearchDTO item) {
        setUpModelB(model, item);
        return "report/table";
    }

    @RequestMapping(value = "/loc-gender-support-group/export/excel", method = RequestMethod.GET)
    public void getExcelExportSupportGroup(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Aggregate_Location_Gender_Cross_Tabulation");
        forceDownLoadDatabase(officeExportService.exportExcelXLSXFile(locGenderReportService.getDefaultReportB(item.getInstance(item)), name), name, response);
    }

    @RequestMapping(value = "/loc-gender-healthcenter/export/excel", method = RequestMethod.GET)
    public void getExcelExportHealthCenter(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Aggregate_Location_Gender_Cross_Tabulation");
        forceDownLoadDatabase(officeExportService.exportExcelXLSXFile(locGenderReportService.getDefaultReport(item.getInstance(item)), name), name, response);
    }
}
