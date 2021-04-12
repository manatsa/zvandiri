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

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.business.util.pivot.dto.BaseContactPivotDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.service.ContactPivotService;

/**
 *
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/report/pivot/contact")
public class ContactPivotReportController extends BaseController {
    
    @Resource
    private ContactPivotService contactPivotService;
    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private FacilityService facilityService;
    
    @RequestMapping("/index")
    public String getContactPivotReportIndex(ModelMap model, SearchDTO item){
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX+"Contact Pivot Reports");
        model.addAttribute("provinces", provinceService.getAll());
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
            }
        }
        model.addAttribute("item", item);
        return "report/pivot/contact";
    }
    
    @RequestMapping("/getcontact")
    @ResponseBody
    public List<BaseContactPivotDTO> getPatientData(SearchDTO dto){
        return contactPivotService.get(dto);
    }
}