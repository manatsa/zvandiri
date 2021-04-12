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
package zw.org.zvandiri.portal.web.controller.admin;

import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import zw.org.zvandiri.business.domain.ArvMedicine;
import zw.org.zvandiri.business.service.ArvMedicineService;
import zw.org.zvandiri.business.util.dto.ItemDeleteDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import static zw.org.zvandiri.portal.web.controller.IAppTitle.APP_PREFIX;
import zw.org.zvandiri.portal.web.validator.ArvMedicineValidator;

/**
 *
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/admin/arv-medicine")
public class ArvMedicineController extends BaseController {

    @Resource
    private ArvMedicineService arvMedicineService;
    @Resource
    private ArvMedicineValidator arvMedicineValidator;

    public String setUpModel(ModelMap model, ArvMedicine item) {
        model.addAttribute("pageTitle", APP_PREFIX + "Create/ Edit Arv Medicine");
        model.addAttribute("item", item);
        model.addAttribute("itemDelete", "item.list?type=3");
        return "admin/medicineForm";
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String getForm(ModelMap model, @RequestParam(required = false) String id) {
        ArvMedicine item = new ArvMedicine();
        if (id != null) {
            item = arvMedicineService.get(id);
        }
        return setUpModel(model, item);
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.POST)
    public String saveItem(@ModelAttribute("item") @Valid ArvMedicine item, BindingResult result, ModelMap model) {
        arvMedicineValidator.validate(item, result);
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        if (result.hasErrors()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return setUpModel(model, item);
        }
        arvMedicineService.save(item);
        return "redirect:item.list?type=1";
    }

    @RequestMapping(value = {"/item.list", "/"}, method = RequestMethod.GET)
    public String itemList(ModelMap model, @RequestParam(required = false) Integer type) {
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        model.addAttribute("pageTitle", APP_PREFIX + "Arv Medicine List");
        model.addAttribute("items", arvMedicineService.getAll());
        if (type != null) {
            model.addAttribute("message", AppMessage.getMessage(type));
        }
        return "admin/medicineList";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.GET)
    public String getDeleteForm(@RequestParam("id") String id, ModelMap model) {
        ArvMedicine item = arvMedicineService.get(id);
        ItemDeleteDTO dto = new ItemDeleteDTO(id, item.getName() + " Arv Medicine", "item.list?type=3");
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", APP_PREFIX + "Delete " + item.getName() + " ArvMedicine");
        return "admin/deleteItem";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String deleteItem(@Valid ItemDeleteDTO dto) {
        arvMedicineService.delete(arvMedicineService.get(dto.getId()));
        return "redirect:item.list?type=2";
    }
}
