/*
 * Copyright 2015 Judge Muzinda.
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
package zw.org.zvandiri.portal.web.controller.admin;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import zw.org.zvandiri.business.domain.Facility;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.dto.ItemDeleteDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.validator.FacilityValidator;

/**
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/admin/facility")
public class FacilityController extends BaseController {

    @Resource
    private FacilityService facilityService;
    @Resource
    private FacilityValidator facilityValidator;
    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String facilityForm(ModelMap model, @RequestParam(required = false) String id) {
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        model.addAttribute("pageTitle", APP_PREFIX + "Create/ Edit Facility");
        Facility f = new Facility();
        if (id != null) {
            f = facilityService.get(id);
            if (f.getDistrict() != null) {
                f.setProvince(f.getDistrict().getProvince());
                model.addAttribute("districts", districtService.getDistrictByProvince(f.getProvince()));
            }
        }
        model.addAttribute("item", f);
        model.addAttribute("formAction", "item.form");
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("itemDelete", "item.list?type=3");
        return "admin/facilityForm";
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.POST)
    public String saveFacility(@ModelAttribute("item") @Valid Facility facility, BindingResult result, ModelMap model) {
        facilityValidator.validate(facility, result);
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", APP_PREFIX + "Create/ Edit Facility");
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            model.addAttribute("item", facility);
            model.addAttribute("provinces", provinceService.getAll());
            if (facility.getProvince() != null) {
                model.addAttribute("districts", districtService.getDistrictByProvince(facility.getProvince()));
            }
            model.addAttribute("formAction", "item.form");
            return "admin/facilityForm";
        }
        facilityService.save(facility);
        return "redirect:item.list?type=1";
    }

    @RequestMapping(value = {"/item.list", "/"}, method = RequestMethod.GET)
    public String facilityList(ModelMap model, @RequestParam(required = false) Integer type) {
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        model.addAttribute("pageTitle", APP_PREFIX + "Facility List");
        model.addAttribute("items", facilityService.getAll());
        if (type != null) {
            model.addAttribute("message", AppMessage.getMessage(type));
        }
        return "admin/facilityList";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.GET)
    public String getFacilityDeleteForm(@RequestParam("id") String id, ModelMap model) {
        Facility facility = facilityService.get(id);
        ItemDeleteDTO dto = new ItemDeleteDTO(id, facility.getName(), "item.list?type=3");
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", APP_PREFIX + "Delete " + facility.getName() + " Facility");
        return "admin/deleteItem";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String deleteFacility(@Valid ItemDeleteDTO dto) {
        facilityService.delete(facilityService.get(dto.getId()));
        return "redirect:item.list?type=2";
    }
}