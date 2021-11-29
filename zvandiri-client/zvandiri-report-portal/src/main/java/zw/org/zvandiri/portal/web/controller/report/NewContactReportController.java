package zw.org.zvandiri.portal.web.controller.report;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.Contact;
import zw.org.zvandiri.business.service.ContactReportService;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.controller.report.parallel.GenericCountReportTask;
import zw.org.zvandiri.report.api.DatabaseHeader;
import zw.org.zvandiri.report.api.service.DetailedReportService;
import zw.org.zvandiri.report.api.service.OfficeExportService;

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
            //Optional.ofNullable(contact.getPatient().getoINumber()).ifPresent(oi::setCellValue);
            oi.setCellValue(contact.getPatient().getoINumber());

            Cell patientName = uncontactedRow.createCell(count++);
            //Optional.ofNullable(contact.getPatient().getName()).ifPresent(patientName::setCellValue);
            patientName.setCellValue(contact.getPatient().getName());

            Cell dateOfBirth = uncontactedRow.createCell(count++);
            if(contact.getPatient().getDateOfBirth()!=null){
                dateOfBirth.setCellValue(contact.getPatient().getDateOfBirth());
                dateOfBirth.setCellStyle(cellStyle);
            }else{
                dateOfBirth.setCellType(Cell.CELL_TYPE_BLANK);
            }
            //Optional.ofNullable(contact.getPatient().getDateOfBirth()).ifPresent(dateOfBirth::setCellValue);


            Cell age = uncontactedRow.createCell(count++);
            //Optional.ofNullable(contact.getPatient().getAge()).ifPresent(age::setCellValue);
            age.setCellValue(contact.getPatient().getAge());

            Cell sex = uncontactedRow.createCell(count++);
            //Optional<Gender> sexOptional=Optional.ofNullable(contact.getPatient().getGender());
            sex.setCellValue(contact.getPatient().getGender().getName());

            Cell province = uncontactedRow.createCell(count++);
            //Optional<Province> provinceOptional=Optional.ofNullable(contact.getPatient().getPrimaryClinic().getDistrict().getProvince());
            province.setCellValue(contact.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());

            Cell district = uncontactedRow.createCell(count++);
            //Optional<District> districtOptional=Optional.ofNullable(contact.getPatient().getPrimaryClinic().getDistrict());
            district.setCellValue(contact.getPatient().getPrimaryClinic().getDistrict().getName());

            Cell primaryClinic = uncontactedRow.createCell(count++);
            //Optional<Facility> facilityOptional=Optional.ofNullable(contact.getPatient().getPrimaryClinic());
            primaryClinic.setCellValue(contact.getPatient().getPrimaryClinic().getName());

            Cell entry = uncontactedRow.createCell(count++);
            if(contact.getDateCreated()!=null){
                entry.setCellValue(contact.getDateCreated());
                entry.setCellStyle(cellStyle);
            }else{
                entry.setCellType(Cell.CELL_TYPE_BLANK);
            }
            //Optional.ofNullable(contact.getDateCreated()).ifPresent(entry::setCellValue);


            Cell contactDate = uncontactedRow.createCell(count++);
            if(contact.getContactDate()!=null){
                contactDate.setCellValue(contact.getContactDate());
                contactDate.setCellStyle(cellStyle);
            }else{
                contactDate.setCellType(Cell.CELL_TYPE_BLANK);
            }
            //Optional.ofNullable(contact.getContactDate()).ifPresent(contactDate::setCellValue);


            Cell care = uncontactedRow.createCell(count++);
            //Optional<CareLevel> careOptional=Optional.ofNullable(contact.getCareLevel());
            care.setCellValue(contact.getCurrentCareLevel().getName());

            Cell loc = uncontactedRow.createCell(count++);
            //Optional<Location> locOptional=Optional.ofNullable(contact.getLocation());
            loc.setCellValue(contact.getLocation()!=null?contact.getLocation().getName():"");

            Cell position = uncontactedRow.createCell(count++);
            //Optional<Position> posOptional=Optional.ofNullable(contact.getPosition());
            position.setCellValue(contact.getPosition()!=null?contact.getPosition().getName():"");

            Cell reason = uncontactedRow.createCell(count++);
            //Optional<Reason> reasonOptional=Optional.ofNullable(contact.getReason());
            reason.setCellValue(contact.getReason()!=null?contact.getReason().getName():"");

            Cell follow = uncontactedRow.createCell(count++);
            //Optional<FollowUp> followOptional=Optional.ofNullable(contact.getFollowUp());
            follow.setCellValue(contact.getFollowUp().getName());

            Cell subjective = uncontactedRow.createCell(count++);
            //Optional.ofNullable(contact.getSubjective()).ifPresent(subjective::setCellValue);
            subjective.setCellValue(contact.getSubjective()!=null?contact.getSubjective():"");

            Cell objective = uncontactedRow.createCell(count++);
            //Optional.ofNullable(contact.getObjective()).ifPresent(objective::setCellValue);
            objective.setCellValue(contact.getObjective()!=null?contact.getObjective():"");

            Cell plan = uncontactedRow.createCell(count++);
            //Optional.ofNullable(contact.getPlan()).ifPresent(plan::setCellValue);
            plan.setCellValue(contact.getPlan()!=null?contact.getPlan():"");

            Cell action = uncontactedRow.createCell(count++);
            //Optional<ActionTaken> actionOptional=Optional.ofNullable(contact.getActionTaken());
            action.setCellValue(contact.getActionTaken()!=null?contact.getActionTaken().getName():"");

            Cell last = uncontactedRow.createCell(count++);
            if(contact.getLastClinicAppointmentDate()!=null){
                last.setCellValue(contact.getLastClinicAppointmentDate());
                last.setCellStyle(cellStyle);
            }else{
                last.setCellType(Cell.CELL_TYPE_BLANK);
            }
            //Optional.ofNullable(contact.getLastClinicAppointmentDate()).ifPresent(last::setCellValue);


            Cell attended = uncontactedRow.createCell(count++);
            //Optional<YesNo> attendedOptional=Optional.ofNullable(contact.getAttendedClinicAppointment());
            attended.setCellValue(contact.getAttendedClinicAppointment()!=null?contact.getAttendedClinicAppointment().getName():"");

            Cell next = uncontactedRow.createCell(count++);
            if(contact.getNextClinicAppointmentDate()!=null){
                next.setCellValue(contact.getNextClinicAppointmentDate());
                next.setCellStyle(cellStyle);
            }else{
                next.setCellType(Cell.CELL_TYPE_BLANK);
            }
            //Optional.ofNullable(contact.getNextClinicAppointmentDate()).ifPresent(next::setCellValue);


            Cell outcome = uncontactedRow.createCell(count++);
            //Optional<VisitOutcome> outcomeOptional=Optional.ofNullable(contact.getVisitOutcome());
            outcome.setCellValue(contact.getVisitOutcome()!=null?contact.getVisitOutcome().getName():"");

            Cell isCats = uncontactedRow.createCell(count++);
            //Optional<YesNo> catOptional=Optional.ofNullable(contact.getPatient().getCat());
            isCats.setCellValue(contact.getPatient().getCat()!=null?contact.getPatient().getCat().getName():"");

            Cell youngMumGroup = uncontactedRow.createCell(count++);
            //Optional<YesNo> ymmOptional=Optional.ofNullable(contact.getPatient().getYoungMumGroup());
            youngMumGroup.setCellValue(contact.getPatient().getYoungMumGroup()!=null?contact.getPatient().getYoungMumGroup().getName():"");

            Cell ymd = uncontactedRow.createCell(count++);
            //Optional<YesNo> ymdOptional=Optional.ofNullable(contact.getPatient().getYoungDadGroup());
            ymd.setCellValue(contact.getPatient().getYoungDadGroup()!=null?contact.getPatient().getYoungDadGroup().getName():"");

        }

        return  workbook;

    }


}
