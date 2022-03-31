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
import zw.org.zvandiri.business.service.RelationshipService;
import zw.org.zvandiri.business.util.dto.PrimaryCareGiverDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.validator.PrimaryCareGiverValidator;

/**
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/patient")
public class PrimaryCareGiverController extends BaseController {

    @Resource
    private PatientService patientService;
    @Resource
    private PrimaryCareGiverValidator primaryCareGiverValidator;
    @Resource
    private RelationshipService relationshipService;

    public String setUpModel(ModelMap model, PrimaryCareGiverDTO item) {
        model.addAttribute("pageTitle", APP_PREFIX + " " + item.getPatient().getName() + "'s Primary CareGiver");
        model.addAttribute("patient", item.getPatient());
        model.addAttribute("item", item);
        getPatientStatus(item.getPatient(), model);
        setViralLoad(model, item.getPatient());
        model.addAttribute("relationships", relationshipService.getAll());
        return "patient/primaryCareGiver";
    }

    @RequestMapping(value = "/primary-care-giver.form", method = RequestMethod.GET)
    public String getForm(ModelMap model, @RequestParam String id) {
        Patient item = patientService.get(id);
        return setUpModel(model, new PrimaryCareGiverDTO(item));
    }

    @RequestMapping(value = "/primary-care-giver.form", method = RequestMethod.POST)
    public String saveItem(ModelMap model, @ModelAttribute("item") @Valid PrimaryCareGiverDTO item, BindingResult result) {
        if (!item.getPatient().getPatientStatus()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message(INACTIVE_MESSAGE).messageType(MessageType.ERROR).build());
            return setUpModel(model, item);
        }
        primaryCareGiverValidator.validate(item, result);
        if (result.hasErrors()) {
            setUpModel(model, item);
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return "patient/primaryCareGiver";
        }
        Patient p = patientService.save(item.getInstance(item));
        return "redirect:dashboard/profile.htm?type=1&id=" + p.getId();
    }
}
