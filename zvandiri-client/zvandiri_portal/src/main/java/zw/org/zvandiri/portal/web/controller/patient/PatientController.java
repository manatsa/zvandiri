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

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import zw.org.zvandiri.business.domain.Cadre;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.CaderType;
import zw.org.zvandiri.business.domain.util.ClientType;
import zw.org.zvandiri.business.domain.util.PatientChangeEvent;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.business.util.dto.ItemDeleteDTO;
import zw.org.zvandiri.business.util.dto.PatientSearchDTO;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.validator.PatientValidator;

/**
 *
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/patient")
public class PatientController extends BaseController {

    @Resource
    private PatientService patientService;
    @Resource
    CadreService cadreService;
    @Resource
    UserService userService;
    @Resource
    private PatientValidator patientValidator;
    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private SupportGroupService supportGroupService;
    @Resource
    private EducationService educationService;
    @Resource
    private EducationLevelService educationLevelService;
    @Resource
    private FacilityService facilityService;
    @Resource
    private RefererService refererService;
    @Resource
    private DisabilityCategoryService disabilityCategoryService;
    @Resource
    private RelationshipService relationshipService;
    @Resource
    private ReasonForNotReachingOLevelService reasonForNotReachingOLevelService;

    public String setUpModel(ModelMap model, Patient item) {
        model.addAttribute("pageTitle", APP_PREFIX + "Create/ Edit Client");
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        model.addAttribute("provinces", provinceService.getAll());
        model.addAttribute("educations", educationService.getAll());
        model.addAttribute("educationLevels", educationLevelService.getAll());
        model.addAttribute("referers", refererService.getAll());
        model.addAttribute("relationships", relationshipService.getAll());
        model.addAttribute("reasonForNotReachingOLevels", reasonForNotReachingOLevelService.getAll());
        model.addAttribute("mobileOwner", Boolean.TRUE);
        model.addAttribute("formAction", "item.form");
        model.addAttribute("secondaryMobileOwner", Boolean.TRUE);
        model.addAttribute("hasDisability", Boolean.FALSE);
        getPatientStatus(item, model);
        model.addAttribute("disabilityCategories", disabilityCategoryService.getAll());
        if (item.getPrimaryClinic() != null) {
            item.setDistrict(item.getPrimaryClinic().getDistrict());
            item.setProvince(item.getPrimaryClinic().getDistrict().getProvince());
        }
        if (item.getSupportGroup() != null) {
            item.setSupportGroupDistrict(item.getSupportGroup().getDistrict());
        }
        if (item.getMobileOwner() != null && item.getMobileOwner().equals(YesNo.NO)) {
            model.addAttribute("mobileOwner", Boolean.FALSE);
        }
        if (item.getOwnSecondaryMobile() != null && item.getOwnSecondaryMobile().equals(YesNo.NO)) {
            model.addAttribute("secondaryMobileOwner", Boolean.FALSE);
        }
        if (item.getDisability() != null && item.getDisability().equals(YesNo.YES)) {
            model.addAttribute("hasDisability", Boolean.TRUE);
        }
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
            }
            if (item.getSupportGroupDistrict() != null) {
                model.addAttribute("supportGroups", supportGroupService.getByDistrict(item.getSupportGroupDistrict()));
            }
        }
        model.addAttribute("item", item);
        return "patient/itemForm";
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String getForm(ModelMap model, @RequestParam(required = false) String id) {
        Patient item = new Patient();
        if (id != null) {
            item = patientService.get(id);
        }
        return setUpModel(model, item);
    }

    @RequestMapping(value = "item.form", method = RequestMethod.POST)
    public String saveItem(@ModelAttribute("item") @Valid Patient item, ModelMap model, BindingResult result) {
        if (!item.getPatientStatus()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message(INACTIVE_MESSAGE).messageType(MessageType.ERROR).build());
            return setUpModel(model, item);
        }
        patientValidator.validateAll(item, result);
        if (result.hasErrors()) {
            setUpModel(model, item);
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message(sortErrors(result.getAllErrors())).messageType(MessageType.ERROR).build());
            return "patient/itemForm";
        }
        Patient p = patientService.save(item);
        return "redirect:dashboard/profile.htm?type=1&id=" + p.getId();
    }

    @RequestMapping(value = "reload-form", method = RequestMethod.POST)
    public String reloadForm(@ModelAttribute("item") Patient item, ModelMap model) {
        setUpModel(model, item);
        return "patient/itemForm";
    }

    @RequestMapping(value = "/item.list", method = RequestMethod.GET)
    public String getItemList(ModelMap model) {
        model.addAttribute("pageTitle", APP_PREFIX + "Clients List");
        model.addAttribute("items", patientService.getAll());
        return "patient/itemList";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getPatientIndex(ModelMap model, @RequestParam(required = false) Integer type) {
        model.addAttribute("pageTitle", APP_PREFIX + "Search Clients");
        if (type != null) {
            model.addAttribute("message", AppMessage.getMessage(type));
        }
        return "patient/index";
    }

    @RequestMapping(value = "/search-children-cats", method = RequestMethod.GET)
    @ResponseBody
    public List<PatientSearchDTO> searchPatient(@RequestParam("search") String search) {
        String[] names = search.split(" ");
        SearchDTO item = getUserLevelObjectState(new SearchDTO());
        return PatientSearchDTO.getInstance(patientService.search(item, names));
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.GET)
    public String getDeleteForm(@RequestParam("id") String id, ModelMap model) {
        Patient item = patientService.get(id);
        ItemDeleteDTO dto = new ItemDeleteDTO(id, item.getName(), "dashboard/profile.htm?type=3&id=" + item.getId());
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", APP_PREFIX + "Delete " + item.getName() + " :" + dto.getName());
        return "admin/deleteItem";

    }

    @RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String deleteItem(@Valid ItemDeleteDTO dto) {
        Patient item = patientService.get(dto.getId());
        //patientService.delete(item);
        return "redirect:index.htm?type=2";
    }

    @RequestMapping("create-cadre")
    public String createCadre(@RequestParam("id") String id){
        Patient patient=patientService.get(id);
        Cadre cadre=new Cadre();
        cadre.setActive(true);
        cadre.setDistrict(patient.getPrimaryClinic().getDistrict());
        cadre.setAddress(patient.getAddress());
        cadre.setProvince(patient.getPrimaryClinic().getDistrict().getProvince());
        CaderType caderType=null;
        if( (patient.getCat()!=null && patient.getCat().equals(YesNo.YES)) || (patient.getClientType()!=null &&patient.getClientType().equals(ClientType.CAYPLHIV))){
            caderType=CaderType.CATS;
        }else if((patient.getClientType()!=null && patient.getClientType().equals(ClientType.YOUNG_MUM)) || (patient.getYoungMumGroup()!=null && patient.getYoungMumGroup().equals(YesNo.YES))){
            caderType=CaderType.YMM;
        }else if(patient.getClientType()!=null && patient.getClientType().equals(ClientType.YOUNG_DAD)){
            caderType=CaderType.YMD;
        }else{
            return "redirect:index.htm?type=7";
        }
        cadre.setCaderType(caderType);
        cadre.setCreatedBy(userService.getCurrentUser());
        cadre.setDateCreated(new Date());
        cadre.setDateOfBirth(patient.getDateOfBirth());
        cadre.setFirstName(patient.getFirstName());
        cadre.setLastName(patient.getLastName());
        cadre.setGender(patient.getGender());
        cadre.setPrimaryClinic(patient.getPrimaryClinic());
        cadre.setHasPatient(YesNo.YES);
        cadre.setMiddleName(patient.getMiddleName());
        cadre.setMobileNumber(patient.getMobileNumber());
        cadre.setStatus(PatientChangeEvent.ACTIVE);
        cadre.setSupportGroup(patient.getSupportGroup());

        if(cadre.getCaderType()!=null){
            if(cadreService.checkDuplicate(cadre)){
                cadreService.save(cadre);
                return "redirect:index.htm?type=1";
            }else{
                return "redirect:index.htm?type=6";
            }
        }else{
            return "redirect:/zvandiri/cadre/index.htm?type=5";
        }


    }

    private String sortErrors(List<ObjectError> errors){
        String result="";
        for(ObjectError error: errors){
            result+=error.toString();
        }
        return  result;
    }

}
