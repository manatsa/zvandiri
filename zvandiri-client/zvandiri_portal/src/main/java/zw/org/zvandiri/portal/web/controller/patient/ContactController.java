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
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import zw.org.zvandiri.business.domain.Contact;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.CareLevel;
import zw.org.zvandiri.business.domain.util.ContactAssessment;
import zw.org.zvandiri.business.domain.util.Reason;
import zw.org.zvandiri.business.domain.util.UserLevel;
import zw.org.zvandiri.business.service.ActionTakenService;
import zw.org.zvandiri.business.service.AssessmentService;
import zw.org.zvandiri.business.service.ContactService;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.EnhancedService;
import zw.org.zvandiri.business.service.ExternalReferralService;
import zw.org.zvandiri.business.service.InternalReferralService;
import zw.org.zvandiri.business.service.LocationService;
import zw.org.zvandiri.business.service.PatientService;
import zw.org.zvandiri.business.service.PositionService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.service.ServiceOfferedService;
import zw.org.zvandiri.business.service.StableService;
import zw.org.zvandiri.business.service.UserRoleService;
import zw.org.zvandiri.business.service.UserService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.ItemDeleteDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import static zw.org.zvandiri.portal.web.controller.IAppTitle.APP_PREFIX;

import java.util.Arrays;
import java.util.HashSet;
import zw.org.zvandiri.business.service.LabTaskService;

import zw.org.zvandiri.portal.web.validator.BeneficiaryContactValidator;
import zw.org.zvandiri.portal.web.validator.ContactsValidator;

/**
 *
 * @author Judge Muzinda
 */

@Controller
@RequestMapping("/beneficiary/contact")
public class ContactController extends BaseController {

    @Resource
    private PatientService patientService;
    @Resource
    private LocationService locationService;
    @Resource
    private PositionService positionService;
    @Resource
    private ExternalReferralService externalReferralService;
    @Resource
    private InternalReferralService internalReferralService;
    @Resource
    private StableService stableService;
    @Resource
    private EnhancedService enhancedService;
    @Resource
    private ContactService contactService;
    @Resource
    private BeneficiaryContactValidator beneficiaryContactValidator;
    @Resource
    private AssessmentService assessmentService;
    @Resource
    private ActionTakenService actionTakenService;
    @Resource
    private UserService userService;
    @Resource
    private ServiceOfferedService serviceOfferedService;
    @Resource
    private DistrictService districtService;
    @Resource
    private ProvinceService provinceService;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private LabTaskService labTaskService;
    @Resource
    ContactsValidator contactsValidator;

    private final Logger LOG = Logger.getLogger(ContactController.class);

    @InitBinder
    protected void initBinder(final HttpServletRequest request, final ServletRequestDataBinder binder) {
        binder.addValidators(contactsValidator);
    }

