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
import zw.org.zvandiri.business.domain.ObstercHist;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.ObstercHistService;
import zw.org.zvandiri.business.service.PatientService;
import zw.org.zvandiri.business.util.dto.ItemDeleteDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import static zw.org.zvandiri.portal.web.controller.IAppTitle.APP_PREFIX;
import zw.org.zvandiri.portal.web.validator.ObstetricValidator;

/**
 *
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/beneficiary/obstetric")
public class ObstetricController extends BaseController {

    @Resource
    private ObstercHistService obstercHistService;
    @Resource
    private PatientService patientService;
    @Resource
    private ObstetricValidator obstetricValidator;

    public String setUpModel(ModelMap model, ObstercHist item) {
        model.addAttribute("pageTitle", APP_PREFIX + " " + item.getPatient().getName() + "'s Obstetric History");
        model.addAttribute("patient", item.getPatient());
        model.addAttribute("preg", Boolean.FALSE);
        model.addAttribute("birth", Boolean.FALSE);
        getPatientStatus(item.getPatient(), model);
        if (item.getPregnant() != null && item.getPregnant().equals(YesNo.YES)) {
            model.addAttribute("preg", Boolean.TRUE);
        }
        if (item.getPregCurrent() != null && item.getPregCurrent().equals(YesNo.YES)) {
            model.addAttribute("pregCurrent", Boolean.TRUE);
        }
        model.addAttribute("item", item);
        setViralLoad(model, item.getPatient());
        return "patient/obstetricForm";
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String getForm(ModelMap model, @RequestParam(required = false) String patientId, @RequestParam(required = false) String itemId) {
        ObstercHist item;
        if (itemId != null) {
            item = obstercHistService.get(itemId);
            return setUpModel(model, item);
        }
        item = new ObstercHist(patientService.get(patientId));
        return setUpModel(model, item);
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.POST)
    public String saveItem(ModelMap model, @ModelAttribute("item") @Valid ObstercHist item, BindingResult result) {
        if (!item.getPatient().getPatientStatus()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message(INACTIVE_MESSAGE).messageType(MessageType.ERROR).build());
            return setUpModel(model, item);
        }
        obstetricValidator.validate(item, result);
        if (result.hasErrors()) {
            setUpModel(model, item);
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return "patient/obstetricForm";
        }
        obstercHistService.save(item);
        return "redirect:../mental-health/item.list?type=1&id=" + item.getPatient().getId();
    }

    @RequestMapping(value = "/item.list", method = RequestMethod.GET)
    public String getItemList(@RequestParam String id, @RequestParam(required = false) Integer type, ModelMap model) {
        Patient item = patientService.get(id);
        ObstercHist obstercHist = obstercHistService.getByPatient(item);
        model.addAttribute("pageTitle", APP_PREFIX + " " + item.getName() + "'s Obstetric History");
        model.addAttribute("patient", item);
        if (type != null) {
            model.addAttribute("message", AppMessage.getMessage(type));
        }
        model.addAttribute("preg", Boolean.FALSE);
        model.addAttribute("birth", Boolean.FALSE);
        if (obstercHist != null) {
            if (obstercHist.getPregnant() != null && obstercHist.getPregnant().equals(YesNo.YES)) {
                model.addAttribute("preg", Boolean.TRUE);
            }
            if (obstercHist.getPregCurrent() != null && obstercHist.getPregCurrent().equals(YesNo.YES)) {
                model.addAttribute("pregCurrent", Boolean.TRUE);
            }
        }
        setViralLoad(model, item);
        getPatientStatus(item, model);
        model.addAttribute("obstericHist", obstercHist);
        return "patient/obstetricList";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.GET)
    public String getDeleteForm(@RequestParam("id") String id, ModelMap model) {
        ObstercHist item = obstercHistService.get(id);
        ItemDeleteDTO dto = new ItemDeleteDTO(id, item.getPatient().getName() + " : Obstetric History Item", "item.list?type=3&id=" + item.getPatient().getId());
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", APP_PREFIX + "Delete " + item.getPatient().getName() + " :" + dto.getName());
        return "admin/deleteItem";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String deleteItem(@Valid ItemDeleteDTO dto) {
        ObstercHist item = obstercHistService.get(dto.getId());
        Patient patient = item.getPatient();
        obstercHistService.delete(item);
        return "redirect:item.list?type=2&id=" + patient.getId();
    }
}
