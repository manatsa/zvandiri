package zw.org.zvandiri.portal.web.controller.report;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.Contact;
import zw.org.zvandiri.business.domain.InvestigationTest;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.User;
import zw.org.zvandiri.business.domain.util.TestType;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.controller.report.parallel.GenericCountReportTask;
import zw.org.zvandiri.report.api.DatabaseHeader;
import zw.org.zvandiri.report.api.service.parallel.PatientDatabaseExportTask;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;


/**
 *
 * @author manatsachinyeruse@gmail.com
 */

@Controller
@RequestMapping("/report/new/patient")
public class NewClientDetailedReportController extends BaseController {

   @Resource
   private ProvinceService provinceService;
   @Resource
   private DistrictService districtService;
   @Resource
   private FacilityService facilityService;
   @Resource
   private CatDetailService catDetailService;
   @Resource
   private UserService userService;
   @Resource
   InvestigationTestService investigationTestService;
   @Resource
   ContactService contactService;
   @Resource
   private DetailedPatientReportService detailedPatientReportService;
   List<Patient> patients=new ArrayList<>();

   public String setUpModel(ModelMap model, SearchDTO item, boolean post) {
       item = getUserLevelObjectState(item);
       model.addAttribute("pageTitle", APP_PREFIX + "New Patient Detailed Report");
       model.addAttribute("provinces", provinceService.getAll());
       if (item.getProvince() != null) {
           model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
           if (item.getDistrict() != null) {
               model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
           }
       }
       if (post) {
           model.addAttribute("excelExport", "/report/new/patient/export/excel" + item.getQueryString(item.getInstance(item)));
           model.addAttribute("items", patients);
       }
       model.addAttribute("item", item.getInstance(item));
       return "report/newPatientDateRangeReport";
   }

   @RequestMapping(value = "/detailed", method = RequestMethod.GET)
   public String getReferralReportIndex(ModelMap model) {
       return setUpModel(model, new SearchDTO(), false);
   }

   @RequestMapping(value = "/detailed", method = RequestMethod.POST)
   public String getReferralReportIndex(ModelMap model, @ModelAttribute("item") @Valid SearchDTO item, BindingResult result) {
       final long start = System.currentTimeMillis();
       User user=userService.getCurrentUser();
       System.err.println("\n\nUser :: "+user.getUserName()+" <=> District:"+(user.getUserLevel()!=null?user.getDistrict(): catDetailService.getByEmail(user.getUserName()).getPrimaryClinic().getDistrict().getName())+" <<<<<<<<<<<<<<< New Patient Detailed Report >>>>>>>>>>>>>>>>>");
       item.setFirstResult(0);
       item.setPageSize(detailedPatientReportService.getCount(item).intValue());
       List<String> ids = detailedPatientReportService.getIds(item);
       ForkJoinPool pool = ForkJoinPool.commonPool();
       patients = pool.invoke(new PatientDatabaseExportTask(ids, detailedPatientReportService));
       //List<Patient> patients=detailedPatientReportService.get(ids);
       final long end = System.currentTimeMillis();
       final long time = end - start;
       System.err.println("User::" + user.getUserName() + "==== User Level:" + user.getUserLevel() + " ==>:: All Patientss (min)::" + (double) (time / 60000));


       System.err.println("New Patient Detailed Items::"+patients.size());
       model.addAttribute("items", patients);
       return setUpModel(model, item, true);
   }

   @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
   public void getExcelExport(HttpServletResponse response, SearchDTO item) {
       String name = DateUtil.getFriendlyFileName("New_Client_Detailed_Report");
       forceDownLoadDatabase(newPatientsDetails(item), name, response);
       System.err.println("<<<<<<<<<<<<<<< New Patient Detailed Report Downloaded >>>>>>>>>>>>>>>>>");
   }

