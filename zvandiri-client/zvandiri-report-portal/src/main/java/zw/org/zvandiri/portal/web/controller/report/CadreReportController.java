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
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.domain.util.*;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.controller.report.parallel.CadreToolsReportTask;
import zw.org.zvandiri.report.api.DatabaseHeader;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author Mana
 */
@Controller
@RequestMapping("/report/cadre")
public class CadreReportController extends BaseController {

    @Resource
    private ProvinceService provinceService;

    @Resource
    private DistrictService districtService;

    @Resource
    private FacilityService facilityService;

    @Resource
    private SupportGroupService supportGroupService;

    @Resource
    CadreReportService cadreService;

    @Resource
    MobilePhoneService mobilePhoneService;

    @Resource
    BicycleService bicycleService;

    List<Cadre> cadres = new ArrayList<>();

    public void setUpModel(ModelMap model, SearchDTO item, boolean post) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "Cadre Tools Report");
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("item", item.getInstance(item));

        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
                model.addAttribute("supportGroups", supportGroupService.getByDistrict(item.getDistrict()));
            }
        }
        if (post) {
            model.addAttribute("excelExport", "/report/cadre/export/excel" + item.getQueryString(item.getInstance(item)));
            model.addAttribute("items", cadres);
        }
    }

    @RequestMapping(value = "/range", method = RequestMethod.GET)
    public String getReportIndex(ModelMap model) {
        setUpModel(model, new SearchDTO(), false);
        return "report/cadreReport";
    }

    @RequestMapping(value = "/range", method = RequestMethod.POST)
    public String getReportResult(ModelMap model, @ModelAttribute("item") SearchDTO item) {
        item = getUserLevelObjectState(item);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        cadres=pool.invoke(new CadreToolsReportTask(DateUtil.generateArray(cadreService.getCount(item)), cadreService, item));
        //System.err.println("Cadres Size: "+cadres.size());
        //System.err.println("First Cadre: "+cadres.get(0).toString());
        setUpModel(model, item, true);
        return "report/cadreReport";
    }

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void getExcelExportHealthCenter(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Cadres_Tools_Report");
        forceDownLoadXLSX(cadresWorkbook(), name, response);
    }

    public XSSFWorkbook cadresWorkbook() {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFCreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        // tb Ipt here
        XSSFSheet cadreToolsSheet = workbook.createSheet("cadres");
        int cadreRowNum = 0;
        XSSFRow cadreRow = cadreToolsSheet.createRow(cadreRowNum++);
        int lastContactedDTOCellNum = 0;
        for (String title : DatabaseHeader.CADRE_TOOL_HEADER) {
            XSSFCell cell = cadreRow.createCell(lastContactedDTOCellNum++);
            cell.setCellValue(title);
        }

        for (Cadre cadre : cadres) {
            int count = 0;

            MobilePhone phone=mobilePhoneService.getByCadre(cadre);
            Bicycle bike=bicycleService.getByCadre(cadre);

//            System.err.println("CADRE: "+cadre+"PHONE: \n"+phone+"\nBIKE: "+bike);
            cadreRow = cadreToolsSheet.createRow(cadreRowNum++);


            XSSFCell patientName = cadreRow.createCell(count++);
            Optional.ofNullable(cadre.getFirstName()).ifPresent(patientName::setCellValue);

            XSSFCell lastName = cadreRow.createCell(count++);
            Optional.ofNullable(cadre.getLastName()).ifPresent(lastName::setCellValue);

            XSSFCell age = cadreRow.createCell(count++);
            Optional.ofNullable(cadre.getAge()).ifPresent(age::setCellValue);

            XSSFCell dateOfBirth = cadreRow.createCell(count++);
            Optional.ofNullable(cadre.getDateOfBirth()).ifPresent(dateOfBirth::setCellValue);
            dateOfBirth.setCellStyle(cellStyle);

            XSSFCell sex = cadreRow.createCell(count++);
            Optional<Gender> sexOptional=Optional.ofNullable(cadre.getGender());
            sex.setCellValue(sexOptional.isPresent()? sexOptional.get().getName(): null);

            XSSFCell cadreStatus = cadreRow.createCell(count++);
            Optional<PatientChangeEvent> cadreStatusOptional=Optional.ofNullable(cadre.getStatus());
            cadreStatus.setCellValue(cadreStatusOptional.isPresent()? cadreStatusOptional.get().getName(): null);

            XSSFCell cadreType = cadreRow.createCell(count++);
            Optional<CaderType> cadreTypeOptional=Optional.ofNullable(cadre.getCaderType());
            cadreType.setCellValue(cadreTypeOptional.isPresent()? cadreTypeOptional.get().getName(): null);

            XSSFCell primaryClinic = cadreRow.createCell(count++);
            Optional<Facility> facilityOptional=Optional.ofNullable(cadre.getPrimaryClinic());
            primaryClinic.setCellValue(facilityOptional.isPresent()? facilityOptional.get().getName(): null);

            XSSFCell district = cadreRow.createCell(count++);
            Optional<District> districtOptional=Optional.ofNullable(cadre.getDistrict());
            district.setCellValue(districtOptional.isPresent()? districtOptional.get().getName(): null);

            XSSFCell province = cadreRow.createCell(count++);
            Optional<Province> provinceOptional=Optional.ofNullable(cadre.getProvince());
            province.setCellValue(provinceOptional.isPresent()? provinceOptional.get().getName(): null);

            XSSFCell make = cadreRow.createCell(count++);
            if(phone!=null)Optional.ofNullable(phone.getPhoneMake()).ifPresent(make::setCellValue);

            XSSFCell model = cadreRow.createCell(count++);
            if(phone!=null)Optional.ofNullable(phone.getPhoneModel()).ifPresent(model::setCellValue);

            XSSFCell phoneDateIssued = cadreRow.createCell(count++);
            if(phone!=null){
                Optional.ofNullable(phone.getDateIssued()).ifPresent(phoneDateIssued::setCellValue);
                phoneDateIssued.setCellStyle(cellStyle);
            }


            XSSFCell phoneDateRecovered = cadreRow.createCell(count++);
            if(phone!=null){
                Optional.ofNullable(phone.getDateRecovered()).ifPresent(phoneDateRecovered::setCellValue);
                phoneDateRecovered.setCellStyle(cellStyle);
            }


            XSSFCell dateCreated = cadreRow.createCell(count++);
            if(phone!=null){
                Optional.ofNullable(phone.getDateCreated()).ifPresent(dateCreated::setCellValue);
                dateCreated.setCellStyle(cellStyle);
            }


            XSSFCell condition = cadreRow.createCell(count++);
            if(phone!=null){
                Optional<Condition> conditionOptional=Optional.ofNullable(phone.getPhoneCondition());
                condition.setCellValue(conditionOptional.isPresent()? conditionOptional.get().getName(): null);
            }


            XSSFCell phoneStatus = cadreRow.createCell(count++);
            if(phone!=null){
                Optional<PhoneStatus> statusOptional=Optional.ofNullable(phone.getPhoneStatus());
                phoneStatus.setCellValue(statusOptional.isPresent()? statusOptional.get().getName(): null);
            }


            XSSFCell imei1 = cadreRow.createCell(count++);
            if(phone!=null)Optional.ofNullable(phone.getImei1()).ifPresent(imei1::setCellValue);

            XSSFCell imei2 = cadreRow.createCell(count++);
            if(phone!=null)Optional.ofNullable(phone.getImei2()).ifPresent(imei2::setCellValue);

            XSSFCell msisdn1 = cadreRow.createCell(count++);
            if(phone!=null)Optional.ofNullable(phone.getMsisdn1()).ifPresent(msisdn1::setCellValue);

            XSSFCell msisdn2 = cadreRow.createCell(count++);
            if(phone!=null)Optional.ofNullable(phone.getMsisdn2()).ifPresent(msisdn2::setCellValue);

            XSSFCell issues = cadreRow.createCell(count++);
            if(phone!=null)Optional.ofNullable(phone.getPhoneIssues()).ifPresent(issues::setCellValue);

            XSSFCell type = cadreRow.createCell(count++);
            if(bike!=null)Optional.ofNullable(bike.getBikeType()).ifPresent(type::setCellValue);

            XSSFCell cadreDateIssued = cadreRow.createCell(count++);
            if(bike!=null){
                Optional.ofNullable(bike.getDateIssued()).ifPresent(cadreDateIssued::setCellValue);
                cadreDateIssued.setCellStyle(cellStyle);
            }


            XSSFCell cadreDateRecovered = cadreRow.createCell(count++);
            if(bike!=null){
                Optional.ofNullable(bike.getDateRecovered()).ifPresent(cadreDateRecovered::setCellValue);
                cadreDateRecovered.setCellStyle(cellStyle);
            }


            XSSFCell bikeDateCreated = cadreRow.createCell(count++);
            if(bike!=null){
                Optional.ofNullable(cadre.getDateCreated()).ifPresent(bikeDateCreated::setCellValue);
                dateCreated.setCellStyle(cellStyle);
            }


            XSSFCell bikeCondition = cadreRow.createCell(count++);
            if(bike!=null){
                Optional<Condition> conditionOptional=Optional.ofNullable(bike.getBikeCondition());
                bikeCondition.setCellValue(conditionOptional.isPresent()? conditionOptional.get().getName(): null);
            }


            XSSFCell bikeStatus = cadreRow.createCell(count++);
            if(bike!=null){
                Optional<PhoneStatus> statusOptional=Optional.ofNullable(bike.getBikeStatus());
                bikeStatus.setCellValue(statusOptional.isPresent()? statusOptional.get().getName(): null);
            }


            XSSFCell bikeIssues = cadreRow.createCell(count++);
            if(bike!=null)Optional.ofNullable(bike.getBikeIssues()).ifPresent(bikeIssues::setCellValue);


        }

        return workbook;
    }

}
