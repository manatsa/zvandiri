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

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.PatientChangeEvent;
import zw.org.zvandiri.business.domain.util.TestType;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.DetailedPatientReportService;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.PatientReportService;
import zw.org.zvandiri.business.service.PeriodService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.service.SupportGroupService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.service.DetailedReportService;
import zw.org.zvandiri.report.api.service.OfficeExportService;

/**
 *
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/report/mortality")
public class MortalityReportController extends BaseController {

    @Resource
    private DetailedReportService detailedReportService;
    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private SupportGroupService supportGroupService;
    @Resource
    private FacilityService facilityService;
    @Resource
    private PeriodService periodService;
    @Resource
    private OfficeExportService officeExportService;
    @Resource
    private DetailedPatientReportService detailedPatientReportService;
    @Resource
    private PatientReportService patientReportService;

    public void setUpModel(ModelMap model, SearchDTO item, Boolean post, Boolean hei) {
        item = getUserLevelObjectState(item);
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("periods", periodService.getAll());
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
                model.addAttribute("supportGroups", supportGroupService.getByDistrict(item.getDistrict()));
            }
        }


        if (post) {
            model.addAttribute("excelExport", "/report/mortality/export/excel" + item.getQueryString(item.getInstance(item)));
            model.addAttribute("items", patientReportService.getPatientDeceased(item.getInstance(item)));
        }


        model.addAttribute("item", item.getInstance(item));
    }

    @RequestMapping(value = "/range", method = RequestMethod.GET)
    public String getMortalityRangeIndex(ModelMap model, SearchDTO dto) {
        model.addAttribute("pageTitle", APP_PREFIX + "Mortality Detailed Report");
        Boolean post = Boolean.TRUE;
        if (dto.getStatus() != null && dto.getStatus().equals(PatientChangeEvent.ACTIVE) && (dto.getMaxViralLoad() == null && dto.getMinCd4Count() == null)) {
            post = Boolean.FALSE;
        }
        setUpModel(model, dto, post, Boolean.FALSE);
        return "report/mortalityDetailedReport";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test()
    {
        return "test";
    }


    @RequestMapping(value = {"/range","/range.htm"}, method = RequestMethod.POST)
    public String getMortalityRangeIndexPost(ModelMap model, @ModelAttribute("item") @Valid SearchDTO item) {
        System.err.println("************************************************Now checking mortalities ++++++++++++++++++++++++++++++++");
        model.addAttribute("pageTitle", APP_PREFIX + "Mortality Detailed Report");
        setUpModel(model, item, Boolean.TRUE, Boolean.FALSE);
        return "report/mortalityDetailedReport";
    }



    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void getExcelExport(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Detailed_Mortality_Report");
        List<Patient> items=patientReportService.getPatientDeceased(item);
        forceDownLoadDatabase(officeExportService.exportExcelXLSXFile(detailedReportService.getDeceased(items), name), name, response);
    }
}
