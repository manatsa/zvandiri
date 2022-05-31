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
package zw.org.zvandiri.portal.web.controller.process;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.domain.util.FollowUp;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.business.util.UUIDGen;

import javax.annotation.Resource;

/**
 *
 * @author manatsachinyeruse@gmail.com
 */
@Repository("patientContactCreationService")
public class PatientContactCreationServiceImpl implements PatientContactCreationService {
    
    @Resource
    private ContactService contactService;
    @Resource
    MentalHealthScreeningService mentalHealthScreeningService;
    @Resource
    TbIptService tbIptService;
    @Resource
    ReferralService referralService;
    @Resource
    InvestigationTestService investigationTestService;

    @Override
    public PatientContact createPatientContact() {
        return new PatientContact();
    }


    @Transactional
    @Override
    public PatientContact savePatientContact(PatientContact patientContact) {


        MentalHealthScreening mentalHealthScreening=patientContact.getMentalHealthScreening();
        if(mentalHealthScreening.getScreenedForMentalHealth().equals(YesNo.YES)){
            mentalHealthScreening=mentalHealthScreeningService.save(mentalHealthScreening);
            patientContact.setMentalHealthScreening(mentalHealthScreening);
            System.err.println(" >>>> SAVED MENTAL HEALTH SCREENING :"+mentalHealthScreening.toString());
        }else{
            System.err.println(" <<<<<< No MENTAL HEALTH SCREENING item to save!");
        }

        TbIpt tbIpt=patientContact.getTbIpt();
        if(tbIpt.getScreenedForTb().equals(YesNo.YES)){
            tbIpt=tbIptService.save(tbIpt);
            patientContact.setTbIpt(tbIpt);
            System.err.println(" >>>>> SAVED TB/TPT :"+tbIpt.toString());
        }else{
            System.err.println(" <<<<<< No TB/TPT item to save!");
        }



        Referral referral=patientContact.getReferral();
        if(referral.getHasReferred().equals(YesNo.YES)){
            referral=referralService.save(referral);
            System.err.println(" >>>>> SAVED REFERRAL ITEM :"+referral.toString());
            patientContact.setReferral(referral);
        }else{
            System.err.println(" <<<<<< No REFERRAL item to save!");
        }

        Contact contact=patientContact.getContact();
        contact.setSystemDeterminedCareLevel(contact.getCareLevelAfterAssessment());
        if((contact.getNonClinicalAssessments()!=null && !contact.getNonClinicalAssessments().isEmpty()) || (contact.getClinicalAssessments()!=null && !contact.getClinicalAssessments().isEmpty())){
            contact.setSystemDeterminedCareLevel(FollowUp.ENHANCED);
            System.err.println(" >>>>>>>  Client is Enhanced");
        }

        contact=contactService.save(contact);
        System.err.println(" >>>>> SAVED CONTACT ITEM :"+contact.toString());
        patientContact.setContact(contact);

        System.err.println(" ********** PATIENT CONTACT :"+patientContact.toString());

        return patientContact;
    }

    @Override
    public Contact getLastPatientContact(Patient patient){
        return contactService.findLatestContact(patient);
    }

    @Override
    public FollowUp getCurrentCareLevel(PatientContact patientContact) {

        if(patientContact.getMentalHealthScreening().getScreenedForMentalHealth().equals(YesNo.YES)){
            if((patientContact.getMentalHealthScreening().getIdentifiedRisks()!=null && patientContact.getMentalHealthScreening().getIdentifiedRisks().size()>0)){
                return FollowUp.ENHANCED;
            }
        }else{
            // get previous latest screening
        }

        if(patientContact.getTbIpt().getScreenedForTb().equals(YesNo.YES)){
            if(patientContact.getTbIpt().getTbSymptoms()!=null && patientContact.getTbIpt().getTbSymptoms().size()>0){
                return FollowUp.ENHANCED;
            }
        }else{
            // get previous latest screening
        }

        if((patientContact.getContact()!=null && patientContact.getContact().getClinicalAssessments()!=null && patientContact.getContact().getClinicalAssessments().size()>0)
            || (patientContact.getContact()!=null && patientContact.getContact().getNonClinicalAssessments()!=null && patientContact.getContact().getNonClinicalAssessments().size()>0)
        ){
            return FollowUp.ENHANCED;
        }


        return FollowUp.STANDARD;
    }

}