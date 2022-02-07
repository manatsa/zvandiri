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
package zw.org.zvandiri.report.api.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.Reportutil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.report.api.GenericReportModel;
import zw.org.zvandiri.report.api.service.DetailedReportService;
import zw.org.zvandiri.report.api.service.parallel.ContactReportTask;
import zw.org.zvandiri.report.api.service.parallel.GenericCountReportTask;

/**
 *
 * @author Judge Muzinda
 */
@Repository
public class DetailedReportServiceImpl implements DetailedReportService {

    @Resource
    private DetailedPatientReportService detailedPatientReportService;
    @Resource
    private ContactReportService contactReportService;
    @Resource
    ContactService contactService;
    @Resource
    InvestigationTestService testService;
    @Resource
    MentalHealthScreeningService mentalHealthScreeningService;

    @Override
    public List<GenericReportModel> getDefaultReport(SearchDTO dto) {
        String[] headers = {"Name", "Age", "Date of Birth", "Gender", "Date Joined", "Viral Load", "CD4 Count",
            "Province", "District", "Primary Clinic", "Support Group", "Mobile Number", "Referer",  "IS CATS", "In YMM Programme","In YMD Programme"};
        List<GenericReportModel> items = new ArrayList<>();
        items.add(new GenericReportModel(Arrays.asList(headers)));
        for (Patient item : detailedPatientReportService.get(dto.getInstance(dto))) {
            String[] inner = {
                    item.getName(),
                    item.getAge() + "",
                    DateUtil.getStringFromDate(item.getDateOfBirth()),
                    item.getGender().getName(),
                    item.getDateJoin(),
                    item.getViralLoad() != null ? item.getViralLoad() + "" : "",
                    item.getCd4Count() != null ? item.getCd4Count() + "" : "",
                    item.getPrimaryClinic().getDistrict().getProvince().getName(),
                    item.getPrimaryClinic().getDistrict().getName(),
                    item.getPrimaryClinic().getName(),
                    item.getSupportGroup() != null ? item.getSupportGroup().getName() : "",
                    item.getMobileNumber(),
                    item.getReferer() != null ? item.getReferer().getName() : "",
                    item.getCat()!=null?item.getCat().getName():"",
                    item.getYoungMumGroup()!=null?item.getYoungMumGroup().getName():"",
                    item.getYoungDadGroup()!=null?item.getYoungDadGroup().getName():"",
            };
            items.add(new GenericReportModel(Arrays.asList(inner)));
        }
        return items;
    }

    @Override
    public List<GenericReportModel> get(List<Patient> patients) {
        String[] headers = {"Name", "OI/ ART Number", "Age", "Date of Birth", "Gender", "Mode of Transimission", "Disability Status",
            "Current Drug Regimen", "Region", "District", "Primary Clinic", "Support Group", "Referer","Last Contact Date",
            "Current Care Level","Last Contacted By","Viral Load","Date Taken","Mental Health Risk","Mental Health Identified Risks","Date Screened","IS CATS", "In YMM Programme", "In YMD Programme"};

        List<GenericReportModel> items = new ArrayList<>();
        items.add(new GenericReportModel(Arrays.asList(headers)));
        for (Patient item : patients) {
            //Contact lastContact=item.getLastPatientContact(contactService);
            InvestigationTest vlTest=item.getLastPatientVL(testService);
            //MentalHealthScreening mentalHealthScreening=item.getLastPatientMentalHealthScreening(mentalHealthScreeningService);
            String[] inner = {
                    item.getName(),
                    item.getoINumber(),
                    item.getAge() + "",
                    DateUtil.getStringFromDate(item.getDateOfBirth()),
                    item.getGender().getName(),
                    item.getTransmissionMode() != null ? item.getTransmissionMode().getName() : "",
                    item.getDisabilityStatus() != null ? item.getDisabilityStatus().getName() : "",
                    item.getCurrentArvRegimen(),
                    item.getPrimaryClinic().getDistrict().getProvince().getName(),
                    item.getPrimaryClinic().getDistrict().getName(),
                    item.getPrimaryClinic().getName(),
                    item.getSupportGroup() != null ? item.getSupportGroup().getName() : "",
                    item.getReferer() != null ? item.getReferer().getName() : "",
                    /*lastContact!=null?lastContact.getContactDate().toString():"",
                    lastContact!=null?lastContact.getCareLevel().getName():"",
                    lastContact!=null?lastContact.getCreatedBy().getDisplayName():"",*/
                    vlTest!=null? vlTest.getResult()+"":"",
                    vlTest!=null? vlTest.getDateTaken().toString():"",
                    /*mentalHealthScreening!=null?mentalHealthScreening.getRisk().getName():"",
                    mentalHealthScreening!=null? Reportutil.StringsFromList(mentalHealthScreening.getIdentifiedRisks()):"",
                    mentalHealthScreening!=null? mentalHealthScreening.getDateScreened()!=null?mentalHealthScreening.getDateScreened().toString():"":"",*/
                    item.getCat() != null ? item.getCat().getName() : "",
                    item.getYoungMumGroup() != null ? item.getYoungMumGroup().getName() : "",
                    item.getYoungDadGroup() != null ? item.getYoungDadGroup().getName() : ""
            };
            items.add(new GenericReportModel(Arrays.asList(inner)));
        }
        return items;
    }

