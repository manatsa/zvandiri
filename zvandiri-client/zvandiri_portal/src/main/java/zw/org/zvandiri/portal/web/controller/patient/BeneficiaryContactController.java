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
package zw.org.zvandiri.portal.web.controller.patient;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.service.PatientService;
import zw.org.zvandiri.business.util.dto.ContactDetailsDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.validator.ContactValidator;

/**
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/patient")
public class BeneficiaryContactController extends BaseController {

    @Resource
    private PatientService patientService;
    @Resource
    private ContactValidator contactValidator;

    public void setUpModel(ModelMap model, ContactDetailsDTO item) {
        model.addAttribute("pageTitle", APP_PREFIX + " " + item.getPatient().getName() + "'s Contact Details");
        model.addAttribute("patient", item.getPatient());
        getPatientStatus(item.getPatient(), model);
        setViralLoad(model, item.getPatient());
        model.addAttribute("item", item);
    }

    @RequestMapping(value = "/contact.form", method = RequestMethod.GET)
    public String getForm(ModelMap model, @RequestParam String id) {
        Patient item = patientService.get(id);
        setUpModel(model, new ContactDetailsDTO(item));
        return "patient/beneficiaryContactForm";
    }

    @RequestMapping(value = "/contact.form", method = RequestMethod.POST)
    public String saveItem(ModelMap model, @ModelAttribute("item") @Valid ContactDetailsDTO item, BindingResult result) {
        contactValidator.validate(item, result);
        if (result.hasErrors()) {
            setUpModel(model, item);
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return "patient/beneficiaryContactForm";
        }
        Patient p = patientService.save(item.getInstance(item));
        return "redirect:dashboard/profile.htm?type=1&id=" + p.getId();
    }
}