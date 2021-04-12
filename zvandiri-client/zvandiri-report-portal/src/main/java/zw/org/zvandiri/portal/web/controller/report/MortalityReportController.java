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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.Mortality;
import zw.org.zvandiri.business.domain.util.PatientChangeEvent;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.DatabaseHeader;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import zw.org.zvandiri.portal.web.controller.report.parallel.MortalityTask;

/**
 *
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/report/mortality")
public class MortalityReportController extends BaseController {

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
    MortalityService mortalityService;

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
            ForkJoinPool pool = ForkJoinPool.commonPool();
            List results = pool.invoke(new MortalityTask(DateUtil.generateArray(mortalityService.count(item)), mortalityService, item));
            model.addAttribute("items", results);
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

    @RequestMapping(value = {"/range"}, method = RequestMethod.POST)
    public String getMortalityRangeIndexPost(ModelMap model, @ModelAttribute("item") @Valid SearchDTO item) {
        model.addAttribute("pageTitle", APP_PREFIX + "Mortality Detailed Report");
        setUpModel(model, item, Boolean.TRUE, Boolean.FALSE);
        return "report/mortalityDetailedReport";
    }

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void getExcelExport(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Detailed_Mortality_Report");
        forceDownLoadDatabase(createMortalityWorkbook(item), name, response);
    }

    public XSSFWorkbook createMortalityWorkbook(SearchDTO dto) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle XSSFCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        XSSFCellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        ForkJoinPool pool = ForkJoinPool.commonPool();
        List<Mortality> mortalitys = pool.invoke(new MortalityTask(DateUtil.generateArray(mortalityService.count(dto)), mortalityService, dto));

        XSSFSheet mortalityDetails = workbook.createSheet("Patient_Mortality");
        int mortalityRowNum = 0;
        XSSFRow mortalityRow = mortalityDetails.createRow(mortalityRowNum++);
        int mortalityXSSFCellNum = 0;
        for (String title : DatabaseHeader.MORTALITY_HEADER) {
            XSSFCell XSSFCell = mortalityRow.createCell(mortalityXSSFCellNum++);
            XSSFCell.setCellValue(title);
        }

        for (Mortality mortality : mortalitys) {
            int count = 0;
            mortalityRow = mortalityDetails.createRow(mortalityRowNum++);
            XSSFCell id = mortalityRow.createCell(count);
            id.setCellValue(mortality.getPatient().getPatientNumber());
            XSSFCell patientName = mortalityRow.createCell(++count);
            patientName.setCellValue(mortality.getPatient().getName());
            XSSFCell dateOfBirth = mortalityRow.createCell(++count);
            dateOfBirth.setCellValue(mortality.getPatient().getDateOfBirth());
            dateOfBirth.setCellStyle(XSSFCellStyle);
            XSSFCell age = mortalityRow.createCell(++count);
            age.setCellValue(mortality.getPatient().getAge());
            XSSFCell sex = mortalityRow.createCell(++count);
            sex.setCellValue(mortality.getPatient().getGender().getName());
            Cell cat = mortalityRow.createCell(++count);
            cat.setCellValue(mortality.getPatient().getCat() != null ? mortality.getPatient().getCat().getName() : "");
            XSSFCell province = mortalityRow.createCell(++count);
            province.setCellValue(mortality.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
            XSSFCell district = mortalityRow.createCell(++count);
            district.setCellValue(mortality.getPatient().getPrimaryClinic().getDistrict().getName());
            XSSFCell primaryClinic = mortalityRow.createCell(++count);
            primaryClinic.setCellValue(mortality.getPatient().getPrimaryClinic().getName());

            XSSFCell dateOfDeath = mortalityRow.createCell(++count);
            if (mortality.getDateOfDeath() != null) {
                dateOfDeath.setCellValue(mortality.getDateOfDeath());
                dateOfDeath.setCellStyle(XSSFCellStyle);
            } else {
                dateOfDeath.setCellValue("");
            }
            XSSFCell causeOfDeath = mortalityRow.createCell(++count);
            causeOfDeath.setCellValue(mortality.getCauseOfDeath() != null ? mortality.getCauseOfDeath().getName() : "");
            XSSFCell causeOfDeathDetails = mortalityRow.createCell(++count);
            causeOfDeathDetails.setCellValue(mortality.getCauseOfDeathDetails());
            XSSFCell receivingEnhancedCare = mortalityRow.createCell(++count);
            receivingEnhancedCare.setCellValue(mortality.getReceivingEnhancedCare() != null ? mortality.getReceivingEnhancedCare().getName() : "");
            XSSFCell datePutOnEnhancedCare = mortalityRow.createCell(++count);
            if (mortality.getDatePutOnEnhancedCare() != null) {
                datePutOnEnhancedCare.setCellValue(mortality.getDatePutOnEnhancedCare());
                datePutOnEnhancedCare.setCellStyle(XSSFCellStyle);
            } else {
                datePutOnEnhancedCare.setCellValue("");
            }
            XSSFCell caseBackground = mortalityRow.createCell(++count);
            caseBackground.setCellValue(mortality.getCaseBackground());
            XSSFCell careProvided = mortalityRow.createCell(++count);
            careProvided.setCellValue(mortality.getCareProvided());
            XSSFCell home = mortalityRow.createCell(++count);
            home.setCellValue(mortality.getHome());

            XSSFCell beneficiary = mortalityRow.createCell(++count);
            beneficiary.setCellValue(mortality.getBeneficiary());
            XSSFCell facility = mortalityRow.createCell(++count);
            facility.setCellValue(mortality.getFacility());
            XSSFCell cats = mortalityRow.createCell(++count);
            cats.setCellValue(mortality.getCats());
            XSSFCell zm = mortalityRow.createCell(++count);
            zm.setCellValue(mortality.getZm());
            XSSFCell other = mortalityRow.createCell(++count);
            other.setCellValue(mortality.getOther());
            XSSFCell contactWithZM = mortalityRow.createCell(++count);
            contactWithZM.setCellValue(mortality.getContactWithZM() != null ? mortality.getContactWithZM().getName() : "");
            XSSFCell dateOfContactWithZim = mortalityRow.createCell(++count);
            if (mortality.getDateOfContactWithZim() != null) {
                dateOfContactWithZim.setCellValue(mortality.getDateOfContactWithZim());
                dateOfContactWithZim.setCellStyle(XSSFCellStyle);
            } else {
                dateOfContactWithZim.setCellValue("");
            }
            XSSFCell descriptionOfCase = mortalityRow.createCell(++count);
            descriptionOfCase.setCellValue(mortality.getDescriptionOfCase());
            XSSFCell learningPoints = mortalityRow.createCell(++count);
            learningPoints.setCellValue(mortality.getLearningPoints());
            XSSFCell actionPlan = mortalityRow.createCell(++count);
            actionPlan.setCellValue(mortality.getActionPlan());

        }

        return workbook;
    }

}
