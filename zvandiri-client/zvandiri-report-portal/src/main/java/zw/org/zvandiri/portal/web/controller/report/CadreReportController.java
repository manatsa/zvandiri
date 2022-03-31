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
import zw.org.zvandiri.business.domain.Cadre;
import zw.org.zvandiri.business.domain.MobilePhone;
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
import java.util.concurrent.ForkJoinPool;

/**
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
        cadres = pool.invoke(new CadreToolsReportTask(DateUtil.generateArray(cadreService.getCount(item)), cadreService, item));
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

            MobilePhone phone = mobilePhoneService.getByCadre(cadre);
            Bicycle bike = bicycleService.getByCadre(cadre);

//            System.err.println("CADRE: "+cadre+"PHONE: \n"+phone+"\nBIKE: "+bike);
            cadreRow = cadreToolsSheet.createRow(cadreRowNum++);


            XSSFCell patientName = cadreRow.createCell(count++);
            //Optional.ofNullable(cadre.getFirstName()).ifPresent(patientName::setCellValue);
            patientName.setCellValue(cadre.getFirstName());

            XSSFCell lastName = cadreRow.createCell(count++);
            //Optional.ofNullable(cadre.getLastName()).ifPresent(lastName::setCellValue);
            lastName.setCellValue(cadre.getLastName());

            XSSFCell age = cadreRow.createCell(count++);
            //Optional.ofNullable(cadre.getAge()).ifPresent(age::setCellValue);
            age.setCellValue(cadre.getAge());

            XSSFCell dateOfBirth = cadreRow.createCell(count++);
            if (cadre.getDateOfBirth() != null) {
                dateOfBirth.setCellValue(cadre.getDateOfBirth());
                dateOfBirth.setCellStyle(cellStyle);
            } else {
                dateOfBirth.setCellType(XSSFCell.CELL_TYPE_BLANK);
            }
            //Optional.ofNullable(cadre.getDateOfBirth()).ifPresent(dateOfBirth::setCellValue);


            XSSFCell sex = cadreRow.createCell(count++);
            //Optional<Gender> sexOptional=Optional.ofNullable(cadre.getGender());
            sex.setCellValue(cadre.getGender().getName());

            XSSFCell cadreStatus = cadreRow.createCell(count++);
            //Optional<PatientChangeEvent> cadreStatusOptional=Optional.ofNullable(cadre.getStatus());
            cadreStatus.setCellValue(cadre.getStatus() != null ? cadre.getStatus().getName() : "");

            XSSFCell cadreType = cadreRow.createCell(count++);
            //Optional<CaderType> cadreTypeOptional=Optional.ofNullable(cadre.getCaderType());
            cadreType.setCellValue(cadre.getCaderType() != null ? cadre.getCaderType().getName() : "");

            XSSFCell primaryClinic = cadreRow.createCell(count++);
            //Optional<Facility> facilityOptional=Optional.ofNullable(cadre.getPrimaryClinic());
            primaryClinic.setCellValue(cadre.getPrimaryClinic().getName());

            XSSFCell district = cadreRow.createCell(count++);
            //Optional<District> districtOptional=Optional.ofNullable(cadre.getDistrict());
            district.setCellValue(cadre.getDistrict().getName());

            XSSFCell province = cadreRow.createCell(count++);
            //Optional<Province> provinceOptional=Optional.ofNullable(cadre.getProvince());
            province.setCellValue(cadre.getProvince().getName());

            XSSFCell make = cadreRow.createCell(count++);
            if (phone != null) {
                make.setCellValue(phone.getPhoneMake());
            }

            XSSFCell model = cadreRow.createCell(count++);
            if (phone != null) {
                model.setCellValue(phone.getPhoneModel());
            }

            XSSFCell phoneDateIssued = cadreRow.createCell(count++);
            if (phone != null) {
                if (phone.getDateIssued() != null) {
                    phoneDateIssued.setCellValue(phone.getDateIssued());
                    phoneDateIssued.setCellStyle(cellStyle);
                } else {
                    phoneDateIssued.setCellType(XSSFCell.CELL_TYPE_BLANK);
                }

            }


            XSSFCell phoneDateRecovered = cadreRow.createCell(count++);
            if (phone != null) {
                if (phone.getDateRecovered() != null) {
                    phoneDateRecovered.setCellValue(phone.getDateRecovered());
                    phoneDateRecovered.setCellStyle(cellStyle);
                } else {
                    phoneDateRecovered.setCellType(XSSFCell.CELL_TYPE_BLANK);
                }

            }


            XSSFCell dateCreated = cadreRow.createCell(count++);
            if (phone != null) {
                if (phone.getDateCreated() != null) {
                    dateCreated.setCellValue(phone.getDateCreated());
                    dateCreated.setCellStyle(cellStyle);
                } else {
                    dateCreated.setCellType(XSSFCell.CELL_TYPE_BLANK);
                }

            }


            XSSFCell condition = cadreRow.createCell(count++);
            if (phone != null) {
                //Optional<Condition> conditionOptional=Optional.ofNullable(phone.getPhoneCondition());
                condition.setCellValue(phone.getPhoneCondition() != null ? phone.getPhoneCondition().getName() : "");
            }


            XSSFCell phoneStatus = cadreRow.createCell(count++);
            if (phone != null) {
                //Optional<PhoneStatus> statusOptional=Optional.ofNullable(phone.getPhoneStatus());
                phoneStatus.setCellValue(phone.getPhoneStatus() != null ? phone.getPhoneStatus().getName() : "");
            }


            XSSFCell imei1 = cadreRow.createCell(count++);
            if (phone != null) {
                imei1.setCellValue(phone.getImei1());
            }

            XSSFCell imei2 = cadreRow.createCell(count++);
            if (phone != null) {
                imei2.setCellValue(phone.getImei2() != null ? phone.getImei2() : "");
            }

            XSSFCell msisdn1 = cadreRow.createCell(count++);
            if (phone != null) {
                msisdn1.setCellValue(phone.getMsisdn1());
            }

            XSSFCell msisdn2 = cadreRow.createCell(count++);
            if (phone != null) {
                msisdn2.setCellValue(phone.getMsisdn2() != null ? phone.getMsisdn2() : "");
            }

            XSSFCell issues = cadreRow.createCell(count++);
            if (phone != null) {
                issues.setCellValue(phone.getPhoneIssues() != null ? phone.getPhoneIssues() : "");
            }

            XSSFCell type = cadreRow.createCell(count++);
            if (bike != null) {
                type.setCellValue(bike.getBikeType());
            }

            XSSFCell cadreDateIssued = cadreRow.createCell(count++);
            if (bike != null) {
                if (bike.getDateIssued() != null) {
                    cadreDateIssued.setCellValue(bike.getDateIssued());
                    cadreDateIssued.setCellStyle(cellStyle);
                } else {
                    cadreDateIssued.setCellType(XSSFCell.CELL_TYPE_BLANK);
                }
                //Optional.ofNullable(bike.getDateIssued()).ifPresent(cadreDateIssued::setCellValue);

            }


            XSSFCell cadreDateRecovered = cadreRow.createCell(count++);
            if (bike != null) {
                if (bike.getDateRecovered() != null) {
                    cadreDateRecovered.setCellValue(bike.getDateRecovered());
                    cadreDateRecovered.setCellStyle(cellStyle);
                } else {
                    cadreDateRecovered.setCellType(XSSFCell.CELL_TYPE_BLANK);
                }
                //Optional.ofNullable(bike.getDateRecovered()).ifPresent(cadreDateRecovered::setCellValue);

            }


            XSSFCell bikeDateCreated = cadreRow.createCell(count++);
            if (bike != null) {
                if (bike.getDateCreated() != null) {
                    bikeDateCreated.setCellValue(bike.getDateCreated());
                    dateCreated.setCellStyle(cellStyle);
                } else {

                }
                //Optional.ofNullable(cadre.getDateCreated()).ifPresent(bikeDateCreated::setCellValue);

            }


            XSSFCell bikeCondition = cadreRow.createCell(count++);
            if (bike != null) {
                //Optional<Condition> conditionOptional=Optional.ofNullable(bike.getBikeCondition());
                bikeCondition.setCellValue(bike.getBikeCondition() != null ? bike.getBikeCondition().getName() : "");
            }


            XSSFCell bikeStatus = cadreRow.createCell(count++);
            if (bike != null) {
                //Optional<PhoneStatus> statusOptional=Optional.ofNullable(bike.getBikeStatus());
                bikeStatus.setCellValue(bike.getBikeStatus() != null ? bike.getBikeStatus().getName() : "");
            }


            XSSFCell bikeIssues = cadreRow.createCell(count++);
            if (bike != null) {
                bikeIssues.setCellValue(bike.getBikeIssues() != null ? bike.getBikeIssues() : "");
            }


        }

        return workbook;
    }

}
