/*
 * Copyright 2017 Judge Muzinda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package zw.org.zvandiri.portal.web.controller.pivot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zw.org.zvandiri.business.domain.util.PatientChangeEvent;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.business.util.pivot.dto.BasePatientExitingProgramPivotDTO;
import zw.org.zvandiri.business.util.pivot.dto.BasePatientPivotDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.service.PatientPivotService;

/**
 *
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/report/pivot/patient")
public class PatientPivotReportController extends BaseController {

    @Resource
    private PatientPivotService patientPivotService;
    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private FacilityService facilityService;

    @RequestMapping("/index")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DATA_CLERK') or hasRole('ROLE_M_AND_E_OFFICER') or hasRole('ROLE_HOD_M_AND_E')")
    public String getPatientPivotReportIndex(ModelMap model, SearchDTO item) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "Patient Pivot Reports");
        model.addAttribute("provinces", provinceService.getAll());
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
            }
        }
        model.addAttribute("item", item);
        return "report/pivot/patient";
    }
    
    @RequestMapping("/patients-exiting-program")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DATA_CLERK') or hasRole('ROLE_M_AND_E_OFFICER') or hasRole('ROLE_HOD_M_AND_E')")
    public String getPatientExitingProgram(ModelMap model, SearchDTO item) {
        model.addAttribute("pageTitle", APP_PREFIX + "Inactive Patient Pivot Reports");
        model.addAttribute("provinces", provinceService.getAll());
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
            }
        }
        model.addAttribute("item", item);
        return "report/pivot/inActivePatient";
    }

    @RequestMapping("/getpatient")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_DATA_CLERK') or hasRole('ROLE_M_AND_E_OFFICER') or hasRole('ROLE_HOD_M_AND_E')")
    @ResponseBody
    public List<BasePatientPivotDTO> getPatientData(SearchDTO dto) {
        return patientPivotService.get(dto);
    }

    @RequestMapping("/getinactivepatient")
    @ResponseBody
    public List<BasePatientExitingProgramPivotDTO> getInActivePatientData(SearchDTO dto) {
        dto.setStatuses(new HashSet<>(
                Arrays.asList(
                        PatientChangeEvent.DECEASED,
                        PatientChangeEvent.GRADUATED,
                        PatientChangeEvent.LOST_TO_FOLOWUP,
                        PatientChangeEvent.OPT_OUT
                )
        ));
        return patientPivotService.getInactivePatients(dto);
    }
}
