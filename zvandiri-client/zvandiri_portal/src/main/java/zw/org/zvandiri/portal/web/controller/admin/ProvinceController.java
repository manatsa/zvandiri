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
import zw.org.zvandiri.business.domain.Province;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.dto.ItemDeleteDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.validator.ProvinceValidator;

/**
 * @author Edward Zengeni
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/admin/province")
public class ProvinceController extends BaseController {

    @Resource
    private ProvinceService provinceService;
    @Resource
    private ProvinceValidator provinceValidator;

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String provinceForm(ModelMap model, @RequestParam(required = false) String id) {
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        model.addAttribute("pageTitle", APP_PREFIX + "Create/ Edit Province");
        Province p = new Province();
        if (id != null) {
            p = provinceService.get(id);
        }
        model.addAttribute("item", p);
        model.addAttribute("itemDelete", "item.list?type=3");
        return "admin/provinceForm";
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.POST)
    public String saveProvince(@ModelAttribute("item") @Valid Province province, BindingResult result, ModelMap model) {
        provinceValidator.validate(province, result);
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", APP_PREFIX + "Create/ Edit Province");
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());

            model.addAttribute("item", province);
            return "admin/provinceForm";
        }
        provinceService.save(province);
        return "redirect:item.list?type=1";
    }

    @RequestMapping(value = {"/item.list", "/"}, method = RequestMethod.GET)
    public String provinceList(ModelMap model, @RequestParam(required = false) Integer type) {
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        model.addAttribute("pageTitle", APP_PREFIX + "Province List");
        model.addAttribute("items", provinceService.getAll());
        if (type != null) {
            model.addAttribute("message", AppMessage.getMessage(type));
        }
        return "admin/provinceList";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.GET)
    public String getProvinceDeleteForm(@RequestParam("id") String id, ModelMap model) {
        Province province = provinceService.get(id);
        ItemDeleteDTO dto = new ItemDeleteDTO(id, province.getName(), "item.list?type=3");
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", APP_PREFIX + "Delete " + province.getName() + " Province");
        return "admin/deleteItem";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String deleteProvince(@Valid ItemDeleteDTO dto) {
        provinceService.delete(provinceService.get(dto.getId()));
        return "redirect:item.list?type=2";
    }
}