
package zw.org.zvandiri.portal.web.controller.report;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.util.UserLevel;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.service.CaseloadManagementService;
import zw.org.zvandiri.report.api.service.OfficeExportService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author manatsachinyeruse@gmail.com
 */
@Controller
@RequestMapping("/report/case-load-management")
public class CaseloadManagementController extends BaseController {

    @Resource
    private CaseloadManagementService caseloadManagementService;
    
    @Resource
    private DistrictService districtService;
    @Resource
    ProvinceService provinceService;
    @Resource
    FacilityService facilityService;



    public void setUpModel(ModelMap model, SearchDTO item) {
        item = getUserLevelObjectState(item);
        //System.err.println(item.getUserLevel().getName());
        model.addAttribute("item", item);
        model.addAttribute("pageTitle", APP_PREFIX + "Caseload Management Plan");

        model.addAttribute("districts", districtService.getAll());
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("facilities", (getUserName().getUserLevel()== UserLevel.DISTRICT && getUserName().getDistrict()!=null)
                ?facilityService.getOptByDistrict(getUserName().getDistrict()): facilityService.getAll());


    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getExportDatabaseIndex(ModelMap model) {
        setUpModel(model, new SearchDTO());
        return "report/caseloadManagement";
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DATA_CLERK') or hasRole('ROLE_M_AND_E_OFFICER') or hasRole('ROLE_HOD_M_AND_E')")
    public void getExcelExport(ModelMap model,HttpServletResponse response, @ModelAttribute("item") SearchDTO item) {
        long startTime=System.currentTimeMillis();
        item = getUserLevelObjectState(item);
        String name = DateUtil.getFriendlyFileName("Zvandiri_Caseload_Management_Plan");
        forceDownLoadXLSX(caseloadManagementService.exportCaseload(name, item), name, response);

        System.err.println(" >>>> Caseload Mgt Time Taken :"+(System.currentTimeMillis()-startTime)/60000+" minutes.");
    }

}
