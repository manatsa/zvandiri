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

import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.Bicycle;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.DatabaseHeader;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * @author Mana
 */
@Controller
@RequestMapping("/report/bicycle")
public class BicycleController extends BaseController {

    @Resource
    private ProvinceService provinceService;

    @Resource
    private DistrictService districtService;

    @Resource
    private FacilityService facilityService;

    @Resource
    private SupportGroupService supportGroupService;

    @Resource
    BicycleReportService bicycleReportService;

    List<Bicycle> bicycles = new ArrayList<>();

    public void setUpModel(ModelMap model, SearchDTO item, boolean post) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "Cadre Bicycles Report");
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("item", item.getInstance(item));
        //model.addAttribute("excelExport", "/report/last-bicycleed/export/excel" + item.getQueryString(item.getInstance(item)));

        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
                model.addAttribute("supportGroups", supportGroupService.getByDistrict(item.getDistrict()));
            }
        }
        if (post) {
            model.addAttribute("excelExport", "/report/bicycle/export/excel" + item.getQueryString(item.getInstance(item)));
            model.addAttribute("items", bicycles);
        }
    }

    @RequestMapping(value = "/range", method = RequestMethod.GET)
    public String getReportIndex(ModelMap model) {
        setUpModel(model, new SearchDTO(), false);
        return "report/bicycleReport";
    }

    @RequestMapping(value = "/range", method = RequestMethod.POST)
    public String getReportResult(ModelMap model, @ModelAttribute("item") SearchDTO item) {
        item = getUserLevelObjectState(item);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        //bicycles=pool.invoke(new LastContactedClientTask(DateUtil.generateArray(lastContactedService.countLastContacted(item)), lastContactedService, item));
        bicycles = bicycleReportService.get(item);
        setUpModel(model, item, true);
        return "report/bicycleReport";
    }

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void getExcelExportHealthCenter(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Cadres_Bicycles_Report");
        forceDownLoadXLSX(createLastContactWorkbook(), name, response);
    }

    public XSSFWorkbook createLastContactWorkbook() {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFCreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        // tb Ipt here
        XSSFSheet cadreBikeSheet = workbook.createSheet("Bicycles");
        int bicycleRowNum = 0;
        XSSFRow bicycleRow = cadreBikeSheet.createRow(bicycleRowNum++);
        int lastContactedDTOCellNum = 0;
        for (String title : DatabaseHeader.CADRE_BIKES_HEADER) {
            XSSFCell cell = bicycleRow.createCell(lastContactedDTOCellNum++);
            cell.setCellValue(title);
        }

        for (Bicycle bicycle : bicycles) {
            int count = 0;

            bicycleRow = cadreBikeSheet.createRow(bicycleRowNum++);


            XSSFCell patientName = bicycleRow.createCell(count++);
            //Optional.ofNullable(bicycle.getCadre().getFirstName()).ifPresent(patientName::setCellValue);

            XSSFCell lastName = bicycleRow.createCell(count++);
            //Optional.ofNullable(bicycle.getCadre().getLastName()).ifPresent(lastName::setCellValue);

            XSSFCell age = bicycleRow.createCell(count++);
            //Optional.ofNullable(bicycle.getCadre().getAge()).ifPresent(age::setCellValue);

            XSSFCell dateOfBirth = bicycleRow.createCell(count++);
            //Optional.ofNullable(bicycle.getCadre().getDateOfBirth()).ifPresent(dateOfBirth::setCellValue);
            dateOfBirth.setCellStyle(cellStyle);

            XSSFCell sex = bicycleRow.createCell(count++);
            //Optional<Gender> sexOptional=Optional.ofNullable(bicycle.getCadre().getGender());
            sex.setCellValue(bicycle.getCadre().getGender().getName());

            XSSFCell cadreStatus = bicycleRow.createCell(count++);
            // Optional<PatientChangeEvent> cadreStatusOptional=Optional.ofNullable(bicycle.getCadre().getStatus());
            cadreStatus.setCellValue(bicycle.getCadre().getStatus().getName());

            XSSFCell primaryClinic = bicycleRow.createCell(count++);
            //Optional<Facility> facilityOptional=Optional.ofNullable(bicycle.getCadre().getPrimaryClinic());
            primaryClinic.setCellValue(bicycle.getCadre().getPrimaryClinic().getName());

            XSSFCell district = bicycleRow.createCell(count++);
            //Optional<District> districtOptional=Optional.ofNullable(bicycle.getCadre().getDistrict());
            district.setCellValue(bicycle.getCadre().getDistrict().getName());

            XSSFCell province = bicycleRow.createCell(count++);
            //Optional<Province> provinceOptional=Optional.ofNullable(bicycle.getCadre().getProvince());
            province.setCellValue(bicycle.getCadre().getProvince().getName());

            XSSFCell make = bicycleRow.createCell(count++);
            //Optional.ofNullable(bicycle.getBikeType()).ifPresent(make::setCellValue);
            make.setCellValue(bicycle.getBikeType());

            XSSFCell bicycleDateIssued = bicycleRow.createCell(count++);
            if (bicycle.getDateIssued() != null) {
                bicycleDateIssued.setCellValue(bicycle.getDateIssued());
                bicycleDateIssued.setCellStyle(cellStyle);
            } else {
                bicycleDateIssued.setCellType(XSSFCell.CELL_TYPE_BLANK);
            }
            //Optional.ofNullable(bicycle.getDateIssued()).ifPresent(bicycleDateIssued::setCellValue);


            XSSFCell bicycleDateRecovered = bicycleRow.createCell(count++);
            if (bicycle.getDateRecovered() != null) {
                bicycleDateRecovered.setCellValue(bicycle.getDateRecovered());
                bicycleDateRecovered.setCellStyle(cellStyle);
            } else {
                bicycleDateRecovered.setCellType(XSSFCell.CELL_TYPE_BLANK);
            }
            //Optional.ofNullable(bicycle.getDateRecovered()).ifPresent(bicycleDateRecovered::setCellValue);


            XSSFCell dateCreated = bicycleRow.createCell(count++);
            if (bicycle.getDateCreated() != null) {
                dateCreated.setCellValue(bicycle.getDateCreated());
                dateCreated.setCellStyle(cellStyle);
            } else {
                dateCreated.setCellType(XSSFCell.CELL_TYPE_BLANK);
            }
            //Optional.ofNullable(bicycle.getDateCreated()).ifPresent(dateCreated::setCellValue);


            XSSFCell condition = bicycleRow.createCell(count++);
            //Optional<Condition> conditionOptional=Optional.ofNullable(bicycle.getBikeCondition());
            condition.setCellValue(bicycle.getBikeCondition() != null ? bicycle.getBikeCondition().getName() : "");

            XSSFCell bicycleStatus = bicycleRow.createCell(count++);
            //Optional<PhoneStatus> statusOptional=Optional.ofNullable(bicycle.getBikeStatus());
            bicycleStatus.setCellValue(bicycle.getBikeStatus() != null ? bicycle.getBikeStatus().getName() : "");

            XSSFCell issues = bicycleRow.createCell(count++);
            //Optional.ofNullable(bicycle.getBikeIssues()).ifPresent(issues::setCellValue);
            issues.setCellValue(bicycle.getBikeIssues() != null ? bicycle.getBikeIssues() : "");

        }

        return workbook;
    }

}
