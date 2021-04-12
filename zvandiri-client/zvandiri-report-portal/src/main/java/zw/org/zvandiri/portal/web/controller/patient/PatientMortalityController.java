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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import zw.org.zvandiri.business.domain.Mortality;
import zw.org.zvandiri.business.domain.PatientHistory;
import zw.org.zvandiri.business.domain.util.PatientChangeEvent;
import zw.org.zvandiri.business.service.PatientHistoryService;
import zw.org.zvandiri.business.service.PatientService;
import zw.org.zvandiri.business.util.dto.PatientDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.validator.MortalityValidator;

/**
 *
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/patient/patient-death")
public class PatientMortalityController extends BaseController {

    @Resource
    private PatientService patientService;
    @Resource
    private MortalityValidator mortalityValidator;
    @Resource
    private PatientHistoryService patientHistoryService;

    public String setUpModel(ModelMap model, Mortality item) {
        model.addAttribute("pageTitle", APP_PREFIX + item.getPatient().getName() + " : Record Patient Death");
        model.addAttribute("item", item);
        model.addAttribute("patient", item.getPatient());
        getPatientStatus(item.getPatient(), model);
        setViralLoad(model, item.getPatient());
        return "patient/patientMortalityForm";
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String getForm(ModelMap model, @RequestParam String id) {
        return setUpModel(model, new Mortality(patientService.get(id)));
    }

    @RequestMapping(value = "/item", method = RequestMethod.POST)
    public String saveItem(ModelMap model, @ModelAttribute("item") Mortality item, BindingResult result) {
        if (!item.getPatient().getPatientStatus()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message(INACTIVE_MESSAGE).messageType(MessageType.ERROR).build());
            return setUpModel(model, item);
        }
        mortalityValidator.validate(item, result);
        if (result.hasErrors()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return setUpModel(model, item);
        }
        PatientDTO dto = new PatientDTO(item.getPatient());
        dto.getPatient().setStatus(PatientChangeEvent.DECEASED);
        patientHistoryService.saveMortality(new PatientHistory(item.getPatient()), dto.getPatientStatusInstance(dto), item);
        return "redirect:../dashboard/profile.htm?type=1&id=" + item.getPatient().getId();
    }
}
