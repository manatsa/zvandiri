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
import zw.org.zvandiri.business.domain.Referral;
import zw.org.zvandiri.business.domain.util.ReferalType;
import zw.org.zvandiri.business.service.PatientService;
import zw.org.zvandiri.business.service.ReferralService;
import zw.org.zvandiri.business.service.ServicesReferredService;
import zw.org.zvandiri.business.util.dto.ItemDeleteDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.validator.ReferralValidator;

/**
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/patient/referral")
public class ReferralController extends BaseController {

    @Resource
    private ServicesReferredService servicesReferredService;
    @Resource
    private PatientService patientService;
    @Resource
    private ReferralService referralService;
    @Resource
    private ReferralValidator referralValidator;

    public String setUpModel(ModelMap model, Referral item, String view) {
        model.addAttribute("pageTitle", APP_PREFIX + "Referral for : " + item.getPatient().getName());
        model.addAttribute("hivStiItems", servicesReferredService.getByType(ReferalType.HIV_STI_PREVENTION));
        model.addAttribute("laboratoryItems", servicesReferredService.getByType(ReferalType.LABORATORY_DIAGNOSES));
        model.addAttribute("oiArtItems", servicesReferredService.getByType(ReferalType.OI_ART_SERVICES));
        model.addAttribute("tbItems", servicesReferredService.getByType(ReferalType.TB_SERVICES));
        model.addAttribute("srhItems", servicesReferredService.getByType(ReferalType.SRH_SERVICES));
        model.addAttribute("psychItems", servicesReferredService.getByType(ReferalType.PSYCHO_SOCIAL_SUPPORT));
        model.addAttribute("legalItems", servicesReferredService.getByType(ReferalType.LEGAL_SUPPORT));
        model.addAttribute("item", item);
        getPatientStatus(item.getPatient(), model);
        model.addAttribute("patient", item.getPatient());
        setViralLoad(model, item.getPatient());
        return "patient/" + view;
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String getForm(ModelMap model, @RequestParam(required = false) String itemId, @RequestParam(required = false) String patientId) {
        if (itemId != null) {
            return setUpModel(model, referralService.get(itemId), "referralForm");
        }
        return setUpModel(model, new Referral(patientService.get(patientId)), "referralForm");
    }

    @RequestMapping(value = "/item.view", method = RequestMethod.GET)
    public String getreferralView(ModelMap model, @RequestParam(required = false) String itemId, @RequestParam(required = false) String patientId) {
        if (itemId != null) {
            return setUpModel(model, referralService.get(itemId), "referralView");
        }
        return setUpModel(model, new Referral(patientService.get(patientId)), "referralView");
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.POST)
    public String saveItem(ModelMap model, @ModelAttribute("item") Referral item, BindingResult result) {
        if (!item.getPatient().getPatientStatus()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message(INACTIVE_MESSAGE).messageType(MessageType.ERROR).build());
            return setUpModel(model, item, "referralForm");
        }
        referralValidator.validate(item, result);
        if (result.hasErrors()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return setUpModel(model, item, "referralForm");
        }
        referralService.save(item);
        return "redirect:item.list?id=" + item.getPatient().getId();
    }

    @RequestMapping(value = "/item.list", method = RequestMethod.GET)
    public String getList(ModelMap model, @RequestParam String id) {
        Patient item = patientService.get(id);
        model.addAttribute("pageTitle", APP_PREFIX + "Referrals for :" + item.getName());
        model.addAttribute("items", referralService.getByPatient(item));
        model.addAttribute("patient", item);
        getPatientStatus(item, model);
        setViralLoad(model, item);
        return "patient/referralList";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.GET)
    public String getDeleteForm(@RequestParam("id") String id, ModelMap model) {
        Referral item = referralService.get(id);
        ItemDeleteDTO dto = new ItemDeleteDTO(id, item.getPatient().getName() + " : Obstetric History Item", "item.list?type=3&id=" + item.getPatient().getId());
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", APP_PREFIX + "Delete " + item.getPatient().getName() + " :" + dto.getName());
        return "admin/deleteItem";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String deleteItem(@Valid ItemDeleteDTO dto) {
        Referral item = referralService.get(dto.getId());
        Patient patient = item.getPatient();
        referralService.delete(item);
        return "redirect:item.list?type=2&id=" + patient.getId();
    }
}
