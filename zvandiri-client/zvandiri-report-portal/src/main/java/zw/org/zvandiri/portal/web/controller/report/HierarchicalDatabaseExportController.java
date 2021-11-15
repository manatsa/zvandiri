
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
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.service.OfficeExportService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author jmuzinda
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
        dto = getUserLevelObjectState(dto);
        String name = DateUtil.getFriendlyFileName("Zvandiri_Hierarchical_Database_Export");
        forceDownLoadXLSX(officeExportService.exportDatabase(name, dto), name, response);
        System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> DTO : "+dto);
    }

}