    public String setUpModel(ModelMap model, Contact item, String view) {
        Patient patient = item.getPatient();
        model.addAttribute("pageTitle", APP_PREFIX + " " + patient.getName() + " Contact");
        model.addAttribute("positions", positionService.getAll());
        model.addAttribute("locations", locationService.getAll());
        model.addAttribute("item", item);
        model.addAttribute("formAction", "item.form");
        model.addAttribute("patient", patient);
        model.addAttribute("external", Boolean.FALSE);
        model.addAttribute("internal", Boolean.FALSE);
        model.addAttribute("stable", Boolean.FALSE);
        model.addAttribute("enhanced", Boolean.FALSE);
        model.addAttribute("intensive", Boolean.FALSE);
        model.addAttribute("internalStaff", Boolean.FALSE);
        model.addAttribute("clinicalAssessments", assessmentService.getByAssessmentType(ContactAssessment.CLINICAL));
        model.addAttribute("nonClinicalAssessments", assessmentService.getByAssessmentType(ContactAssessment.NON_CLINICAL));
        model.addAttribute("actionTaken", actionTakenService.getAll());
        model.addAttribute("servicesOffered", serviceOfferedService.getAll());
        model.addAttribute("labTasks", labTaskService.getAll());
        model.addAttribute("showProvince", Boolean.FALSE);
        model.addAttribute("showDistrict", Boolean.FALSE);
        if (item.getUserLevel() != null) {
            if (item.getUserLevel().equals(UserLevel.PROVINCE)) {
                model.addAttribute("provinces", provinceService.getAll());
                model.addAttribute("showProvince", Boolean.TRUE);
            } else if (item.getUserLevel().equals(UserLevel.DISTRICT)) {
                model.addAttribute("provinces", provinceService.getAll());
                /*if (item.getId() != null) {
                    item.setProvince(item.getDistrict().getProvince());
                }*/
                model.addAttribute("showProvince", Boolean.TRUE);
                model.addAttribute("showDistrict", Boolean.TRUE);
                //model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            }
        }
        /*if (item.getReason() != null) {
            if (item.getReason().equals(Reason.EXTERNAL_REFERRAL)) {
                model.addAttribute("external", Boolean.TRUE);
                model.addAttribute("externalReferrals", externalReferralService.getAll());
            } else if (item.getReason().equals(Reason.INTERNAL_REFERRAL)) {
                model.addAttribute("internal", Boolean.TRUE);
                model.addAttribute("internalReferrals", internalReferralService.getAll());
            }
        }
        if (item.getCareLevel() != null) {
            if (item.getCareLevel().equals(CareLevel.STANDARD)) {
                model.addAttribute("stable", Boolean.TRUE);
                model.addAttribute("stables", stableService.getAll());
            } else if (item.getCareLevel().equals(CareLevel.ENHANCED)) {
                model.addAttribute("enhanced", Boolean.TRUE);
                model.addAttribute("enhanceds", enhancedService.getAll());
            }
        }
        if (item.getActionTaken() != null && item.getActionTaken().getName().equalsIgnoreCase("Internal Referral")) {
            model.addAttribute("staff", userRoleService.findUsersInRoles(new HashSet<>(Arrays.asList(new String [] {"ROLE_PSYCHOLOGIST", "ROLE_DOCTOR"}))));
            model.addAttribute("internalStaff", Boolean.TRUE);
        }
        // only do this when contact date is null meaning user is not in edit or view mode
        if (item.getContactDate() == null) {
            Contact latestContact = contactService.findLatestContact(patient);
            if (latestContact != null) {
                item.setLastClinicAppointmentDate(latestContact.getNextClinicAppointmentDate());
                item.setCareLevel(latestContact.getCareLevel());
            }
        }*/

        getPatientStatus(item.getPatient(), model);
        setViralLoad(model, item.getPatient());
        return "patient/" + view;
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String getForm(ModelMap model, @RequestParam(required = false) String patientId, @RequestParam(required = false) String id) {
        Contact item;
        if (id != null) {
            item = contactService.get(id);
            return setUpModel(model, item, "contactForm");
        }
        return setUpModel(model, new Contact(patientService.get(patientId)), "contactForm");
    }

    @RequestMapping(value = "/item.view", method = RequestMethod.GET)
    public String getContactView(ModelMap model, @RequestParam(required = false) String patientId, @RequestParam(required = false) String id) {
        Contact item;
        if (id != null) {
            item = contactService.get(id);
            return setUpModel(model, item, "contactView");
        }
        return setUpModel(model, new Contact(patientService.get(patientId)), "contactView");
    }

    @RequestMapping(value = "reload-form", method = RequestMethod.POST)
    public String reloadForm(ModelMap model, @ModelAttribute("item") Contact item) {
        return setUpModel(model, item, "contactForm");
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.POST)
    public String saveItem(ModelMap model, @ModelAttribute("item") @Valid Contact item, BindingResult result) {
        if (!item.getPatient().getPatientStatus()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message(INACTIVE_MESSAGE).messageType(MessageType.ERROR).build());
            return setUpModel(model, item, "contactForm");
        }
        beneficiaryContactValidator.validate(item, result);
        if (result.hasErrors()) {
            setUpModel(model, item, "contactForm");
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return "patient/contactForm";
        }
        contactService.saveContactDTO(item);
        //if external referral redirect to new referral form
        /*if (item.getActionTaken() != null && item.getActionTaken().getName().equalsIgnoreCase("External Referral")) {
            return "redirect:../../patient/referral/item.form?patientId=" + item.getPatient().getId();
        }*/
        return "redirect:../../patient/dashboard/profile.htm?type=1&id=" + item.getPatient().getId();
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.GET)
    public String getDeleteForm(@RequestParam("id") String id, ModelMap model) {
        Contact item = contactService.get(id);
        String label = item.getPatient().getName() + " : Contact record for : " + DateUtil.getStringFromDate(item.getContactDate());
        ItemDeleteDTO dto = new ItemDeleteDTO(id, label, "../../patient/dashboard/profile.htm?type=3&id=" + item.getId());
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", APP_PREFIX + "Delete " + label + " :" + dto.getName());
        return "admin/deleteItem";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String deleteItem(@Valid ItemDeleteDTO dto) {
        Contact contact = contactService.get(dto.getId());
        Patient item = contact.getPatient();
        contactService.delete(contact);
        return "redirect:../../patient/dashboard/profile.htm?type=2&id=" + item.getId();
    }
}
