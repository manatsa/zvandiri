package zw.org.zvandiri.portal.web.controller.report;

import java.util.ArrayList;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.Patient;
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
import zw.org.zvandiri.portal.web.controller.report.parallel.UnContactedClientTask;

@Controller
@RequestMapping("/report/uncontacted")
public class UncontactedReportController extends BaseController {

    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private FacilityService facilityService;
    @Resource
    PatientReportService patientReportService;

    List<Patient> patients = new ArrayList<>();

    public String setUpModel(ModelMap model, SearchDTO item, boolean post) {
        item = getUserLevelObjectState(item);

        model.addAttribute("pageTitle", APP_PREFIX + "Uncontacted Clients Report");
        model.addAttribute("provinces", provinceService.getAll());
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
            }
        }
        if (post) {
            model.addAttribute("excelExport", "/report/uncontacted/export/excel" + item.getQueryString(item.getInstance(item)));
            model.addAttribute("items", patients);
        }
        model.addAttribute("item", item.getInstance(item));
        return "report/uncontactedClients";
    }

    @RequestMapping(value = "/range", method = RequestMethod.GET)
    public String getUncontactedSetup(ModelMap model) {
        return setUpModel(model, new SearchDTO(), false);
    }

    @RequestMapping(value = "/range", method = RequestMethod.POST)
    public String getUncontactedClients(HttpServletResponse response, ModelMap model, @ModelAttribute("item") @Valid SearchDTO item, BindingResult result) {
        item = getUserLevelObjectState(item);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        patients = pool.invoke(new UnContactedClientTask(DateUtil.generateArray(patientReportService.countUncontacted(item)), patientReportService, item));
        return setUpModel(model, item, true);
    }

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void getExcelExport(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Uncontacted_Clients_Report");
        forceDownLoadDatabase(uncontactedPatients(item), name, response);
    }

    public XSSFWorkbook uncontactedPatients(SearchDTO dto) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
        // add contact assessments
        Sheet uncontactedClientsDetails = workbook.createSheet("Uncontacted Clients");
        int assessmentRowNum = 0;
        Row uncontactedRow = uncontactedClientsDetails.createRow(assessmentRowNum++);
        int assessmentCellNum = 0;
        for (String title : DatabaseHeader.UNCONTACTED_CLIENTS_HEADER) {
            Cell cell = uncontactedRow.createCell(assessmentCellNum++);
            cell.setCellValue(title);
        }

        for (Patient patient : patients) {

            int count = 0;
            uncontactedRow = uncontactedClientsDetails.createRow(assessmentRowNum++);

            Cell patientName = uncontactedRow.createCell(count++);
            patientName.setCellValue(patient.getName());

            Cell dateOfBirth = uncontactedRow.createCell(count++);
            dateOfBirth.setCellValue(patient.getDateOfBirth());
            dateOfBirth.setCellStyle(cellStyle);

            Cell age = uncontactedRow.createCell(count++);
            age.setCellValue(patient.getAge());

            Cell sex = uncontactedRow.createCell(count++);
            sex.setCellValue(patient.getGender().getName());

            Cell cat = uncontactedRow.createCell(count++);
            cat.setCellValue(patient.getCat() != null ? patient.getCat().getName() : "");

            Cell address = uncontactedRow.createCell(count++);
            address.setCellValue(patient.getAddress());

            Cell address1 = uncontactedRow.createCell(count++);
            address1.setCellValue(patient.getAddress1());

            Cell phone = uncontactedRow.createCell(count++);
            phone.setCellValue(patient.getMobileNumber() == null ? "" : patient.getMobileNumber());
            Cell phone1 = uncontactedRow.createCell(count++);
            phone1.setCellValue(patient.getSecondaryMobileNumber() == null ? "" : patient.getSecondaryMobileNumber());
            Cell province = uncontactedRow.createCell(count++);
            province.setCellValue(patient.getPrimaryClinic().getDistrict().getProvince().getName());
            Cell district = uncontactedRow.createCell(count++);
            district.setCellValue(patient.getPrimaryClinic().getDistrict().getName() == null ? "" : patient.getPrimaryClinic().getDistrict().getName());
            Cell primaryClinic = uncontactedRow.createCell(count++);
            primaryClinic.setCellValue(patient.getPrimaryClinic().getName() == null ? "" : patient.getPrimaryClinic().getName());

        }

        return workbook;
    }

}
