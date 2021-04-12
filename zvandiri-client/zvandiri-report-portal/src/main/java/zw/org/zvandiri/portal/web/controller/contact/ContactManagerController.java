/*
 * Copyright 2016 Judge Muzinda.
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
package zw.org.zvandiri.portal.web.controller.contact;

import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.service.ContactReportService;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.service.SupportGroupService;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;

/**
 *
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/patient/contact/manager")
public class ContactManagerController extends BaseController {

    @Resource
    private ContactReportService contactReportService;
    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private FacilityService facilityService;
    @Resource
    private SupportGroupService supportGroupService;

    public String setUpModel(ModelMap model, SearchDTO item, Boolean post) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "Contact Management Panel");
        model.addAttribute("provinces", provinceService.getAll());
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
                model.addAttribute("supportGroups", supportGroupService.getByDistrict(item.getDistrict()));
            }
        }
        model.addAttribute("item", item.getInstance(item));
        if (post) {
            model.addAttribute("contacts", contactReportService.get(item.getInstance(item)));
        }
        return "contact/itemList";
    }

    @RequestMapping(value = "/item.list", method = RequestMethod.GET)
    public String getForm(ModelMap model) {
        return setUpModel(model, new SearchDTO(), Boolean.FALSE);
    }

    @RequestMapping(value = "/item.list", method = RequestMethod.POST)
    public String processRequest(ModelMap model, @ModelAttribute("item") @Valid SearchDTO dto) {
        return setUpModel(model, dto, Boolean.TRUE);
    }
}
