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
package zw.org.zvandiri.mobile.api.resource;


import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.domain.util.CareLevel;
import zw.org.zvandiri.business.domain.util.Gender;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.repo.PatientRepo;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.MobilePatientDTO;
import zw.org.zvandiri.business.util.dto.MobileStaticsDTO;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * @author manatsachinyeruse@gmail.com
 */
@Component
@Path("/mobile/patient/statics/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MobilePatientResource {

    @Resource
    private CatDetailService catDetailService;
    @Resource
    private ContactService contactService;
    @Resource
    private PatientService patientService;
    @Resource
    PatientRepo patientRepo;
    @Resource
    private ReferralService referralService;
    @Resource
    private UserService userService;

    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private SupportGroupService supportGroupService;
    @Resource
    private FacilityService facilityService;
    @Resource
    private RelationshipService relationshipService;
    @Resource
    private RefererService refererService;
    @Resource
    private OrphanStatusService orphanStatusService;
    @Resource
    private EducationService educationService;
    @Resource
    private EducationLevelService educationLevelService;
    @Resource
    private LocationService locationService;
    @Resource
    private PositionService positionService;
    @Resource
    private InternalReferralService internalReferralService;
    @Resource
    private ExternalReferralService externalReferralService;
    @Resource
    private ChronicInfectionService chronicInfectionService;
    @Resource
    private StableService stableService;
    @Resource
    private EnhancedService enhancedService;
    @Resource
    private ServicesReferredService servicesReferredService;
    @Resource
    private HivCoInfectionService hivCoInfectionService;
    @Resource
    private MentalHealthService mentalHealthService;
    @Resource
    private DisabilityCategoryService disabilityCategoryService;
    @Resource
    private AssessmentService assessmentService;
    @Resource
    private ArvMedicineService arvMedicineService;
    @Resource
    private HospCauseService hospService;
    @Resource
    private SubstanceService substanceService;
    @Resource
    private ActionTakenService actionTakenService;
    @Resource
    private ReasonForNotReachingOLevelService reasonForNotReachingOLevelService;
    @Resource
    private ServiceOfferedService serviceOfferedService;
    @Resource
    private LabTaskService labTaskService;


    @GET
    @Path("/cats-patients-statics")
    public MobileStaticsDTO getCatPatients(@QueryParam("email") String email) {
        MobileStaticsDTO mobileStaticsDTO = new MobileStaticsDTO();

        CatDetail catDetail = catDetailService.getByEmail(email);
        List<MobilePatientDTO> patients = patientService.getFacilityPatients(catDetail);
        List<District> districts = districtService.getAll();
        List<Province> provinces = provinceService.getAll();
        List<ServiceOffered> servicesOffered = serviceOfferedService.getAll();
        System.err.println("Services Offerred: " + servicesOffered);
        List<ServicesReferred> servicesReferred = servicesReferredService.getAll();
        List<Assessment> assessments = assessmentService.getAll();


        mobileStaticsDTO.setPatients(patients);
        mobileStaticsDTO.setDistricts(districts);
        mobileStaticsDTO.setProvinces(provinces);
        mobileStaticsDTO.setServiceOffereds(servicesOffered);
        mobileStaticsDTO.setServicesReferred(servicesReferred);
        mobileStaticsDTO.setAssessments(assessments);

        System.err.println("<= User :" + email + " => Statics");
//        System.err.println(mobileStaticsDTO.toString());

        return mobileStaticsDTO;
    }

    @GET
    @Path("/get-cats")
    public CatDetail getCats(@QueryParam("email") String email) {
        return catDetailService.getByEmail(email);
    }

    @POST
    @Path("/add-contacts")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public ResponseEntity<Map<String, Object>> addContact(Contact contact) {
        System.err.println("Sent Contact : " + contact);
        Map<String, Object> response = validateContact(contact);
        if (!response.isEmpty()) {
            System.err.println(response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            /*if(contact.getReferredPersonId() != null) {
                contact.setReferredPerson(userService.get(contact.getReferredPersonId()));
            }*/
            contactService.save(contact);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "System error occurred saving contact");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        for (String key : response.keySet()) {
            System.err.println(response.get(key));
        }
        response.put("message", "Contact created sucessfully");
        System.err.println("Response => " + response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @POST
    @Path("/add-patient")
    public ResponseEntity<Map<String, Object>> addPatient(Patient patient) {
        Map<String, Object> response = validatePatient(patient);
        if (!response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            patientService.save(patient);
        } catch (Exception e) {
            response.put("message", "System error occurred saving patient");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Patient created sucessfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @POST
    @Path("/add-disability")
    public ResponseEntity<Map<String, Object>> addPatientDisabilities(Patient patient) {
        Map<String, Object> response = new HashMap<>();
        if (!response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            Set<PatientDisability> disabilitys = patient.getDisabilityCategorys();
            Patient item = patientService.get(patient.getId());
            for (PatientDisability disability : disabilitys) {
                disability.setPatient(item);
            }
            item.setDisabilityCategorys(disabilitys);
            patientService.save(item);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "System error occurred saving patient");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Patient created sucessfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @POST
    @Path("/change-facility")
    public ResponseEntity<Map<String, Object>> changeFacility(Patient patient) {
        Map<String, Object> response = new HashMap<>();
        if (!response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            Patient item = patientService.get(patient.getId());
            item.setPrimaryClinic(patient.getPrimaryClinic());
            patientService.save(item);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "System error occurred saving patient");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Patient created sucessfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @POST
    @Path("/add-referral")
    public ResponseEntity<Map<String, Object>> addReferral(Referral referral) {
        Map<String, Object> response = validateReferral(referral);
        if (!response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            referralService.save(referral);
        } catch (Exception e) {
            response.put("message", "System error occurred saving referral");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Referral created sucessfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Map<String, Object> validateReferral(Referral item) {
        Map<String, Object> response = new HashMap<>();
        if (item.getReferralDate() == null) {
            response.put("referralDate", "Field is required");
        }
        if (item.getOrganisation() == null) {
            response.put("organisation", "Field is required");
        }
        if (item.getReferralDate() != null && item.getReferralDate().after(new Date())) {
            response.put("referralDate", "Date cannot be in the future");
        }
        if (item.getReferralDate() != null && item.getPatient().getDateOfBirth() != null && item.getReferralDate().before(item.getPatient().getDateOfBirth())) {
            response.put("referralDate", "Date cannot be before individual was born");
        }
        if (item.getDateAttended() != null && item.getDateAttended().after(new Date())) {
            response.put("dateAttended", "Date cannot be in the future");
        }
        if (item.getDateAttended() != null && item.getPatient().getDateOfBirth() != null && item.getDateAttended().before(item.getPatient().getDateOfBirth())) {
            response.put("dateAttended", "Date cannot be before individual was born");
        }
        if ((item.getReferralDate() != null && item.getDateAttended() != null) && item.getDateAttended().before(item.getReferralDate())) {
            response.put("dateAttended", "Date attended cannot be before referral date");
        }
        // check that @least one section is checked
        boolean serviceReq = false;
        if ((item.getHivStiServicesReq() != null && !item.getHivStiServicesReq().isEmpty())) {
            serviceReq = true;
        }
        if (!serviceReq && (item.getLaboratoryReq() != null && !item.getLaboratoryReq().isEmpty())) {
            serviceReq = true;
        }
        if (!serviceReq && (item.getOiArtReq() != null && !item.getOiArtReq().isEmpty())) {
            serviceReq = true;
        }
        if (!serviceReq && (item.getLegalReq() != null && !item.getLegalReq().isEmpty())) {
            serviceReq = true;
        }
        if (!serviceReq && (item.getPsychReq() != null && !item.getPsychReq().isEmpty())) {
            serviceReq = true;
        }
        if (!serviceReq && (item.getSrhReq() != null && !item.getSrhReq().isEmpty())) {
            serviceReq = true;
        }
        if (!serviceReq && (item.getTbReq() != null && !item.getTbReq().isEmpty())) {
            serviceReq = true;
        }
        if (!serviceReq) {
            response.put("servicesRequestedError", "item.select.one");
        }
        if (item.getDateAttended() != null) {
            if (item.getAttendingOfficer() == null) {
                response.put("attendingOfficer", "Field is required");
            }
            if (item.getDesignation() == null) {
                response.put("designation", "Field is required");
            }
            if (item.getActionTaken() == null) {
                response.put("actionTaken", "Field is required");
            }
        }
        return response;
    }

    private Map<String, Object> validatePatient(Patient item) {
        Map<String, Object> response = new HashMap<>();
        String ZIMBABWE = "\\d{10}";
        if (StringUtils.isEmpty(item.getFirstName())) {
            response.put("firstName", "Field is required");
        }
        if (StringUtils.isEmpty(item.getLastName())) {
            response.put("lastName", "Field is required");
        }
        if (item.getDateOfBirth() == null) {
            response.put("dateOfBirth", "Field is required");
        }
        if (item.getGender() == null) {
            response.put("gender", "Field is required");
        }
        if (item.getDateOfBirth() != null) {
            if (item.getDateOfBirth().after(new Date())) {
                response.put("dateOfBirth", "Date cannot be in the future");
            }
            if (item.getDateOfBirth().before(DateUtil.getDateFromAge(30))) {
                response.put("dateOfBirth", "Date too early");
            }
        }
        if (StringUtils.isNotEmpty(item.getMobileNumber()) && !item.getMobileNumber().matches(ZIMBABWE)) {
            response.put("mobileNumber", "Mobile number not valid");
        }
        if (StringUtils.isNotEmpty(item.getMobileNumber()) && item.getMobileOwner() == null) {
            response.put("mobileOwner", "Field is required");
        }
        if (item.getMobileOwner() != null && item.getMobileOwner().equals(YesNo.NO) && item.getOwnerName() == null) {
            response.put("ownerName", "Field is required");
        }
        if (item.getMobileOwner() != null && item.getMobileOwner().equals(YesNo.NO) && item.getMobileOwnerRelation() == null) {
            response.put("mobileOwnerRelation", "Field is required");
        }
        if ((item.getOwnSecondaryMobile() != null && item.getOwnSecondaryMobile().equals(YesNo.YES)) && StringUtils.isEmpty(item.getSecondaryMobileNumber())) {
            response.put("secondaryMobileNumber", "Field is required");
        }
        if (StringUtils.isNotEmpty(item.getSecondaryMobileNumber()) && item.getOwnSecondaryMobile() == null) {
            response.put("ownSecondaryMobile", "Field is required");
        }
        if (item.getOwnSecondaryMobile() != null && item.getOwnSecondaryMobile().equals(YesNo.NO) && StringUtils.isEmpty(item.getSecondaryMobileOwnerName())) {
            response.put("secondaryMobileOwnerName", "Field is required");
        }
        if ((item.getOwnSecondaryMobile() != null && item.getOwnSecondaryMobile().equals(YesNo.NO)) && item.getSecondaryMobileownerRelation() == null) {
            response.put("secondaryMobileownerRelation", "Field is required");
        }
        if ((item.getOwnSecondaryMobile() != null && item.getOwnSecondaryMobile().equals(YesNo.NO)) && StringUtils.isEmpty(item.getSecondaryMobileOwnerName())) {
            response.put("secondaryMobileOwnerName", "Field is required");
        }
        if (StringUtils.isEmpty(item.getAddress())) {
            response.put("address", "Field is required");
        }
        if (item.getPrimaryClinic() == null) {
            response.put("primaryClinic", "Field is required");
        }
        if (item.getSupportGroup() == null) {
            response.put("supportGroup", "Field is required");
        }
        if (StringUtils.isNotEmpty(item.getFirstName()) && StringUtils.isNotEmpty(item.getLastName()) && item.getDateOfBirth() != null && item.getPrimaryClinic() != null) {
            if (patientService.checkDuplicate(item, null)) {
                response.put("patientExist", "Patient already exists");
            }
        }
        if (item.getDateJoined() == null) {
            response.put("dateJoined", "Field is required");
        }
        if (StringUtils.isEmpty(item.getRefererName())) {
            response.put("refererName", "Field is required");
        }
        if (item.getEducation() == null) {
            response.put("education", "Field is required");
        }
        if (item.getEducationLevel() == null) {
            response.put("educationLevel", "Field is required");
        }
        /*if (item.getEducation() != null && item.getEducation().getName().equalsIgnoreCase("Out of School")) {
            if ((item.getEducationLevel() != null
                    && (item.getEducationLevel().getName().equalsIgnoreCase("N/A")
                    || item.getEducationLevel().getName().equalsIgnoreCase("Primary School")))
                    && item.getReasonForNotReachingOLevel() == null) {
                response.put("reasonForNotReachingOLevel", "Field is required");
            }
        }*/
        if (item.getReferer() == null) {
            response.put("referer", "Field is required");
        }
        if (item.getDateJoined() != null && item.getDateJoined().after(new Date())) {
            response.put("dateJoined", "Date cannot be in the future");
        }
        if (item.getDateJoined() != null && item.getDateOfBirth() != null && item.getDateJoined().before(item.getDateOfBirth())) {
            response.put("dateJoined", "Date cannot be before individual was born");
        }
        if (item.getHivStatusKnown() != null && item.getHivStatusKnown().equals(YesNo.YES) && item.getTransmissionMode() == null) {
            response.put("transmissionMode", "Field is required");
        }
        if (item.getDateTested() != null && item.getDateTested().after(new Date())) {
            response.put("dateTested", "Date cannot be in the future");
        }
        if (item.getDateTested() != null && item.getDateOfBirth() != null && item.getDateTested().before(item.getDateOfBirth())) {
            response.put("dateTested", "Date cannot be before individual was born");
        }
        if (item.getHivStatusKnown() != null && item.getHivStatusKnown().equals(YesNo.YES)) {
            if (item.gethIVDisclosureLocation() == null) {
                response.put("hIVDisclosureLocation", "Field is required");
            }
        }
        if (item.getDisability() != null && item.getDisability().equals(YesNo.YES) && item.getDisabilityCategorys() == null) {
            response.put("disabilityCategorys", "Select at least one item in this list");
        }
        if (item.getYoungMumGroup() != null && item.getYoungMumGroup().equals(YesNo.YES) && (item.getGender() != null && item.getGender().equals(Gender.MALE))) {
            response.put("youngMumGroup", "Only female beneficiaries can be in this group");
        }
        if ((item.getGender() != null && item.getGender().equals(Gender.FEMALE)) && item.getYoungMumGroup() != null && item.getYoungMumGroup().equals(YesNo.YES) && (item.getDateOfBirth() != null && item.getAge() <= 10)) {
            response.put("youngMumGroup", "Too young to be a mom");
        }
        if (item.getCat() != null && item.getCat().equals(YesNo.YES) && (item.getDateOfBirth() != null && item.getAge() <= 10)) {
            response.put("youngMumGroup", "Too young to be a CATS");
        }
        return response;
    }

    private Map<String, Object> validateContact(Contact item) {
        Map<String, Object> response = new HashMap<>();
        if (item.getContactDate() == null) {
            response.put("contactDate", "Field is required");
        }
        if (item.getCareLevel() == null) {
            response.put("careLevel", "Field is required");
        }
        if (item.getLocation() == null) {
            response.put("location", "Field is required");
        }
        if (item.getPosition() == null) {
            response.put("position", "Field is required");
        }
        if (item.getCareLevelAfterAssessment() != null) {
            if (item.getCareLevelAfterAssessment().equals(CareLevel.STANDARD) && item.getStables() == null) {
                response.put("stables", "Select at least one item in this list");
            }
            if (item.getCareLevelAfterAssessment().equals(CareLevel.ENHANCED) && item.getEnhanceds() == null) {
                response.put("enhanceds", "Select at least one item in this list");
            }
        }
        if (item.getContactDate() != null && item.getContactDate().after(new Date())) {
            response.put("contactDate", "Date cannot be in the future");
        }
        if (item.getContactDate() != null && item.getPatient().getDateOfBirth() != null && item.getContactDate().before(item.getPatient().getDateOfBirth())) {
            response.put("contactDate", "Date cannot be before individual was born");
        }
        if (item.getLastClinicAppointmentDate() != null && item.getLastClinicAppointmentDate().after(new Date())) {
            response.put("lastClinicAppointmentDate", "Date cannot be in the future");
        }
        if (item.getLastClinicAppointmentDate() != null && item.getPatient().getDateOfBirth() != null && item.getLastClinicAppointmentDate().before(item.getPatient().getDateOfBirth())) {
            response.put("lastClinicAppointmentDate", "Date cannot be before individual was born");
        }
        if (item.getLastClinicAppointmentDate() != null && item.getAttendedClinicAppointment() == null) {
            response.put("attendedClinicAppointment", "Field is required");
        }
        return response;
    }


}