    @Override
    public List<GenericReportModel> getDeceased(List<Patient> patients) {
        String[] headers = {"Name", "OI/ ART Number", "Age", "Date of Birth", "Gender"
                , "Region", "District", "Primary Clinic", "Support Group", "Referer", "Date Modified"};

        List<GenericReportModel> items = new ArrayList<>();
        items.add(new GenericReportModel(Arrays.asList(headers)));
        for (Patient item : patients) {
            String[] inner = {
                    item.getName(),
                    item.getoINumber(),
                    item.getAge() + "",
                    DateUtil.getStringFromDate(item.getDateOfBirth()),
                    item.getGender().getName(),
                    item.getPrimaryClinic().getDistrict().getProvince().getName(),
                    item.getPrimaryClinic().getDistrict().getName(),
                    item.getPrimaryClinic().getName(),
                    item.getSupportGroup() != null ? item.getSupportGroup().getName() : "",
                    item.getReferer() != null ? item.getReferer().getName() : "",
                    DateUtil.getStringFromDate(item.getDateModified())
            };
            items.add(new GenericReportModel(Arrays.asList(inner)));
        }
        return items;
    }

    @Override
    public List<GenericReportModel> getDefaultReportB(SearchDTO dto) {
        String[] headers = {"Name", "Age", "Gender", "Phone No.","Is CATS","In YMM Programme","In YMD Programme", "District", "Clinic", "Date Of Entry",
            "Current Care Level", "Contact Date", "Follow Up", "Place of Contact", "New Level of Care", "Contacted By"};

        List<GenericReportModel> items = new ArrayList<>();
        items.add(new GenericReportModel(Arrays.asList(headers)));
        ForkJoinPool pool = ForkJoinPool.commonPool();
        List<Contact> result = pool.invoke(new GenericCountReportTask(DateUtil.generateArray(contactReportService.getCount(dto)), contactReportService, dto));
        List<GenericReportModel> contactItems=pool.invoke(new ContactReportTask(result));

        //System.err.println("+++++++++++++++++++++++++ Contacts Items :"+ contactItems.size());
//                for (Contact item : result) {
//            String[] inner = {
//                item.getPatient().getName(),
//                item.getPatient().getAge() + "",
//                item.getPatient().getGender().getName(),
//                item.getPatient().getMobileNumber(),
//                item.getPatient().getPrimaryClinic().getDistrict().getName(),
//                item.getPatient().getPrimaryClinic().getName(),
//                item.getCareLevel().getName(),
//                DateUtil.getStringFromDate(item.getContactDate()),
//                item.getFollowUp().getName(),
//                item.getLocation().getName(),
//                item.getCareLevel().getName(),
//                item.getPosition().getName()
//            };
//            items.add(new GenericReportModel(Arrays.asList(inner)));
//        }
        items.addAll(contactItems);
        return items;
    }

    @Override
    public List<GenericReportModel> getCatsDetailExcel(List<CatDetail> list) {
        
        String[] headers = {"Name", "D.O.B", "Age", "Sex", "Certificate Number",
            "Phone Number", "Date Joined", "Facility", "Graduation Date",
            "Bled For VL", "VL Date", "VL Result", "Regimen",
            "Date Started Regimen", "Sexually Active", "Screened for TB", "TB Screening Date", 
        "TB Outcome", "Received Treatment for TB", "TB Treatment Outcome", "Has Children", "Current Status", "Issued Bicycle", 
        "Issued Phone", "Phone Model"};
        
        List<GenericReportModel> items = new ArrayList<>();
        items.add(new GenericReportModel(Arrays.asList(headers)));
        for (CatDetail item : list) {
            String inner[] = {
                item.getPatient().getName(),
                DateUtil.formatDate(item.getPatient().getDateOfBirth()),
                String.valueOf(item.getPatient().getAge()),
                item.getPatient().getGender().getName(),
                "",
                item.getPatient().getMobileNumber(),
                DateUtil.formatDate(item.getDateAsCat()),
                item.getPrimaryClinic().getName(),
                item.getGraduationDate(),
                item.getVlResultTaken() != null ? item.getVlResultTaken().getName() : "",
                item.getVlDate() != null ? DateUtil.formatDate(item.getVlDate()) : "",
                String.valueOf(item.getPatient().getViralLoad()),
                item.getPatient().getCurrentArvRegimen(),
                item.getRegimenDate() != null ? DateUtil.formatDate(item.getRegimenDate()) : "",
                item.getSexuallyActive() != null ? item.getSexuallyActive().getName() : "", 
                item.getTbScreening() != null ? item.getTbScreening().getName() : "",
                item.getTbScreeningDate() != null ? DateUtil.formatDate(item.getTbScreeningDate()) : "", 
                item.getOutcome(), item.getReceivedTreatment(), item.getTreatmentOutcome(), item.getHaveChildren(),
                item.getCurrentStatus(), item.getBicycle(), item.getPhone(), item.getPhoneMode()
                
            };
            items.add(new GenericReportModel(Arrays.asList(inner)));
        }
        return items;
    }

}
