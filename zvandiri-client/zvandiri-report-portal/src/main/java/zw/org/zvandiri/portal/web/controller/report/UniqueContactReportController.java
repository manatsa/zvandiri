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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.service.ContactReportService;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.controller.report.parallel.UniqueContactTask;
import zw.org.zvandiri.report.api.DatabaseHeader;

/**
 * @author jackie muzinda
 */
@Controller
@RequestMapping("/report/unique/contacted")
public class UniqueContactReportController extends BaseController {

    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private FacilityService facilityService;
    @Resource
    private ContactReportService contactReportService;

    List<Patient> patients = new ArrayList<>();

    public String setUpModel(ModelMap model, SearchDTO item, boolean post) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "Unique Contact Detailed Report");
        model.addAttribute("provinces", provinceService.getAll());
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
            }
        }
        if (post) {
            model.addAttribute("excelExport", "/report/unique/contacted/export/excel" + item.getQueryString(item.getInstance(item)));
            //model.addAttribute("items", contactReportService.get(item.getInstance(item)));
        }
        model.addAttribute("item", item.getInstance(item));
        return "report/uniqueContactDetailedReport";
    }

    @RequestMapping(value = "/range", method = RequestMethod.GET)
    public String getReferralReportIndex(ModelMap model) {
        return setUpModel(model, new SearchDTO(), false);
    }

    @RequestMapping(value = "/range", method = RequestMethod.POST)
    public String getReferralReportIndex(ModelMap model, @ModelAttribute("item") @Valid SearchDTO item, BindingResult result) {
        item = getUserLevelObjectState(item);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        patients = pool.invoke(new UniqueContactTask(DateUtil.generateArray(contactReportService.countUnique(item)), contactReportService, item));
        model.addAttribute("items", patients);
        return setUpModel(model, item, true);
    }

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void getExcelExport(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Unique_Detailed_Contact_Report");
        forceDownLoadDatabase(getUniqueContactClients(name, item), name, response);
    }


    public XSSFWorkbook getUniqueContactClients(String name, SearchDTO dto) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
        // add contact assessments
        Sheet uncontactedClientsDetails = workbook.createSheet("Unique contacted Clients");
        int assessmentRowNum = 0;
        Row uncontactedRow = uncontactedClientsDetails.createRow(assessmentRowNum++);
        int assessmentCellNum = 0;
        for (String title : DatabaseHeader.UNIQUE_CONTACTED_CLIENTS_HEADER) {
            Cell cell = uncontactedRow.createCell(assessmentCellNum++);
            cell.setCellValue(title);
        }


        for (Patient patient : patients) {

            int count = 0;
            uncontactedRow = uncontactedClientsDetails.createRow(assessmentRowNum++);

            Cell oinumber = uncontactedRow.createCell(count++);
            oinumber.setCellValue(patient.getoINumber());

            Cell patientFName = uncontactedRow.createCell(count++);
            patientFName.setCellValue(patient.getFirstName());

            Cell patientLName = uncontactedRow.createCell(count++);
            patientLName.setCellValue(patient.getLastName());

            Cell age = uncontactedRow.createCell(count++);
            age.setCellValue(patient.getAge());

            Cell sex = uncontactedRow.createCell(count++);
            sex.setCellValue(patient.getGender().getName());

            Cell phone = uncontactedRow.createCell(count++);
            phone.setCellValue(patient.getMobileNumber() == null ? "" : patient.getMobileNumber());

            Cell phone1 = uncontactedRow.createCell(count++);
            phone1.setCellValue(patient.getSecondaryMobileNumber() == null ? "" : patient.getSecondaryMobileNumber());

            Cell address = uncontactedRow.createCell(count++);
            address.setCellValue(patient.getAddress());

            Cell address1 = uncontactedRow.createCell(count++);
            address1.setCellValue(patient.getAddress1());

            Cell province = uncontactedRow.createCell(count++);
            province.setCellValue(patient.getPrimaryClinic().getDistrict().getProvince().getName());

            Cell district = uncontactedRow.createCell(count++);
            district.setCellValue(patient.getPrimaryClinic().getDistrict().getName() == null ? "" : patient.getPrimaryClinic().getDistrict().getName());

            Cell primaryClinic = uncontactedRow.createCell(count++);
            primaryClinic.setCellValue(patient.getPrimaryClinic().getName() == null ? "" : patient.getPrimaryClinic().getName());

            Cell isCats = uncontactedRow.createCell(count++);
            isCats.setCellValue(
                    patient.getCat() != null ? patient.getCat().getName() : null
            );
            Cell youngMumGroup = uncontactedRow.createCell(count++);
            youngMumGroup.setCellValue(
                    patient.getYoungMumGroup() != null ? patient.getYoungMumGroup().getName() : null
            );
            Cell ymd = uncontactedRow.createCell(count++);
            ymd.setCellValue(
                    patient.getYoungDadGroup() != null ? patient.getYoungDadGroup().getName() : null
            );
        }

        return workbook;
    }
}
