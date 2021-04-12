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

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.TbIpt;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.DatabaseHeader;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.SupportGroupService;
import zw.org.zvandiri.business.service.TbIptService;
import zw.org.zvandiri.portal.web.controller.report.parallel.TbIptTask;

/**
 *
 * @author tasu
 */
@Controller
@RequestMapping("/report/tb-screening")
public class TbScreeningReportController extends BaseController {

    @Resource
    private ProvinceService provinceService;

    @Resource
    private DistrictService districtService;

    @Resource
    private FacilityService facilityService;

    @Resource
    private SupportGroupService supportGroupService;

    @Resource
    TbIptService tbIptService;

    List<TbIpt> tbIpts = new ArrayList<>();

    public void setUpModel(ModelMap model, SearchDTO item, boolean post) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "TB Screening Report");
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("item", item.getInstance(item));
        model.addAttribute("excelExport", "/report/tb-screening/screening/export/excel" + item.getQueryString(item.getInstance(item)));

        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
                model.addAttribute("supportGroups", supportGroupService.getByDistrict(item.getDistrict()));
            }
        }
        if (post) {
            model.addAttribute("excelExport", "/report/tb-screening/screening/export/excel" + item.getQueryString(item.getInstance(item)));
            model.addAttribute("items", tbIpts);
        }
    }

    @RequestMapping(value = "/screening", method = RequestMethod.GET)
    public String getReportIndex(ModelMap model) {
        setUpModel(model, new SearchDTO(), false);
        return "report/tbScreenReport";
    }

    @RequestMapping(value = "/screening", method = RequestMethod.POST)
    public String getReportResult(ModelMap model, @ModelAttribute("item") SearchDTO item) {
        item = getUserLevelObjectState(item);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        tbIpts = pool.invoke(new TbIptTask(DateUtil.generateArray(tbIptService.count(item)), tbIptService, item));
        setUpModel(model, item, true);
        return "report/tbScreenReport";
    }

    @RequestMapping(value = "/screening/export/excel", method = RequestMethod.GET)
    public void getExcelExportHealthCenter(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("TB_Screening_Report");
        forceDownLoadXLSX(createTBIPTWorkbook(item), name, response);
    }

    public XSSFWorkbook createTBIPTWorkbook(SearchDTO dto) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFCreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        // tb Ipt here
        XSSFSheet tbIptDetails = workbook.createSheet("Patient_TBIPT");
        int tbIptRowNum = 0;
        XSSFRow tbIptRow = tbIptDetails.createRow(tbIptRowNum++);
        int tbIptCellNum = 0;
        for (String title : DatabaseHeader.TB_IPT_HEADER) {
            XSSFCell cell = tbIptRow.createCell(tbIptCellNum++);
            cell.setCellValue(title);
        }

        for (TbIpt tbIpt : tbIpts) {
            int count = 0;
            tbIptRow = tbIptDetails.createRow(tbIptRowNum++);
            XSSFCell id = tbIptRow.createCell(count);
            id.setCellValue(tbIpt.getPatient().getPatientNumber());
            XSSFCell patientName = tbIptRow.createCell(++count);
            patientName.setCellValue(tbIpt.getPatient().getName());
            XSSFCell dateOfBirth = tbIptRow.createCell(++count);
            dateOfBirth.setCellValue(tbIpt.getPatient().getDateOfBirth());
            dateOfBirth.setCellStyle(cellStyle);
            XSSFCell age = tbIptRow.createCell(++count);
            age.setCellValue(tbIpt.getPatient().getAge());
            XSSFCell sex = tbIptRow.createCell(++count);
            sex.setCellValue(tbIpt.getPatient().getGender().getName());
            Cell cat = tbIptRow.createCell(++count);
            cat.setCellValue(tbIpt.getPatient().getCat() != null ? tbIpt.getPatient().getCat().getName() : "");
            XSSFCell province = tbIptRow.createCell(++count);
            province.setCellValue(tbIpt.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
            XSSFCell district = tbIptRow.createCell(++count);
            district.setCellValue(tbIpt.getPatient().getPrimaryClinic().getDistrict().getName());
            XSSFCell primaryClinic = tbIptRow.createCell(++count);
            primaryClinic.setCellValue(tbIpt.getPatient().getPrimaryClinic().getName());

            XSSFCell screenedForTb = tbIptRow.createCell(++count);
            screenedForTb.setCellValue(tbIpt.getScreenedForTb() != null ? tbIpt.getScreenedForTb().getName() : "");
            XSSFCell dateScreened = tbIptRow.createCell(++count);
            if (tbIpt.getDateScreened() != null) {
                dateScreened.setCellValue(tbIpt.getDateScreened());
                dateScreened.setCellStyle(cellStyle);
            } else {
                dateScreened.setCellValue("");
            }
            XSSFCell identifiedWithTb = tbIptRow.createCell(++count);
            identifiedWithTb.setCellValue(tbIpt.getIdentifiedWithTb() != null ? tbIpt.getIdentifiedWithTb().getName() : "");
            XSSFCell tbIdentificationOutcome = tbIptRow.createCell(++count);
            tbIdentificationOutcome.setCellValue(tbIpt.getTbIdentificationOutcome() != null ? tbIpt.getTbIdentificationOutcome().getName() : "");
            XSSFCell dateStartedTreatment = tbIptRow.createCell(++count);
            if (tbIpt.getDateStartedTreatment() != null) {
                dateStartedTreatment.setCellValue(tbIpt.getDateStartedTreatment());
                dateStartedTreatment.setCellStyle(cellStyle);
            } else {
                dateStartedTreatment.setCellValue("");
            }
            XSSFCell referralForSputum = tbIptRow.createCell(++count);
            referralForSputum.setCellValue(tbIpt.getReferralForSputum());
            XSSFCell tbTreatmentOutcome = tbIptRow.createCell(++count);
            tbTreatmentOutcome.setCellValue(tbIpt.getTbTreatmentOutcome() != null ? tbIpt.getTbTreatmentOutcome().getName() : "");
            XSSFCell referredForIpt = tbIptRow.createCell(++count);
            referredForIpt.setCellValue(tbIpt.getReferredForIpt() != null ? tbIpt.getReferredForIpt().getName() : "");
            XSSFCell onIpt = tbIptRow.createCell(++count);
            onIpt.setCellValue(tbIpt.getOnIpt() != null ? tbIpt.getOnIpt().getName() : "");
            XSSFCell dateStartedIpt = tbIptRow.createCell(++count);
            if (tbIpt.getDateStartedTreatment() != null) {
                dateStartedIpt.setCellValue(tbIpt.getDateStartedTreatment());
                dateStartedIpt.setCellStyle(cellStyle);
            } else {
                dateStartedIpt.setCellValue("");
            }
        }

        return workbook;
    }

}
