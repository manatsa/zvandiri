/*
 * Copyright 2016 Tasunungurwa Muzinda.
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

import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.util.CareLevel;
import zw.org.zvandiri.business.domain.util.CrossTabOption;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;

import static zw.org.zvandiri.portal.web.controller.IAppTitle.APP_PREFIX;

import zw.org.zvandiri.report.api.service.OfficeExportService;
import zw.org.zvandiri.report.api.service.ProblemReportService;

/**
 * @author Tasunungurwa Muzinda
 */
@Controller
@RequestMapping("/report/problem")
public class ProblemReportController extends BaseController {

    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private OfficeExportService officeExportService;
    @Resource
    private ProblemReportService problemReportService;
    @Resource
    private FacilityService facilityService;

    @RequestMapping(value = "/problem-report", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_ZM') or hasRole('ROLE_M_AND_E_OFFICER') or hasRole('ROLE_HOD_M_AND_E') or hasRole('ROLE_MANAGEMENT')")
    public String getScreenForm(ModelMap model, SearchDTO item) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "Combined Statistical Report");
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("careLevels", CareLevel.values());
        model.addAttribute("crossTabOptions", CrossTabOption.values());
        model.addAttribute("item", item);
        model.addAttribute("excelExport", "/report/problem-report/excel" + item.getQueryString(item.getInstance(item)));
        return "report/report";
    }

    @RequestMapping(value = "/problem-report", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_ZM') or hasRole('ROLE_M_AND_E_OFFICER') or hasRole('ROLE_HOD_M_AND_E') or hasRole('ROLE_MANAGEMENT')")
    public String getMonthlyReportForm(ModelMap model, @ModelAttribute("item") SearchDTO item) {
        item = getUserLevelObjectState(item);
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("pageTitle", APP_PREFIX + "Combined Statistical Report");
        model.addAttribute("provinces", provinceService.getAll());
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
            }
        }
        model.addAttribute("excelExport", "/report/problem/problem-report/excel" + item.getQueryString(item.getInstance(item)));
        model.addAttribute("item", item.getInstance(item));
        model.addAttribute("items", problemReportService.getDefaultReport(item.getInstance(item)));
        model.addAttribute("careLevels", CareLevel.values());
        model.addAttribute("crossTabOptions", CrossTabOption.values());
        if (item.getCrossTabOptions() != null && !item.getCrossTabOptions().isEmpty()) {
            Set<CrossTabOption> options = item.getCrossTabOptions();
            model.addAttribute("colSpan", options.contains(CrossTabOption.GENDER) ? 3 : 5);
            return options.size() > 1 ? "report/statisticalReportTwo" : "report/statisticalReport";
        }
        return "report/report";
    }

    @RequestMapping("/problem-report/excel")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_ZM') or hasRole('ROLE_M_AND_E_OFFICER') or hasRole('ROLE_HOD_M_AND_E') or hasRole('ROLE_MANAGEMENT')")
    public void getExcelExport(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Combined Statistical Report");
        forceDownLoadDatabase(officeExportService.exportExcelXLSXFile(problemReportService.getDefaultReport(item.getInstance(item)), name), name, response);
    }
}
