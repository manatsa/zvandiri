/*
 * Copyright 2017 jmuzinda.
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

import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.Gender;
import zw.org.zvandiri.business.service.PatientService;
import zw.org.zvandiri.business.util.dto.PatientHeuDTO;
import zw.org.zvandiri.business.util.dto.PatientSearchDTO;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.validator.PatientValidator;

/**
 * @author jmuzinda
 */
@Controller
@RequestMapping("/patient/heu")
public class HeuController extends BaseController {

    @Resource
    private PatientService patientService;
    @Resource
    private PatientValidator patientValidator;

    public String setUpModel(ModelMap model, PatientHeuDTO item) {
        model.addAttribute("pageTitle", APP_PREFIX + " Add " + item.getPatient().getName() + " Mother's Details");
        model.addAttribute("patient", item.getPatient());
        model.addAttribute("item", item);
        getPatientStatus(item.getPatient(), model);
        setViralLoad(model, item.getPatient());
        return "patient/heuMotherForm";
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String getForm(ModelMap model, @RequestParam(required = false) String patientId, @RequestParam(required = false) String motherId) {
        PatientHeuDTO item = new PatientHeuDTO();
        item.setPatient(patientService.get(patientId));
        if (motherId != null) {
            item.setMotherOfHeu(patientService.get(motherId));
        }
        return setUpModel(model, item);
    }

    @RequestMapping(value = "/item.save", method = RequestMethod.GET)
    public String saveItem(ModelMap model, @ModelAttribute("item") @Valid PatientHeuDTO item, BindingResult result) {
        patientValidator.validateHeu(item, result);
        if (result.hasErrors()) {
            setUpModel(model, item);
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return setUpModel(model, item);
        }
        patientService.save(item.getInstance(item));
        return "redirect:../dashboard/profile.htm?type=1&id=" + item.getPatient().getId();
    }

    @RequestMapping(value = "/search-heu-mothers", method = RequestMethod.GET)
    @ResponseBody
    public List<PatientSearchDTO> searchPatient(@RequestParam("search") String search, @RequestParam("patientId") String patientId) {
        Patient item = patientService.get(patientId);
        String[] names = search.split(" ");
        SearchDTO dto = new SearchDTO();
        dto.setGender(Gender.FEMALE);
        dto.setDistrict(item.getPrimaryClinic().getDistrict());
        return PatientSearchDTO.getInstance(patientService.getYoungMothers(dto, names));
    }
}
