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
package zw.org.zvandiri.portal.web.controller.admin;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.Settings;
import zw.org.zvandiri.business.service.SettingsService;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.validator.SettingsValidator;

/**
 *
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/admin/settings")
public class SettingsController extends BaseController {
    
    @Resource
    private SettingsService settingsService;
    @Resource
    private SettingsValidator settingsValidator;
    
    public String setUpModel(ModelMap model, Settings item){
        model.addAttribute("pageTitle", APP_PREFIX+"Create/ Edit System Settings");
        model.addAttribute("item", item);
        model.addAttribute("itemDelete", "item.form");
        return "admin/systemSettingsForm";
    }
    
    @RequestMapping(value = {"/item.form", "/"}, method = RequestMethod.GET)
    public String getForm(ModelMap model){
        Settings item = settingsService.getItem();
        if(item != null){
            return setUpModel(model, item);
        }
        return setUpModel(model, new Settings());
    }
    
    @RequestMapping(value = {"/item.form", "/"}, method = RequestMethod.POST)
    public String saveItem(ModelMap model, @ModelAttribute("item") Settings item, BindingResult result){
        settingsValidator.validate(item, result);
        if(result.hasErrors()){
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return setUpModel(model, item);
        }
        settingsService.save(item);
        return "redirect:item.form";
    }
}