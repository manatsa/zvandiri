/*
 * Copyright 2018 tasu.
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
import zw.org.zvandiri.business.domain.MentalHealthScreening;
import zw.org.zvandiri.business.domain.util.Diagnosis;
import zw.org.zvandiri.business.domain.util.Gender;
import zw.org.zvandiri.business.domain.util.IdentifiedRisk;
import zw.org.zvandiri.business.domain.util.Intervention;
import zw.org.zvandiri.business.domain.util.MentalHealthScreeningType;
import zw.org.zvandiri.business.domain.util.Referral;
import zw.org.zvandiri.business.domain.util.Support;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.ActionTakenService;
import zw.org.zvandiri.business.service.MentalHealthScreeningService;
import zw.org.zvandiri.business.service.PatientService;
import zw.org.zvandiri.business.util.dto.ItemDeleteDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import static zw.org.zvandiri.portal.web.controller.IAppTitle.APP_PREFIX;
import static zw.org.zvandiri.portal.web.controller.IAppTitle.INACTIVE_MESSAGE;
import zw.org.zvandiri.portal.web.validator.MentalHealthScreeningValidator;

/**
 *
 * @author tasu
 */
@Controller
@RequestMapping("/beneficiary/mental-health-screening")
public class MentalHealthScreeningController extends BaseController{
    
    @Resource
    private PatientService patientService;
    @Resource
    private MentalHealthScreeningService service;
    @Resource
    private MentalHealthScreeningValidator validator;
    @Resource
    private ActionTakenService actionTakenService;
    
    
    public String setUpModel(ModelMap model, MentalHealthScreening item) {
        model.addAttribute("pageTitle", APP_PREFIX + " " + item.getPatient().getName() + ": Mental Health Screening");
        model.addAttribute("patient", item.getPatient());
        model.addAttribute("item", item);
        model.addAttribute("risks", IdentifiedRisk.values());
        model.addAttribute("supports", Support.values());
        model.addAttribute("referrals", Referral.values());
        model.addAttribute("diagnoses", Diagnosis.values());
        model.addAttribute("interventions", Intervention.values());
        model.addAttribute("yesNo", YesNo.values());
        model.addAttribute("mentalHealthScreeningType", MentalHealthScreeningType.values());
        model.addAttribute("showForm", Boolean.FALSE);
        model.addAttribute("showRisk", Boolean.FALSE);
        model.addAttribute("showSupport", Boolean.FALSE);
        model.addAttribute("showReferral", Boolean.FALSE);
        model.addAttribute("showDiagnosis", Boolean.FALSE);
        model.addAttribute("showIntervention", Boolean.FALSE);
        model.addAttribute("formAction", "item.form");
        if(item.getScreenedForMentalHealth() != null) {
            if(item.getScreenedForMentalHealth().equals(YesNo.YES)) {
                model.addAttribute("showForm", Boolean.TRUE);
            }else{
                model.addAttribute("showForm", Boolean.FALSE);
            }
        }
        if(item.getRisk()!= null) {
            if(item.getRisk().equals(YesNo.YES)) {
                model.addAttribute("showRisk", Boolean.TRUE);
            }else{
                model.addAttribute("showRisk", Boolean.FALSE);
            }
        }
        if(item.getSupport()!= null) {
            if(item.getSupport().equals(YesNo.YES)) {
                model.addAttribute("showSupport", Boolean.TRUE);
            }else{
                model.addAttribute("showSupport", Boolean.FALSE);
            }
        }
        if(item.getReferral()!= null) {
            if(item.getReferral().equals(YesNo.YES)) {
                model.addAttribute("showReferral", Boolean.TRUE);
            }else{
                model.addAttribute("showReferral", Boolean.FALSE);
            }
        }
        if(item.getDiagnosis()!= null) {
            if(item.getDiagnosis().equals(YesNo.YES)) {
                model.addAttribute("showDiagnosis", Boolean.TRUE);
            }else{
                model.addAttribute("showDiagnosis", Boolean.FALSE);
            }
        }
        if(item.getIntervention()!= null) {
            if(item.getIntervention().equals(YesNo.YES)) {
                model.addAttribute("showIntervention", Boolean.TRUE);
            }else{
                model.addAttribute("showIntervention", Boolean.FALSE);
            }
        }
        getPatientStatus(item.getPatient(), model);
        setViralLoad(model, item.getPatient());
        return "patient/mentalHealthScreeningForm1";
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String getForm(ModelMap model, @RequestParam(required = false) String id, @RequestParam(required = false) String itemId) {
        MentalHealthScreening item;
        if (itemId != null) {
            item = service.get(itemId);
            System.err.println(item);
            return setUpModel(model, item);
        }else {
            item = new MentalHealthScreening(patientService.get(id));
            return setUpModel(model, item);
        }
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.POST)
    public String saveItem(ModelMap model, @ModelAttribute("item") @Valid MentalHealthScreening item, BindingResult result) {
        System.err.println("MH ITEM:: "+item);
        if (!item.getPatient().getPatientStatus()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message(INACTIVE_MESSAGE).messageType(MessageType.ERROR).build());
            return setUpModel(model, item);
        }
        validator.validate(item, result);
        if (result.hasErrors()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return setUpModel(model, item);
        }
        service.save(item);
        if(item.getPatient().getGender() != null && item.getPatient().getGender().equals(Gender.MALE)){
            return "redirect:../mental-health-screening/item.list?type=1&id=" + item.getPatient().getId();
        }
        return "redirect:item.list?type=1&id=" + item.getPatient().getId();
    }

    @RequestMapping(value = "/item.list", method = RequestMethod.GET)
    public String getItemList(@RequestParam String id, @RequestParam(required = false) Integer type, ModelMap model) {
        Patient patient = patientService.get(id);
        model.addAttribute("pageTitle", APP_PREFIX + " " + patient.getName() + ": Mental Health Screening");
        model.addAttribute("patient", patient);
        model.addAttribute("screens", service.findByPatient(patient));
        if (type != null) {
            model.addAttribute("message", AppMessage.getMessage(type));
        }
        getPatientStatus(patient, model);
        setViralLoad(model, patient);
        return "patient/mentalHealthScreeningList";
    }
    
    @RequestMapping(value = "item.delete", method = RequestMethod.GET)
    public String getDeleteForm(@RequestParam("id") String id, ModelMap model) {
        MentalHealthScreening item = service.get(id);
        ItemDeleteDTO dto = new ItemDeleteDTO(id, item.getPatient().getName() + " : Mental Health Screening", "item.list?type=3&id=" + item.getPatient().getId());
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", APP_PREFIX + "Delete " + item.getPatient().getName() + " :" + dto.getName());
        return "admin/deleteItem";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String deleteItem(@Valid ItemDeleteDTO dto) {
        MentalHealthScreening item = service.get(dto.getId());
        Patient patient = item.getPatient();
        service.delete(item);
        return "redirect:item.list?type=2&id=" + patient.getId();
    }
    
    @RequestMapping(value = "reload-form", method = RequestMethod.POST)
    public String reloadForm(ModelMap model, @ModelAttribute("item") MentalHealthScreening item) {
        return setUpModel(model, item);
    }
}
