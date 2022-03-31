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
import zw.org.zvandiri.business.domain.UserRole;
import zw.org.zvandiri.business.service.UserRoleService;
import zw.org.zvandiri.business.util.dto.ItemDeleteDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.validator.UserRoleValidator;

/**
 * @author Edward Zengeni
 */
@Controller
@RequestMapping("/admin/user-role")
public class UserRoleController extends BaseController {

    @Resource
    private UserRoleService userRoleService;
    @Resource
    private UserRoleValidator userRoleValidator;

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String userRoleForm(ModelMap model, @RequestParam(required = false) String id) {
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        model.addAttribute("pageTitle", APP_PREFIX + "Create/ Edit UserRole");
        UserRole p = new UserRole();
        if (id != null) {
            p = userRoleService.get(id);
        }
        model.addAttribute("item", p);
        model.addAttribute("itemDelete", "item.list?type=3");
        return "admin/userRoleForm";
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.POST)
    public String saveUserRole(@ModelAttribute("item") @Valid UserRole userRole, BindingResult result, ModelMap model) {
        userRoleValidator.validate(userRole, result);
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", APP_PREFIX + "Create/ Edit UserRole");
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            model.addAttribute("item", userRole);
            return "admin/userRoleForm";
        }
        userRoleService.save(userRole);
        return "redirect:item.list?type=1";
    }

    @RequestMapping(value = {"/item.list", "/"}, method = RequestMethod.GET)
    public String userRoleList(ModelMap model, @RequestParam(required = false) Integer type) {
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        model.addAttribute("pageTitle", APP_PREFIX + "UserRole List");
        model.addAttribute("items", userRoleService.getAll());
        if (type != null) {
            model.addAttribute("message", getMessage(type));
        }
        return "admin/userRoleList";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.GET)
    public String getUserRoleDeleteForm(@RequestParam("id") String id, ModelMap model) {
        UserRole userRole = userRoleService.get(id);
        ItemDeleteDTO dto = new ItemDeleteDTO(id, userRole.getName(), "item.list?type=3");
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", APP_PREFIX + "Delete " + userRole.getName() + " UserRole");
        return "admin/deleteItem";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String deleteUserRole(@Valid ItemDeleteDTO dto) {
        userRoleService.delete(userRoleService.get(dto.getId()));
        return "redirect:userRole.list?type=2";
    }
}
