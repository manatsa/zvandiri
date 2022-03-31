/*
 * Copyright 2014 Edward Zengeni.
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
import zw.org.zvandiri.business.domain.District;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.dto.ItemDeleteDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.validator.DistrictValidator;

/**
 * @author Edward Zengeni
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/admin/district")
public class DistrictController extends BaseController {

    @Resource
    private DistrictService districtService;
    @Resource
    private DistrictValidator districtValidator;
    @Resource
    private ProvinceService provinceService;

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String districtForm(ModelMap model, @RequestParam(required = false) String id) {
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        model.addAttribute("pageTitle", APP_PREFIX + "Create/ Edit District");
        model.addAttribute("itemDelete", "item.list");
        model.addAttribute("provinces", provinceService.getAll());
        District p = new District();
        if (id != null) {
            p = districtService.get(id);
        }
        model.addAttribute("item", p);
        return "admin/districtForm";
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.POST)
    public String saveDistrict(@ModelAttribute("item") @Valid District district, BindingResult result, ModelMap model) {
        districtValidator.validate(district, result);
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", APP_PREFIX + "Create/ Edit District");
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            model.addAttribute("provinces", provinceService.getAll());
            model.addAttribute("item", district);
            model.addAttribute("itemDelete", "district.list");
            return "admin/districtForm";
        }
        districtService.save(district);
        return "redirect:item.list?type=1";
    }

    @RequestMapping(value = {"/item.list", "/"}, method = RequestMethod.GET)
    public String districtList(ModelMap model, @RequestParam(required = false) Integer type) {
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        model.addAttribute("pageTitle", APP_PREFIX + "District List");
        model.addAttribute("items", districtService.getAll());
        if (type != null) {
            model.addAttribute("message", AppMessage.getMessage(type));
        }
        return "admin/districtList";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.GET)
    public String getDistrictDeleteForm(@RequestParam("id") String id, ModelMap model) {
        District district = districtService.get(id);
        ItemDeleteDTO dto = new ItemDeleteDTO(id, district.getName(), "item.list");
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", APP_PREFIX + "Delete " + district.getName() + " District");
        return "admin/deleteItem";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String deleteDistrict(@Valid ItemDeleteDTO dto) {
        districtService.delete(districtService.get(dto.getId()));
        return "redirect:item.list?type=2";
    }
}