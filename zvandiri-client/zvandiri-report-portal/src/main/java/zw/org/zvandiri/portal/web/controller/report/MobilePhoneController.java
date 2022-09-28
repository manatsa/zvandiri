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

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.MobilePhone;
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
 *
 * @author Mana
 */
@Controller
@RequestMapping("/report/mobile/phone")
public class MobilePhoneController extends BaseController {

    @Resource
    private ProvinceService provinceService;

    @Resource
    private DistrictService districtService;

    @Resource
    private FacilityService facilityService;

    @Resource
    private SupportGroupService supportGroupService;

    @Resource
    MobilePhoneReportService mobilePhoneService;

    List<MobilePhone> mobilePhones = new ArrayList<>();

    public void setUpModel(ModelMap model, SearchDTO item, boolean post) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "Cadre Mobile Phones Report");
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("item", item.getInstance(item));
        model.addAttribute("excelExport", "/report/last-phoneed/export/excel" + item.getQueryString(item.getInstance(item)));

        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
                model.addAttribute("supportGroups", supportGroupService.getByDistrict(item.getDistrict()));
            }
        }
        if (post) {
            model.addAttribute("excelExport", "/report/mobile/phone/export/excel" + item.getQueryString(item.getInstance(item)));
            model.addAttribute("items", mobilePhones);
        }
    }

    @RequestMapping(value = "/range", method = RequestMethod.GET)
    public String getReportIndex(ModelMap model) {
        setUpModel(model, new SearchDTO(), false);
        return "report/mobilePhoneReport";
    }

    @RequestMapping(value = "/range", method = RequestMethod.POST)
    public String getReportResult(ModelMap model, @ModelAttribute("item") SearchDTO item) {
        item = getUserLevelObjectState(item);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        //phones=pool.invoke(new LastContactedClientTask(DateUtil.generateArray(lastContactedService.countLastContacted(item)), lastContactedService, item));
        mobilePhones = mobilePhoneService.get(item);
        setUpModel(model, item, true);
        return "report/mobilePhoneReport";
    }

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void getExcelExportHealthCenter(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Cadres_Mobile_Phones_Report");
        forceDownLoadXLSX(createLastContactWorkbook(), name, response);
    }

    public XSSFWorkbook createLastContactWorkbook() {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFCreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        XSSFSheet cadrePhoneRow = workbook.createSheet("Mobile_Phones");
        int phoneRowNum = 0;
        XSSFRow phoneRow = cadrePhoneRow.createRow(phoneRowNum++);
        int cadrePhoneCellNum = 0;
        for (String title : DatabaseHeader.CADRE_PHONES_HEADER) {
            XSSFCell cell = phoneRow.createCell(cadrePhoneCellNum++);
            cell.setCellValue(title);
        }

        for (MobilePhone phone : mobilePhones) {
            int count = 0;

            phoneRow = cadrePhoneRow.createRow(phoneRowNum++);

            XSSFCell patientName = phoneRow.createCell(count++);
            //Optional.ofNullable(phone.getCadre().getFirstName()).ifPresent(patientName::setCellValue);
            patientName.setCellValue(phone.getCadre().getFirstName());

            XSSFCell lastName = phoneRow.createCell(count++);
            //Optional.ofNullable(phone.getCadre().getLastName()).ifPresent(lastName::setCellValue);
            lastName.setCellValue(phone.getCadre().getLastName());

            XSSFCell age = phoneRow.createCell(count++);
            //Optional.ofNullable(phone.getCadre().getAge()).ifPresent(age::setCellValue);
            age.setCellValue(phone.getCadre().getAge());

            XSSFCell dateOfBirth = phoneRow.createCell(count++);
            if(phone.getCadre().getDateOfBirth()!=null){
                dateOfBirth.setCellValue(phone.getCadre().getDateOfBirth());
                dateOfBirth.setCellStyle(cellStyle);
            }else{
                dateOfBirth.setCellType(CellType.BLANK);
            }
            //Optional.ofNullable(phone.getCadre().getDateOfBirth()).ifPresent(dateOfBirth::setCellValue);


            XSSFCell sex = phoneRow.createCell(count++);
            //Optional<Gender> sexOptional=Optional.ofNullable(phone.getCadre().getGender());
            sex.setCellValue(phone.getCadre().getGender().getName());

            XSSFCell cadreStatus = phoneRow.createCell(count++);
            //Optional<PatientChangeEvent> cadreStatusOptional=Optional.ofNullable(phone.getCadre().getStatus());
            cadreStatus.setCellValue(phone.getCadre().getStatus()!=null?phone.getCadre().getStatus().getName():"");

            XSSFCell primaryClinic = phoneRow.createCell(count++);
            //Optional<Facility> facilityOptional=Optional.ofNullable(phone.getCadre().getPrimaryClinic());
            primaryClinic.setCellValue(phone.getCadre().getPrimaryClinic().getName());

            XSSFCell district = phoneRow.createCell(count++);
            //Optional<District> districtOptional=Optional.ofNullable(phone.getCadre().getDistrict());
            district.setCellValue(phone.getCadre().getDistrict().getName());

            XSSFCell province = phoneRow.createCell(count++);
            //Optional<Province> provinceOptional=Optional.ofNullable(phone.getCadre().getProvince());
            province.setCellValue(phone.getCadre().getProvince().getName());

            XSSFCell make = phoneRow.createCell(count++);
            //Optional.ofNullable(phone.getPhoneMake()).ifPresent(make::setCellValue);
            make.setCellValue(phone.getPhoneMake());

            XSSFCell model = phoneRow.createCell(count++);
            //Optional.ofNullable(phone.getPhoneModel()).ifPresent(model::setCellValue);
            model.setCellValue(phone.getPhoneModel());

            XSSFCell serial = phoneRow.createCell(count++);
            //Optional.ofNullable(phone.getSerialNumber()).ifPresent(serial::setCellValue);
            serial.setCellValue(phone.getSerialNumber());

            XSSFCell phoneDateIssued = phoneRow.createCell(count++);
            if(phone.getDateIssued()!=null){
                phoneDateIssued.setCellValue(phone.getDateIssued());
                phoneDateIssued.setCellStyle(cellStyle);
            }else{
                phoneDateIssued.setCellType(CellType.BLANK);
            }
            //Optional.ofNullable(phone.getDateIssued()).ifPresent(phoneDateIssued::setCellValue);


            XSSFCell phoneDateRecovered = phoneRow.createCell(count++);
            if(phone.getDateRecovered()!=null){
                phoneDateRecovered.setCellValue(phone.getDateRecovered());
                phoneDateRecovered.setCellStyle(cellStyle);
            }else{
                phoneDateRecovered.setCellType(CellType.BLANK);
            }
            //Optional.ofNullable(phone.getDateRecovered()).ifPresent(phoneDateRecovered::setCellValue);


            XSSFCell dateCreated = phoneRow.createCell(count++);
            if(phone.getDateCreated()!=null){
                dateCreated.setCellValue(phone.getDateCreated());
                dateCreated.setCellStyle(cellStyle);
            }else{
                dateCreated.setCellType(CellType.BLANK);
            }
            //Optional.ofNullable(phone.getDateCreated()).ifPresent(dateCreated::setCellValue);


            XSSFCell condition = phoneRow.createCell(count++);
            //Optional<Condition> conditionOptional=Optional.ofNullable(phone.getPhoneCondition());
            condition.setCellValue(phone.getPhoneCondition()!=null?phone.getPhoneCondition().getName():"");

            XSSFCell phoneStatus = phoneRow.createCell(count++);
            //Optional<PhoneStatus> statusOptional=Optional.ofNullable(phone.getPhoneStatus());
            phoneStatus.setCellValue(phone.getPhoneStatus()!=null?phone.getPhoneStatus().getName():"");

            XSSFCell imei1 = phoneRow.createCell(count++);
            //Optional.ofNullable(phone.getImei1()).ifPresent(imei1::setCellValue);
            imei1.setCellValue(phone.getImei1());

            XSSFCell imei2 = phoneRow.createCell(count++);
            //Optional.ofNullable(phone.getImei2()).ifPresent(imei2::setCellValue);
            imei2.setCellValue(phone.getImei2()!=null? phone.getImei2():"");

            XSSFCell msisdn1 = phoneRow.createCell(count++);
            //Optional.ofNullable(phone.getMsisdn1()).ifPresent(msisdn1::setCellValue);
            msisdn1.setCellValue(phone.getMsisdn1());

            XSSFCell msisdn2 = phoneRow.createCell(count++);
            //Optional.ofNullable(phone.getMsisdn2()).ifPresent(msisdn2::setCellValue);
            msisdn2.setCellValue(phone.getMsisdn2()!=null?phone.getMsisdn2():"");

            XSSFCell issues = phoneRow.createCell(count++);
            //Optional.ofNullable(phone.getPhoneIssues()).ifPresent(issues::setCellValue);
            issues.setCellValue(phone.getPhoneIssues()!=null?phone.getPhoneIssues():"");


        }

        return workbook;
    }

}
