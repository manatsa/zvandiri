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
import zw.org.zvandiri.business.domain.TbIpt;
import zw.org.zvandiri.business.domain.util.TbIdentificationOutcome;
import zw.org.zvandiri.business.domain.util.TbSymptom;
import zw.org.zvandiri.business.domain.util.TbTreatmentOutcome;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.PatientService;
import zw.org.zvandiri.business.service.TbIptService;
import zw.org.zvandiri.business.util.dto.ItemDeleteDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;

import static zw.org.zvandiri.portal.web.controller.IAppTitle.APP_PREFIX;

import zw.org.zvandiri.portal.web.validator.TbScreeningValidator;

/**
 * @author tasu
 */
@Controller
@RequestMapping("/patient/tb-screening")
public class TbScreeningController extends BaseController {

    @Resource
    private PatientService patientService;
    @Resource
    private TbIptService service;
    @Resource
    private TbScreeningValidator validator;

    public String setUpModel(ModelMap map, TbIpt item) {
        map.addAttribute("pageTitle", APP_PREFIX + " " + item.getPatient().getName() + "'s Tb Screening History");
        map.addAttribute("item", item);
        map.addAttribute("patient", item.getPatient());
        map.addAttribute("yesNo", YesNo.values());
        map.addAttribute("outcomes", TbTreatmentOutcome.values());
        map.addAttribute("symptoms", TbSymptom.values());
        map.addAttribute("tbIdentificationOutcomes", TbIdentificationOutcome.values());
        setViralLoad(map, item.getPatient());
        map.addAttribute("formAction", "item.form");
        map.addAttribute("showForm", Boolean.FALSE);
        map.addAttribute("showActionTaken", Boolean.FALSE);
        map.addAttribute("onTbTreatment", Boolean.FALSE);
        map.addAttribute("onIpt", Boolean.FALSE);

        if (item.getScreenedForTb() != null && item.getScreenedForTb().equals(YesNo.YES)) {
            map.addAttribute("showForm", Boolean.TRUE);
        } else {
            map.addAttribute("showForm", Boolean.FALSE);
        }
        if (item.getIdentifiedWithTb() != null && item.getIdentifiedWithTb().equals(YesNo.YES)) {
            map.addAttribute("showActionTaken", Boolean.TRUE);
        } else {
            map.addAttribute("showActionTaken", Boolean.FALSE);
        }
        if (item.getTbIdentificationOutcome() != null && item.getTbIdentificationOutcome().equals(TbIdentificationOutcome.ON_TB_TREATMENT)) {
            map.addAttribute("onTbTreatment", Boolean.TRUE);
        } else {
            map.addAttribute("onTbTreatment", Boolean.FALSE);
        }
        if (item.getOnIpt() != null && item.getOnIpt().equals(YesNo.YES)) {
            map.addAttribute("onIpt", Boolean.TRUE);
        } else {
            map.addAttribute("onIpt", Boolean.FALSE);
        }
        return "patient/tbScreeningForm";
    }

    @RequestMapping(value = "item.form", method = RequestMethod.GET)
    public String getForm(ModelMap map, @RequestParam(required = false) String id, @RequestParam(required = false) String patientId) {
        TbIpt item;
        if (id != null) {
            item = service.get(id);
            return setUpModel(map, item);
        }
        item = new TbIpt(patientService.get(patientId));
        return setUpModel(map, item);
    }

    @RequestMapping(value = "item.form", method = RequestMethod.POST)
    public String saveItem(ModelMap map, @ModelAttribute("item") @Valid TbIpt item, BindingResult result) {
        validator.validate(item, result);
        map.addAttribute("message", new AppMessage.MessageBuilder().build());
        if (result.hasErrors()) {
            map.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return setUpModel(map, item);
        }
        service.save(item);
        return "redirect:item.list?type=1&id=" + item.getPatient().getId();
    }

    @RequestMapping(value = "/item.list", method = RequestMethod.GET)
    public String getItemList(@RequestParam String id, @RequestParam(required = false) Integer type, ModelMap model) {
        Patient item = patientService.get(id);
        model.addAttribute("pageTitle", APP_PREFIX + " " + item.getName() + "'s Tb Screening History");
        model.addAttribute("patient", item);
        if (type != null) {
            model.addAttribute("message", AppMessage.getMessage(type));
        }
        getPatientStatus(item, model);
        setViralLoad(model, item);
        model.addAttribute("tbScreen", service.getLatest(item));
        model.addAttribute("items", service.getByPatient(item));
        return "patient/tbScreeningList";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.GET)
    public String getDeleteForm(@RequestParam("id") String id, ModelMap model) {
        Patient patient = patientService.get(id);
        ItemDeleteDTO dto = new ItemDeleteDTO(id, patient.getName(), "item.list");
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", APP_PREFIX + "Delete " + patient.getName());
        return "admin/deleteItem";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String delete(@Valid ItemDeleteDTO dto) {
        TbIpt item = service.get(dto.getId());
        Patient patient = item.getPatient();
        service.delete(item);
        return "redirect:item.list?type=2&id=" + patient.getId();
    }

    @RequestMapping(value = "reload-form", method = RequestMethod.POST)
    public String reloadForm(ModelMap model, @ModelAttribute("item") TbIpt item) {
        return setUpModel(model, item);
    }
}
