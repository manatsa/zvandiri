package zw.org.zvandiri.portal.web.controller.report;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.service.ContactReportService;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.service.DetailedReportService;
import zw.org.zvandiri.report.api.service.OfficeExportService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

    @RequestMapping(value = "/detailed", method = RequestMethod.GET)
    public String getReferralReportIndex(ModelMap model) {
        return setUpModel(model, new SearchDTO(), false);
    }

    @RequestMapping(value = "/detailed", method = RequestMethod.POST)
    public String getReferralReportIndex(ModelMap model, @ModelAttribute("item") @Valid SearchDTO item, BindingResult result) {
        return setUpModel(model, item, true);
    }

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void getExcelExport(HttpServletResponse response, SearchDTO item) {
        String name = DateUtil.getFriendlyFileName("Detailed_Morbidity_Report");
        forceDownLoadDatabase(officeExportService.exportExcelXLSXFile(detailedReportService.getDefaultReportB(item.getInstance(item)), name), name, response);
    }
}
