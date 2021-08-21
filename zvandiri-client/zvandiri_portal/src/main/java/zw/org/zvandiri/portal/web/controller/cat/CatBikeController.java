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

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import zw.org.zvandiri.business.domain.Bicycle;
import zw.org.zvandiri.business.domain.CatDetail;
import zw.org.zvandiri.business.domain.MobilePhone;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.Condition;
import zw.org.zvandiri.business.domain.util.PhoneStatus;
import zw.org.zvandiri.business.repo.MobilePhoneRepository;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.validator.CatDetailValidator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author mana
 */
@Controller
@RequestMapping("/patient/cat/bike")
public class CatBikeController extends BaseController {

    @Resource
    private BicycleService bicycleService;
    @Resource
    private PatientService patientService;
    @Resource
    private FacilityService facilityService;
    @Resource
    private DistrictService districtService;
    @Resource
    private ProvinceService provinceService;


    public String setUpModel(ModelMap model, Bicycle item) {
        model.addAttribute("pageTitle", APP_PREFIX + "Create/ Edit CATS :"+ item.getPatient().getName());
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("formAction", "item.form");
        model.addAttribute("bikeCondition", Condition.values());
        model.addAttribute("bikeStatus", PhoneStatus.values());
        if(item!=null){
            if(item.getPatient()!=null){
                if (item.getPatient().getPrimaryClinic().getProvince() != null) {
                    model.addAttribute("districts", districtService.getDistrictByProvince(item.getPatient().getProvince()));
                    if (item.getPatient().getDistrict() != null) {
                        model.addAttribute("facilities", facilityService.getOptByDistrict(item.getPatient().getDistrict()));
                    }
                }
            }

            model.addAttribute("patient", item.getPatient());
            model.addAttribute("item", item);

        }else{
            model.addAttribute("item", new Bicycle());

        }

        return "cat/bikeForm";
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String getForm(ModelMap model, @RequestParam(required = false) String id, @ModelAttribute("bike") Bicycle phone1) {

        Patient patient = patientService.get(id);
        Bicycle bike=bicycleService.getByPatient(patient);

        if(bike!=null){
            return setUpModel(model, bike);
        }else{

            bike=new Bicycle();

            bike.setPatient(patient);
        }

        return setUpModel(model, bike);
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.POST)
    public String saveItem(ModelMap model, @ModelAttribute("item") @Valid Bicycle item, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return setUpModel(model, item);
        }
        bicycleService.save(item);
        return "redirect:../../dashboard/profile?id=" + item.getPatient().getId() + "&type=1";
    }
}