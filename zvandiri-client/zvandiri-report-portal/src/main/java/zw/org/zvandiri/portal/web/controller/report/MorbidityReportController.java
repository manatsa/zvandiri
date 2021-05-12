package zw.org.zvandiri.portal.web.controller.report;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.Assessment;
import zw.org.zvandiri.business.domain.Contact;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.DatabaseHeader;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import zw.org.zvandiri.portal.web.controller.report.parallel.GenericCountReportTask;

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
    private DetailedPatientReportService detailedPatientReportService;

    List<Contact> contacts = new ArrayList<>();

    public String setUpModel(ModelMap model, SearchDTO item, boolean post) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "Morbidity Report");
        model.addAttribute("provinces", provinceService.getAll());
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
            }
        }
        if (post) {
            model.addAttribute("excelExport", "/report/morbidity/export/excel" + item.getQueryString(item.getInstance(item)));
            ForkJoinPool pool = ForkJoinPool.commonPool();
            contacts = pool.invoke(new GenericCountReportTask(DateUtil.generateArray(contactReportService.getCount(item)), contactReportService, item));
            model.addAttribute("items", contacts);
        }
        model.addAttribute("item", item.getInstance(item));
        return "report/morbidityDetailedReport";
    }

    @RequestMapping(value = "/range", method = RequestMethod.GET)
    public String getReferralReportIndex(ModelMap model) {
        return setUpModel(model, new SearchDTO(), false);
    }

    @RequestMapping(value = "/range", method = RequestMethod.POST)
    public String getReferralReportIndex(HttpServletResponse response, ModelMap model, @ModelAttribute("item") @Valid SearchDTO item, BindingResult result) {
        return setUpModel(model, item, true);
    }

//    @RequestMapping(value="/export/excel", method = RequestMethod.GET)
//    public void downloadAll(HttpServletResponse response, @ModelAttribute("item") @Valid SearchDTO item){
//        String name = DateUtil.getFriendlyFileName("Detailed_Morbidity_Report");
//        forceDownLoadDatabase(morbidityPatients(name, item), name, response);
//    }
    public XSSFWorkbook morbidityPatients(String name, SearchDTO dto) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle XSSFCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        XSSFCellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
        // add contact assessments
        XSSFSheet assessmentDetails = workbook.createSheet("Morbidity");
        int assessmentRowNum = 0;
        XSSFRow assessmentHeader = assessmentDetails.createRow(assessmentRowNum++);
        int assessmentXSSFCellNum = 0;
        for (String title : DatabaseHeader.ASSESSMENT_HEADER) {
            XSSFCell XSSFCell = assessmentHeader.createCell(assessmentXSSFCellNum++);
            XSSFCell.setCellValue(title);
        }

        ForkJoinPool pool = ForkJoinPool.commonPool();
        List<Patient> patients = pool.invoke(new GenericCountReportTask(DateUtil.generateArray(detailedPatientReportService.getCount(dto)), detailedPatientReportService, dto));

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
                    XSSFCell id = assessmentHeader.createCell(count);
                    id.setCellValue(contact.getPatient().getPatientNumber());
                    XSSFCell patientName = assessmentHeader.createCell(++count);
                    patientName.setCellValue(contact.getPatient().getName());
                    XSSFCell dateOfBirth = assessmentHeader.createCell(++count);
                    dateOfBirth.setCellValue(contact.getPatient().getDateOfBirth());
                    dateOfBirth.setCellStyle(XSSFCellStyle);
                    XSSFCell age = assessmentHeader.createCell(++count);
                    age.setCellValue(contact.getPatient().getAge());
                    XSSFCell sex = assessmentHeader.createCell(++count);
                    sex.setCellValue(contact.getPatient().getGender().getName());
                    XSSFCell province = assessmentHeader.createCell(++count);
                    province.setCellValue(contact.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                    XSSFCell district = assessmentHeader.createCell(++count);
                    district.setCellValue(contact.getPatient().getPrimaryClinic().getDistrict().getName());
                    XSSFCell primaryClinic = assessmentHeader.createCell(++count);
                    primaryClinic.setCellValue(contact.getPatient().getPrimaryClinic().getName());
                    XSSFCell contactDate = assessmentHeader.createCell(++count);
                    contactDate.setCellValue(contact.getContactDate());
                    contactDate.setCellStyle(XSSFCellStyle);
                    XSSFCell careLevel = assessmentHeader.createCell(++count);
                    careLevel.setCellValue(contact.getCareLevel().getName());
                    XSSFCell assessmentType = assessmentHeader.createCell(++count);
                    assessmentType.setCellValue(item.getContactAssessment().getName());
                    XSSFCell assessment = assessmentHeader.createCell(++count);
                    assessment.setCellValue(item.toString());
                }

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
