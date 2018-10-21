/*
 * Copyright 2018 tasu.
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
import zw.org.zvandiri.business.service.PeriodService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import static zw.org.zvandiri.portal.web.controller.IAppTitle.APP_PREFIX;
import zw.org.zvandiri.report.api.service.DataEntryReportService;
import zw.org.zvandiri.report.api.service.OfficeExportService;
import zw.org.zvandiri.report.api.service.TbScreeningReportReportService;

/**
 *
 * @author tasu
 */
@Controller
@RequestMapping("/report/tb-screening")
public class TbScreeningReportController extends BaseController{
    
    @Resource
    private OfficeExportService officeExportService;
    @Resource
    private TbScreeningReportReportService reportService;
    @Resource
    private ProvinceService provinceService;
    @Resource
    private PeriodService periodService;

    public void setUpModel(ModelMap model, SearchDTO item) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "TB Screening Report");
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("periods", periodService.getActivePeriods());
        model.addAttribute("item", item.getInstance(item));
        model.addAttribute("excelExport", "/report/tb-screening/screening/export/excel" + item.getQueryString(item.getInstance(item)));
        model.addAttribute("items", reportService.getDefaultReport(item.getInstance(item)));
    }

    @RequestMapping(value = "/screening", method = RequestMethod.GET)
    public String getReportIndex(ModelMap model) {
        setUpModel(model, new SearchDTO());
        return "report/tbScreeningReport";
    }

    @RequestMapping(value = "/screening", method = RequestMethod.POST)
    public String getReportResult(ModelMap model, @ModelAttribute("item") SearchDTO item) {
        setUpModel(model, item);
        return "report/tbScreeningReport";
    }

    @RequestMapping(value = "/screening/export/excel", method = RequestMethod.GET)
    public void getExcelExportHealthCenter(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("TB Screening Report");
        forceDownLoad(officeExportService.exportExcelFile(reportService.getDefaultReport(item.getInstance(item)), name), name, response);
    }
}
