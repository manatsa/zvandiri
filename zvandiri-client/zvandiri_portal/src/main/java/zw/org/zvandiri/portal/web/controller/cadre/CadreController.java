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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import zw.org.zvandiri.business.domain.*;
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

import zw.org.zvandiri.portal.web.controller.IAppTitle;
import zw.org.zvandiri.portal.web.validator.TbScreeningValidator;

import java.util.List;

/**
 *
 * @author manatsachinyeruse@gmail.com
 */


@Controller
@RequestMapping("/cadre")
public class CadreController extends BaseController implements IAppTitle {

    @Resource
    private CadreService cadreService;
    @Resource
    private BicycleService bicycleService;
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
    @Resource
    PatientService patientService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String getIndex(ModelMap map, @RequestParam(required = false) String id, @RequestParam(required = false) Integer type) {
        map.addAttribute("pageTitle", APP_PREFIX + " " + "Cadre Management");
        map.addAttribute("cadres",cadreService.getAll());
        if(type!=null && type>0) {
            map.addAttribute("message", AppMessage.getMessage(type));
            map.addAttribute("hasType", Boolean.TRUE);
        }
        return "cadre/index";
    }

    public String setUpModel(ModelMap map, Cadre item, int type) {
        map.addAttribute("pageTitle", APP_PREFIX + " " + "Cadre Management");
        map.addAttribute("provinces", provinceService.getAll());
        map.addAttribute("districts", item!=null && item.getProvince()!=null?districtService.getDistrictByProvince(item.getProvince()):districtService.getAll());
        map.addAttribute("facilities", item!=null && item.getDistrict()!=null?facilityService.getOptByDistrict(item.getDistrict()):facilityService.getAll());
        map.addAttribute("supportGroups", item!=null && item.getDistrict()!=null?supportGroupService.getByDistrict(item.getDistrict()):supportGroupService.getAll());
        map.addAttribute("item", item);
        map.addAttribute("formAction", "item.form");
        map.addAttribute("cadres",cadreService.getAll());
//        map.addAttribute("type",type);
        return "cadre/cadreForm";
    }


    @RequestMapping(value = "item.form", method = RequestMethod.GET)
    public String getForm(ModelMap map, @RequestParam(required = false) String id, @RequestParam(required = false) String patientId, @RequestParam(required = false) Integer type, @RequestParam(required = false) Integer cadreType) {
        //System.err.println("%%%%%%%%%%%%%%%%%%% after redirection-GET &&&&&&&&&&&&&&&&&&&&&&&&&& ");
        Cadre item;
        if (id != null) {
            item = cadreService.get(id);
            return setUpModel(map, item, MessageType.ERROR.ordinal());
        }
        if(patientId!=null) {
            item = new Cadre(patientId);
        }else{
            item=new Cadre();
        }

        if (type != null) {
            map.addAttribute("message", AppMessage.getMessage(type));
        }
        item.setCaderType(CaderType.get(cadreType));

//        System.err.println(">>>>>>>>>>>>>>>>>>>> ITEM"+item.toString());

        return setUpModel(map, item,MessageType.MESSAGE.ordinal());
    }


    @RequestMapping(value = "item.form", method = RequestMethod.POST)
    public String saveItem(ModelMap map, @ModelAttribute("item") @Valid Cadre item, BindingResult result) {
        System.err.println("%%%%%%%%%%%%%%%%%%% after redirection-POST &&&&&&&&&&&&&&&&&&&&&&&&&& ");
        map.addAttribute("message", new AppMessage.MessageBuilder().build());
        if (result.hasErrors()) {
            map.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return setUpModel(map, item, MessageType.ERROR.ordinal());
        }
        cadreService.save(item);
        return "redirect:index?type=1&id=" + item.getId();
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
        Patient patient=new Patient();
        if(cadre.getPatientId()!=null && !cadre.getPatientId().isEmpty()){
            patient=patientService.get(cadre.getPatientId());
            model.addAttribute("cat", patient.getCat()==YesNo.YES);
            model.addAttribute("ymm", patient.getYoungMumGroup()==YesNo.YES);
        }
        Bicycle bicycle=bicycleService.getByCadre(cadre);
        MobilePhone phone=mobilePhoneService.getByCadre(cadre);
        model.addAttribute("cadre", cadre);
        model.addAttribute("bike", bicycle);
        model.addAttribute("phone", phone);
        model.addAttribute("hasPatient", patient.getId()!=null);
        model.addAttribute("patient", patient);
        model.addAttribute("canEdit", Boolean.TRUE);
        model.addAttribute("hasBike", bicycle!=null);
        model.addAttribute("hasPhone", phone!=null);
        return "cadre/dashboard";
    }
    
    @RequestMapping(value = "reload-form", method = RequestMethod.POST)
    public String reloadForm(ModelMap model, @ModelAttribute("item") Cadre item) {
        return setUpModel(model, item,1);
    }


    @RequestMapping(value = "/search-cadres", method = RequestMethod.GET)
    @ResponseBody
    public List<CadreSearchDTO> searchPatient(@RequestParam("search") String search) {
        String[] names = search.split(" ");
        SearchDTO item = getUserLevelObjectState(new SearchDTO());
        return CadreSearchDTO.getInstance(cadreService.search(item, names));
    }

    @RequestMapping(value = "cadre-type")
    public String getCadreType(ModelMap map, @RequestParam(required = false) String id, @RequestParam(required = false) String patientId, @RequestParam(required = false) Integer type){
        Cadre item;
        if(patientId!=null) {
            item = new Cadre(patientId);
        }else{
            item=new Cadre();
        }

        map.addAttribute("item", item);
        map.addAttribute("formAction", "cadre-type");
        map.addAttribute("cadreTypes",cadreService.getAll());
        return "cadre/cadreType";
    }


    @RequestMapping(value = "cadre-type", method = RequestMethod.POST)
    public String saveCadreType(ModelMap map, @ModelAttribute("item") @Valid Cadre item, BindingResult result, @ModelAttribute("page") String page) {
        map.addAttribute("message", new AppMessage.MessageBuilder().build());
        if (result.hasErrors()) {
            map.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return setUpModel(map, item, MessageType.ERROR.ordinal());
        }
        return (item.getCaderType().getCode()==CaderType.CATS.getCode() || item.getCaderType().getCode()==CaderType.YMM.getCode())?"redirect:../patient/index.htm?type=6" :"redirect:item.form?cadreType="+item.getCaderType().getCode();
    }

}
