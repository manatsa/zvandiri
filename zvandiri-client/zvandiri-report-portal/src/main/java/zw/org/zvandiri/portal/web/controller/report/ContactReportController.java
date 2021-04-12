/*
 * Copyright 2017 jackie muzinda.
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

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.service.ContactReportService;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import static zw.org.zvandiri.portal.web.controller.IAppTitle.APP_PREFIX;
import zw.org.zvandiri.portal.web.controller.report.parallel.GenericCountReportTask;
import zw.org.zvandiri.report.api.service.DetailedReportService;
import zw.org.zvandiri.report.api.service.OfficeExportService;

/**
 *
 * @author jackie muzinda
 */
@Controller
@RequestMapping("/report/contact")
public class ContactReportController extends BaseController {

    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private FacilityService facilityService;
    @Resource
    private ContactReportService contactReportService;
    @Resource
    private OfficeExportService officeExportService;
    @Resource
    private DetailedReportService detailedReportService;

    public String setUpModel(ModelMap model, SearchDTO item, boolean post) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "Contact Detailed Report");
        model.addAttribute("provinces", provinceService.getAll());
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
            }
        }
        if (post) {
            model.addAttribute("excelExport", "/report/contact/export/excel" + item.getQueryString(item.getInstance(item)));
            ForkJoinPool pool = ForkJoinPool.commonPool();
            List items = pool.invoke(new GenericCountReportTask(DateUtil.generateArray(contactReportService.getCount(item)), contactReportService, item));
            model.addAttribute("items", items);
        }
        model.addAttribute("item", item.getInstance(item));
        return "report/contactDetailedReport";
    }

    @RequestMapping(value = "/detailed", method = RequestMethod.GET)
    public String getReferralReportIndex(ModelMap model) {
        return setUpModel(model, new SearchDTO(), false);
    }

    @RequestMapping(value = "/detailed", method = RequestMethod.POST)
    public String getReferralReportIndex(ModelMap model, @ModelAttribute("item") @Valid SearchDTO item, BindingResult result) {
        return setUpModel(model, item, true);
    }

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void getExcelExport(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Detailed_Contact_Report");
        forceDownLoadDatabase(officeExportService.exportExcelXLSXFile(detailedReportService.getDefaultReportB(item.getInstance(item)), name), name, response);
    }
}
