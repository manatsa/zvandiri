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
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.LastContactedDTO;
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

    List<LastContactedDTO> contacts = new ArrayList<>();

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

        for (LastContactedDTO lastContactedDTO : contacts) {
            int count = 0;

            contactRow = lastContactSheet.createRow(contactRowNum++);

            XSSFCell id = contactRow.createCell(count);
            id.setCellValue(lastContactedDTO.getOiNumber());

            XSSFCell patientName = contactRow.createCell(++count);
            patientName.setCellValue(lastContactedDTO.getFullName());

            XSSFCell sex = contactRow.createCell(++count);
            sex.setCellValue(lastContactedDTO.getGender());

            XSSFCell dateOfBirth = contactRow.createCell(++count);
            dateOfBirth.setCellValue(lastContactedDTO.getDob());
            dateOfBirth.setCellStyle(cellStyle);

            XSSFCell dateJoined = contactRow.createCell(++count);
            dateJoined.setCellValue(lastContactedDTO.getDateJoined());
            dateJoined.setCellStyle(cellStyle);

            XSSFCell address = contactRow.createCell(++count);
            address.setCellValue(lastContactedDTO.getAddress());

            XSSFCell phone = contactRow.createCell(++count);
            phone.setCellValue(lastContactedDTO.getMobileNumber());

            XSSFCell primaryClinic = contactRow.createCell(++count);
            primaryClinic.setCellValue(lastContactedDTO.getFacility());

            XSSFCell district = contactRow.createCell(++count);
            district.setCellValue(lastContactedDTO.getDistrict());

            XSSFCell province = contactRow.createCell(++count);
            province.setCellValue(lastContactedDTO.getProvince());

            XSSFCell contactDate = contactRow.createCell(++count);
            contactDate.setCellValue(lastContactedDTO.getContactDate());
            contactDate.setCellStyle(cellStyle);

            XSSFCell followup = contactRow.createCell(++count);
            followup.setCellValue(lastContactedDTO.getFollowup());

        }

        return workbook;
    }

}
