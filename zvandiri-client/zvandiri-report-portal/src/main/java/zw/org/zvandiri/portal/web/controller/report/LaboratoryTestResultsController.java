/*
 * Copyright 2018 jmuzinda.
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
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import zw.org.zvandiri.business.domain.InvestigationTest;
import zw.org.zvandiri.business.domain.util.TestType;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.PatientReportService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.DatabaseHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import zw.org.zvandiri.portal.web.controller.report.parallel.LabTestTask;

/**
 *
 * @author jmuzinda
 */
@Controller
@RequestMapping("/report/test-results")
public class LaboratoryTestResultsController extends BaseController {

    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private FacilityService facilityService;
    @Resource
    private PatientReportService patientReportService;
    List<InvestigationTest> results = new ArrayList<>();

    public String setUpModel(ModelMap model, SearchDTO item, String type, boolean post) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "Lab Results Reports");
        model.addAttribute("provinces", provinceService.getAll());
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
            }
        }
        if (type.equals("viral-load")) {
            item.setMaxViralLoad(0);
            item.setTestType(TestType.VIRAL_LOAD);
        } else if (type.equals("cd4-count")) {
            item.setMinCd4Count(2000000);
            item.setTestType(TestType.CD4_COUNT);
        }
        model.addAttribute("excelExport", "/report/test-results/export/excel" + item.getQueryString(item.getInstance(item)));
        if (post) {
            model.addAttribute("items", results);
        }
        model.addAttribute("item", item.getInstance(item));
        return "report/laboratoryDetailedReport";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_ZM') or hasRole('ROLE_M_AND_E_OFFICER') or hasRole('ROLE_HOD_M_AND_E')")
    public String getReferralReportIndex(ModelMap model, @RequestParam String type) {
        return setUpModel(model, new SearchDTO(), type, false);
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_ZM') or hasRole('ROLE_M_AND_E_OFFICER') or hasRole('ROLE_HOD_M_AND_E')")
    public String getReferralReportIndex(ModelMap model, @RequestParam String type, @ModelAttribute("item") @Valid SearchDTO item, BindingResult result) {
        item = getUserLevelObjectState(item);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        results = pool.invoke(new LabTestTask(DateUtil.generateArray(patientReportService.getPatientWithViralLoad(item)), patientReportService, item));
        return setUpModel(model, item, type, true);
    }

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void getExcelExport(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Clients_Lab_Results_Report");
        forceDownLoadDatabase(clientLabResults(item), name, response);
    }

    public XSSFWorkbook clientLabResults(SearchDTO dto) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
        // add contact assessments
        Sheet labResults = workbook.createSheet("Lab Results");
        int assessmentRowNum = 0;
        Row resultsRow = labResults.createRow(assessmentRowNum++);
        int assessmentCellNum = 0;
        for (String title : DatabaseHeader.VLS_CLIENTS_HEADER) {
            Cell cell = resultsRow.createCell(assessmentCellNum++);
            cell.setCellValue(title);
        }

        for (InvestigationTest test : results) {

            int count = 0;
            resultsRow = labResults.createRow(assessmentRowNum++);

            Cell patientName = resultsRow.createCell(count++);
            patientName.setCellValue(test.getPatient().getName());

            Cell dateOfBirth = resultsRow.createCell(count++);
            dateOfBirth.setCellValue(test.getPatient().getDateOfBirth());
            dateOfBirth.setCellStyle(cellStyle);

            Cell age = resultsRow.createCell(count++);
            age.setCellValue(test.getPatient().getAge());

            Cell sex = resultsRow.createCell(count++);
            sex.setCellValue(test.getPatient().getGender().getName());

            Cell TestResult = resultsRow.createCell(count++);
            TestResult.setCellValue(test.getResult() != null ? test.getResult().toString() : "");

            Cell TestType = resultsRow.createCell(count++);
            TestType.setCellValue(test.getTestType().getName());

            Cell suppression = resultsRow.createCell(count++);
            suppression.setCellValue(test.getViralLoadSuppressionStatus());

            Cell entryDate = resultsRow.createCell(count++);
            if (test.getDateCreated() != null) {
                entryDate.setCellValue(test.getDateCreated());
                entryDate.setCellStyle(cellStyle);
            } else {
                entryDate.setCellValue("");
            }

            Cell dateTaken = resultsRow.createCell(count++);
            if (test.getDateTaken() != null) {
                dateTaken.setCellValue(test.getDateTaken());
                dateTaken.setCellStyle(cellStyle);
            } else {
                dateTaken.setCellValue("");
            }

            Cell address = resultsRow.createCell(count++);
            address.setCellValue(test.getPatient().getAddress());

            Cell phone = resultsRow.createCell(count++);
            phone.setCellValue(test.getPatient().getMobileNumber() == null ? "" : test.getPatient().getMobileNumber());

            Cell referer = resultsRow.createCell(count++);
            referer.setCellValue(test.getPatient().getRefererName() != null ? test.getPatient().getRefererName() : "");

            Cell province = resultsRow.createCell(count++);
            province.setCellValue(test.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
            Cell district = resultsRow.createCell(count++);
            district.setCellValue(test.getPatient().getPrimaryClinic().getDistrict().getName() == null ? "" : test.getPatient().getPrimaryClinic().getDistrict().getName());
            Cell primaryClinic = resultsRow.createCell(count++);
            primaryClinic.setCellValue(test.getPatient().getPrimaryClinic().getName() == null ? "" : test.getPatient().getPrimaryClinic().getName());

            Cell isCats = resultsRow.createCell(++count);
            isCats.setCellValue(
            		test.getPatient().getCat() != null ? test.getPatient().getCat().getName() : null
            );
            Cell youngMumGroup = resultsRow.createCell(++count);
            youngMumGroup.setCellValue(
            		test.getPatient().getYoungMumGroup() != null ? test.getPatient().getYoungMumGroup().getName() : null
            );
            
        }

        return workbook;
    }

}
