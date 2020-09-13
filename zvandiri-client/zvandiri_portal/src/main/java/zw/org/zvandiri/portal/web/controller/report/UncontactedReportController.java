package zw.org.zvandiri.portal.web.controller.report;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.Assessment;
import zw.org.zvandiri.business.domain.Contact;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.repo.ContactRepo;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.DatabaseHeader;
import zw.org.zvandiri.report.api.service.DetailedReportService;
import zw.org.zvandiri.report.api.service.OfficeExportService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/report/contact/uncontacted/")
public class UncontactedReportController extends BaseController {

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

    @Resource
    private DetailedPatientReportService detailedPatientReportService;
    @Resource
    private MortalityService mortalityService;

    @Resource
    PatientReportService patientReportService;

    @Resource
    private ContactRepo contactService;

    public String setUpModel(ModelMap model, SearchDTO item, boolean post) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "Mortality Report");
        model.addAttribute("provinces", provinceService.getAll());
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
            }
        }
        if (post) {
            model.addAttribute("excelExport", "/report/morbidity/export/excel" + item.getQueryString(item.getInstance(item)));
            model.addAttribute("items", contactReportService.get(item.getInstance(item)));
        }
        model.addAttribute("item", item.getInstance(item));
        return "report/uncontactedClients";
    }

    @RequestMapping(value = "/range", method = RequestMethod.GET)
    public String getUncontactedSetup(ModelMap model) {
        return setUpModel(model, new SearchDTO(), false);
    }

    @RequestMapping(value = "/range", method = RequestMethod.POST)
    public String getUncontactedClients(HttpServletResponse response,ModelMap model, @ModelAttribute("item") @Valid SearchDTO item, BindingResult result) {

        String name = DateUtil.getFriendlyFileName("Uncontacted_Clients_Report");
        forceDownLoadDatabase(uncontactedPatients(item), name, response);
        return setUpModel(model, item, true);
    }


    public Workbook uncontactedPatients(SearchDTO dto) {
        Workbook workbook = new XSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
        // add contact assessments
        Sheet uncontactedClientsDetails = workbook.createSheet("Uncontacted Clients");
        int assessmentRowNum = 0;
        Row assessmentHeader = uncontactedClientsDetails.createRow(assessmentRowNum++);
        int assessmentCellNum = 0;
        for (String title : DatabaseHeader.UNCONTACTED_CLIENTS_HEADER) {
            Cell cell = assessmentHeader.createCell(assessmentCellNum++);
            cell.setCellValue(title);
        }

        List<Patient> patients = patientReportService.getUncontactedClients(dto.getInstance(dto));


        for (Patient patient : patients) {

                    int count = 0;
                    assessmentHeader = uncontactedClientsDetails.createRow(assessmentRowNum++);
                    Cell id = assessmentHeader.createCell(count);
                    id.setCellValue(patient.getPatientNumber());
                    Cell patientName = assessmentHeader.createCell(++count);
                    patientName.setCellValue(patient.getName());
                    Cell dateOfBirth = assessmentHeader.createCell(++count);
                    dateOfBirth.setCellValue(patient.getDateOfBirth());
                    dateOfBirth.setCellStyle(cellStyle);
                    Cell age = assessmentHeader.createCell(++count);
                    age.setCellValue(patient.getAge());
                    Cell sex = assessmentHeader.createCell(++count);
                    sex.setCellValue(patient.getGender().getName());
                    Cell province = assessmentHeader.createCell(++count);
                    province.setCellValue(patient.getPrimaryClinic().getDistrict().getProvince().getName());
                    Cell district = assessmentHeader.createCell(++count);
                    district.setCellValue(patient.getPrimaryClinic().getDistrict().getName());
                    Cell primaryClinic = assessmentHeader.createCell(++count);
                    primaryClinic.setCellValue(patient.getPrimaryClinic().getName());



            }

        return workbook;
    }



}
