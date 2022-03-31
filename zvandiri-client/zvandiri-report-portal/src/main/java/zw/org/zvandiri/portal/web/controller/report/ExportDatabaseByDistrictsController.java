
package zw.org.zvandiri.portal.web.controller.report;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import zw.org.zvandiri.business.domain.District;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.service.OfficeExportService;

/**
 * @author jmuzinda
 */
@Controller
@RequestMapping("/report/export-districts-databases")
public class ExportDatabaseByDistrictsController extends BaseController {

    @Resource
    private OfficeExportService officeExportService;

    @Resource
    private DistrictService districtService;


    public void setUpModel(ModelMap model, SearchDTO item) {
        item = getUserLevelObjectState(item);
        model.addAttribute("item", item);
        model.addAttribute("pageTitle", APP_PREFIX + "Export Database By Districts");

        List<District> districtList = districtService.getAll();
        // System.err.println(">>>>>>>>>>>>>>>>>>>>>>> Districts <<<<<<< "+ districtList.size());
        model.addAttribute("districts", districtList);


    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getExportDatabaseIndex(ModelMap model) {
        setUpModel(model, new SearchDTO());
        return "report/exportDatabaseByDistricts";
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DATA_CLERK') or hasRole('ROLE_M_AND_E_OFFICER') or hasRole('ROLE_HOD_M_AND_E')")
    public void getExcelExport(ModelMap model, HttpServletResponse response, @ModelAttribute("item") SearchDTO dto) {

        //System.err.println(">>>>>>>>>>>>>>>>>>>>>> Districts Selected >>>>>>>>>>>>>"+printDistricts(dto.getDistricts()));
        dto = getUserLevelObjectState(dto);
        String name = DateUtil.getFriendlyFileName("Zvandiri_Database_By_Districts");
        forceDownLoadXLSX(officeExportService.exportDatabase(name, dto), name, response);
    }

    /*private String printDistricts(List<District> districts){
        return  districts.stream().map(District::getName).collect(Collectors.joining(","));
    }*/
}
