/*
 * Copyright 2016 Judge Muzinda
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
import zw.org.zvandiri.business.domain.ArvAdverseEffect;
import zw.org.zvandiri.business.domain.ArvHist;
import zw.org.zvandiri.business.service.ArvAdverseEffectService;
import zw.org.zvandiri.business.service.ArvHistService;
import zw.org.zvandiri.business.util.dto.ItemDeleteDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.validator.ArvAdverseEffectsValidator;

/**
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/patient/arv-adverse-effects")
public class ArvAdverseEffectsController extends BaseController {

    @Resource
    private ArvAdverseEffectService arvAdverseEffectService;
    @Resource
    private ArvHistService arvHistService;
    @Resource
    private ArvAdverseEffectsValidator arvAdverseEffectsValidator;

    public String setUpModel(ModelMap model, ArvAdverseEffect item) {
        model.addAttribute("pageTitle", APP_PREFIX + "Adverse Effect to :" + item.getArvHist().getArvMedicine());
        model.addAttribute("item", item);
        model.addAttribute("patient", item.getArvHist().getPatient());
        getPatientStatus(item.getArvHist().getPatient(), model);
        setViralLoad(model, item.getArvHist().getPatient());
        return "patient/arvAdverseEffectForm";
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String getForm(ModelMap model, @RequestParam(required = false) String itemId, @RequestParam(required = false) String arvHistId) {
        if (itemId != null) {
            return setUpModel(model, arvAdverseEffectService.get(itemId));
        }
        return setUpModel(model, new ArvAdverseEffect(arvHistService.get(arvHistId)));
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.POST)
    public String saveItem(ModelMap model, @ModelAttribute("item") @Valid ArvAdverseEffect item, BindingResult result) {
        if (!item.getArvHist().getPatient().getPatientStatus()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message(INACTIVE_MESSAGE).messageType(MessageType.ERROR).build());
            return setUpModel(model, item);
        }
        arvAdverseEffectsValidator.validate(item, result);
        if (result.hasErrors()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return setUpModel(model, item);
        }
        arvAdverseEffectService.save(item);
        return "redirect:item.list?id=" + item.getArvHist().getId();
    }

    @RequestMapping(value = "/item.list", method = RequestMethod.GET)
    public String getItemList(ModelMap model, @RequestParam String id) {
        ArvHist item = arvHistService.get(id);
        model.addAttribute("pageTitle", APP_PREFIX + item.getArvMedicine().getName() + " : Adverse Effects List");
        model.addAttribute("item", item);
        model.addAttribute("patient", item.getPatient());
        getPatientStatus(item.getPatient(), model);
        setViralLoad(model, item.getPatient());
        model.addAttribute("items", arvAdverseEffectService.getByArvHist(item));
        return "patient/arvAdverseEffectList";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.GET)
    public String getDeleteForm(@RequestParam("id") String id, ModelMap model) {
        ArvAdverseEffect item = arvAdverseEffectService.get(id);
        ItemDeleteDTO dto = new ItemDeleteDTO(id, item.getArvHist().getArvMedicine().getName() + " : Adverse Effect Item", "item.list?type=3&id=" + item.getArvHist().getId());
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", APP_PREFIX + "Delete " + item.getArvHist().getArvMedicine().getName() + " :" + dto.getName());
        return "admin/deleteItem";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String deleteItem(@Valid ItemDeleteDTO dto) {
        ArvAdverseEffect item = arvAdverseEffectService.get(dto.getId());
        ArvHist arvHist = item.getArvHist();
        arvAdverseEffectService.delete(item);
        return "redirect:item.list?type=2&id=" + arvHist.getId();
    }
}