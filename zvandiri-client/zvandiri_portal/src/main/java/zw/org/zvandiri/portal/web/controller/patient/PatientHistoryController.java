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

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.service.ArvHistService;
import zw.org.zvandiri.business.service.ChronicInfectionItemService;
import zw.org.zvandiri.business.service.FamilyService;
import zw.org.zvandiri.business.service.HivConInfectionItemService;
import zw.org.zvandiri.business.service.MedicalHistService;
import zw.org.zvandiri.business.service.MentalHealthItemService;
import zw.org.zvandiri.business.service.ObstercHistService;
import zw.org.zvandiri.business.service.PatientService;
import zw.org.zvandiri.business.service.SocialHistService;
import zw.org.zvandiri.business.service.SrhHistService;
import zw.org.zvandiri.business.service.SubstanceItemService;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.web.controller.BaseController;

/**
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/beneficiary/history")
public class PatientHistoryController extends BaseController {

    @Resource
    private PatientService patientService;
    @Resource
    private MedicalHistService medicalHistService;
    @Resource
    private ChronicInfectionItemService chronicInfectionItemService;
    @Resource
    private HivConInfectionItemService hivConInfectionItemService;
    @Resource
    private ArvHistService arvHistService;
    @Resource
    private MentalHealthItemService mentalHealthItemService;
    @Resource
    private SrhHistService srhHistService;
    @Resource
    private ObstercHistService obstercHistService;
    @Resource
    private SocialHistService socialHistService;
    @Resource
    private SubstanceItemService substanceItemService;
    @Resource
    private FamilyService familyService;

    @RequestMapping(value = "/item.htm", method = RequestMethod.GET)
    public String getProfile(@RequestParam String id, @RequestParam(required = false) Integer type, ModelMap model) {
        Patient item = patientService.get(id);
        model.addAttribute("pageTitle", APP_PREFIX + " " + item.getName() + "'s Patient History");
        model.addAttribute("patient", item);
        if (type != null) {
            model.addAttribute("message", AppMessage.getMessage(type));
        }
        model.addAttribute("medHists", medicalHistService.getByPatient(item));
        model.addAttribute("obstericHist", obstercHistService.getByPatient(item));
        model.addAttribute("family", familyService.getByPatient(item));
        model.addAttribute("srhHist", srhHistService.getByPatient(item));
        model.addAttribute("socialHist", socialHistService.getByPatient(item));
        model.addAttribute("substances", substanceItemService.getByPatient(item));
        model.addAttribute("arvHists", arvHistService.getByPatient(item));
        model.addAttribute("infections", chronicInfectionItemService.getByPatient(item));
        model.addAttribute("hivInfections", hivConInfectionItemService.getByPatient(item));
        model.addAttribute("mentalHealths", mentalHealthItemService.getByPatient(item));
        setViralLoad(model, item);
        return "patient/patientHistory";
    }
}