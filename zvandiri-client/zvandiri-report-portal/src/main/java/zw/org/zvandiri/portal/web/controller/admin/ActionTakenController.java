/*
 * Copyright 2017 User.
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
import zw.org.zvandiri.business.domain.ActionTaken;
import zw.org.zvandiri.business.service.ActionTakenService;
import zw.org.zvandiri.business.util.dto.ItemDeleteDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import static zw.org.zvandiri.portal.web.controller.IAppTitle.APP_PREFIX;
import zw.org.zvandiri.portal.web.validator.ActionTakenValidator;

/**
 *
 * @author User
 */
@Controller
@RequestMapping("/admin/action-taken")
public class ActionTakenController extends BaseController{
    
    
    @Resource
    private ActionTakenService actionTakenService;
    @Resource
    private ActionTakenValidator actionTakenValidator;

    public void setUpModel(ModelMap model, ActionTaken item) {
        model.addAttribute("pageTitle", APP_PREFIX + "Create/ Edit Action Taken");
        model.addAttribute("item", item);
        model.addAttribute("itemDelete", "item.list?type=3");
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String getForm(ModelMap model, @RequestParam(required = false) String id) {
        ActionTaken item = new ActionTaken();
        if (id != null) {
            item = actionTakenService.get(id);
        }
        setUpModel(model, item);
        return "admin/nameForm";
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.POST)
    public String saveItem(@ModelAttribute("item") @Valid ActionTaken item, BindingResult result, ModelMap model) {
        actionTakenValidator.validate(item, result);
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        if (result.hasErrors()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            setUpModel(model, item);
            return "admin/nameForm";
        }
        actionTakenService.save(item);
        return "redirect:item.list?type=1";
    }

    @RequestMapping(value = {"/item.list", "/"}, method = RequestMethod.GET)
    public String itemList(ModelMap model, @RequestParam(required = false) Integer type) {
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        model.addAttribute("pageTitle", APP_PREFIX + "Action Taken List");
        model.addAttribute("items", actionTakenService.getAll());
        if (type != null) {
            model.addAttribute("message", AppMessage.getMessage(type));
        }
        return "admin/nameList";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.GET)
    public String getDeleteForm(@RequestParam("id") String id, ModelMap model) {
        ActionTaken item = actionTakenService.get(id);
        ItemDeleteDTO dto = new ItemDeleteDTO(id, item.getName() + " Action Taken", "item.list?type=3");
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", APP_PREFIX + "Delete " + item.getName() + " Action Taken");
        return "admin/deleteItem";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String deleteItem(@Valid ItemDeleteDTO dto) {
        actionTakenService.delete(actionTakenService.get(dto.getId()));
        return "redirect:item.list?type=2";
    }
}
