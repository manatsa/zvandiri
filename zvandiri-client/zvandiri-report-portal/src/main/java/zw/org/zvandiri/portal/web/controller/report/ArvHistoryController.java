package zw.org.zvandiri.portal.web.controller.report;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.ArvHist;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.DatabaseHeader;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import zw.org.zvandiri.portal.web.controller.report.parallel.GenericCountReportTask;

@Controller
@RequestMapping("/report/arvhist")
public class ArvHistoryController extends BaseController {

    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private FacilityService facilityService;
    @Resource
    private ArvHistReportService reportService;
    List<ArvHist> arvHists = new ArrayList<>();

    public String setUpModel(ModelMap model, SearchDTO item, boolean post) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "ARVHist Report");
        model.addAttribute("provinces", provinceService.getAll());
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
            }
        }
        if (post) {
            model.addAttribute("excelExport", "/report/arvhist/export/excel" + item.getQueryString(item.getInstance(item)));
            model.addAttribute("items", arvHists);
        }
        model.addAttribute("item", item.getInstance(item));
        return "report/arvHistDetailedReport";
    }

    @RequestMapping(value = "/range", method = RequestMethod.GET)
    public String getReferralReportIndex(ModelMap model) {
        return setUpModel(model, new SearchDTO(), false);
    }

    @RequestMapping(value = "/range", method = RequestMethod.POST)
    public String getReferralReportIndex(HttpServletResponse response, ModelMap model, @ModelAttribute("item") @Valid SearchDTO item, BindingResult result) {
        item = getUserLevelObjectState(item);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        arvHists = pool.invoke(new GenericCountReportTask(DateUtil.generateArray(reportService.getCount(item)), reportService, item));
        return setUpModel(model, item, true);
    }

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void downloadAll(HttpServletResponse response, @ModelAttribute("item") @Valid SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Detailed_ARVHist_Report");
        forceDownLoadDatabase(getARVHistoryWorkbook(item), name, response);
    }

    public XSSFWorkbook getARVHistoryWorkbook(SearchDTO dto) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle XSSFCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        XSSFCellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
        XSSFSheet arvHistDetails = workbook.createSheet("ARV_HISTORY");
        int arvHistXSSFRowNum = 0;
        XSSFRow arvHistXSSFRow = arvHistDetails.createRow(arvHistXSSFRowNum++);
        int arvHistXSSFCellNum = 0;
        for (String title : DatabaseHeader.ARV_HISTORY_HEADER) {
            XSSFCell XSSFCell = arvHistXSSFRow.createCell(arvHistXSSFCellNum++);
            XSSFCell.setCellValue(title);
        }

        for (ArvHist arvHist : arvHists) {
            int count = 0;
            arvHistXSSFRow = arvHistDetails.createRow(arvHistXSSFRowNum++);
            XSSFCell id = arvHistXSSFRow.createCell(count);
            id.setCellValue(arvHist.getPatient().getPatientNumber());
            XSSFCell patientName = arvHistXSSFRow.createCell(++count);
            patientName.setCellValue(arvHist.getPatient().getName());
            XSSFCell dateOfBirth = arvHistXSSFRow.createCell(++count);
            dateOfBirth.setCellValue(arvHist.getPatient().getDateOfBirth());
            dateOfBirth.setCellStyle(XSSFCellStyle);
            XSSFCell age = arvHistXSSFRow.createCell(++count);
            age.setCellValue(arvHist.getPatient().getAge());
            XSSFCell sex = arvHistXSSFRow.createCell(++count);
            sex.setCellValue(arvHist.getPatient().getGender().getName());

            XSSFCell province = arvHistXSSFRow.createCell(++count);
            province.setCellValue(arvHist.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
            XSSFCell district = arvHistXSSFRow.createCell(++count);
            district.setCellValue(arvHist.getPatient().getPrimaryClinic().getDistrict().getName());
            XSSFCell primaryClinic = arvHistXSSFRow.createCell(++count);
            primaryClinic.setCellValue(arvHist.getPatient().getPrimaryClinic().getName());

            XSSFCell arvHistMedicine = arvHistXSSFRow.createCell(++count);
            arvHistMedicine.setCellValue(arvHist.getMedicines());
            XSSFCell startDate = arvHistXSSFRow.createCell(++count);
            if (arvHist.getStartDate() != null) {
                startDate.setCellValue(arvHist.getStartDate());
                startDate.setCellStyle(XSSFCellStyle);
            } else {
                startDate.setCellValue("");
            }
            XSSFCell endDate = arvHistXSSFRow.createCell(++count);
            if (arvHist.getEndDate() != null) {
                endDate.setCellValue(arvHist.getEndDate());
                endDate.setCellStyle(XSSFCellStyle);
            } else {
                endDate.setCellValue("");
            }
            
            XSSFCell isCats = arvHistXSSFRow.createCell(++count);
            isCats.setCellValue(
            		arvHist.getPatient().getCat() != null ? arvHist.getPatient().getCat().getName() : null
            );
            XSSFCell youngMumGroup = arvHistXSSFRow.createCell(++count);
            youngMumGroup.setCellValue(
            		arvHist.getPatient().getYoungMumGroup() != null ? arvHist.getPatient().getYoungMumGroup().getName() : null
            );
        }

        return workbook;
    }
}
