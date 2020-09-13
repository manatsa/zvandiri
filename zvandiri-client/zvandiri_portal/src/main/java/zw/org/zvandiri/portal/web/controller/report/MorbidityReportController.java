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
@RequestMapping("/report/morbidity")
public class MorbidityReportController extends BaseController {

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
        return "report/morbidityDetailedReport";
    }

    @RequestMapping(value = "/range", method = RequestMethod.GET)
    public String getReferralReportIndex(ModelMap model) {
        return setUpModel(model, new SearchDTO(), false);
    }

    @RequestMapping(value = "/range", method = RequestMethod.POST)
    public String getReferralReportIndex(HttpServletResponse response,ModelMap model, @ModelAttribute("item") @Valid SearchDTO item, BindingResult result) {

        String name = DateUtil.getFriendlyFileName("Detailed_Morbidity_Report");
        forceDownLoadDatabase(morbidityPatients(name, item), name, response);
        return setUpModel(model, item, true);
    }


    public Workbook morbidityPatients(String name, SearchDTO dto) {
        Workbook workbook = new XSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
        // add contact assessments
        Sheet assessmentDetails = workbook.createSheet("Morbidity");
        int assessmentRowNum = 0;
        Row assessmentHeader = assessmentDetails.createRow(assessmentRowNum++);
        int assessmentCellNum = 0;
        for (String title : DatabaseHeader.ASSESSMENT_HEADER) {
            Cell cell = assessmentHeader.createCell(assessmentCellNum++);
            cell.setCellValue(title);
        }

        List<Patient> patients = detailedPatientReportService.get(dto.getInstance(dto));


        for (Patient patient : patients) {

            Set<Contact> contacts = new HashSet<>();
            contacts.addAll(patient.getContacts());

            for (Contact contact : contacts) {
                //if (!contact.getClinicalAssessments().isEmpty() || !contact.getNonClinicalAssessments().isEmpty()) {
                Set<Assessment> clinicalAssessment = contact.getClinicalAssessments();
                if (clinicalAssessment != null) {
                    clinicalAssessment.addAll(contact.getNonClinicalAssessments());
                }
                for (Assessment item : contact.getClinicalAssessments()) {
                    int count = 0;
                    assessmentHeader = assessmentDetails.createRow(assessmentRowNum++);
                    Cell id = assessmentHeader.createCell(count);
                    id.setCellValue(contact.getPatient().getPatientNumber());
                    Cell patientName = assessmentHeader.createCell(++count);
                    patientName.setCellValue(contact.getPatient().getName());
                    Cell dateOfBirth = assessmentHeader.createCell(++count);
                    dateOfBirth.setCellValue(contact.getPatient().getDateOfBirth());
                    dateOfBirth.setCellStyle(cellStyle);
                    Cell age = assessmentHeader.createCell(++count);
                    age.setCellValue(contact.getPatient().getAge());
                    Cell sex = assessmentHeader.createCell(++count);
                    sex.setCellValue(contact.getPatient().getGender().getName());
                    Cell province = assessmentHeader.createCell(++count);
                    province.setCellValue(contact.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                    Cell district = assessmentHeader.createCell(++count);
                    district.setCellValue(contact.getPatient().getPrimaryClinic().getDistrict().getName());
                    Cell primaryClinic = assessmentHeader.createCell(++count);
                    primaryClinic.setCellValue(contact.getPatient().getPrimaryClinic().getName());
                    Cell contactDate = assessmentHeader.createCell(++count);
                    contactDate.setCellValue(contact.getContactDate());
                    contactDate.setCellStyle(cellStyle);
                    Cell careLevel = assessmentHeader.createCell(++count);
                    careLevel.setCellValue(contact.getCareLevel().getName());
                    Cell assessmentType = assessmentHeader.createCell(++count);
                    assessmentType.setCellValue(item.getContactAssessment().getName());
                    Cell assessment = assessmentHeader.createCell(++count);
                    assessment.setCellValue(item.toString());
                }
                //}else{
                //System.err.println("Contact without assessment :"+contact.getPatient().getFirstName()+" "+contact.getPatient().getLastName());
                //}
//            if (assessmentRowNum >= 65535) {
//                break;
//            }
            }

        }

        return workbook;
    }

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void getExcelExport(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Detailed_Morbidity_Report");
        forceDownLoadDatabase(morbidityPatients(name, item), name, response);
    }


}
