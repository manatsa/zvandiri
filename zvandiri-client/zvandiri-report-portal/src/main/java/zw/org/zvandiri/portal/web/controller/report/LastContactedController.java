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
import zw.org.zvandiri.business.domain.util.FollowUp;
import zw.org.zvandiri.business.domain.util.Gender;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.LastContactedDTO;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.controller.report.parallel.LastContactedClientTask;
import zw.org.zvandiri.portal.web.controller.report.parallel.TbIptTask;
import zw.org.zvandiri.portal.web.controller.report.parallel.UnContactedClientTask;
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
@RequestMapping("/report/last-contacted")
public class LastContactedController extends BaseController {

    @Resource
    private ProvinceService provinceService;

    @Resource
    private DistrictService districtService;

    @Resource
    private FacilityService facilityService;

    @Resource
    private SupportGroupService supportGroupService;

    @Resource
    LastContactedService lastContactedService;

    List<Contact> contacts = new ArrayList<>();

    public void setUpModel(ModelMap model, SearchDTO item, boolean post) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "Patient Last Contact Report");
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("item", item.getInstance(item));
        model.addAttribute("excelExport", "/report/last-contacted/export/excel" + item.getQueryString(item.getInstance(item)));

        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
                model.addAttribute("supportGroups", supportGroupService.getByDistrict(item.getDistrict()));
            }
        }
        if (post) {
            model.addAttribute("excelExport", "/report/last-contacted/export/excel" + item.getQueryString(item.getInstance(item)));
            model.addAttribute("items", contacts);
        }
    }

    @RequestMapping(value = "/range", method = RequestMethod.GET)
    public String getReportIndex(ModelMap model) {
        setUpModel(model, new SearchDTO(), false);
        return "report/lastContactedReport";
    }

    @RequestMapping(value = "/range", method = RequestMethod.POST)
    public String getReportResult(ModelMap model, @ModelAttribute("item") SearchDTO item) {
        item = getUserLevelObjectState(item);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        //contacts=pool.invoke(new LastContactedClientTask(DateUtil.generateArray(lastContactedService.countLastContacted(item)), lastContactedService, item));
        contacts = lastContactedService.get(item);
        setUpModel(model, item, true);
        return "report/lastContactedReport";
    }

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void getExcelExportHealthCenter(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Client_Last Contact_Report");
        forceDownLoadXLSX(createLastContactWorkbook(), name, response);
    }

    public XSSFWorkbook createLastContactWorkbook() {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFCreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        // tb Ipt here
        XSSFSheet lastContactSheet = workbook.createSheet("Patient_Last_Contact");
        int contactRowNum = 0;
        XSSFRow contactRow = lastContactSheet.createRow(contactRowNum++);
        int lastContactedDTOCellNum = 0;
        for (String title : DatabaseHeader.LAST_CONTACT_HEADER) {
            XSSFCell cell = contactRow.createCell(lastContactedDTOCellNum++);
            cell.setCellValue(title);
        }

        for (Contact contact : contacts) {
            int count = 0;

            contactRow = lastContactSheet.createRow(contactRowNum++);

            XSSFCell id = contactRow.createCell(count);
            //id.setCellValue(contact.getPatient().getoINumber());
            Optional.ofNullable(contact.getPatient().getoINumber()).ifPresent(id::setCellValue);

            XSSFCell patientName = contactRow.createCell(++count);
            //patientName.setCellValue(contact.getPatient().getName());
            Optional.ofNullable(contact.getPatient().getFirstName()).ifPresent(patientName::setCellValue);

            XSSFCell lastName = contactRow.createCell(++count);
            //patientName.setCellValue(contact.getPatient().getName());
            Optional.ofNullable(contact.getPatient().getLastName()).ifPresent(lastName::setCellValue);

            XSSFCell sex = contactRow.createCell(++count);
            //sex.setCellValue(contact.getPatient().getGender().getName());
            Optional<Gender> sexOptional=Optional.ofNullable(contact.getPatient().getGender());
            sex.setCellValue(sexOptional.isPresent()? sexOptional.get().getName(): null);


            XSSFCell dateOfBirth = contactRow.createCell(++count);
            Optional.ofNullable(contact.getPatient().getDateOfBirth()).ifPresent(dateOfBirth::setCellValue);
            dateOfBirth.setCellStyle(cellStyle);

            XSSFCell dateJoined = contactRow.createCell(++count);
            Optional.ofNullable(contact.getPatient().getDateJoined()).ifPresent(dateJoined::setCellValue);
            dateJoined.setCellStyle(cellStyle);

            XSSFCell address = contactRow.createCell(++count);
            //address.setCellValue(contact.getPatient().getAddress());
            Optional.ofNullable(contact.getPatient().getAddress()).ifPresent(address::setCellValue);

            XSSFCell phone = contactRow.createCell(++count);
            Optional.ofNullable(contact.getPatient().getMobileNumber()).ifPresent(phone::setCellValue);

            XSSFCell status = contactRow.createCell(++count);
            Optional.ofNullable(contact.getPatient().getStatus().getName()).ifPresent(status::setCellValue);

            XSSFCell isCat = contactRow.createCell(++count);
            //phone.setCellValue(contact.getPatient().getMobileNumber());
            Optional<YesNo> isCatOptional=Optional.ofNullable(contact.getPatient().getCat());
            isCat.setCellValue(isCatOptional.isPresent()? isCatOptional.get().getName(): null);

            XSSFCell isYMM = contactRow.createCell(++count);
            //phone.setCellValue(contact.getPatient().getMobileNumber());
            Optional<YesNo> isYMMOptional=Optional.ofNullable(contact.getPatient().getYoungMumGroup());
            isYMM.setCellValue(isYMMOptional.isPresent()? isYMMOptional.get().getName(): null);

            XSSFCell primaryClinic = contactRow.createCell(++count);
            Optional<Facility> facilityOptional=Optional.ofNullable(contact.getPatient().getPrimaryClinic());
            primaryClinic.setCellValue(facilityOptional.isPresent()? facilityOptional.get().getName(): null);

            XSSFCell district = contactRow.createCell(++count);
            Optional<District> districtOptional=Optional.ofNullable(contact.getPatient().getPrimaryClinic().getDistrict());
            district.setCellValue(districtOptional.isPresent()? districtOptional.get().getName(): null);

            XSSFCell province = contactRow.createCell(++count);
            Optional<Province> provinceOptional=Optional.ofNullable(contact.getPatient().getPrimaryClinic().getDistrict().getProvince());
            province.setCellValue(provinceOptional.isPresent()? provinceOptional.get().getName(): null);

            XSSFCell contactDate = contactRow.createCell(++count);
            Optional.ofNullable(contact.getContactDate()).ifPresent(contactDate::setCellValue);
            contactDate.setCellStyle(cellStyle);

            XSSFCell followup = contactRow.createCell(++count);
            //followup.setCellValue(contact.getFollowUp().getName());
            Optional<FollowUp> followupOptional=Optional.ofNullable(contact.getFollowUp());
            followup.setCellValue(followupOptional.isPresent()? followupOptional.get().getName(): null);

            XSSFCell cd4Count = contactRow.createCell(++count);
            //followup.setCellValue(contact.getFollowUp().getName());
            Optional<InvestigationTest> cd4Optional=Optional.ofNullable(contact.getCd4Count());

            if (cd4Optional.isPresent()) {
                Optional.ofNullable(cd4Optional.get().getResult()).ifPresent(cd4Count::setCellValue);
            } else {
                cd4Count.setCellValue("");
            }

            XSSFCell vl = contactRow.createCell(++count);
            //followup.setCellValue(contact.getFollowUp().getName());
            Optional<InvestigationTest> vlOptional=Optional.ofNullable(contact.getViralLoad());
            if (vlOptional.isPresent()) {
                Optional.ofNullable(vlOptional.get().getResult()).ifPresent(vl::setCellValue);
            } else {
                vl.setCellValue("");
            }

            XSSFCell plan = contactRow.createCell(++count);
            Optional.ofNullable(contact.getPlan()).ifPresent(plan::setCellValue);


        }

        return workbook;
    }

}
