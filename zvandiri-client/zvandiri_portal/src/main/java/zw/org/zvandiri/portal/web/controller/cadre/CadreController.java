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
package zw.org.zvandiri.portal.web.controller.cadre;

import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import zw.org.zvandiri.business.domain.Cadre;
import zw.org.zvandiri.business.domain.MobilePhone;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.TbIpt;
import zw.org.zvandiri.business.domain.util.*;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.business.util.dto.CadreSearchDTO;
import zw.org.zvandiri.business.util.dto.ItemDeleteDTO;
import zw.org.zvandiri.business.util.dto.PatientSearchDTO;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import static zw.org.zvandiri.portal.web.controller.IAppTitle.APP_PREFIX;
import zw.org.zvandiri.portal.web.validator.TbScreeningValidator;

import java.util.List;

/**
 *
 * @author manatsachinyeruse@gmail.com
 */


@Controller
@RequestMapping("/cadre")
public class CadreController extends BaseController {

    @Resource
    private CadreService cadreService;
    @Resource
    private PatientService patientService;
    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private FacilityService facilityService;
    @Resource
    MobilePhoneService mobilePhoneService;
    @Resource
    SupportGroupService supportGroupService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String getIndex(ModelMap map, @RequestParam(required = false) String id) {
        map.addAttribute("pageTitle", APP_PREFIX + " " + "Cadre Management");
        map.addAttribute("cadres",cadreService.getAll());
        return "cadre/index";
    }

    public String setUpModel(ModelMap map, Cadre item) {
        map.addAttribute("pageTitle", APP_PREFIX + " " + "Cadre Management");
        map.addAttribute("provinces", provinceService.getAll());
        map.addAttribute("districts", districtService.getAll());
        map.addAttribute("facilities", facilityService.getAll());
        map.addAttribute("supportGroups", supportGroupService.getAll());
        map.addAttribute("item", item);
        map.addAttribute("formAction", "item.form");
        map.addAttribute("cadres",cadreService.getAll());
        return "cadre/cadreForm";
    }


    @RequestMapping(value = "item.form", method = RequestMethod.GET)
    public String getForm(ModelMap map, @RequestParam(required = false) String id, @RequestParam(required = false) String patientId) {
        Cadre item;
        if (id != null) {
            item = cadreService.get(id);
            return setUpModel(map, item);
        }
        if(patientId!=null) {
            item = new Cadre(patientId);
        }else{
            item=new Cadre();
        }
        return setUpModel(map, item);
    }


    @RequestMapping(value = "item.form", method = RequestMethod.POST)
    public String saveItem(ModelMap map, @ModelAttribute("item") @Valid Cadre item, BindingResult result) {
        map.addAttribute("message", new AppMessage.MessageBuilder().build());
        if (result.hasErrors()) {
            map.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return setUpModel(map, item);
        }
        item=cadreService.save(item);
        return "redirect:index?type=1&id=" + item.getId();
        //return "cadre/index";
    }

    /*@RequestMapping(value = "item.delete", method = RequestMethod.GET)
    public String getDeleteForm(@RequestParam("id") String id, ModelMap model) {
        Patient patient = cadreService.get(id);
        ItemDeleteDTO dto = new ItemDeleteDTO(id, patient.getName(), "item.list");
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", APP_PREFIX + "Delete " + patient.getName());
        return "admin/deleteItem";
    }*/

    /*@RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String delete(@Valid ItemDeleteDTO dto) {
        TbIpt item = service.get(dto.getId());
        Patient patient = item.getPatient();
        service.delete(item);
        return "redirect:item.list?type=2&id=" + patient.getId();
    }*/

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String viewCadre(ModelMap model, @RequestParam("id") String id){
        Cadre cadre=cadreService.get(id);
        model.addAttribute("cadre", cadre);
        return "cadre/dashboard";
    }
    
    @RequestMapping(value = "reload-form", method = RequestMethod.POST)
    public String reloadForm(ModelMap model, @ModelAttribute("item") Cadre item) {
        return setUpModel(model, item);
    }


    @RequestMapping(value = "/search-cadres", method = RequestMethod.GET)
    @ResponseBody
    public List<CadreSearchDTO> searchPatient(@RequestParam("search") String search) {
        String[] names = search.split(" ");
        SearchDTO item = getUserLevelObjectState(new SearchDTO());
        return CadreSearchDTO.getInstance(cadreService.search(item, names));
    }
}
