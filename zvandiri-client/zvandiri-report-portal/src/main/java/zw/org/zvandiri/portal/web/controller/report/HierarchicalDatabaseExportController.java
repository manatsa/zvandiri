
package zw.org.zvandiri.portal.web.controller.report;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.District;
import zw.org.zvandiri.business.domain.Facility;
import zw.org.zvandiri.business.domain.Province;
import zw.org.zvandiri.business.domain.User;
import zw.org.zvandiri.business.domain.util.UserLevel;
import zw.org.zvandiri.business.domain.util.UserType;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.service.UserService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.service.OfficeExportService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author manatsachinyeruse@gmail.com
 */
@Controller
@RequestMapping("/report/hierarchical-database-export")
public class HierarchicalDatabaseExportController extends BaseController {

    @Resource
    private OfficeExportService officeExportService;
    
    @Resource
    private DistrictService districtService;
    @Resource
    ProvinceService provinceService;
    @Resource
    FacilityService facilityService;
    @Resource
    UserService userService;



    public void setUpModel(ModelMap model, SearchDTO item) {
        item = getUserLevelObjectState(item);
        model.addAttribute("item", item);
        model.addAttribute("pageTitle", APP_PREFIX + "Hierarchical Database Export");

        model.addAttribute("districts", districtService.getAll());
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("facilities", facilityService.getAll());


    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getExportDatabaseIndex(ModelMap model) {
        setUpModel(model, new SearchDTO());
        return "report/hierarchicalDatabaseExport";
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DATA_CLERK') or hasRole('ROLE_M_AND_E_OFFICER') or hasRole('ROLE_HOD_M_AND_E')")
    public void getExcelExport(ModelMap model,HttpServletResponse response, @ModelAttribute("item") SearchDTO dto) {
        try{
            String currentUserName=userService.getCurrentUsername();
            User currentUser=userService.getCurrentUser();

            dto = getUserLevelObjectState(dto);
            dto.setCurrentUserName(currentUserName);
            dto.setUserLevel(currentUser.getUserLevel());
            System.err.println("Current User: "+userService.getCurrentUsername()+"//// User::"+userService.getCurrentUser());
            String name = DateUtil.getFriendlyFileName("Zvandiri_Hierarchical_Database_Export");
            long startTime=System.currentTimeMillis();
            forceDownLoadXLSX(officeExportService.exportDatabase(name, dto), name, response);
            System.err.println(" >>>>>> >>>>>User:"+dto.getCurrentUserName()+" === User Level:"+dto.getUserLevel()+" Time to finish report : "+((System.currentTimeMillis()-startTime)/60000)+" minutes\n\n");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
