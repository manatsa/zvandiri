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
import zw.org.zvandiri.business.domain.SocialHist;
import zw.org.zvandiri.business.domain.util.AbuseType;
import zw.org.zvandiri.business.domain.util.Gender;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.PatientService;
import zw.org.zvandiri.business.service.RelationshipService;
import zw.org.zvandiri.business.service.SocialHistService;
import zw.org.zvandiri.business.util.dto.ItemDeleteDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import static zw.org.zvandiri.portal.web.controller.IAppTitle.APP_PREFIX;
import zw.org.zvandiri.portal.web.validator.SocialHistoryValidator;

/**
 *
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/beneficiary/socialhist")
public class SocialHistoryController extends BaseController {

    @Resource
    private SocialHistService socialHistService;
    @Resource
    private PatientService patientService;
    @Resource
    private SocialHistoryValidator socialHistoryValidator;
    @Resource
    private RelationshipService relationshipService;

    public String setUpModel(ModelMap model, SocialHist item) {
        model.addAttribute("pageTitle", APP_PREFIX + " " + item.getPatient().getName() + "'s Social History");
        model.addAttribute("patient", item.getPatient());
        model.addAttribute("item", item);
        model.addAttribute("relationships", relationshipService.getAll());
        model.addAttribute("abuse", Boolean.FALSE);
        model.addAttribute("abuseTypes", AbuseType.values());
        getPatientStatus(item.getPatient(), model);
        setViralLoad(model, item.getPatient());
        if(item.getAbuse() != null && item.getAbuse().equals(YesNo.YES)){
            model.addAttribute("abuse", Boolean.TRUE);
        }
        return "patient/socialHistForm";
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String getForm(ModelMap model, @RequestParam(required = false) String patientId, @RequestParam(required = false) String itemId) {
        SocialHist item;
        if (itemId != null) {
            item = socialHistService.get(itemId);
            return setUpModel(model, item);
        }
        item = new SocialHist(patientService.get(patientId));
        return setUpModel(model, item);
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.POST)
    public String saveItem(ModelMap model, @ModelAttribute("item") @Valid SocialHist item, BindingResult result) {
        if (!item.getPatient().getPatientStatus()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message(INACTIVE_MESSAGE).messageType(MessageType.ERROR).build());
            return setUpModel(model, item);
        }
        socialHistoryValidator.validate(item, result);
        if (result.hasErrors()) {
            setUpModel(model, item);
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return "patient/socialHistForm";
        }
        socialHistService.save(item);
        if(item.getPatient().getGender() != null && item.getPatient().getGender().equals(Gender.MALE)){
            return "redirect:../mental-health/item.list?type=1&id=" + item.getPatient().getId();
        }
        return "redirect:..//obstetric/item.list?type=1&id=" + item.getPatient().getId();
    }

    @RequestMapping(value = "/item.list", method = RequestMethod.GET)
    public String getItemList(@RequestParam String id, @RequestParam(required = false) Integer type, ModelMap model) {
        Patient patient = patientService.get(id);
        SocialHist item = socialHistService.getByPatient(patient);
        model.addAttribute("pageTitle", APP_PREFIX + " " + patient.getName() + "'s Social History");
        model.addAttribute("patient", patient);
        model.addAttribute("female", Boolean.FALSE);
        if(patient.getGender() != null && patient.getGender().equals(Gender.FEMALE)){
            model.addAttribute("female", Boolean.TRUE);
        }
        if (type != null) {
            model.addAttribute("message", AppMessage.getMessage(type));
        }
        model.addAttribute("abuse", Boolean.FALSE);
        if(item != null && item.getAbuse() != null && item.getAbuse().equals(YesNo.YES)){
            model.addAttribute("abuse", Boolean.TRUE);
        }
        getPatientStatus(patient, model);
        setViralLoad(model, patient);
        model.addAttribute("socialHist", item);
        return "patient/socialHistList";
    }
    
    @RequestMapping(value = "item.delete", method = RequestMethod.GET)
    public String getDeleteForm(@RequestParam("id") String id, ModelMap model) {
        SocialHist item = socialHistService.get(id);
        ItemDeleteDTO dto = new ItemDeleteDTO(id, item.getPatient().getName() + " : Social History Item", "item.list?type=3&id=" + item.getPatient().getId());
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", APP_PREFIX + "Delete " + item.getPatient().getName() + " :" + dto.getName());
        return "admin/deleteItem";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String deleteItem(@Valid ItemDeleteDTO dto) {
        SocialHist item = socialHistService.get(dto.getId());
        Patient patient = item.getPatient();
        socialHistService.delete(item);
        return "redirect:item.list?type=2&id=" + patient.getId();
    }
}