   public XSSFWorkbook newPatientsDetails(SearchDTO dto) {


       final long start = System.currentTimeMillis();
       XSSFWorkbook workbook = new XSSFWorkbook();
       XSSFCellStyle XSSFCellStyle = workbook.createCellStyle();
       XSSFCreationHelper createHelper = workbook.getCreationHelper();
       XSSFCellStyle.setDataFormat(
               createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
       // Patient details XSSFSheet
       XSSFSheet patientDetails = workbook.createSheet("Patient_Details");
       try {

           int XSSFRowNum = 0;
           XSSFRow header = patientDetails.createRow(XSSFRowNum++);
           int XSSFCellNum = 0;
           for (String title : DatabaseHeader.PATIENT_HEADER) {
               XSSFCell XSSFCell = header.createCell(XSSFCellNum++);
               XSSFCell.setCellValue(title);
           }

           final long start_patient = System.currentTimeMillis();
           if (patients != null) {
               for (Patient patient : patients) {
                   int count = 0;
                   InvestigationTest vlTest = investigationTestService.getLatestTestByTestType(patient, TestType.VIRAL_LOAD);

                   Contact contact = contactService.findLatestContact(patient);

                   header = patientDetails.createRow(XSSFRowNum++);
                   XSSFCell id = header.createCell(count);
                   id.setCellValue(patient.getPatientNumber());
                   XSSFCell patientName = header.createCell(++count);
                   patientName.setCellValue(patient.getName());

                   XSSFCell clientType = header.createCell(++count);
                   clientType.setCellValue(patient.getClientType() != null ? patient.getClientType().getName() : null);

                   XSSFCell artNumber = header.createCell(++count);
                   artNumber.setCellValue(patient.getoINumber());

                   XSSFCell dateOfBirth = header.createCell(++count);
                   dateOfBirth.setCellValue(patient.getDateOfBirth());
                   dateOfBirth.setCellStyle(XSSFCellStyle);
                   XSSFCell age = header.createCell(++count);
                   age.setCellValue(patient.getAge());
                   XSSFCell dateEntered = header.createCell(++count);
                   if (patient.getDateCreated() != null) {
                       dateEntered.setCellValue(patient.getDateCreated());
                       dateEntered.setCellStyle(XSSFCellStyle);
                   } else {
                       dateEntered.setCellValue("");
                   }
                   XSSFCell dateJoined = header.createCell(++count);
                   if (patient.getDateJoined() != null) {
                       dateJoined.setCellValue(patient.getDateJoined());
                       dateJoined.setCellStyle(XSSFCellStyle);
                   } else {
                       dateJoined.setCellValue("");
                   }
                   dateJoined.setCellStyle(XSSFCellStyle);

                   XSSFCell gender = header.createCell(++count);
                   gender.setCellValue(patient.getGender().getName());

                   String addressDetails = patient.getAddress() != null ? patient.getAddress() : " , " + patient.getAddress1() != null ? patient.getAddress1() : "";
                   XSSFCell address = header.createCell(++count);
                   address.setCellValue(addressDetails);

                   XSSFCell mobileNumber = header.createCell(++count);
                   mobileNumber.setCellValue(patient.getMobileNumber());

                   XSSFCell consetToMHelaath = header.createCell(++count);
                   consetToMHelaath.setCellValue(patient.getConsentToMHealth() != null ? patient.getConsentToMHealth().getName() : null);

                   XSSFCell education = header.createCell(++count);
                   education.setCellValue(patient.getEducation() != null ? patient.getEducation().getName() : null);

                   XSSFCell highestEducation = header.createCell(++count);
                   highestEducation.setCellValue(patient.getEducationLevel() != null ? patient.getEducationLevel().getName() : null);

                   XSSFCell ctype = header.createCell(++count);
                   ctype.setCellValue(patient.getClientType() != null ? patient.getClientType().getName() : null);

                   XSSFCell regimen = header.createCell(++count);
                   regimen.setCellValue(patient.getArtRegimen() != null ? patient.getArtRegimen() : null);

                   XSSFCell refer = header.createCell(++count);
                   refer.setCellValue(patient.getReferer() != null ? patient.getReferer().getName() : null);

                   XSSFCell province = header.createCell(++count);
                   province.setCellValue(patient.getPrimaryClinic().getDistrict().getProvince().getName());

                   XSSFCell district = header.createCell(++count);
                   district.setCellValue(patient.getPrimaryClinic().getDistrict().getName());

                   XSSFCell primaryClinic = header.createCell(++count);
                   primaryClinic.setCellValue(patient.getPrimaryClinic().getName());

                   XSSFCell supportGroup = header.createCell(++count);
                   supportGroup.setCellValue(patient.getSupportGroup() != null ? patient.getSupportGroup().getName() : null);

                   XSSFCell dateTested = header.createCell(++count);
                   if (patient.getDateTested() != null) {
                       dateTested.setCellValue(patient.getDateTested());
                       dateTested.setCellStyle(XSSFCellStyle);
                   } else {
                       dateTested.setCellValue("");
                   }

                   XSSFCell hivDisclosureLoc = header.createCell(++count);
                   hivDisclosureLoc.setCellValue(patient.gethIVDisclosureLocation() != null ? patient.gethIVDisclosureLocation().getName() : null);

                   XSSFCell disclosureType = header.createCell(++count);
                   disclosureType.setCellValue(patient.getDisclosureType() != null ? patient.getDisclosureType().getName() : null);

                   XSSFCell isKey = header.createCell(++count);
                   isKey.setCellValue(patient.getIsKeypopulation() != null ? patient.getIsKeypopulation().getName() : null);

                   XSSFCell key = header.createCell(++count);
                   key.setCellValue(patient.getKeyPopulation() != null ? patient.getKeyPopulation().getName() : null);

                   XSSFCell birthCert = header.createCell(++count);
                   birthCert.setCellValue(patient.getHaveBirthCertificate() != null ? patient.getHaveBirthCertificate().getName() : null);

                   XSSFCell hasDisability = header.createCell(++count);
                   hasDisability.setCellValue(patient.getDisability() != null ? patient.getDisability().getName() : null);

                   XSSFCell isCats = header.createCell(++count);
                   isCats.setCellValue(patient.getCat() != null ? patient.getCat().getName() : null);

                   XSSFCell youngMumGroup = header.createCell(++count);
                   youngMumGroup.setCellValue(patient.getYoungMumGroup() != null ? patient.getYoungMumGroup().getName() : null);

                   XSSFCell ymd = header.createCell(++count);
                   ymd.setCellValue(patient.getYoungDadGroup() != null ? patient.getYoungDadGroup().getName() : null);

                   XSSFCell transMode = header.createCell(++count);
                   transMode.setCellValue(patient.getTransmissionMode() != null ? patient.getTransmissionMode().getName() : null);

                   XSSFCell drugRegimen = header.createCell(++count);
                   drugRegimen.setCellValue(patient.getCurrentArvRegimen() != null ? patient.getCurrentArvRegimen() : null);

                   XSSFCell hivStatusKnown = header.createCell(++count);
                   hivStatusKnown.setCellValue(patient.getHivStatusKnown() != null ? patient.getHivStatusKnown().getName() : null);

                   XSSFCell patientStatus = header.createCell(++count);
                   patientStatus.setCellValue(patient.getStatus() != null ? patient.getStatus().getName() : null);

                   XSSFCell contactDate = header.createCell(++count);
                   if (contact != null && contact.getContactDate() != null) {
                       contactDate.setCellValue(contact.getContactDate());
                       contactDate.setCellStyle(XSSFCellStyle);
                   } else {
                       contactDate.setCellType(CellType.BLANK);
                   }

                   XSSFCell careLevel = header.createCell(++count);
                   careLevel.setCellValue(contact != null && contact.getCareLevelAfterAssessment() != null ? contact.getCareLevelAfterAssessment().getName() : "");

                   XSSFCell vlresult = header.createCell(++count);
                   if (vlTest != null && vlTest.getResult() != null) {
                       vlresult.setCellValue(vlTest.getResult());
                   } else {
                       vlresult.setCellType(CellType.BLANK);
                   }

                   XSSFCell vlTnd = header.createCell(++count);
                   if (vlTest != null && vlTest.getTnd() != null) {
                       vlTnd.setCellValue(vlTest.getTnd());
                   } else {
                       vlTnd.setCellType(CellType.BLANK);
                   }


                   XSSFCell vlDateTaken = header.createCell(++count);
                   if (vlTest != null) {
                       vlDateTaken.setCellValue(vlTest.getDateTaken());
                       vlDateTaken.setCellStyle(XSSFCellStyle);
                   } else {
                       vlDateTaken.setCellValue("");
                   }

               }
           }
           final long patient_end = System.currentTimeMillis();
           System.err.println("::Finished Collation of New Patient Details (min)::" + (double) ((patient_end - start_patient) / 60000));

           return workbook;

       } catch (Exception e) {
            System.err.println("Error Collating new Patient Details ::"+e.getMessage());
       }

       return null;
   }
}
