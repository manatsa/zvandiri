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
package zw.org.zvandiri.portal.web.controller.cat;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import zw.org.zvandiri.business.domain.CatActivity;
import zw.org.zvandiri.business.domain.CatDetail;
import zw.org.zvandiri.business.service.CatActivityService;
import zw.org.zvandiri.business.service.CatDetailService;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.dto.ItemDeleteDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;

import static zw.org.zvandiri.portal.web.controller.IAppTitle.APP_PREFIX;

import zw.org.zvandiri.portal.web.validator.CatActivityValidator;

/**
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/cats-management")
public class CatActivityController extends BaseController {

    @Resource
    private CatActivityService catActivityService;
    @Resource
    private CatDetailService catDetailService;
    @Resource
    private DistrictService districtService;
    @Resource
    private ProvinceService provinceService;
    @Resource
    private CatActivityValidator catActivityValidator;

    public String setUpModel(ModelMap model, CatActivity item) {
        model.addAttribute("pageTitle", APP_PREFIX + "Create/ Edit CATS Activity :" + item.getCatDetail().getPatient().getName());
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("formAction", "item.form");
        if (item.getDistrict() != null) {
            item.setProvince(item.getDistrict().getProvince());
        }
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
        }
        model.addAttribute("patient", item.getCatDetail().getPatient());
        model.addAttribute("item", item);
        if (item.getId() != null) {
            return "cat/activityForm";
        }
        return "cat/activityForm";
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String getForm(ModelMap model, @RequestParam(required = false) String catId, @RequestParam(required = false) String itemId) {
        CatActivity item;
        if (itemId != null) {
            item = catActivityService.get(itemId);
            return setUpModel(model, item);
        }
        item = new CatActivity(catDetailService.get(catId));
        return setUpModel(model, item);
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.POST)
    public String saveItem(ModelMap model, @ModelAttribute("item") @Valid CatActivity item, BindingResult result) {
        catActivityValidator.validate(item, result);
        if (result.hasErrors()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return setUpModel(model, item);
        }
        catActivityService.save(item);
        return "redirect:item.list?id=" + item.getCatDetail().getId() + "&type=1";
    }

    @RequestMapping(value = "/item.list", method = RequestMethod.GET)
    public String getList(ModelMap model, @RequestParam(required = false) String id) {
        CatDetail catDetail = catDetailService.get(id);
        model.addAttribute("pageTitle", APP_PREFIX + "CATS Activity List :" + catDetail.getPatient().getName());
        model.addAttribute("patient", catDetail.getPatient());
        model.addAttribute("item", catDetail);
        model.addAttribute("items", catActivityService.getByCat(id));
        return "cat/activityList";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.GET)
    public String getDeleteForm(@RequestParam("id") String id, ModelMap model) {
        CatActivity item = catActivityService.get(id);
        ItemDeleteDTO dto = new ItemDeleteDTO(id, item.getCatDetail().getPatient().getName() + " : CATS Activity Item", "item.list?type=3&id=" + item.getCatDetail().getId());
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", APP_PREFIX + "Delete " + item.getCatDetail().getPatient().getName() + " :" + dto.getName());
        return "admin/deleteItem";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String deleteItem(@Valid ItemDeleteDTO dto) {
        CatActivity item = catActivityService.get(dto.getId());
        //catActivityService.delete(item);
        return "redirect:item.list?type=2&id=" + item.getCatDetail().getId();
    }
}
