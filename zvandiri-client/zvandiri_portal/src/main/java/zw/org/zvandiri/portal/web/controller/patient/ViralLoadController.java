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
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.InvestigationTest;
import zw.org.zvandiri.business.domain.util.TestType;
import zw.org.zvandiri.business.service.PatientService;
import zw.org.zvandiri.business.service.InvestigationTestService;
import zw.org.zvandiri.business.util.dto.ItemDeleteDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.validator.ViralLoadValidator;

/**
 *
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/patient/viral-load")
public class ViralLoadController extends BaseController {
    
    @Resource
    private InvestigationTestService investigationTestService;
    @Resource
    private PatientService patientService;
    @Resource
    private ViralLoadValidator viralLoadValidator;

    //Patient patient;

//    @InitBinder
//    protected void initBinder(final HttpServletRequest request, final ServletRequestDataBinder binder) {
//        binder.addValidators(viralLoadValidator);
//    }

    public String setUpModel(ModelMap model, InvestigationTest item) {
        model.addAttribute("pageTitle", APP_PREFIX + " " + item.getPatient().getName() + "'s Viral Load");
        model.addAttribute("patient", item.getPatient());
        model.addAttribute("item", item);
        model.addAttribute("investigationTest", Boolean.TRUE);
        model.addAttribute("eid", Boolean.FALSE);
        getPatientStatus(item.getPatient(), model);
        setViralLoad(model, item.getPatient());
        return "patient/labResultForm";
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String getForm(ModelMap model, @RequestParam(required = false) String patientId, @RequestParam(required = false) String itemId) {
        InvestigationTest item;
        if (itemId != null) {
            item = investigationTestService.get(itemId);
            return setUpModel(model, item);
        }else{
            Patient patient=patientService.get(patientId);
            item = new InvestigationTest(patient, TestType.VIRAL_LOAD);
            return setUpModel(model, item);
        }

    }

    @RequestMapping(value = "/item.form", method = RequestMethod.POST)
    public String saveItem(ModelMap model, @ModelAttribute("item") @Valid InvestigationTest item, BindingResult result) {
        Patient patient=item.getPatient();
        //System.err.println(patient);

        if (!patient.getPatientStatus()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message(INACTIVE_MESSAGE).messageType(MessageType.ERROR).build());
            return setUpModel(model, item);
        }
        item.setPatient(patient);

        viralLoadValidator.validate(item, result);
        if (result.hasErrors()) {
            setUpModel(model, item);
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message(makeStringErrors(result)).messageType(MessageType.ERROR).build());
            return setUpModel(model, item);
        }

        investigationTestService.save(item);
        return "redirect:../dashboard/profile.htm?type=1&id=" + item.getPatient().getId();
    }

    private String makeStringErrors(BindingResult errors){
        String result="";
        for(ObjectError error:errors.getAllErrors()){
            result+=error.toString()+"\n";
        }
        return result;
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.GET)
    public String getDeleteForm(@RequestParam("id") String id, ModelMap model) {
        InvestigationTest item = investigationTestService.get(id);
        ItemDeleteDTO dto = new ItemDeleteDTO(id, item.getPatient().getName() + " : Viral Load Item", "../dashboard/profile.htm?type=3&id=" + item.getPatient().getId());
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", APP_PREFIX + "Delete " + item.getPatient().getName() + " :" + dto.getName());
        return "admin/deleteItem";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String deleteItem(@Valid ItemDeleteDTO dto) {
        InvestigationTest item = investigationTestService.get(dto.getId());
        Patient patient = item.getPatient();
        investigationTestService.delete(item);
        return "redirect:../dashboard/profile.htm?type=2&id=" + patient.getId();
    }
}