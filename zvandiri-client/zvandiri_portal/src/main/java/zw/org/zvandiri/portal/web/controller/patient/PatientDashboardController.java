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
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import zw.org.zvandiri.business.domain.Bicycle;
import zw.org.zvandiri.business.domain.MobilePhone;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.Gender;
import zw.org.zvandiri.business.domain.util.TestType;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;

/**
 *
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/patient/dashboard")
public class  PatientDashboardController extends BaseController {

    @Resource
    private PatientService patientService;
    @Resource
    private DependentService dependentService;
    @Resource
    private ContactService contactService;
    @Resource
    private CatDetailService catDetailService;
    @Resource
    private InvestigationTestService investigationTestService;
    @Resource
    private EidTestService eidTestService;
    @Resource
    MobilePhoneService mobilePhoneService;
    @Resource
    BicycleService bicycleService;


    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getProfile(@RequestParam String id, @RequestParam(required = false) Integer type, ModelMap model) {
        Patient item = patientService.get(id);
//        MobilePhone phone=mobilePhoneService.getByCadre(item);
//        Bicycle bike=bicycleService.getByCadre(item);

        model.addAttribute("pageTitle", APP_PREFIX + " " + item.getName() + "'s Dashboard");
        model.addAttribute("patient", item);
        /*model.addAttribute("phone", phone);
        model.addAttribute("bike", bike);*/
        model.addAttribute("caregiver", Boolean.FALSE);
        model.addAttribute("cat", Boolean.FALSE);
        model.addAttribute("female", Boolean.FALSE);
        model.addAttribute("canEdit", Boolean.TRUE);
        model.addAttribute("canReInstate", Boolean.FALSE);
        model.addAttribute("heu", Boolean.FALSE);
        if(item.getGender() != null && item.getGender().equals(Gender.FEMALE)){
            model.addAttribute("female", Boolean.TRUE);
        }
        if (type != null) {
            model.addAttribute("message", AppMessage.getMessage(type));
        }
        if (item.getPfirstName() != null || (item.getPfirstName() != null && !item.getPfirstName().equals(""))) {
            model.addAttribute("caregiver", Boolean.TRUE);
        }
        if (item.getCat() != null && item.getCat().equals(YesNo.YES)) {

            if (!patientService.hasCatDetailRecord(item)) {
                model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message(getPatient(item)).messageType(MessageType.WARNING).build());
            }else{
                //System.err.println("IS CATS? "+item.getCat());
                model.addAttribute("cat", Boolean.TRUE);
                model.addAttribute("catDetail", catDetailService.getByPatient(item));
            }
        }
        if(item.getHeuReg()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message(getHeu(item)).messageType(MessageType.WARNING).build());
        }
        if(item.getContacts().isEmpty()) {
            model.addAttribute("contactsMessage", new AppMessage.MessageBuilder(Boolean.TRUE).message(getContacts(item)).messageType(MessageType.WARNING).build());
        }
        if(item.getMentalHealthScreenings().isEmpty()) {
            model.addAttribute("mentalHealthMessage", new AppMessage.MessageBuilder(Boolean.TRUE).message(getMentalHealth(item)).messageType(MessageType.WARNING).build());
        }
        if(item.getTbIpts().isEmpty()) {
            model.addAttribute("tbScreeningMessage", new AppMessage.MessageBuilder(Boolean.TRUE).message(getTbScreening(item)).messageType(MessageType.WARNING).build());
        }
        if (!item.getPatientStatus()) {
            getPatientStatus(item, model);
        }
        if(item.getHei() != null && item.getHei().equals(YesNo.YES)) {
            model.addAttribute("heu", Boolean.TRUE);
        }

        /*if(phone!=null){
                model.addAttribute("hasPhone", true);
        }
        if(bike!=null){
            model.addAttribute("hasBike", true);
        }*/
        model.addAttribute("dependants", dependentService.getByPatient(item));
        model.addAttribute("contacts", contactService.getByPatient(item));
        model.addAttribute("cd4counts", investigationTestService.getByPatientAndTestType(item, TestType.CD4_COUNT));
        model.addAttribute("viralLoads", investigationTestService.getByPatientAndTestType(item, TestType.VIRAL_LOAD));
        model.addAttribute("eids", eidTestService.getByPatient(item));

        setViralLoad(model, item);
        return "patient/dashboard";
    }

    private String getPatient(Patient patient) {
        StringBuilder warning = new StringBuilder();
        warning.append("Please complete the following before proceeding<br/><ul>");
        warning.append("<li><a class='alert-link' href='../cat/detail/item.form?patientId=");
        warning.append(patient.getId());
        warning.append("'>Add CATS Details</a></li>");
        warning.append("</ul>");
        return warning.toString();
    }
    
    private String getContacts(Patient patient) {
        StringBuilder warning = new StringBuilder();
        warning.append("Please complete the following before proceeding<br/><ul>");
        warning.append("<li><a class='alert-link' href='../../beneficiary/contact/item.form?patientId=");
        warning.append(patient.getId());
        warning.append("'>Add Contacts</a></li>");
        warning.append("</ul>");
        return warning.toString();
    }
    
    private String getMentalHealth(Patient patient) {
        StringBuilder warning = new StringBuilder();
        warning.append("Please complete the following before proceeding<br/><ul>");
        warning.append("<li><a class='alert-link' href='../../beneficiary/mental-health-screening/item.form?patientId=");
        warning.append(patient.getId());
        warning.append("'>Add Mental Health Screening</a></li>");
        warning.append("</ul>");
        return warning.toString();
    }
    
    private String getTbScreening(Patient patient) {
        StringBuilder warning = new StringBuilder();
        warning.append("Please complete the following before proceeding<br/><ul>");
        warning.append("<li><a class='alert-link' href='../tb-screening/item.form?patientId=");
        warning.append(patient.getId());
        warning.append("'>Add TB Screening</a></li>");
        warning.append("</ul>");
        return warning.toString();
    }
    
    private String getHeu(Patient patient) {
        StringBuilder warning = new StringBuilder();
        warning.append("Please complete the following before proceeding<br/><ul>");
        warning.append("<li><a class='alert-link' href='../heu/item.form?patientId=");
        warning.append(patient.getId());
        warning.append("'>Add HEU Mother Details</a></li>");
        warning.append("</ul>");
        return warning.toString();
    }
}
