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
package zw.org.zvandiri.portal.web.controller.cadre;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import zw.org.zvandiri.business.domain.Bicycle;
import zw.org.zvandiri.business.domain.Cadre;
import zw.org.zvandiri.business.domain.util.Condition;
import zw.org.zvandiri.business.domain.util.PhoneStatus;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 *
 * @author mana
 */
@Controller
@RequestMapping("/cadre/bike")
public class CadreBikeController extends BaseController {

    @Resource
    private BicycleService bicycleService;
    @Resource
    private CadreService cadreService;
    @Resource
    private FacilityService facilityService;
    @Resource
    private DistrictService districtService;
    @Resource
    private ProvinceService provinceService;

    Cadre cadre=new Cadre();


    public String setUpModel(ModelMap model, Bicycle item, Integer type) {
        model.addAttribute("pageTitle", APP_PREFIX + "Create/ Edit Cadre");
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("formAction", "item.form");
        model.addAttribute("bikeCondition", Condition.values());
        model.addAttribute("bikeStatus", PhoneStatus.values());
        model.addAttribute("cadre", this.cadre);
        model.addAttribute("item", item);
        if(type!=null && type>0) {
            model.addAttribute("message", AppMessage.getMessage(type));
        }

            if(cadre!=null){
                if (cadre.getPrimaryClinic().getProvince() != null) {
                    model.addAttribute("districts", districtService.getDistrictByProvince(cadre.getProvince()));
                    if (item.getCadre().getDistrict() != null) {
                        model.addAttribute("facilities", facilityService.getOptByDistrict(cadre.getDistrict()));
                    }
                }
            }
        return "cadre/bikeForm";
    }


    @RequestMapping(value = "/create.form", method = RequestMethod.GET)
    public String getCreateForm(ModelMap model, @RequestParam(required = false) String id) {
        Cadre cadre = cadreService.get(id);
        this.cadre=cadre;
        Bicycle item=new Bicycle();
        item.setCadre(cadre);
        return setUpModel(model, item, null );
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String getForm(ModelMap model, @RequestParam(required = false) String id, @RequestParam(required = false) String cadreId) {
        cadre = cadreService.get(cadreId);
        Bicycle item = bicycleService.get(id);

        //System.err.println("CADRE: "+cadre+"\nBike: "+item);

        if(item==null){
            throw new IllegalStateException("Bicycle to be edited cannot be null");
        }
        return setUpModel(model, item ,null);
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.POST)
    public String saveItem(@ModelAttribute("item") @Valid Bicycle item, @ModelAttribute("cadre") @Valid Cadre cadre, @ModelAttribute("page") String page, ModelMap model,  BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return setUpModel(model, item, Integer.valueOf(1));
        }
        item.setCadre(this.cadre);
        bicycleService.save(item);
        return "redirect:"+page+"/cadre/view?type=1&id="+this.cadre.getId();
    }
}