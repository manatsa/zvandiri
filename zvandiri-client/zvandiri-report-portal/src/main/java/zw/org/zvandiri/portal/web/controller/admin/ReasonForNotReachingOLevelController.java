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
import zw.org.zvandiri.business.domain.ReasonForNotReachingOLevel;
import zw.org.zvandiri.business.service.ReasonForNotReachingOLevelService;
import zw.org.zvandiri.business.util.dto.ItemDeleteDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import static zw.org.zvandiri.portal.web.controller.IAppTitle.APP_PREFIX;
import zw.org.zvandiri.portal.web.validator.ReasonForNotReachingOLevelValidator;

/**
 *
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/admin/reason-for-not-reaching-olevel")
public class ReasonForNotReachingOLevelController extends BaseController {
    
    @Resource
    private ReasonForNotReachingOLevelService reasonForNotReachingOLevelService;
    @Resource
    private ReasonForNotReachingOLevelValidator reasonForNotReachingOLevelValidator;

    public void setUpModel(ModelMap model, ReasonForNotReachingOLevel item) {
        model.addAttribute("pageTitle", APP_PREFIX + "Create/ Edit Reason For Not Reaching O-Level");
        model.addAttribute("item", item);
        model.addAttribute("itemDelete", "item.list?type=3");
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String getForm(ModelMap model, @RequestParam(required = false) String id) {
        ReasonForNotReachingOLevel item = new ReasonForNotReachingOLevel();
        if (id != null) {
            item = reasonForNotReachingOLevelService.get(id);
        }
        setUpModel(model, item);
        return "admin/nameForm";
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.POST)
    public String saveItem(@ModelAttribute("item") @Valid ReasonForNotReachingOLevel item, BindingResult result, ModelMap model) {
        reasonForNotReachingOLevelValidator.validate(item, result);
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        if (result.hasErrors()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            setUpModel(model, item);
            return "admin/nameForm";
        }
        reasonForNotReachingOLevelService.save(item);
        return "redirect:item.list?type=1";
    }

    @RequestMapping(value = {"/item.list", "/"}, method = RequestMethod.GET)
    public String itemList(ModelMap model, @RequestParam(required = false) Integer type) {
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        model.addAttribute("pageTitle", APP_PREFIX + "Reason For Not Reaching O-Level List");
        model.addAttribute("items", reasonForNotReachingOLevelService.getAll());
        if (type != null) {
            model.addAttribute("message", AppMessage.getMessage(type));
        }
        return "admin/nameList";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.GET)
    public String getDeleteForm(@RequestParam("id") String id, ModelMap model) {
        ReasonForNotReachingOLevel item = reasonForNotReachingOLevelService.get(id);
        ItemDeleteDTO dto = new ItemDeleteDTO(id, item.getName() + " Reason For Not Reaching O-Level", "item.list?type=3");
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", APP_PREFIX + "Delete " + item.getName() + " Reason For Not Reaching O-Level");
        return "admin/deleteItem";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String deleteItem(@Valid ItemDeleteDTO dto) {
        reasonForNotReachingOLevelService.delete(reasonForNotReachingOLevelService.get(dto.getId()));
        return "redirect:item.list?type=2";
    }
}