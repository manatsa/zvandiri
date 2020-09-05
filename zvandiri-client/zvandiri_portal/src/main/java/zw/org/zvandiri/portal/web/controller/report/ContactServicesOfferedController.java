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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.util.DateRangeItem;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.service.ContactServicesOfferedReportService;
import zw.org.zvandiri.report.api.service.OfficeExportService;

/**
 *
 * @author tasu
 */
@Controller
@RequestMapping("/report/contact-services-offered")
public class ContactServicesOfferedController extends BaseController{
    @Resource
    private OfficeExportService officeExportService;
    @Resource
    private ContactServicesOfferedReportService reportService;
    
    @RequestMapping(value = "/three-months", method = RequestMethod.GET)
    public String get3MonthReport(ModelMap map){
        SearchDTO dto = new SearchDTO();
        dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd()));
        map.addAttribute("pageTitle", APP_PREFIX + " Contact Services Offered Report");
        map.addAttribute("items", reportService.getDefaultReport(dto));
        map.addAttribute("excelExport", "/report/contact-services-offered/export/excel" + dto.getQueryString(dto.getInstance(dto)));
        return "report/contactServicesOfferedReport";
    }
    
    @RequestMapping(value = "/six-months", method = RequestMethod.GET)
    public String get6MonthReport(ModelMap map){
        SearchDTO dto = new SearchDTO();
        dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_SIX_MONTHS.getEnd()));
        map.addAttribute("pageTitle", APP_PREFIX + " Contact Services Offered Report");
        map.addAttribute("items", reportService.getDefaultReport(dto));
        map.addAttribute("excelExport", "/report/contact-services-offered/export/excel" + dto.getQueryString(dto.getInstance(dto)));
        return "report/contactServicesOfferedReport";
    }
    
    @RequestMapping(value = "/twelve-months", method = RequestMethod.GET)
    public String get12MonthReport(ModelMap map){
        SearchDTO dto = new SearchDTO();
        dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWELVE_MONTHS.getEnd()));
        map.addAttribute("pageTitle", APP_PREFIX + " Contact Services Offered Report");
        map.addAttribute("items", reportService.getDefaultReport(dto));
        map.addAttribute("excelExport", "/report/contact-services-offered/export/excel" + dto.getQueryString(dto.getInstance(dto)));
        return "report/contactServicesOfferedReport";
    }
    
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void getExcelExport(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Contact Services Offered Report");
        forceDownLoadDatabase(officeExportService.exportExcelXLSXFile(reportService.getDefaultReport(item.getInstance(item)), name), name, response);
    }
}
