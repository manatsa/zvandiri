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
package zw.org.zvandiri.portal.web.controller.cat;

import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import zw.org.zvandiri.business.domain.CatDetail;
import zw.org.zvandiri.business.service.CatDetailService;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.PatientService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.validator.CatDetailValidator;

/**
 *
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/patient/cat/detail")
public class CatDetailController extends BaseController {

    @Resource
    private CatDetailService catDetailService;
    @Resource
    private PatientService patientService;
    @Resource
    private FacilityService facilityService;
    @Resource
    private DistrictService districtService;
    @Resource
    private ProvinceService provinceService;
    @Resource
    private CatDetailValidator catDetailValidator;

    public String setUpModel(ModelMap model, CatDetail item) {
        model.addAttribute("pageTitle", APP_PREFIX + "Create/ Edit CATS :" + item.getPatient().getName());
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("formAction", "item.form");
        if (item.getPrimaryClinic() != null && item.getDistrict() == null) {
            item.setDistrict(item.getPrimaryClinic().getDistrict());
            item.setProvince(item.getPrimaryClinic().getDistrict().getProvince());
        }
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
            }
        }
        model.addAttribute("patient", item.getPatient());
        model.addAttribute("item", item);
        if(item.getId() != null){
            return "cat/editCatDetail";
        }
        return "cat/itemForm";
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String getForm(ModelMap model, @RequestParam(required = false) String patientId, @RequestParam(required = false) String id) {
        CatDetail item;
        if (id != null) {
            item = catDetailService.get(id);
            return setUpModel(model, item);
        }
        item = new CatDetail(patientService.get(patientId));
        return setUpModel(model, item);
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.POST)
    public String saveItem(ModelMap model, @ModelAttribute("item") @Valid CatDetail item, BindingResult result) {
        catDetailValidator.validate(item, result);
        if (result.hasErrors()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return setUpModel(model, item);
        }
        catDetailService.save(item);
        return "redirect:../../dashboard/profile?id=" + item.getPatient().getId() + "&type=1";
    }
}