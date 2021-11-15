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

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.domain.util.*;
import zw.org.zvandiri.business.service.ContactReportService;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.controller.report.parallel.GenericCountReportTask;
import zw.org.zvandiri.portal.web.controller.report.parallel.UniqueContactTask;
import zw.org.zvandiri.report.api.DatabaseHeader;
import zw.org.zvandiri.report.api.service.DetailedReportService;
import zw.org.zvandiri.report.api.service.OfficeExportService;
import zw.org.zvandiri.report.api.service.parallel.NewContactReportTask;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author manatsachinyeruse@gmail.com
 */
@Controller
@RequestMapping("/report/new/contact")
public class NewContactReportController extends BaseController {

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
    List<Contact> contacts=new ArrayList<>();

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
            model.addAttribute("excelExport", "/report/new/contact/export/excel" + item.getQueryString(item.getInstance(item)));
            ForkJoinPool pool = ForkJoinPool.commonPool();
            //List items = pool.invoke(new GenericCountReportTask(DateUtil.generateArray(contactReportService.getCount(item)), contactReportService, item));
            model.addAttribute("items", contacts);
        }
        model.addAttribute("item", item.getInstance(item));
        return "report/newContactDetailedReport";
    }

    @RequestMapping(value = "/detailed", method = RequestMethod.GET)
    public String getReferralReportIndex(ModelMap model) {
        return setUpModel(model, new SearchDTO(), false);
    }

    @RequestMapping(value = "/detailed", method = RequestMethod.POST)
    public String getReferralReportIndex(ModelMap model, @ModelAttribute("item") @Valid SearchDTO item, BindingResult result) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        contacts = pool.invoke(new GenericCountReportTask(DateUtil.generateArray(contactReportService.getCount(item)), contactReportService, item));
        model.addAttribute("items", contacts);
        return setUpModel(model, item, true);
    }

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void getExcelExport(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Detailed_Contact_Report");
        forceDownLoadDatabase(contactedPatients(item), name, response);

    }

    public XSSFWorkbook contactedPatients(SearchDTO dto) {



        XSSFWorkbook workbook = new XSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
        // add contact assessments
        Sheet uncontactedClientsDetails = workbook.createSheet("Contacts");
        int assessmentRowNum = 0;
        Row uncontactedRow = uncontactedClientsDetails.createRow(assessmentRowNum++);
        int assessmentCellNum = 0;
        for (String title : DatabaseHeader.CONTACT_HEADER) {
            Cell cell = uncontactedRow.createCell(assessmentCellNum++);
            cell.setCellValue(title);
        }

        for (Contact contact : contacts) {

            int count = 0;
            uncontactedRow = uncontactedClientsDetails.createRow(assessmentRowNum++);

            Cell oi = uncontactedRow.createCell(count++);
            Optional.ofNullable(contact.getPatient().getoINumber()).ifPresent(oi::setCellValue);

            Cell patientName = uncontactedRow.createCell(count++);
            Optional.ofNullable(contact.getPatient().getName()).ifPresent(patientName::setCellValue);

            Cell dateOfBirth = uncontactedRow.createCell(count++);
            Optional.ofNullable(contact.getPatient().getDateOfBirth()).ifPresent(dateOfBirth::setCellValue);
            dateOfBirth.setCellStyle(cellStyle);

            Cell age = uncontactedRow.createCell(count++);
            Optional.ofNullable(contact.getPatient().getAge()).ifPresent(age::setCellValue);

            Cell sex = uncontactedRow.createCell(count++);
            Optional<Gender> sexOptional=Optional.ofNullable(contact.getPatient().getGender());
            sex.setCellValue(sexOptional.isPresent()? sexOptional.get().getName(): null);

            Cell province = uncontactedRow.createCell(count++);
            Optional<Province> provinceOptional=Optional.ofNullable(contact.getPatient().getPrimaryClinic().getDistrict().getProvince());
            province.setCellValue(provinceOptional.isPresent()? provinceOptional.get().getName(): null);

            Cell district = uncontactedRow.createCell(count++);
            Optional<District> districtOptional=Optional.ofNullable(contact.getPatient().getPrimaryClinic().getDistrict());
            district.setCellValue(districtOptional.isPresent()? districtOptional.get().getName(): null);

            Cell primaryClinic = uncontactedRow.createCell(count++);
            Optional<Facility> facilityOptional=Optional.ofNullable(contact.getPatient().getPrimaryClinic());
            primaryClinic.setCellValue(facilityOptional.isPresent()? facilityOptional.get().getName(): null);

            Cell entry = uncontactedRow.createCell(count++);
            Optional.ofNullable(contact.getDateCreated()).ifPresent(entry::setCellValue);
            entry.setCellStyle(cellStyle);

            Cell contactDate = uncontactedRow.createCell(count++);
            Optional.ofNullable(contact.getContactDate()).ifPresent(contactDate::setCellValue);
            contactDate.setCellStyle(cellStyle);

            Cell care = uncontactedRow.createCell(count++);
            Optional<CareLevel> careOptional=Optional.ofNullable(contact.getCareLevel());
            care.setCellValue(careOptional.isPresent()? careOptional.get().getName(): " ");

            Cell loc = uncontactedRow.createCell(count++);
            Optional<Location> locOptional=Optional.ofNullable(contact.getLocation());
            loc.setCellValue(locOptional.isPresent()? locOptional.get().getName(): " ");

            Cell position = uncontactedRow.createCell(count++);
            Optional<Position> posOptional=Optional.ofNullable(contact.getPosition());
            position.setCellValue(posOptional.isPresent()? posOptional.get().getName(): " ");

            Cell reason = uncontactedRow.createCell(count++);
            Optional<Reason> reasonOptional=Optional.ofNullable(contact.getReason());
            reason.setCellValue(reasonOptional.isPresent()? reasonOptional.get().getName(): " ");

            Cell follow = uncontactedRow.createCell(count++);
            Optional<FollowUp> followOptional=Optional.ofNullable(contact.getFollowUp());
            follow.setCellValue(followOptional.isPresent()? followOptional.get().getName(): " ");

            Cell subjective = uncontactedRow.createCell(count++);
            Optional.ofNullable(contact.getSubjective()).ifPresent(subjective::setCellValue);

            Cell objective = uncontactedRow.createCell(count++);
            Optional.ofNullable(contact.getObjective()).ifPresent(objective::setCellValue);

            Cell plan = uncontactedRow.createCell(count++);
            Optional.ofNullable(contact.getPlan()).ifPresent(plan::setCellValue);

            Cell action = uncontactedRow.createCell(count++);
            Optional<ActionTaken> actionOptional=Optional.ofNullable(contact.getActionTaken());
            action.setCellValue(actionOptional.isPresent()? actionOptional.get().getName(): null);

            Cell last = uncontactedRow.createCell(count++);
            Optional.ofNullable(contact.getLastClinicAppointmentDate()).ifPresent(last::setCellValue);
            last.setCellStyle(cellStyle);

            Cell attended = uncontactedRow.createCell(count++);
            Optional<YesNo> attendedOptional=Optional.ofNullable(contact.getAttendedClinicAppointment());
            attended.setCellValue(attendedOptional.isPresent()? attendedOptional.get().getName(): null);

            Cell next = uncontactedRow.createCell(count++);
            Optional.ofNullable(contact.getNextClinicAppointmentDate()).ifPresent(next::setCellValue);
            next.setCellStyle(cellStyle);

            Cell outcome = uncontactedRow.createCell(count++);
            Optional<VisitOutcome> outcomeOptional=Optional.ofNullable(contact.getVisitOutcome());
            outcome.setCellValue(outcomeOptional.isPresent()? outcomeOptional.get().getName(): null);

            Cell isCats = uncontactedRow.createCell(count++);
            Optional<YesNo> catOptional=Optional.ofNullable(contact.getPatient().getCat());
            isCats.setCellValue(catOptional.isPresent()? catOptional.get().getName(): null);

            Cell youngMumGroup = uncontactedRow.createCell(count++);
            Optional<YesNo> ymmOptional=Optional.ofNullable(contact.getPatient().getYoungMumGroup());
            youngMumGroup.setCellValue(ymmOptional.isPresent()? ymmOptional.get().getName(): null);

            Cell ymd = uncontactedRow.createCell(count++);
            Optional<YesNo> ymdOptional=Optional.ofNullable(contact.getPatient().getYoungDadGroup());
            ymd.setCellValue(ymdOptional.isPresent()? ymdOptional.get().getName(): null);

        }

        return  workbook;

    }


}
