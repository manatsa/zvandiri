/*
 * Copyright 2015 Judge Muzinda.
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import zw.org.zvandiri.business.domain.ArvHist;
import zw.org.zvandiri.business.domain.Assessment;
import zw.org.zvandiri.business.domain.ChronicInfectionItem;
import zw.org.zvandiri.business.domain.Contact;
import zw.org.zvandiri.business.domain.Dependent;
import zw.org.zvandiri.business.domain.HivConInfectionItem;
import zw.org.zvandiri.business.domain.InvestigationTest;
import zw.org.zvandiri.business.domain.MentalHealthItem;
import zw.org.zvandiri.business.domain.MentalHealthScreening;
import zw.org.zvandiri.business.domain.Mortality;
import zw.org.zvandiri.business.domain.ObstercHist;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.Referral;
import zw.org.zvandiri.business.domain.ServicesReferred;
import zw.org.zvandiri.business.domain.SocialHist;
import zw.org.zvandiri.business.domain.SubstanceItem;
import zw.org.zvandiri.business.domain.TbIpt;
import zw.org.zvandiri.business.repo.ContactRepo;
import zw.org.zvandiri.business.service.ContactReportService;
import zw.org.zvandiri.business.service.ContactService;
import zw.org.zvandiri.business.service.DetailedPatientReportService;
import zw.org.zvandiri.business.service.MortalityService;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.report.api.DatabaseHeader;
import zw.org.zvandiri.report.api.GenericReportModel;
import zw.org.zvandiri.report.api.service.OfficeExportService;

/**
 *
 * @author Judge Muzinda
 */
@Repository
public class OfficeExportServiceImpl implements OfficeExportService {

    @Resource
    private DetailedPatientReportService detailedPatientReportService;
    @Resource
    private MortalityService mortalityService;

    @Autowired
    ContactReportService contactReportService;

    @Resource
    private ContactRepo contactService;

    @Override
    public Workbook exportExcelFile(List<GenericReportModel> rows, String name) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(name);
        int rowNum = 0;
        for (GenericReportModel model : rows) {
            HSSFRow row = sheet.createRow(rowNum++);
            int cellNum = 0;
            for (String item : model.getRow()) {
                Cell cell = row.createCell(cellNum++);
                cell.setCellValue(item);
            }
        }
        return workbook;
    }


    @Override
    public Workbook exportExcelXLSXFile(List<GenericReportModel> rows, String name) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(name);
        int rowNum = 0;
        for (GenericReportModel model : rows) {
            Row row = sheet.createRow(rowNum++);
            int cellNum = 0;
            for (String item : model.getRow()) {
                Cell cell = row.createCell(cellNum++);
                cell.setCellValue(item);
            }
        }
        return workbook;
    }


    @Override
    public XWPFDocument exportWordFile(List<GenericReportModel> rows, String name) {
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraphOne = document.createParagraph();
        XWPFTable table = document.createTable();
        int rowNum = 0;
//        for (GenericReportModel model : rows) {
//            XWPFTableRow tableRowOne = table.getRow(rowNum++);
//            int cellNum = 0;
//            for (String item : model.getRow()) {
//                XWPFRun paragraphOneRunOne = paragraphOne.createRun();
//                paragraphOneRunOne.setText(item);
//            }
//
//        }
        return document;
    }

    @Override
    public Workbook exportDatabase(String name, SearchDTO dto) {

        Workbook workbook = new XSSFWorkbook();
            CellStyle cellStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            cellStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
            // Patient details sheet
            Sheet patientDetails = workbook.createSheet("Patient_Details");
            int rowNum = 0;
            Row header = patientDetails.createRow(rowNum++);
            int cellNum = 0;
            for (String title : DatabaseHeader.PATIENT_HEADER) {
                Cell cell = header.createCell(cellNum++);
                cell.setCellValue(title);
            }
            SearchDTO dto2=new SearchDTO();
            dto2.setProvince(dto.getProvince());
            dto2.setDistrict(dto.getDistrict());
            dto2.setPrimaryClinic(dto.getPrimaryClinic());

            List<Patient> patients = detailedPatientReportService.get(dto2.getInstance(dto2));
            Set<Referral> referrals = new HashSet<>();
            Set<Contact> contacts = new HashSet<>();
            Set<Dependent> dependents = new HashSet<>();
            Set<ChronicInfectionItem> chronicInfectionItems = new HashSet<>();
            Set<MentalHealthItem> mentalHealthItems = new HashSet<>();
            Set<ObstercHist> obstercHists = new HashSet<>();
            Set<SocialHist> socialHists = new HashSet<>();
            Set<SubstanceItem> substanceItems = new HashSet<>();
            Set<InvestigationTest> investigationTests = new HashSet<>();
            Set<ArvHist> arvHists = new HashSet<>();
            Set<HivConInfectionItem> hivConInfectionItems = new HashSet<>();
            Set<TbIpt> tbIpts = new HashSet<>();
            Set<Mortality> mortalitys = new HashSet<>();
            Set<MentalHealthScreening> mentalHealthScreenings = new HashSet<>();
            int numPatient = 0;
            for (Patient patient : patients) {
                int count = 0;



//            contacts.addAll(patient.getContacts());
                if(dto.getStartDate()!=null && dto.getEndDate()!=null)
                {
                    System.err.println("################################################ Dates Selected from "+dto.getStartDate()+" to "+dto.getEndDate());
                    contacts.addAll(contactService.findByPatientAndContactDate(patient,dto.getStartDate(),dto.getEndDate()));
                }else{
                    System.err.println("******************************** Dates Selected are nulls ");
                    contacts.addAll(contactService.findByPatient(patient));
                }

                referrals.addAll(patient.getReferrals());
                dependents.addAll(patient.getDependents());
                chronicInfectionItems.addAll(patient.getChronicInfectionItems());
                hivConInfectionItems.addAll(patient.getHivConInfectionItems());
                mentalHealthItems.addAll(patient.getMentalHealthItems());
                obstercHists.addAll(patient.getObstercHists());
                socialHists.addAll(patient.getSocialHists());
                substanceItems.addAll(patient.getSubstanceItems());
                investigationTests.addAll(patient.getInvestigationTests());
                arvHists.addAll(patient.getArvHists());
                tbIpts.addAll(patient.getTbIpts());
                mortalitys.addAll(mortalityService.get(dto));
                mentalHealthScreenings.addAll(patient.getMentalHealthScreenings());

                header = patientDetails.createRow(rowNum++);
                Cell id = header.createCell(count);
                id.setCellValue(patient.getPatientNumber());
                Cell patientName = header.createCell(++count);
                patientName.setCellValue(patient.getName());
                Cell artNumber = header.createCell(++count);
                artNumber.setCellValue(patient.getoINumber());
                Cell dateOfBirth = header.createCell(++count);
                dateOfBirth.setCellValue(patient.getDateOfBirth());
                Cell age = header.createCell(++count);
                age.setCellValue(patient.getAge());
                dateOfBirth.setCellStyle(cellStyle);
                Cell dateJoined = header.createCell(++count);
                if (patient.getDateJoined() != null) {
                    dateJoined.setCellValue(patient.getDateJoined());
                    dateJoined.setCellStyle(cellStyle);
                } else {
                    dateJoined.setCellValue("");
                }
                dateJoined.setCellStyle(cellStyle);
                Cell gender = header.createCell(++count);
                gender.setCellValue(patient.getGender().getName());
                String addressDetails = patient.getAddress() != null ? patient.getAddress() : " "
                        + " , " + patient.getAddress1() != null ? patient.getAddress1() : "";
                Cell address = header.createCell(++count);
                address.setCellValue(addressDetails);
                Cell mobileNumber = header.createCell(++count);
                mobileNumber.setCellValue(patient.getMobileNumber());
                Cell consetToMHelaath = header.createCell(++count);
                consetToMHelaath.setCellValue(patient.getConsentToMHealth() != null
                        ? patient.getConsentToMHealth().getName() : null);
                Cell education = header.createCell(++count);
                education.setCellValue(patient.getEducation() != null
                        ? patient.getEducation().getName() : null);
                Cell highestEducation = header.createCell(++count);
                highestEducation.setCellValue(patient.getEducationLevel() != null
                        ? patient.getEducationLevel().getName() : null);
                Cell refer = header.createCell(++count);
                refer.setCellValue(patient.getReferer() != null
                        ? patient.getReferer().getName() : null);
                Cell province = header.createCell(++count);
                province.setCellValue(patient.getPrimaryClinic().getDistrict().getProvince().getName());
                Cell district = header.createCell(++count);
                district.setCellValue(patient.getPrimaryClinic().getDistrict().getName());
                Cell primaryClinic = header.createCell(++count);
                primaryClinic.setCellValue(patient.getPrimaryClinic().getName());
                Cell supportGroup = header.createCell(++count);
                supportGroup.setCellValue(patient.getSupportGroup() != null
                        ? patient.getSupportGroup().getName() : null);
                Cell dateTested = header.createCell(++count);
                if (patient.getDateTested() != null) {
                    dateTested.setCellValue(patient.getDateTested());
                    dateTested.setCellStyle(cellStyle);
                } else {
                    dateTested.setCellValue("");
                }
                Cell hivDisclosureLoc = header.createCell(++count);
                hivDisclosureLoc.setCellValue(
                        patient.gethIVDisclosureLocation() != null
                                ? patient.gethIVDisclosureLocation().getName() : null
                );
                Cell hasDisability = header.createCell(++count);
                hasDisability.setCellValue(
                        patient.getDisability() != null ? patient.getDisability().getName() : null
                );
                Cell isCats = header.createCell(++count);
                isCats.setCellValue(
                        patient.getCat() != null ? patient.getCat().getName() : null
                );
                Cell youngMumGroup = header.createCell(++count);
                youngMumGroup.setCellValue(
                        patient.getYoungMumGroup() != null ? patient.getYoungMumGroup().getName() : null
                );
                Cell transMode = header.createCell(++count);
                transMode.setCellValue(
                        patient.getTransmissionMode() != null
                                ? patient.getTransmissionMode().getName() : null);
                Cell hivStatusKnown = header.createCell(++count);
                hivStatusKnown.setCellValue(
                        patient.getHivStatusKnown() != null
                                ? patient.getHivStatusKnown().getName() : null);
                Cell patientStatus = header.createCell(++count);
                patientStatus.setCellValue(
                        patient.getStatus() != null
                                ? patient.getStatus().getName() : null);
                Cell dateStatusChanged = header.createCell(++count);
                if (patient.getStatusChangeDate() != null) {
                    dateStatusChanged.setCellValue(patient.getStatusChangeDate());
                    dateStatusChanged.setCellStyle(cellStyle);
                } else {
                    dateStatusChanged.setCellValue("");
                }
                numPatient++;
                if (numPatient >= 65535) {
                    break;
                }

            }
            // add contacts
            Sheet contactDetails = workbook.createSheet("Patient_Contacts");
            int contactRowNum = 0;
            Row contactHeader = contactDetails.createRow(contactRowNum++);
            int contactCellNum = 0;
            for (String title : DatabaseHeader.CONTACT_HEADER) {
                Cell cell = contactHeader.createCell(contactCellNum++);
                cell.setCellValue(title);
            }
            for (Contact contact : contacts) {
                int count = 0;
                contactHeader = contactDetails.createRow(contactRowNum++);
                Cell id = contactHeader.createCell(count);
                id.setCellValue(contact.getPatient().getPatientNumber());
                Cell patientName = contactHeader.createCell(++count);
                patientName.setCellValue(contact.getPatient().getName());
                Cell dateOfBirth = contactHeader.createCell(++count);
                dateOfBirth.setCellValue(contact.getPatient().getDateOfBirth());
                dateOfBirth.setCellStyle(cellStyle);
                Cell age = contactHeader.createCell(++count);
                age.setCellValue(contact.getPatient().getAge());
                Cell sex = contactHeader.createCell(++count);
                sex.setCellValue(contact.getPatient().getGender().getName());
                Cell province = contactHeader.createCell(++count);
                province.setCellValue(contact.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                Cell district = contactHeader.createCell(++count);
                district.setCellValue(contact.getPatient().getPrimaryClinic().getDistrict().getName());
                Cell primaryClinic = contactHeader.createCell(++count);
                primaryClinic.setCellValue(contact.getPatient().getPrimaryClinic().getName());
                Cell contactDate = contactHeader.createCell(++count);
                contactDate.setCellValue(contact.getContactDate());
                contactDate.setCellStyle(cellStyle);
                Cell careLevel = contactHeader.createCell(++count);
                careLevel.setCellValue(contact.getCareLevel().getName());
                Cell location = contactHeader.createCell(++count);
                location.setCellValue(contact.getLocation() != null ? contact.getLocation().getName() : null);
                Cell position = contactHeader.createCell(++count);
                position.setCellValue(
                        contact.getPosition() != null ? contact.getPosition().getName() : null);
                Cell reason = contactHeader.createCell(++count);
                reason.setCellValue(contact.getReason() != null ? contact.getReason().getName() : null);
                Cell followUp = contactHeader.createCell(++count);
                followUp.setCellValue(contact.getFollowUp() != null ? contact.getFollowUp().getName() : null);
                Cell subjective = contactHeader.createCell(++count);
                subjective.setCellValue(contact.getSubjective());
                Cell objective = contactHeader.createCell(++count);
                objective.setCellValue(contact.getObjective());
                Cell plan = contactHeader.createCell(++count);
                plan.setCellValue(contact.getPlan());
                Cell actionTaken = contactHeader.createCell(++count);
                actionTaken.setCellValue(contact.getActionTaken() != null ? contact.getActionTaken().getName() : null);
                Cell lastClinicAppointmentDate = contactHeader.createCell(++count);
                if (contact.getLastClinicAppointmentDate() != null) {
                    lastClinicAppointmentDate.setCellValue(contact.getLastClinicAppointmentDate());
                    lastClinicAppointmentDate.setCellStyle(cellStyle);
                } else {
                    lastClinicAppointmentDate.setCellValue("");
                }

                Cell attendedClinicAppointment = contactHeader.createCell(++count);
                attendedClinicAppointment.setCellValue(contact.getAttendedClinicAppointment() != null ? contact.getAttendedClinicAppointment().getName() : "");

                Cell nextClinicAppointmentDate = contactHeader.createCell(++count);
                if (contact.getNextClinicAppointmentDate() != null) {
                    nextClinicAppointmentDate.setCellValue(contact.getNextClinicAppointmentDate());
                    nextClinicAppointmentDate.setCellStyle(cellStyle);
                } else {
                    nextClinicAppointmentDate.setCellValue("");
                }

                Cell visitOutcome = contactHeader.createCell(++count);
                visitOutcome.setCellValue(contact.getVisitOutcome() != null ? contact.getVisitOutcome().getName() : null);


                if (contactRowNum >= 65535) {
                    break;
                }
            }

            // add contact assessments
            Sheet assessmentDetails = workbook.createSheet("Patient_Contact_Assessments");
            int assessmentRowNum = 0;
            Row assessmentHeader = assessmentDetails.createRow(assessmentRowNum++);
            int assessmentCellNum = 0;
            for (String title : DatabaseHeader.ASSESSMENT_HEADER) {
                Cell cell = assessmentHeader.createCell(assessmentCellNum++);
                cell.setCellValue(title);
            }
            for (Contact contact : contacts) {
                //if (!contact.getClinicalAssessments().isEmpty() || !contact.getNonClinicalAssessments().isEmpty()) {
                Set<Assessment> clinicalAssessment = contact.getClinicalAssessments();
                if (clinicalAssessment != null) {
                    clinicalAssessment.addAll(contact.getNonClinicalAssessments());
                }
                for (Assessment item : contact.getClinicalAssessments()) {
                    int count = 0;
                    assessmentHeader = assessmentDetails.createRow(assessmentRowNum++);
                    Cell id = assessmentHeader.createCell(count);
                    id.setCellValue(contact.getPatient().getPatientNumber());
                    Cell patientName = assessmentHeader.createCell(++count);
                    patientName.setCellValue(contact.getPatient().getName());
                    Cell dateOfBirth = assessmentHeader.createCell(++count);
                    dateOfBirth.setCellValue(contact.getPatient().getDateOfBirth());
                    dateOfBirth.setCellStyle(cellStyle);
                    Cell age = assessmentHeader.createCell(++count);
                    age.setCellValue(contact.getPatient().getAge());
                    Cell sex = assessmentHeader.createCell(++count);
                    sex.setCellValue(contact.getPatient().getGender().getName());
                    Cell province = assessmentHeader.createCell(++count);
                    province.setCellValue(contact.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                    Cell district = assessmentHeader.createCell(++count);
                    district.setCellValue(contact.getPatient().getPrimaryClinic().getDistrict().getName());
                    Cell primaryClinic = assessmentHeader.createCell(++count);
                    primaryClinic.setCellValue(contact.getPatient().getPrimaryClinic().getName());
                    Cell contactDate = assessmentHeader.createCell(++count);
                    contactDate.setCellValue(contact.getContactDate());
                    contactDate.setCellStyle(cellStyle);
                    Cell careLevel = assessmentHeader.createCell(++count);
                    careLevel.setCellValue(contact.getCareLevel().getName());
                    Cell assessmentType = assessmentHeader.createCell(++count);
                    assessmentType.setCellValue(item.getContactAssessment().getName());
                    Cell assessment = assessmentHeader.createCell(++count);
                    assessment.setCellValue(item.toString());
                }
                //}else{
                //System.err.println("Contact without assessment :"+contact.getPatient().getFirstName()+" "+contact.getPatient().getLastName());
                //}
//            if (assessmentRowNum >= 65535) {
//                break;
//            }
            }
            // add referrals
            Sheet referralDetails = workbook.createSheet("Patient_Referral");
            int referralRowNum = 0;
            Row referralRow = referralDetails.createRow(referralRowNum++);
            int referralCellNum = 0;
            for (String title : DatabaseHeader.REFERRAL_HEADER) {
                Cell cell = referralRow.createCell(referralCellNum++);
                cell.setCellValue(title);
            }
            for (Referral referral : referrals) {
                int count = 0;
                referralRow = referralDetails.createRow(referralRowNum++);
                Cell id = referralRow.createCell(count);
                id.setCellValue(referral.getPatient().getPatientNumber());
                Cell patientName = referralRow.createCell(++count);
                patientName.setCellValue(referral.getPatient().getName());
                Cell dateOfBirth = referralRow.createCell(++count);
                dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                dateOfBirth.setCellStyle(cellStyle);
                Cell age = referralRow.createCell(++count);
                age.setCellValue(referral.getPatient().getAge());
                Cell sex = referralRow.createCell(++count);
                sex.setCellValue(referral.getPatient().getGender().getName());
                Cell province = referralRow.createCell(++count);
                province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                Cell district = referralRow.createCell(++count);
                district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                Cell primaryClinic = referralRow.createCell(++count);
                primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                Cell referralDate = referralRow.createCell(++count);
                referralDate.setCellValue(referral.getReferralDate());
                referralDate.setCellStyle(cellStyle);
                Cell expectedVisitDate = referralRow.createCell(++count);
                if (referral.getExpectedVisitDate() != null) {
                    expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                    expectedVisitDate.setCellStyle(cellStyle);
                } else {
                    expectedVisitDate.setCellValue("");
                }
                Cell organisation = referralRow.createCell(++count);
                organisation.setCellValue(referral.getOrganisation());
                Cell designation = referralRow.createCell(++count);
                designation.setCellValue(referral.getDesignation());
                Cell attendingOfficer = referralRow.createCell(++count);
                attendingOfficer.setCellValue(referral.getAttendingOfficer());
                Cell dateAttended = referralRow.createCell(++count);
                if (referral.getDateAttended() != null) {
                    dateAttended.setCellValue(referral.getDateAttended());
                    dateAttended.setCellStyle(cellStyle);
                } else {
                    dateAttended.setCellValue("");
                }
                Cell actionTaken = referralRow.createCell(++count);
                actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                Cell hivReq = referralRow.createCell(++count);
                hivReq.setCellValue(!referral.getHivStiServicesReq().isEmpty()
                        ? referral.getHivStiServicesReq().toString() : null);
                Cell hivRec = referralRow.createCell(++count);
                hivRec.setCellValue(!referral.getHivStiServicesAvailed().isEmpty()
                        ? referral.getHivStiServicesAvailed().toString() : null);
                Cell oiReq = referralRow.createCell(++count);
                oiReq.setCellValue(!referral.getOiArtReq().isEmpty()
                        ? referral.getOiArtReq().toString() : null);
                Cell oiRec = referralRow.createCell(++count);
                oiRec.setCellValue(!referral.getOiArtAvailed().isEmpty()
                        ? referral.getOiArtAvailed().toString() : null);
                Cell srhReq = referralRow.createCell(++count);
                srhReq.setCellValue(!referral.getSrhReq().isEmpty()
                        ? referral.getSrhReq().toString() : null);
                Cell srhRec = referralRow.createCell(++count);
                srhRec.setCellValue(!referral.getSrhAvailed().isEmpty()
                        ? referral.getSrhAvailed().toString() : null);
                Cell labReq = referralRow.createCell(++count);
                labReq.setCellValue(!referral.getLaboratoryReq().isEmpty()
                        ? referral.getLaboratoryReq().toString() : null);
                Cell labRec = referralRow.createCell(++count);
                labRec.setCellValue(!referral.getLaboratoryAvailed().isEmpty()
                        ? referral.getLaboratoryAvailed().toString() : null);
                Cell tbReq = referralRow.createCell(++count);
                tbReq.setCellValue(!referral.getTbReq().isEmpty()
                        ? referral.getTbReq().toString() : null);
                Cell tbRec = referralRow.createCell(++count);
                tbRec.setCellValue(!referral.getTbAvailed().isEmpty()
                        ? referral.getTbAvailed().toString() : null);
                Cell psReq = referralRow.createCell(++count);
                psReq.setCellValue(!referral.getPsychReq().isEmpty()
                        ? referral.getPsychReq().toString() : null);
                Cell psRec = referralRow.createCell(++count);
                psRec.setCellValue(!referral.getPsychAvailed().isEmpty()
                        ? referral.getPsychAvailed().toString() : null);
                Cell legalReq = referralRow.createCell(++count);
                legalReq.setCellValue(!referral.getLegalReq().isEmpty()
                        ? referral.getLegalReq().toString() : null);
                Cell legalRec = referralRow.createCell(++count);
                legalRec.setCellValue(!referral.getLegalAvailed().isEmpty()
                        ? referral.getLegalAvailed().toString() : null);
                if (referralRowNum >= 65535) {
                    break;
                }
            }

            // add hiv sti services referred
            Sheet hivStiReferredDetails = workbook.createSheet("Hiv_And_Sti_Services_Referred");
            int hivStiReferredRowNum = 0;
            Row hivStiReferredRow = hivStiReferredDetails.createRow(hivStiReferredRowNum++);
            int hivStiReferredCellNum = 0;
            for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                Cell cell = hivStiReferredRow.createCell(hivStiReferredCellNum++);
                cell.setCellValue(title);
            }
            for (Referral referral : referrals) {
                if (!referral.getHivStiServicesReq().isEmpty()) {
                    for (ServicesReferred referred : referral.getHivStiServicesReq()) {
                        int count = 0;
                        hivStiReferredRow = hivStiReferredDetails.createRow(hivStiReferredRowNum++);
                        Cell id = hivStiReferredRow.createCell(count);
                        id.setCellValue(referral.getPatient().getPatientNumber());
                        Cell patientName = hivStiReferredRow.createCell(++count);
                        patientName.setCellValue(referral.getPatient().getName());
                        Cell dateOfBirth = hivStiReferredRow.createCell(++count);
                        dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                        dateOfBirth.setCellStyle(cellStyle);
                        Cell age = hivStiReferredRow.createCell(++count);
                        age.setCellValue(referral.getPatient().getAge());
                        Cell sex = hivStiReferredRow.createCell(++count);
                        sex.setCellValue(referral.getPatient().getGender().getName());
                        Cell province = hivStiReferredRow.createCell(++count);
                        province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                        Cell district = hivStiReferredRow.createCell(++count);
                        district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                        Cell primaryClinic = hivStiReferredRow.createCell(++count);
                        primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                        Cell referralDate = hivStiReferredRow.createCell(++count);
                        referralDate.setCellValue(referral.getReferralDate());
                        referralDate.setCellStyle(cellStyle);
                        Cell expectedVisitDate = hivStiReferredRow.createCell(++count);
                        if (referral.getExpectedVisitDate() != null) {
                            expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                            expectedVisitDate.setCellStyle(cellStyle);
                        } else {
                            expectedVisitDate.setCellValue("");
                        }
                        Cell organisation = hivStiReferredRow.createCell(++count);
                        organisation.setCellValue(referral.getOrganisation());
                        Cell designation = hivStiReferredRow.createCell(++count);
                        designation.setCellValue(referral.getDesignation());
                        Cell attendingOfficer = hivStiReferredRow.createCell(++count);
                        attendingOfficer.setCellValue(referral.getAttendingOfficer());
                        Cell dateAttended = hivStiReferredRow.createCell(++count);
                        if (referral.getDateAttended() != null) {
                            dateAttended.setCellValue(referral.getDateAttended());
                            dateAttended.setCellStyle(cellStyle);
                        } else {
                            dateAttended.setCellValue("");
                        }
                        Cell actionTaken = hivStiReferredRow.createCell(++count);
                        actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                        Cell hivReq = hivStiReferredRow.createCell(++count);
                        hivReq.setCellValue(referred.toString());
                    }
                }

            }

            // add hiv sti services provided
            Sheet hivStiProvidedDetails = workbook.createSheet("Hiv_And_Sti_Services_Provided");
            int hivStiProvidedRowNum = 0;
            Row hivStiProvidedRow = hivStiProvidedDetails.createRow(hivStiProvidedRowNum++);
            int hivStiProvidedCellNum = 0;
            for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                Cell cell = hivStiProvidedRow.createCell(hivStiProvidedCellNum++);
                cell.setCellValue(title);
            }
            for (Referral referral : referrals) {
                if (!referral.getHivStiServicesAvailed().isEmpty()) {
                    for (ServicesReferred referred : referral.getHivStiServicesAvailed()) {
                        int count = 0;
                        hivStiProvidedRow = hivStiProvidedDetails.createRow(hivStiProvidedRowNum++);
                        Cell id = hivStiProvidedRow.createCell(count);
                        id.setCellValue(referral.getPatient().getPatientNumber());
                        Cell patientName = hivStiProvidedRow.createCell(++count);
                        patientName.setCellValue(referral.getPatient().getName());
                        Cell dateOfBirth = hivStiProvidedRow.createCell(++count);
                        dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                        dateOfBirth.setCellStyle(cellStyle);
                        Cell age = hivStiProvidedRow.createCell(++count);
                        age.setCellValue(referral.getPatient().getAge());
                        Cell sex = hivStiProvidedRow.createCell(++count);
                        sex.setCellValue(referral.getPatient().getGender().getName());
                        sex.setCellValue(referral.getPatient().getGender().getName());
                        Cell province = hivStiProvidedRow.createCell(++count);
                        province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                        Cell district = hivStiProvidedRow.createCell(++count);
                        district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                        Cell primaryClinic = hivStiProvidedRow.createCell(++count);
                        primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                        Cell referralDate = hivStiProvidedRow.createCell(++count);
                        referralDate.setCellValue(referral.getReferralDate());
                        referralDate.setCellStyle(cellStyle);
                        Cell expectedVisitDate = hivStiProvidedRow.createCell(++count);
                        if (referral.getExpectedVisitDate() != null) {
                            expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                            expectedVisitDate.setCellStyle(cellStyle);
                        } else {
                            expectedVisitDate.setCellValue("");
                        }
                        Cell organisation = hivStiProvidedRow.createCell(++count);
                        organisation.setCellValue(referral.getOrganisation());
                        Cell designation = hivStiProvidedRow.createCell(++count);
                        designation.setCellValue(referral.getDesignation());
                        Cell attendingOfficer = hivStiProvidedRow.createCell(++count);
                        attendingOfficer.setCellValue(referral.getAttendingOfficer());
                        Cell dateAttended = hivStiProvidedRow.createCell(++count);
                        if (referral.getDateAttended() != null) {
                            dateAttended.setCellValue(referral.getDateAttended());
                            dateAttended.setCellStyle(cellStyle);
                        } else {
                            dateAttended.setCellValue("");
                        }
                        Cell actionTaken = hivStiProvidedRow.createCell(++count);
                        actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                        Cell hivReq = hivStiProvidedRow.createCell(++count);
                        hivReq.setCellValue(referred.toString());
                    }
                }

            }

            // add oi art services referred
            Sheet oiArtReferredDetails = workbook.createSheet("Oi_Art_Services_Referred");
            int oiArtReferredRowNum = 0;
            Row oiArtReferredRow = oiArtReferredDetails.createRow(oiArtReferredRowNum++);
            int oiArtReferredCellNum = 0;
            for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                Cell cell = oiArtReferredRow.createCell(oiArtReferredCellNum++);
                cell.setCellValue(title);
            }
            for (Referral referral : referrals) {
                if (!referral.getOiArtReq().isEmpty()) {
                    for (ServicesReferred referred : referral.getOiArtReq()) {
                        int count = 0;
                        oiArtReferredRow = oiArtReferredDetails.createRow(oiArtReferredRowNum++);
                        Cell id = oiArtReferredRow.createCell(count);
                        id.setCellValue(referral.getPatient().getPatientNumber());
                        Cell patientName = oiArtReferredRow.createCell(++count);
                        patientName.setCellValue(referral.getPatient().getName());
                        Cell dateOfBirth = oiArtReferredRow.createCell(++count);
                        dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                        dateOfBirth.setCellStyle(cellStyle);
                        Cell age = oiArtReferredRow.createCell(++count);
                        age.setCellValue(referral.getPatient().getAge());
                        Cell sex = oiArtReferredRow.createCell(++count);
                        sex.setCellValue(referral.getPatient().getGender().getName());
                        Cell province = oiArtReferredRow.createCell(++count);
                        province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                        Cell district = oiArtReferredRow.createCell(++count);
                        district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                        Cell primaryClinic = oiArtReferredRow.createCell(++count);
                        primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                        Cell referralDate = oiArtReferredRow.createCell(++count);
                        referralDate.setCellValue(referral.getReferralDate());
                        referralDate.setCellStyle(cellStyle);
                        Cell expectedVisitDate = oiArtReferredRow.createCell(++count);
                        if (referral.getExpectedVisitDate() != null) {
                            expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                            expectedVisitDate.setCellStyle(cellStyle);
                        } else {
                            expectedVisitDate.setCellValue("");
                        }
                        Cell organisation = oiArtReferredRow.createCell(++count);
                        organisation.setCellValue(referral.getOrganisation());
                        Cell designation = oiArtReferredRow.createCell(++count);
                        designation.setCellValue(referral.getDesignation());
                        Cell attendingOfficer = oiArtReferredRow.createCell(++count);
                        attendingOfficer.setCellValue(referral.getAttendingOfficer());
                        Cell dateAttended = oiArtReferredRow.createCell(++count);
                        if (referral.getDateAttended() != null) {
                            dateAttended.setCellValue(referral.getDateAttended());
                            dateAttended.setCellStyle(cellStyle);
                        } else {
                            dateAttended.setCellValue("");
                        }
                        Cell actionTaken = oiArtReferredRow.createCell(++count);
                        actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                        Cell hivReq = oiArtReferredRow.createCell(++count);
                        hivReq.setCellValue(referred.toString());
                    }
                }

            }

            // add oi art services provided
            Sheet oiArtProvidedDetails = workbook.createSheet("Oi_Art_Services_Provided");
            int oiArtProvidedRowNum = 0;
            Row oiArtProvidedRow = oiArtProvidedDetails.createRow(oiArtProvidedRowNum++);
            int oiArtProvidedCellNum = 0;
            for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                Cell cell = oiArtProvidedRow.createCell(oiArtProvidedCellNum++);
                cell.setCellValue(title);
            }
            for (Referral referral : referrals) {
                if (!referral.getOiArtAvailed().isEmpty()) {
                    for (ServicesReferred referred : referral.getOiArtAvailed()) {
                        int count = 0;
                        oiArtProvidedRow = oiArtProvidedDetails.createRow(oiArtProvidedRowNum++);
                        Cell id = oiArtProvidedRow.createCell(count);
                        id.setCellValue(referral.getPatient().getPatientNumber());
                        Cell patientName = oiArtProvidedRow.createCell(++count);
                        patientName.setCellValue(referral.getPatient().getName());
                        Cell dateOfBirth = oiArtProvidedRow.createCell(++count);
                        dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                        dateOfBirth.setCellStyle(cellStyle);
                        Cell age = oiArtProvidedRow.createCell(++count);
                        age.setCellValue(referral.getPatient().getAge());
                        Cell sex = oiArtProvidedRow.createCell(++count);
                        sex.setCellValue(referral.getPatient().getGender().getName());
                        Cell province = oiArtProvidedRow.createCell(++count);
                        province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                        Cell district = oiArtProvidedRow.createCell(++count);
                        district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                        Cell primaryClinic = oiArtProvidedRow.createCell(++count);
                        primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                        Cell referralDate = oiArtProvidedRow.createCell(++count);
                        referralDate.setCellValue(referral.getReferralDate());
                        referralDate.setCellStyle(cellStyle);
                        Cell expectedVisitDate = oiArtProvidedRow.createCell(++count);
                        if (referral.getExpectedVisitDate() != null) {
                            expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                            expectedVisitDate.setCellStyle(cellStyle);
                        } else {
                            expectedVisitDate.setCellValue("");
                        }
                        Cell organisation = oiArtProvidedRow.createCell(++count);
                        organisation.setCellValue(referral.getOrganisation());
                        Cell designation = oiArtProvidedRow.createCell(++count);
                        designation.setCellValue(referral.getDesignation());
                        Cell attendingOfficer = oiArtProvidedRow.createCell(++count);
                        attendingOfficer.setCellValue(referral.getAttendingOfficer());
                        Cell dateAttended = oiArtProvidedRow.createCell(++count);
                        if (referral.getDateAttended() != null) {
                            dateAttended.setCellValue(referral.getDateAttended());
                            dateAttended.setCellStyle(cellStyle);
                        } else {
                            dateAttended.setCellValue("");
                        }
                        Cell actionTaken = oiArtProvidedRow.createCell(++count);
                        actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                        Cell hivReq = oiArtProvidedRow.createCell(++count);
                        hivReq.setCellValue(referred.toString());
                    }
                }

            }

            // add srh services referred
            Sheet srhReferredDetails = workbook.createSheet("Srh_Services_Referred");
            int srhReferredRowNum = 0;
            Row srhReferredRow = srhReferredDetails.createRow(srhReferredRowNum++);
            int srhReferredCellNum = 0;
            for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                Cell cell = srhReferredRow.createCell(srhReferredCellNum++);
                cell.setCellValue(title);
            }
            for (Referral referral : referrals) {
                if (!referral.getSrhReq().isEmpty()) {
                    for (ServicesReferred referred : referral.getSrhReq()) {
                        int count = 0;
                        srhReferredRow = srhReferredDetails.createRow(srhReferredRowNum++);
                        Cell id = srhReferredRow.createCell(count);
                        id.setCellValue(referral.getPatient().getPatientNumber());
                        Cell patientName = srhReferredRow.createCell(++count);
                        patientName.setCellValue(referral.getPatient().getName());
                        Cell dateOfBirth = srhReferredRow.createCell(++count);
                        dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                        dateOfBirth.setCellStyle(cellStyle);
                        Cell age = srhReferredRow.createCell(++count);
                        age.setCellValue(referral.getPatient().getAge());
                        Cell sex = srhReferredRow.createCell(++count);
                        sex.setCellValue(referral.getPatient().getGender().getName());
                        Cell province = srhReferredRow.createCell(++count);
                        province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                        Cell district = srhReferredRow.createCell(++count);
                        district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                        Cell primaryClinic = srhReferredRow.createCell(++count);
                        primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                        Cell referralDate = srhReferredRow.createCell(++count);
                        referralDate.setCellValue(referral.getReferralDate());
                        referralDate.setCellStyle(cellStyle);
                        Cell expectedVisitDate = srhReferredRow.createCell(++count);
                        if (referral.getExpectedVisitDate() != null) {
                            expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                            expectedVisitDate.setCellStyle(cellStyle);
                        } else {
                            expectedVisitDate.setCellValue("");
                        }
                        Cell organisation = srhReferredRow.createCell(++count);
                        organisation.setCellValue(referral.getOrganisation());
                        Cell designation = srhReferredRow.createCell(++count);
                        designation.setCellValue(referral.getDesignation());
                        Cell attendingOfficer = srhReferredRow.createCell(++count);
                        attendingOfficer.setCellValue(referral.getAttendingOfficer());
                        Cell dateAttended = srhReferredRow.createCell(++count);
                        if (referral.getDateAttended() != null) {
                            dateAttended.setCellValue(referral.getDateAttended());
                            dateAttended.setCellStyle(cellStyle);
                        } else {
                            dateAttended.setCellValue("");
                        }
                        Cell actionTaken = srhReferredRow.createCell(++count);
                        actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                        Cell hivReq = srhReferredRow.createCell(++count);
                        hivReq.setCellValue(referred.toString());
                    }
                }

            }

            // add srh services provided
            Sheet srhProvidedDetails = workbook.createSheet("Srh_Services_Provided");
            int srhProvidedRowNum = 0;
            Row srhProvidedRow = srhProvidedDetails.createRow(srhProvidedRowNum++);
            int srhProvidedCellNum = 0;
            for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                Cell cell = srhProvidedRow.createCell(srhProvidedCellNum++);
                cell.setCellValue(title);
            }
            for (Referral referral : referrals) {
                if (!referral.getSrhAvailed().isEmpty()) {
                    for (ServicesReferred referred : referral.getSrhAvailed()) {
                        int count = 0;
                        srhProvidedRow = srhProvidedDetails.createRow(srhProvidedRowNum++);
                        Cell id = srhProvidedRow.createCell(count);
                        id.setCellValue(referral.getPatient().getPatientNumber());
                        Cell patientName = srhProvidedRow.createCell(++count);
                        patientName.setCellValue(referral.getPatient().getName());
                        Cell dateOfBirth = srhProvidedRow.createCell(++count);
                        dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                        dateOfBirth.setCellStyle(cellStyle);
                        Cell age = srhProvidedRow.createCell(++count);
                        age.setCellValue(referral.getPatient().getAge());
                        Cell sex = srhProvidedRow.createCell(++count);
                        sex.setCellValue(referral.getPatient().getGender().getName());
                        Cell province = srhProvidedRow.createCell(++count);
                        province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                        Cell district = srhProvidedRow.createCell(++count);
                        district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                        Cell primaryClinic = srhProvidedRow.createCell(++count);
                        primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                        Cell referralDate = srhProvidedRow.createCell(++count);
                        referralDate.setCellValue(referral.getReferralDate());
                        referralDate.setCellStyle(cellStyle);
                        Cell expectedVisitDate = srhProvidedRow.createCell(++count);
                        if (referral.getExpectedVisitDate() != null) {
                            expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                            expectedVisitDate.setCellStyle(cellStyle);
                        } else {
                            expectedVisitDate.setCellValue("");
                        }
                        Cell organisation = srhProvidedRow.createCell(++count);
                        organisation.setCellValue(referral.getOrganisation());
                        Cell designation = srhProvidedRow.createCell(++count);
                        designation.setCellValue(referral.getDesignation());
                        Cell attendingOfficer = srhProvidedRow.createCell(++count);
                        attendingOfficer.setCellValue(referral.getAttendingOfficer());
                        Cell dateAttended = srhProvidedRow.createCell(++count);
                        if (referral.getDateAttended() != null) {
                            dateAttended.setCellValue(referral.getDateAttended());
                            dateAttended.setCellStyle(cellStyle);
                        } else {
                            dateAttended.setCellValue("");
                        }
                        Cell actionTaken = srhProvidedRow.createCell(++count);
                        actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                        Cell hivReq = srhProvidedRow.createCell(++count);
                        hivReq.setCellValue(referred.toString());
                    }
                }

            }

            // add laboratory services referred
            Sheet laboratoryReferredDetails = workbook.createSheet("Laboratory_Services_Referred");
            int laboratoryReferredRowNum = 0;
            Row laboratoryReferredRow = laboratoryReferredDetails.createRow(laboratoryReferredRowNum++);
            int laboratoryReferredCellNum = 0;
            for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                Cell cell = laboratoryReferredRow.createCell(laboratoryReferredCellNum++);
                cell.setCellValue(title);
            }
            for (Referral referral : referrals) {
                if (!referral.getLaboratoryReq().isEmpty()) {
                    for (ServicesReferred referred : referral.getLaboratoryReq()) {
                        int count = 0;
                        laboratoryReferredRow = laboratoryReferredDetails.createRow(laboratoryReferredRowNum++);
                        Cell id = laboratoryReferredRow.createCell(count);
                        id.setCellValue(referral.getPatient().getPatientNumber());
                        Cell patientName = laboratoryReferredRow.createCell(++count);
                        patientName.setCellValue(referral.getPatient().getName());
                        Cell dateOfBirth = laboratoryReferredRow.createCell(++count);
                        dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                        dateOfBirth.setCellStyle(cellStyle);
                        Cell age = laboratoryReferredRow.createCell(++count);
                        age.setCellValue(referral.getPatient().getAge());
                        Cell sex = laboratoryReferredRow.createCell(++count);
                        sex.setCellValue(referral.getPatient().getGender().getName());
                        Cell province = laboratoryReferredRow.createCell(++count);
                        province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                        Cell district = laboratoryReferredRow.createCell(++count);
                        district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                        Cell primaryClinic = laboratoryReferredRow.createCell(++count);
                        primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                        Cell referralDate = laboratoryReferredRow.createCell(++count);
                        referralDate.setCellValue(referral.getReferralDate());
                        referralDate.setCellStyle(cellStyle);
                        Cell expectedVisitDate = laboratoryReferredRow.createCell(++count);
                        if (referral.getExpectedVisitDate() != null) {
                            expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                            expectedVisitDate.setCellStyle(cellStyle);
                        } else {
                            expectedVisitDate.setCellValue("");
                        }
                        Cell organisation = laboratoryReferredRow.createCell(++count);
                        organisation.setCellValue(referral.getOrganisation());
                        Cell designation = laboratoryReferredRow.createCell(++count);
                        designation.setCellValue(referral.getDesignation());
                        Cell attendingOfficer = laboratoryReferredRow.createCell(++count);
                        attendingOfficer.setCellValue(referral.getAttendingOfficer());
                        Cell dateAttended = laboratoryReferredRow.createCell(++count);
                        if (referral.getDateAttended() != null) {
                            dateAttended.setCellValue(referral.getDateAttended());
                            dateAttended.setCellStyle(cellStyle);
                        } else {
                            dateAttended.setCellValue("");
                        }
                        Cell actionTaken = laboratoryReferredRow.createCell(++count);
                        actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                        Cell hivReq = laboratoryReferredRow.createCell(++count);
                        hivReq.setCellValue(referred.toString());
                    }
                }

            }

            // add laboratory services provided
            Sheet laboratoryProvidedDetails = workbook.createSheet("Laboratory_Services_Provided");
            int laboratoryProvidedRowNum = 0;
            Row laboratoryProvidedRow = laboratoryProvidedDetails.createRow(laboratoryProvidedRowNum++);
            int laboratoryProvidedCellNum = 0;
            for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                Cell cell = laboratoryProvidedRow.createCell(laboratoryProvidedCellNum++);
                cell.setCellValue(title);
            }
            for (Referral referral : referrals) {
                if (!referral.getLaboratoryAvailed().isEmpty()) {
                    for (ServicesReferred referred : referral.getLaboratoryAvailed()) {
                        int count = 0;
                        laboratoryProvidedRow = laboratoryProvidedDetails.createRow(laboratoryProvidedRowNum++);
                        Cell id = laboratoryProvidedRow.createCell(count);
                        id.setCellValue(referral.getPatient().getPatientNumber());
                        Cell patientName = laboratoryProvidedRow.createCell(++count);
                        patientName.setCellValue(referral.getPatient().getName());
                        Cell dateOfBirth = laboratoryProvidedRow.createCell(++count);
                        dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                        dateOfBirth.setCellStyle(cellStyle);
                        Cell age = laboratoryProvidedRow.createCell(++count);
                        age.setCellValue(referral.getPatient().getAge());
                        Cell sex = laboratoryProvidedRow.createCell(++count);
                        sex.setCellValue(referral.getPatient().getGender().getName());
                        Cell province = laboratoryProvidedRow.createCell(++count);
                        province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                        Cell district = laboratoryProvidedRow.createCell(++count);
                        district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                        Cell primaryClinic = laboratoryProvidedRow.createCell(++count);
                        primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                        Cell referralDate = laboratoryProvidedRow.createCell(++count);
                        referralDate.setCellValue(referral.getReferralDate());
                        referralDate.setCellStyle(cellStyle);
                        Cell expectedVisitDate = laboratoryProvidedRow.createCell(++count);
                        if (referral.getExpectedVisitDate() != null) {
                            expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                            expectedVisitDate.setCellStyle(cellStyle);
                        } else {
                            expectedVisitDate.setCellValue("");
                        }
                        Cell organisation = laboratoryProvidedRow.createCell(++count);
                        organisation.setCellValue(referral.getOrganisation());
                        Cell designation = laboratoryProvidedRow.createCell(++count);
                        designation.setCellValue(referral.getDesignation());
                        Cell attendingOfficer = laboratoryProvidedRow.createCell(++count);
                        attendingOfficer.setCellValue(referral.getAttendingOfficer());
                        Cell dateAttended = laboratoryProvidedRow.createCell(++count);
                        if (referral.getDateAttended() != null) {
                            dateAttended.setCellValue(referral.getDateAttended());
                            dateAttended.setCellStyle(cellStyle);
                        } else {
                            dateAttended.setCellValue("");
                        }
                        Cell actionTaken = laboratoryProvidedRow.createCell(++count);
                        actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                        Cell hivReq = laboratoryProvidedRow.createCell(++count);
                        hivReq.setCellValue(referred.toString());
                    }
                }

            }

            // add tb services referred
            Sheet tbReferredDetails = workbook.createSheet("Tb_Services_Referred");
            int tbReferredRowNum = 0;
            Row tbReferredRow = tbReferredDetails.createRow(tbReferredRowNum++);
            int tbReferredCellNum = 0;
            for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                Cell cell = tbReferredRow.createCell(tbReferredCellNum++);
                cell.setCellValue(title);
            }
            for (Referral referral : referrals) {
                if (!referral.getTbReq().isEmpty()) {
                    for (ServicesReferred referred : referral.getTbReq()) {
                        int count = 0;
                        tbReferredRow = tbReferredDetails.createRow(tbReferredRowNum++);
                        Cell id = tbReferredRow.createCell(count);
                        id.setCellValue(referral.getPatient().getPatientNumber());
                        Cell patientName = tbReferredRow.createCell(++count);
                        patientName.setCellValue(referral.getPatient().getName());
                        Cell dateOfBirth = tbReferredRow.createCell(++count);
                        dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                        dateOfBirth.setCellStyle(cellStyle);
                        Cell age = tbReferredRow.createCell(++count);
                        age.setCellValue(referral.getPatient().getAge());
                        Cell sex = tbReferredRow.createCell(++count);
                        sex.setCellValue(referral.getPatient().getGender().getName());
                        Cell province = tbReferredRow.createCell(++count);
                        province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                        Cell district = tbReferredRow.createCell(++count);
                        district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                        Cell primaryClinic = tbReferredRow.createCell(++count);
                        primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                        Cell referralDate = tbReferredRow.createCell(++count);
                        referralDate.setCellValue(referral.getReferralDate());
                        referralDate.setCellStyle(cellStyle);
                        Cell expectedVisitDate = tbReferredRow.createCell(++count);
                        if (referral.getExpectedVisitDate() != null) {
                            expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                            expectedVisitDate.setCellStyle(cellStyle);
                        } else {
                            expectedVisitDate.setCellValue("");
                        }
                        Cell organisation = tbReferredRow.createCell(++count);
                        organisation.setCellValue(referral.getOrganisation());
                        Cell designation = tbReferredRow.createCell(++count);
                        designation.setCellValue(referral.getDesignation());
                        Cell attendingOfficer = tbReferredRow.createCell(++count);
                        attendingOfficer.setCellValue(referral.getAttendingOfficer());
                        Cell dateAttended = tbReferredRow.createCell(++count);
                        if (referral.getDateAttended() != null) {
                            dateAttended.setCellValue(referral.getDateAttended());
                            dateAttended.setCellStyle(cellStyle);
                        } else {
                            dateAttended.setCellValue("");
                        }
                        Cell actionTaken = tbReferredRow.createCell(++count);
                        actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                        Cell hivReq = tbReferredRow.createCell(++count);
                        hivReq.setCellValue(referred.toString());
                    }
                }

            }

            // add tb services provided
            Sheet tbProvidedDetails = workbook.createSheet("Tb_Services_Provided");
            int tbProvidedRowNum = 0;
            Row tbProvidedRow = tbProvidedDetails.createRow(tbProvidedRowNum++);
            int tbProvidedCellNum = 0;
            for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                Cell cell = tbProvidedRow.createCell(tbProvidedCellNum++);
                cell.setCellValue(title);
            }
            for (Referral referral : referrals) {
                if (!referral.getTbAvailed().isEmpty()) {
                    for (ServicesReferred referred : referral.getTbAvailed()) {
                        int count = 0;
                        tbProvidedRow = tbProvidedDetails.createRow(tbProvidedRowNum++);
                        Cell id = tbProvidedRow.createCell(count);
                        id.setCellValue(referral.getPatient().getPatientNumber());
                        Cell patientName = tbProvidedRow.createCell(++count);
                        patientName.setCellValue(referral.getPatient().getName());
                        Cell dateOfBirth = tbProvidedRow.createCell(++count);
                        dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                        dateOfBirth.setCellStyle(cellStyle);
                        Cell age = tbProvidedRow.createCell(++count);
                        age.setCellValue(referral.getPatient().getAge());
                        Cell sex = tbProvidedRow.createCell(++count);
                        sex.setCellValue(referral.getPatient().getGender().getName());
                        Cell province = tbProvidedRow.createCell(++count);
                        province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                        Cell district = tbProvidedRow.createCell(++count);
                        district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                        Cell primaryClinic = tbProvidedRow.createCell(++count);
                        primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                        Cell referralDate = tbProvidedRow.createCell(++count);
                        referralDate.setCellValue(referral.getReferralDate());
                        referralDate.setCellStyle(cellStyle);
                        Cell expectedVisitDate = tbProvidedRow.createCell(++count);
                        if (referral.getExpectedVisitDate() != null) {
                            expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                            expectedVisitDate.setCellStyle(cellStyle);
                        } else {
                            expectedVisitDate.setCellValue("");
                        }
                        Cell organisation = tbProvidedRow.createCell(++count);
                        organisation.setCellValue(referral.getOrganisation());
                        Cell designation = tbProvidedRow.createCell(++count);
                        designation.setCellValue(referral.getDesignation());
                        Cell attendingOfficer = tbProvidedRow.createCell(++count);
                        attendingOfficer.setCellValue(referral.getAttendingOfficer());
                        Cell dateAttended = tbProvidedRow.createCell(++count);
                        if (referral.getDateAttended() != null) {
                            dateAttended.setCellValue(referral.getDateAttended());
                            dateAttended.setCellStyle(cellStyle);
                        } else {
                            dateAttended.setCellValue("");
                        }
                        Cell actionTaken = tbProvidedRow.createCell(++count);
                        actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                        Cell hivReq = tbProvidedRow.createCell(++count);
                        hivReq.setCellValue(referred.toString());
                    }
                }

            }

            // add psych services referred
            Sheet psychReferredDetails = workbook.createSheet("Psych_Services_Referred");
            int psychReferredRowNum = 0;
            Row psychReferredRow = psychReferredDetails.createRow(psychReferredRowNum++);
            int psychReferredCellNum = 0;
            for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                Cell cell = psychReferredRow.createCell(psychReferredCellNum++);
                cell.setCellValue(title);
            }
            for (Referral referral : referrals) {
                if (!referral.getPsychReq().isEmpty()) {
                    for (ServicesReferred referred : referral.getPsychReq()) {
                        int count = 0;
                        psychReferredRow = psychReferredDetails.createRow(psychReferredRowNum++);
                        Cell id = psychReferredRow.createCell(count);
                        id.setCellValue(referral.getPatient().getPatientNumber());
                        Cell patientName = psychReferredRow.createCell(++count);
                        patientName.setCellValue(referral.getPatient().getName());
                        Cell dateOfBirth = psychReferredRow.createCell(++count);
                        dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                        dateOfBirth.setCellStyle(cellStyle);
                        Cell age = psychReferredRow.createCell(++count);
                        age.setCellValue(referral.getPatient().getAge());
                        Cell sex = psychReferredRow.createCell(++count);
                        sex.setCellValue(referral.getPatient().getGender().getName());
                        Cell province = psychReferredRow.createCell(++count);
                        province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                        Cell district = psychReferredRow.createCell(++count);
                        district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                        Cell primaryClinic = psychReferredRow.createCell(++count);
                        primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                        Cell referralDate = psychReferredRow.createCell(++count);
                        referralDate.setCellValue(referral.getReferralDate());
                        referralDate.setCellStyle(cellStyle);
                        Cell expectedVisitDate = psychReferredRow.createCell(++count);
                        if (referral.getExpectedVisitDate() != null) {
                            expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                            expectedVisitDate.setCellStyle(cellStyle);
                        } else {
                            expectedVisitDate.setCellValue("");
                        }
                        Cell organisation = psychReferredRow.createCell(++count);
                        organisation.setCellValue(referral.getOrganisation());
                        Cell designation = psychReferredRow.createCell(++count);
                        designation.setCellValue(referral.getDesignation());
                        Cell attendingOfficer = psychReferredRow.createCell(++count);
                        attendingOfficer.setCellValue(referral.getAttendingOfficer());
                        Cell dateAttended = psychReferredRow.createCell(++count);
                        if (referral.getDateAttended() != null) {
                            dateAttended.setCellValue(referral.getDateAttended());
                            dateAttended.setCellStyle(cellStyle);
                        } else {
                            dateAttended.setCellValue("");
                        }
                        Cell actionTaken = psychReferredRow.createCell(++count);
                        actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                        Cell hivReq = psychReferredRow.createCell(++count);
                        hivReq.setCellValue(referred.toString());
                    }
                }

            }

            // add psych services provided
            Sheet psychProvidedDetails = workbook.createSheet("Psych_Services_Provided");
            int psychProvidedRowNum = 0;
            Row psychProvidedRow = psychProvidedDetails.createRow(psychProvidedRowNum++);
            int psychProvidedCellNum = 0;
            for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                Cell cell = psychProvidedRow.createCell(psychProvidedCellNum++);
                cell.setCellValue(title);
            }
            for (Referral referral : referrals) {
                if (!referral.getPsychAvailed().isEmpty()) {
                    for (ServicesReferred referred : referral.getPsychAvailed()) {
                        int count = 0;
                        psychProvidedRow = psychProvidedDetails.createRow(psychProvidedRowNum++);
                        Cell id = psychProvidedRow.createCell(count);
                        id.setCellValue(referral.getPatient().getPatientNumber());
                        Cell patientName = psychProvidedRow.createCell(++count);
                        patientName.setCellValue(referral.getPatient().getName());
                        Cell dateOfBirth = psychProvidedRow.createCell(++count);
                        dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                        dateOfBirth.setCellStyle(cellStyle);
                        Cell age = psychProvidedRow.createCell(++count);
                        age.setCellValue(referral.getPatient().getAge());
                        Cell sex = psychProvidedRow.createCell(++count);
                        sex.setCellValue(referral.getPatient().getGender().getName());
                        Cell province = psychProvidedRow.createCell(++count);
                        province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                        Cell district = psychProvidedRow.createCell(++count);
                        district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                        Cell primaryClinic = psychProvidedRow.createCell(++count);
                        primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                        Cell referralDate = psychProvidedRow.createCell(++count);
                        referralDate.setCellValue(referral.getReferralDate());
                        referralDate.setCellStyle(cellStyle);
                        Cell expectedVisitDate = psychProvidedRow.createCell(++count);
                        if (referral.getExpectedVisitDate() != null) {
                            expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                            expectedVisitDate.setCellStyle(cellStyle);
                        } else {
                            expectedVisitDate.setCellValue("");
                        }
                        Cell organisation = psychProvidedRow.createCell(++count);
                        organisation.setCellValue(referral.getOrganisation());
                        Cell designation = psychProvidedRow.createCell(++count);
                        designation.setCellValue(referral.getDesignation());
                        Cell attendingOfficer = psychProvidedRow.createCell(++count);
                        attendingOfficer.setCellValue(referral.getAttendingOfficer());
                        Cell dateAttended = psychProvidedRow.createCell(++count);
                        if (referral.getDateAttended() != null) {
                            dateAttended.setCellValue(referral.getDateAttended());
                            dateAttended.setCellStyle(cellStyle);
                        } else {
                            dateAttended.setCellValue("");
                        }
                        Cell actionTaken = psychProvidedRow.createCell(++count);
                        actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                        Cell hivReq = psychProvidedRow.createCell(++count);
                        hivReq.setCellValue(referred.toString());
                    }
                }

            }

            // add legal services referred
            Sheet legalReferredDetails = workbook.createSheet("Legal_Services_Referred");
            int legalReferredRowNum = 0;
            Row legalReferredRow = legalReferredDetails.createRow(legalReferredRowNum++);
            int legalReferredCellNum = 0;
            for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                Cell cell = legalReferredRow.createCell(legalReferredCellNum++);
                cell.setCellValue(title);
            }
            for (Referral referral : referrals) {
                if (!referral.getLegalReq().isEmpty()) {
                    for (ServicesReferred referred : referral.getLegalReq()) {
                        int count = 0;
                        legalReferredRow = legalReferredDetails.createRow(legalReferredRowNum++);
                        Cell id = legalReferredRow.createCell(count);
                        id.setCellValue(referral.getPatient().getPatientNumber());
                        Cell patientName = legalReferredRow.createCell(++count);
                        patientName.setCellValue(referral.getPatient().getName());
                        Cell dateOfBirth = legalReferredRow.createCell(++count);
                        dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                        dateOfBirth.setCellStyle(cellStyle);
                        Cell age = legalReferredRow.createCell(++count);
                        age.setCellValue(referral.getPatient().getAge());
                        Cell sex = legalReferredRow.createCell(++count);
                        sex.setCellValue(referral.getPatient().getGender().getName());
                        Cell province = legalReferredRow.createCell(++count);
                        province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                        Cell district = legalReferredRow.createCell(++count);
                        district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                        Cell primaryClinic = legalReferredRow.createCell(++count);
                        primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                        Cell referralDate = legalReferredRow.createCell(++count);
                        referralDate.setCellValue(referral.getReferralDate());
                        referralDate.setCellStyle(cellStyle);
                        Cell expectedVisitDate = legalReferredRow.createCell(++count);
                        if (referral.getExpectedVisitDate() != null) {
                            expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                            expectedVisitDate.setCellStyle(cellStyle);
                        } else {
                            expectedVisitDate.setCellValue("");
                        }
                        Cell organisation = legalReferredRow.createCell(++count);
                        organisation.setCellValue(referral.getOrganisation());
                        Cell designation = legalReferredRow.createCell(++count);
                        designation.setCellValue(referral.getDesignation());
                        Cell attendingOfficer = legalReferredRow.createCell(++count);
                        attendingOfficer.setCellValue(referral.getAttendingOfficer());
                        Cell dateAttended = legalReferredRow.createCell(++count);
                        if (referral.getDateAttended() != null) {
                            dateAttended.setCellValue(referral.getDateAttended());
                            dateAttended.setCellStyle(cellStyle);
                        } else {
                            dateAttended.setCellValue("");
                        }
                        Cell actionTaken = legalReferredRow.createCell(++count);
                        actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                        Cell hivReq = legalReferredRow.createCell(++count);
                        hivReq.setCellValue(referred.toString());
                    }
                }

            }

            // add legal services provided
            Sheet legalProvidedDetails = workbook.createSheet("Legal_Services_Provided");
            int legalProvidedRowNum = 0;
            Row legalProvidedRow = legalProvidedDetails.createRow(legalProvidedRowNum++);
            int legalProvidedCellNum = 0;
            for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                Cell cell = legalProvidedRow.createCell(legalProvidedCellNum++);
                cell.setCellValue(title);
            }
            for (Referral referral : referrals) {
                if (!referral.getLegalAvailed().isEmpty()) {
                    for (ServicesReferred referred : referral.getLegalAvailed()) {
                        int count = 0;
                        legalProvidedRow = legalProvidedDetails.createRow(legalProvidedRowNum++);
                        Cell id = legalProvidedRow.createCell(count);
                        id.setCellValue(referral.getPatient().getPatientNumber());
                        Cell patientName = legalProvidedRow.createCell(++count);
                        patientName.setCellValue(referral.getPatient().getName());
                        Cell dateOfBirth = legalProvidedRow.createCell(++count);
                        dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                        dateOfBirth.setCellStyle(cellStyle);
                        Cell age = legalProvidedRow.createCell(++count);
                        age.setCellValue(referral.getPatient().getAge());
                        Cell sex = legalProvidedRow.createCell(++count);
                        sex.setCellValue(referral.getPatient().getGender().getName());
                        Cell province = legalProvidedRow.createCell(++count);
                        province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                        Cell district = legalProvidedRow.createCell(++count);
                        district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                        Cell primaryClinic = legalProvidedRow.createCell(++count);
                        primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                        Cell referralDate = legalProvidedRow.createCell(++count);
                        referralDate.setCellValue(referral.getReferralDate());
                        referralDate.setCellStyle(cellStyle);
                        Cell expectedVisitDate = legalProvidedRow.createCell(++count);
                        if (referral.getExpectedVisitDate() != null) {
                            expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                            expectedVisitDate.setCellStyle(cellStyle);
                        } else {
                            expectedVisitDate.setCellValue("");
                        }
                        Cell organisation = legalProvidedRow.createCell(++count);
                        organisation.setCellValue(referral.getOrganisation());
                        Cell designation = legalProvidedRow.createCell(++count);
                        designation.setCellValue(referral.getDesignation());
                        Cell attendingOfficer = legalProvidedRow.createCell(++count);
                        attendingOfficer.setCellValue(referral.getAttendingOfficer());
                        Cell dateAttended = legalProvidedRow.createCell(++count);
                        if (referral.getDateAttended() != null) {
                            dateAttended.setCellValue(referral.getDateAttended());
                            dateAttended.setCellStyle(cellStyle);
                        } else {
                            dateAttended.setCellValue("");
                        }
                        Cell actionTaken = legalProvidedRow.createCell(++count);
                        actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                        Cell hivReq = legalProvidedRow.createCell(++count);
                        hivReq.setCellValue(referred.toString());
                    }
                }

            }

            // add patient dependants
            Sheet dependantDetails = workbook.createSheet("Patient_Dependants");
            int dependantRowNum = 0;
            Row dependantRow = dependantDetails.createRow(dependantRowNum++);
            int dependantCellNum = 0;
            for (String title : DatabaseHeader.DEPENDANT_HEADER) {
                Cell cell = dependantRow.createCell(dependantCellNum++);
                cell.setCellValue(title);
            }
            for (Dependent dependent : dependents) {
                int count = 0;
                dependantRow = dependantDetails.createRow(dependantRowNum++);
                Cell id = dependantRow.createCell(count);
                id.setCellValue(dependent.getPatient().getPatientNumber());
                Cell patientName = dependantRow.createCell(++count);
                patientName.setCellValue(dependent.getPatient().getName());
                Cell patientDateOfBirth = dependantRow.createCell(++count);
                patientDateOfBirth.setCellValue(dependent.getPatient().getDateOfBirth());
                patientDateOfBirth.setCellStyle(cellStyle);
                Cell age = dependantRow.createCell(++count);
                age.setCellValue(dependent.getPatient().getAge());
                Cell sex = dependantRow.createCell(++count);
                sex.setCellValue(dependent.getPatient().getGender().getName());
                Cell dependantName = dependantRow.createCell(++count);
                dependantName.setCellValue(dependent.getName());
                Cell province = dependantRow.createCell(++count);
                province.setCellValue(dependent.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                Cell district = dependantRow.createCell(++count);
                district.setCellValue(dependent.getPatient().getPrimaryClinic().getDistrict().getName());
                Cell primaryClinic = dependantRow.createCell(++count);
                primaryClinic.setCellValue(dependent.getPatient().getPrimaryClinic().getName());
                Cell gender = dependantRow.createCell(++count);
                gender.setCellValue(dependent.getGender().getName());
                Cell dateOfBirth = dependantRow.createCell(++count);
                dateOfBirth.setCellValue(dependent.getDateOfBirth());
                dateOfBirth.setCellStyle(cellStyle);
                Cell hivStatus = dependantRow.createCell(++count);
                hivStatus.setCellValue(dependent.getHivStatus() != null ? dependent.getHivStatus().getName() : "");
            }
            // add patient opportunistic infections
            Sheet opportunisticInfectionDetails = workbook.createSheet("Patient_Opportunistic_Infections");
            int opportunisticInfectionRowNum = 0;
            Row opportunisticInfectionRow = opportunisticInfectionDetails.createRow(opportunisticInfectionRowNum++);
            int opportunisticInfectionCellNum = 0;
            for (String title : DatabaseHeader.OPPORTUNISTIC_INFECTION_HEADER) {
                Cell cell = opportunisticInfectionRow.createCell(opportunisticInfectionCellNum++);
                cell.setCellValue(title);
            }
            for (ChronicInfectionItem chronicInfectionItem : chronicInfectionItems) {
                int count = 0;
                opportunisticInfectionRow = opportunisticInfectionDetails.createRow(opportunisticInfectionRowNum++);
                Cell id = opportunisticInfectionRow.createCell(count);
                id.setCellValue(chronicInfectionItem.getPatient().getPatientNumber());
                Cell patientName = opportunisticInfectionRow.createCell(++count);
                Cell dateOfBirth = opportunisticInfectionRow.createCell(++count);
                dateOfBirth.setCellValue(chronicInfectionItem.getPatient().getDateOfBirth());
                dateOfBirth.setCellStyle(cellStyle);
                Cell age = opportunisticInfectionRow.createCell(++count);
                age.setCellValue(chronicInfectionItem.getPatient().getAge());
                Cell sex = opportunisticInfectionRow.createCell(++count);
                sex.setCellValue(chronicInfectionItem.getPatient().getGender().getName());
                Cell province = opportunisticInfectionRow.createCell(++count);
                province.setCellValue(chronicInfectionItem.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                Cell district = opportunisticInfectionRow.createCell(++count);
                district.setCellValue(chronicInfectionItem.getPatient().getPrimaryClinic().getDistrict().getName());
                Cell primaryClinic = opportunisticInfectionRow.createCell(++count);
                primaryClinic.setCellValue(chronicInfectionItem.getPatient().getPrimaryClinic().getName());
                patientName.setCellValue(chronicInfectionItem.getPatient().getName());
                Cell infection = opportunisticInfectionRow.createCell(++count);
                infection.setCellValue(chronicInfectionItem.getChronicInfection().getName());
                Cell dateDiagnosed = opportunisticInfectionRow.createCell(++count);
                if (chronicInfectionItem.getInfectionDate() != null) {
                    dateDiagnosed.setCellValue(chronicInfectionItem.getInfectionDate());
                    dateDiagnosed.setCellStyle(cellStyle);
                } else {
                    dateDiagnosed.setCellValue("");
                }
                Cell medication = opportunisticInfectionRow.createCell(++count);
                medication.setCellValue(chronicInfectionItem.getMedication());
                Cell currentStatus = opportunisticInfectionRow.createCell(++count);
                currentStatus.setCellValue(chronicInfectionItem.getCurrentStatus() != null ? chronicInfectionItem.getCurrentStatus().getName() : "");
            }
            // add patient hiv-coinfections
            Sheet hivCoInfectionDetails = workbook.createSheet("Patient_HIV_CO_Infections");
            int hivCoInfectionRowNum = 0;
            Row hivCoInfectionRow = hivCoInfectionDetails.createRow(hivCoInfectionRowNum++);
            int hivCoInfectionCellNum = 0;
            for (String title : DatabaseHeader.HIV_CO_INFECTION_HEADER) {
                Cell cell = hivCoInfectionRow.createCell(hivCoInfectionCellNum++);
                cell.setCellValue(title);
            }
            for (HivConInfectionItem hivConInfectionItem : hivConInfectionItems) {
                int count = 0;
                hivCoInfectionRow = hivCoInfectionDetails.createRow(hivCoInfectionRowNum++);
                Cell id = hivCoInfectionRow.createCell(count);
                id.setCellValue(hivConInfectionItem.getPatient().getPatientNumber());
                Cell patientName = hivCoInfectionRow.createCell(++count);
                patientName.setCellValue(hivConInfectionItem.getPatient().getName());
                Cell dateOfBirth = hivCoInfectionRow.createCell(++count);
                dateOfBirth.setCellValue(hivConInfectionItem.getPatient().getDateOfBirth());
                dateOfBirth.setCellStyle(cellStyle);
                Cell age = hivCoInfectionRow.createCell(++count);
                age.setCellValue(hivConInfectionItem.getPatient().getAge());
                Cell sex = hivCoInfectionRow.createCell(++count);
                sex.setCellValue(hivConInfectionItem.getPatient().getGender().getName());
                Cell province = hivCoInfectionRow.createCell(++count);
                province.setCellValue(hivConInfectionItem.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                Cell district = hivCoInfectionRow.createCell(++count);
                district.setCellValue(hivConInfectionItem.getPatient().getPrimaryClinic().getDistrict().getName());
                Cell primaryClinic = hivCoInfectionRow.createCell(++count);
                primaryClinic.setCellValue(hivConInfectionItem.getPatient().getPrimaryClinic().getName());
                Cell infection = hivCoInfectionRow.createCell(++count);
                infection.setCellValue(hivConInfectionItem.getHivCoInfection().getName());
                Cell dateDiagnosed = hivCoInfectionRow.createCell(++count);
                if (hivConInfectionItem.getInfectionDate() != null) {
                    dateDiagnosed.setCellValue(hivConInfectionItem.getInfectionDate());
                    dateDiagnosed.setCellStyle(cellStyle);
                } else {
                    dateDiagnosed.setCellValue("");
                }
                Cell medication = hivCoInfectionRow.createCell(++count);
                medication.setCellValue(hivConInfectionItem.getMedication());
                Cell resolution = hivCoInfectionRow.createCell(++count);
                resolution.setCellValue(hivConInfectionItem.getResolution());
            }
            // add patient mental health
            Sheet mentalHealthDetails = workbook.createSheet("Patient_Mental_Health");
            int mentalHealthRowNum = 0;
            Row mentalHealthRow = mentalHealthDetails.createRow(mentalHealthRowNum++);
            int mentalHealthCellNum = 0;
            for (String title : DatabaseHeader.MENTAL_HIST_HEADER) {
                Cell cell = mentalHealthRow.createCell(mentalHealthCellNum++);
                cell.setCellValue(title);
            }
            for (MentalHealthItem mentalHealthItem : mentalHealthItems) {
                int count = 0;
                mentalHealthRow = mentalHealthDetails.createRow(mentalHealthRowNum++);
                Cell id = mentalHealthRow.createCell(count);
                id.setCellValue(mentalHealthItem.getPatient().getPatientNumber());
                Cell patientName = mentalHealthRow.createCell(++count);
                Cell dateOfBirth = mentalHealthRow.createCell(++count);
                dateOfBirth.setCellValue(mentalHealthItem.getPatient().getDateOfBirth());
                dateOfBirth.setCellStyle(cellStyle);
                Cell age = mentalHealthRow.createCell(++count);
                age.setCellValue(mentalHealthItem.getPatient().getAge());
                Cell sex = mentalHealthRow.createCell(++count);
                sex.setCellValue(mentalHealthItem.getPatient().getGender().getName());
                patientName.setCellValue(mentalHealthItem.getPatient().getName());
                Cell province = mentalHealthRow.createCell(++count);
                province.setCellValue(mentalHealthItem.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                Cell district = mentalHealthRow.createCell(++count);
                district.setCellValue(mentalHealthItem.getPatient().getPrimaryClinic().getDistrict().getName());
                Cell primaryClinic = mentalHealthRow.createCell(++count);
                primaryClinic.setCellValue(mentalHealthItem.getPatient().getPrimaryClinic().getName());
                Cell mentalHealth = mentalHealthRow.createCell(++count);
                mentalHealth.setCellValue(mentalHealthItem.getMentalHealth().getName());
                Cell past = mentalHealthRow.createCell(++count);
                past.setCellValue(mentalHealthItem.getPast());
                Cell current = mentalHealthRow.createCell(++count);
                current.setCellValue(mentalHealthItem.getCurrent());
                Cell receiveProfessionalHelp = mentalHealthRow.createCell(++count);
                receiveProfessionalHelp.setCellValue(
                        mentalHealthItem.getReceivedProfessionalHelp() != null
                                ? mentalHealthItem.getReceivedProfessionalHelp().getName() : ""
                );
                Cell helpStartDate = mentalHealthRow.createCell(++count);
                if (mentalHealthItem.getProfHelpStart() != null) {
                    helpStartDate.setCellValue(mentalHealthItem.getProfHelpStart());
                    helpStartDate.setCellStyle(cellStyle);
                } else {
                    helpStartDate.setCellValue("");
                }
                Cell helpEndDate = mentalHealthRow.createCell(++count);
                if (mentalHealthItem.getProfHelpEnd() != null) {
                    helpEndDate.setCellValue(mentalHealthItem.getProfHelpEnd());
                    helpEndDate.setCellStyle(cellStyle);
                } else {
                    helpEndDate.setCellValue("");
                }
                Cell medication = mentalHealthRow.createCell(++count);
                medication.setCellValue(mentalHealthItem.getMedication());

                Cell hosp = mentalHealthRow.createCell(++count);
                hosp.setCellValue(
                        mentalHealthItem.getBeenHospitalized() != null
                                ? mentalHealthItem.getBeenHospitalized().getName() : ""
                );
                Cell desc = mentalHealthRow.createCell(++count);
                desc.setCellValue(mentalHealthItem.getMentalHistText());
            }
            // add patient obstetrics
            Sheet obsDetails = workbook.createSheet("Patient_Obstetric_Hist");
            int obsRowNum = 0;
            Row obsRow = obsDetails.createRow(obsRowNum++);
            int obsCellNum = 0;
            for (String title : DatabaseHeader.OBSTERIC_HIST_HEADER) {
                Cell cell = obsRow.createCell(obsCellNum++);
                cell.setCellValue(title);
            }
            for (ObstercHist obstercHist : obstercHists) {
                int count = 0;
                obsRow = obsDetails.createRow(obsRowNum++);
                Cell id = obsRow.createCell(count);
                id.setCellValue(obstercHist.getPatient().getPatientNumber());
                Cell patientName = obsRow.createCell(++count);
                patientName.setCellValue(obstercHist.getPatient().getName());
                Cell dateOfBirth = obsRow.createCell(++count);
                dateOfBirth.setCellValue(obstercHist.getPatient().getDateOfBirth());
                dateOfBirth.setCellStyle(cellStyle);
                Cell age = obsRow.createCell(++count);
                age.setCellValue(obstercHist.getPatient().getAge());
                Cell sex = obsRow.createCell(++count);
                sex.setCellValue(obstercHist.getPatient().getGender().getName());
                Cell province = obsRow.createCell(++count);
                province.setCellValue(obstercHist.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                Cell district = obsRow.createCell(++count);
                district.setCellValue(obstercHist.getPatient().getPrimaryClinic().getDistrict().getName());
                Cell primaryClinic = obsRow.createCell(++count);
                primaryClinic.setCellValue(obstercHist.getPatient().getPrimaryClinic().getName());
                Cell liveWith = obsRow.createCell(++count);
                liveWith.setCellValue(obstercHist.getPregnant().getName());
                Cell breatFeeding = obsRow.createCell(++count);
                breatFeeding.setCellValue(obstercHist.getBreafFeedingCurrent() != null ? obstercHist.getBreafFeedingCurrent().getName() : "");
                Cell currentPreg = obsRow.createCell(++count);
                currentPreg.setCellValue(
                        obstercHist.getPregCurrent() != null
                                ? obstercHist.getPregCurrent().getName() : ""
                );
                Cell numAncVisit = obsRow.createCell(++count);
                numAncVisit.setCellValue(
                        obstercHist.getNumberOfANCVisit() != null
                                ? obstercHist.getNumberOfANCVisit().getName() : ""
                );
                Cell gestAge = obsRow.createCell(++count);
                gestAge.setCellValue(
                        obstercHist.getGestationalAge() != null
                                ? obstercHist.getGestationalAge().getName() : ""
                );
                Cell artStarted = obsRow.createCell(++count);
                artStarted.setCellValue(
                        obstercHist.getArtStarted() != null
                                ? obstercHist.getArtStarted().getName() : ""
                );
                Cell numChildren = obsRow.createCell(++count);
                numChildren.setCellValue(obstercHist.getChildren() != null ? obstercHist.getChildren() : 0);
            }
            // add patient social history
            Sheet socialHistDetails = workbook.createSheet("Patient_Social_Hist");
            int socialHistRowNum = 0;
            Row socialHistRow = socialHistDetails.createRow(socialHistRowNum++);
            int socialHistCellNum = 0;
            for (String title : DatabaseHeader.SOCIAL_HISTORY_HEADER) {
                Cell cell = socialHistRow.createCell(socialHistCellNum++);
                cell.setCellValue(title);
            }
            for (SocialHist socialHist : socialHists) {
                int count = 0;
                socialHistRow = socialHistDetails.createRow(socialHistRowNum++);
                Cell id = socialHistRow.createCell(count);
                id.setCellValue(socialHist.getPatient().getPatientNumber());
                Cell patientName = socialHistRow.createCell(++count);
                Cell dateOfBirth = socialHistRow.createCell(++count);
                dateOfBirth.setCellValue(socialHist.getPatient().getDateOfBirth());
                dateOfBirth.setCellStyle(cellStyle);
                Cell age = socialHistRow.createCell(++count);
                age.setCellValue(socialHist.getPatient().getAge());
                Cell sex = socialHistRow.createCell(++count);
                sex.setCellValue(socialHist.getPatient().getGender().getName());
                patientName.setCellValue(socialHist.getPatient().getName());
                Cell province = socialHistRow.createCell(++count);
                province.setCellValue(socialHist.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                Cell district = socialHistRow.createCell(++count);
                district.setCellValue(socialHist.getPatient().getPrimaryClinic().getDistrict().getName());
                Cell primaryClinic = socialHistRow.createCell(++count);
                primaryClinic.setCellValue(socialHist.getPatient().getPrimaryClinic().getName());
                Cell liveWith = socialHistRow.createCell(++count);
                liveWith.setCellValue(socialHist.getLiveWith());
                Cell relationship = socialHistRow.createCell(++count);
                relationship.setCellValue(
                        socialHist.getRelationship() != null
                                ? socialHist.getRelationship().getName() : ""
                );
                Cell abused = socialHistRow.createCell(++count);
                abused.setCellValue(
                        socialHist.getAbuse() != null ? socialHist.getAbuse().getName() : ""
                );
                Cell disclosed = socialHistRow.createCell(++count);
                disclosed.setCellValue(
                        socialHist.getDosclosure() != null ? socialHist.getDosclosure().getName() : ""
                );
                Cell feelSafe = socialHistRow.createCell(++count);
                feelSafe.setCellValue(
                        socialHist.getFeelSafe() != null ? socialHist.getFeelSafe().getName() : ""
                );
                Cell abuseType = socialHistRow.createCell(++count);
                abuseType.setCellValue((socialHist.getAbuseTypes() != null && !socialHist.getAbuseTypes().isEmpty()) ? socialHist.getAbuseTypes().toString() : "");
                Cell abuseOutcome = socialHistRow.createCell(++count);
                abuseOutcome.setCellValue(socialHist.getAbuseOutcome() != null ? socialHist.getAbuseOutcome().getName() : "");

            }
            // add patient substance use history
            Sheet substanceUseDetails = workbook.createSheet("Patient_Substace_Use_Hist");
            int substanceUseRowNum = 0;
            Row substanceUseRow = substanceUseDetails.createRow(substanceUseRowNum++);
            int substanceUseCellNum = 0;
            for (String title : DatabaseHeader.SUBSTANCE_USE_HEADER) {
                Cell cell = substanceUseRow.createCell(substanceUseCellNum++);
                cell.setCellValue(title);
            }
            for (SubstanceItem substanceItem : substanceItems) {
                int count = 0;
                substanceUseRow = substanceUseDetails.createRow(substanceUseRowNum++);
                Cell id = substanceUseRow.createCell(count);
                id.setCellValue(substanceItem.getPatient().getPatientNumber());
                Cell patientName = substanceUseRow.createCell(++count);
                patientName.setCellValue(substanceItem.getPatient().getName());
                Cell dateOfBirth = substanceUseRow.createCell(++count);
                dateOfBirth.setCellValue(substanceItem.getPatient().getDateOfBirth());
                dateOfBirth.setCellStyle(cellStyle);
                Cell age = substanceUseRow.createCell(++count);
                age.setCellValue(substanceItem.getPatient().getAge());
                Cell sex = substanceUseRow.createCell(++count);
                sex.setCellValue(substanceItem.getPatient().getGender().getName());
                Cell province = substanceUseRow.createCell(++count);
                province.setCellValue(substanceItem.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                Cell district = substanceUseRow.createCell(++count);
                district.setCellValue(substanceItem.getPatient().getPrimaryClinic().getDistrict().getName());
                Cell primaryClinic = substanceUseRow.createCell(++count);
                primaryClinic.setCellValue(substanceItem.getPatient().getPrimaryClinic().getName());
                Cell substance = substanceUseRow.createCell(++count);
                substance.setCellValue(substanceItem.getSubstance().getName());
                Cell curent = substanceUseRow.createCell(++count);
                curent.setCellValue(substanceItem.getCurrent() != null ? substanceItem.getCurrent().getName() : "");
                Cell past = substanceUseRow.createCell(++count);
                past.setCellValue(substanceItem.getPast() != null ? substanceItem.getPast().getName() : "");
                Cell startDate = substanceUseRow.createCell(++count);
                if (substanceItem.getStartDate() != null) {
                    startDate.setCellValue(substanceItem.getStartDate());
                    startDate.setCellStyle(cellStyle);
                } else {
                    startDate.setCellValue("");
                }
                Cell endDate = substanceUseRow.createCell(++count);
                if (substanceItem.getEndDate() != null) {
                    endDate.setCellValue(substanceItem.getEndDate());
                    endDate.setCellStyle(cellStyle);
                } else {
                    endDate.setCellValue("");
                }

                Cell drugIntervention = substanceUseRow.createCell(++count);
                drugIntervention.setCellValue(substanceItem.getDrugIntervention() != null ? substanceItem.getDrugIntervention().getName() : "");
                Cell duration = substanceUseRow.createCell(++count);
                duration.setCellValue(substanceItem.getDuration());
            }

            /* start here **/
            // add patient dependants
            Sheet cd4CountDetails = workbook.createSheet("Client_Lab_RESULTS");
            int cd4RowNum = 0;
            Row cd4Row = cd4CountDetails.createRow(cd4RowNum++);
            int cd4CellNum = 0;
            for (String title : DatabaseHeader.CD4_COUNT_HEADER) {
                Cell cell = cd4Row.createCell(cd4CellNum++);
                cell.setCellValue(title);
            }
            for (InvestigationTest cd4Count : investigationTests) {
                int count = 0;
                cd4Row = cd4CountDetails.createRow(cd4RowNum++);
                Cell id = cd4Row.createCell(count);
                id.setCellValue(cd4Count.getPatient().getPatientNumber());
                Cell patientName = cd4Row.createCell(++count);
                patientName.setCellValue(cd4Count.getPatient().getName());
                Cell dateOfBirth = cd4Row.createCell(++count);
                dateOfBirth.setCellValue(cd4Count.getPatient().getDateOfBirth());
                dateOfBirth.setCellStyle(cellStyle);
                Cell age = cd4Row.createCell(++count);
                age.setCellValue(cd4Count.getPatient().getAge());
                Cell sex = cd4Row.createCell(++count);
                sex.setCellValue(cd4Count.getPatient().getGender().getName());
                Cell province = cd4Row.createCell(++count);
                province.setCellValue(cd4Count.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                Cell district = cd4Row.createCell(++count);
                district.setCellValue(cd4Count.getPatient().getPrimaryClinic().getDistrict().getName());
                Cell primaryClinic = cd4Row.createCell(++count);
                primaryClinic.setCellValue(cd4Count.getPatient().getPrimaryClinic().getName());
                Cell testType = cd4Row.createCell(++count);
                testType.setCellValue(cd4Count.getTestType().getName());
                Cell dateTaken = cd4Row.createCell(++count);
                if (cd4Count.getDateTaken() != null) {
                    dateTaken.setCellValue(cd4Count.getDateTaken());
                    dateTaken.setCellStyle(cellStyle);
                } else {
                    dateTaken.setCellValue("");
                }
                Cell cd4Load = cd4Row.createCell(++count);
                cd4Load.setCellValue(cd4Count.getResult() != null ? cd4Count.getResult() : 0);
                Cell source = cd4Row.createCell(++count);
                source.setCellValue(cd4Count.getSource() != null ? cd4Count.getSource().getName() : "");
                Cell nextLabDate = cd4Row.createCell(++count);
                if (cd4Count.getNextTestDate() != null) {
                    nextLabDate.setCellValue(cd4Count.getNextTestDate());
                    nextLabDate.setCellStyle(cellStyle);
                } else {
                    nextLabDate.setCellValue("");
                }
                Cell suppressionStatus = cd4Row.createCell(++count);
                if (cd4Count.getViralLoadSuppressionStatus() != null) {
                    suppressionStatus.setCellValue(cd4Count.getViralLoadSuppressionStatus());
                    suppressionStatus.setCellStyle(cellStyle);
                } else {
                    suppressionStatus.setCellValue("");
                }

                Cell resultsTaken = cd4Row.createCell(++count);
                if (cd4Count.getResultTaken() != null) {
                    resultsTaken.setCellValue(cd4Count.getResultTaken().getName());
                    resultsTaken.setCellStyle(cellStyle);
                } else {
                    resultsTaken.setCellValue("");
                }

                Cell tnd = cd4Row.createCell(++count);
                if (cd4Count.getTnd() != null) {
                    tnd.setCellValue(cd4Count.getTnd());
                    tnd.setCellStyle(cellStyle);
                } else {
                    tnd.setCellValue("");
                }

                Cell recordSource = cd4Row.createCell(++count);
                if (cd4Count.getRecordSource() != null) {
                    recordSource.setCellValue(cd4Count.getRecordSource().getName());
                    recordSource.setCellStyle(cellStyle);
                } else {
                    recordSource.setCellValue("");
                }






            }



            // arv history
            Sheet arvHistDetails = workbook.createSheet("Patient_ARV_HISTORY");
            int arvHistRowNum = 0;
            Row arvHistRow = arvHistDetails.createRow(arvHistRowNum++);
            int arvHistCellNum = 0;
            for (String title : DatabaseHeader.ARV_HISTORY_HEADER) {
                Cell cell = arvHistRow.createCell(arvHistCellNum++);
                cell.setCellValue(title);
            }
            for (ArvHist arvHist : arvHists) {
                int count = 0;
                arvHistRow = arvHistDetails.createRow(arvHistRowNum++);
                Cell id = arvHistRow.createCell(count);
                id.setCellValue(arvHist.getPatient().getPatientNumber());
                Cell patientName = arvHistRow.createCell(++count);
                patientName.setCellValue(arvHist.getPatient().getName());
                Cell dateOfBirth = arvHistRow.createCell(++count);
                dateOfBirth.setCellValue(arvHist.getPatient().getDateOfBirth());
                dateOfBirth.setCellStyle(cellStyle);
                Cell age = arvHistRow.createCell(++count);
                age.setCellValue(arvHist.getPatient().getAge());
                Cell sex = arvHistRow.createCell(++count);
                sex.setCellValue(arvHist.getPatient().getGender().getName());
                Cell province = arvHistRow.createCell(++count);
                province.setCellValue(arvHist.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                Cell district = arvHistRow.createCell(++count);
                district.setCellValue(arvHist.getPatient().getPrimaryClinic().getDistrict().getName());
                Cell primaryClinic = arvHistRow.createCell(++count);
                primaryClinic.setCellValue(arvHist.getPatient().getPrimaryClinic().getName());

                Cell arvHistMedicine = arvHistRow.createCell(++count);
                arvHistMedicine.setCellValue(arvHist.getMedicines());
                Cell startDate = arvHistRow.createCell(++count);
                if (arvHist.getStartDate() != null) {
                    startDate.setCellValue(arvHist.getStartDate());
                    startDate.setCellStyle(cellStyle);
                } else {
                    startDate.setCellValue("");
                }
                Cell endDate = arvHistRow.createCell(++count);
                if (arvHist.getEndDate() != null) {
                    endDate.setCellValue(arvHist.getEndDate());
                    endDate.setCellStyle(cellStyle);
                } else {
                    endDate.setCellValue("");
                }
            }
            // add mortality here
            Sheet mortalityDetails = workbook.createSheet("Patient_Mortality");
            int mortalityRowNum = 0;
            Row mortalityRow = mortalityDetails.createRow(mortalityRowNum++);
            int mortalityCellNum = 0;
            for (String title : DatabaseHeader.MORTALITY_HEADER) {
                Cell cell = mortalityRow.createCell(mortalityCellNum++);
                cell.setCellValue(title);
            }

            for (Mortality mortality : mortalitys) {
                int count = 0;
                mortalityRow = mortalityDetails.createRow(mortalityRowNum++);
                Cell id = mortalityRow.createCell(count);
                id.setCellValue(mortality.getPatient().getPatientNumber());
                Cell patientName = mortalityRow.createCell(++count);
                patientName.setCellValue(mortality.getPatient().getName());
                Cell dateOfBirth = mortalityRow.createCell(++count);
                dateOfBirth.setCellValue(mortality.getPatient().getDateOfBirth());
                dateOfBirth.setCellStyle(cellStyle);
                Cell age = mortalityRow.createCell(++count);
                age.setCellValue(mortality.getPatient().getAge());
                Cell sex = mortalityRow.createCell(++count);
                sex.setCellValue(mortality.getPatient().getGender().getName());
                Cell province = mortalityRow.createCell(++count);
                province.setCellValue(mortality.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                Cell district = mortalityRow.createCell(++count);
                district.setCellValue(mortality.getPatient().getPrimaryClinic().getDistrict().getName());
                Cell primaryClinic = mortalityRow.createCell(++count);
                primaryClinic.setCellValue(mortality.getPatient().getPrimaryClinic().getName());

                Cell dateOfDeath = mortalityRow.createCell(++count);
                if (mortality.getDateOfDeath() != null) {
                    dateOfDeath.setCellValue(mortality.getDateOfDeath());
                    dateOfDeath.setCellStyle(cellStyle);
                } else {
                    dateOfDeath.setCellValue("");
                }
                Cell causeOfDeath = mortalityRow.createCell(++count);
                causeOfDeath.setCellValue(mortality.getCauseOfDeath() != null ? mortality.getCauseOfDeath().getName() : "");
                Cell causeOfDeathDetails = mortalityRow.createCell(++count);
                causeOfDeathDetails.setCellValue(mortality.getCauseOfDeathDetails());
                Cell receivingEnhancedCare = mortalityRow.createCell(++count);
                receivingEnhancedCare.setCellValue(mortality.getReceivingEnhancedCare() != null ? mortality.getReceivingEnhancedCare().getName() : "");
                Cell datePutOnEnhancedCare = mortalityRow.createCell(++count);
                if (mortality.getDatePutOnEnhancedCare() != null) {
                    datePutOnEnhancedCare.setCellValue(mortality.getDatePutOnEnhancedCare());
                    datePutOnEnhancedCare.setCellStyle(cellStyle);
                } else {
                    datePutOnEnhancedCare.setCellValue("");
                }
                Cell caseBackground = mortalityRow.createCell(++count);
                caseBackground.setCellValue(mortality.getCaseBackground());
                Cell careProvided = mortalityRow.createCell(++count);
                careProvided.setCellValue(mortality.getCareProvided());
                Cell home = mortalityRow.createCell(++count);
                home.setCellValue(mortality.getHome());

                Cell beneficiary = mortalityRow.createCell(++count);
                beneficiary.setCellValue(mortality.getBeneficiary());
                Cell facility = mortalityRow.createCell(++count);
                facility.setCellValue(mortality.getFacility());
                Cell cats = mortalityRow.createCell(++count);
                cats.setCellValue(mortality.getCats());
                Cell zm = mortalityRow.createCell(++count);
                zm.setCellValue(mortality.getZm());
                Cell other = mortalityRow.createCell(++count);
                other.setCellValue(mortality.getOther());
                Cell contactWithZM = mortalityRow.createCell(++count);
                contactWithZM.setCellValue(mortality.getContactWithZM() != null ? mortality.getContactWithZM().getName() : "");
                Cell dateOfContactWithZim = mortalityRow.createCell(++count);
                if (mortality.getDateOfContactWithZim() != null) {
                    dateOfContactWithZim.setCellValue(mortality.getDateOfContactWithZim());
                    dateOfContactWithZim.setCellStyle(cellStyle);
                } else {
                    dateOfContactWithZim.setCellValue("");
                }
                Cell descriptionOfCase = mortalityRow.createCell(++count);
                descriptionOfCase.setCellValue(mortality.getDescriptionOfCase());
                Cell learningPoints = mortalityRow.createCell(++count);
                learningPoints.setCellValue(mortality.getLearningPoints());
                Cell actionPlan = mortalityRow.createCell(++count);
                actionPlan.setCellValue(mortality.getActionPlan());

            }

            // tb Ipt here
            Sheet tbIptDetails = workbook.createSheet("Patient_TBIPT");
            int tbIptRowNum = 0;
            Row tbIptRow = tbIptDetails.createRow(tbIptRowNum++);
            int tbIptCellNum = 0;
            for (String title : DatabaseHeader.TB_IPT_HEADER) {
                Cell cell = tbIptRow.createCell(tbIptCellNum++);
                cell.setCellValue(title);
            }

            for (TbIpt tbIpt : tbIpts) {
                int count = 0;
                tbIptRow = tbIptDetails.createRow(tbIptRowNum++);
                Cell id = tbIptRow.createCell(count);
                id.setCellValue(tbIpt.getPatient().getPatientNumber());
                Cell patientName = tbIptRow.createCell(++count);
                patientName.setCellValue(tbIpt.getPatient().getName());
                Cell dateOfBirth = tbIptRow.createCell(++count);
                dateOfBirth.setCellValue(tbIpt.getPatient().getDateOfBirth());
                dateOfBirth.setCellStyle(cellStyle);
                Cell age = tbIptRow.createCell(++count);
                age.setCellValue(tbIpt.getPatient().getAge());
                Cell sex = tbIptRow.createCell(++count);
                sex.setCellValue(tbIpt.getPatient().getGender().getName());
                Cell province = tbIptRow.createCell(++count);
                province.setCellValue(tbIpt.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                Cell district = tbIptRow.createCell(++count);
                district.setCellValue(tbIpt.getPatient().getPrimaryClinic().getDistrict().getName());
                Cell primaryClinic = tbIptRow.createCell(++count);
                primaryClinic.setCellValue(tbIpt.getPatient().getPrimaryClinic().getName());

                Cell screenedForTb = tbIptRow.createCell(++count);
                screenedForTb.setCellValue(tbIpt.getScreenedForTb() != null ? tbIpt.getScreenedForTb().getName() : "");
                Cell dateScreened = tbIptRow.createCell(++count);
                if (tbIpt.getDateScreened() != null) {
                    dateScreened.setCellValue(tbIpt.getDateScreened());
                    dateScreened.setCellStyle(cellStyle);
                } else {
                    dateScreened.setCellValue("");
                }
                Cell tbSymptoms = tbIptRow.createCell(++count);
                tbSymptoms.setCellValue((tbIpt.getTbSymptoms() != null && tbIpt.getTbSymptoms().isEmpty())
                        ? tbIpt.getTbSymptoms().toString() : "");
                Cell identifiedWithTb = tbIptRow.createCell(++count);
                identifiedWithTb.setCellValue(tbIpt.getIdentifiedWithTb() != null ? tbIpt.getIdentifiedWithTb().getName() : "");
                Cell tbIdentificationOutcome = tbIptRow.createCell(++count);
                tbIdentificationOutcome.setCellValue(tbIpt.getTbIdentificationOutcome() != null ? tbIpt.getTbIdentificationOutcome().getName() : "");
                Cell dateStartedTreatment = tbIptRow.createCell(++count);
                if (tbIpt.getDateStartedTreatment() != null) {
                    dateStartedTreatment.setCellValue(tbIpt.getDateStartedTreatment());
                    dateStartedTreatment.setCellStyle(cellStyle);
                } else {
                    dateStartedTreatment.setCellValue("");
                }
                Cell referralForSputum = tbIptRow.createCell(++count);
                referralForSputum.setCellValue(tbIpt.getReferralForSputum());
                Cell tbTreatmentOutcome = tbIptRow.createCell(++count);
                tbTreatmentOutcome.setCellValue(tbIpt.getTbTreatmentOutcome() != null ? tbIpt.getTbTreatmentOutcome().getName() : "");
                Cell referredForIpt = tbIptRow.createCell(++count);
                referredForIpt.setCellValue(tbIpt.getReferredForIpt() != null ? tbIpt.getReferredForIpt().getName() : "");
                Cell onIpt = tbIptRow.createCell(++count);
                onIpt.setCellValue(tbIpt.getOnIpt() != null ? tbIpt.getOnIpt().getName() : "");
                Cell dateStartedIpt = tbIptRow.createCell(++count);
                if (tbIpt.getDateStartedTreatment() != null) {
                    dateStartedIpt.setCellValue(tbIpt.getDateStartedTreatment());
                    dateStartedIpt.setCellStyle(cellStyle);
                } else {
                    dateStartedIpt.setCellValue("");
                }
            }

            // mental health screening
            Sheet mentalHealthScreeningDetails = workbook.createSheet("Patient_Mental_Health_Screening");
            int mentalHealthScreeningRowNum = 0;
            Row mentalHealthScreeningRow = mentalHealthScreeningDetails.createRow(mentalHealthScreeningRowNum++);
            int mentalHealthScreeningCellNum = 0;
            for (String title : DatabaseHeader.MENTAL_HEALTH_SCREENING_HEADER) {
                Cell cell = mentalHealthScreeningRow.createCell(mentalHealthScreeningCellNum++);
                cell.setCellValue(title);
            }

            for (MentalHealthScreening mentalHealthScreening : mentalHealthScreenings) {
                int count = 0;
                mentalHealthScreeningRow = mentalHealthScreeningDetails.createRow(mentalHealthScreeningRowNum++);
                Cell id = mentalHealthScreeningRow.createCell(count);
                id.setCellValue(mentalHealthScreening.getPatient().getPatientNumber());
                Cell patientName = mentalHealthScreeningRow.createCell(++count);
                patientName.setCellValue(mentalHealthScreening.getPatient().getName());
                Cell dateOfBirth = mentalHealthScreeningRow.createCell(++count);
                dateOfBirth.setCellValue(mentalHealthScreening.getPatient().getDateOfBirth());
                dateOfBirth.setCellStyle(cellStyle);
                Cell age = mentalHealthScreeningRow.createCell(++count);
                age.setCellValue(mentalHealthScreening.getPatient().getAge());
                Cell sex = mentalHealthScreeningRow.createCell(++count);
                sex.setCellValue(mentalHealthScreening.getPatient().getGender().getName());
                Cell province = mentalHealthScreeningRow.createCell(++count);
                province.setCellValue(mentalHealthScreening.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                Cell district = mentalHealthScreeningRow.createCell(++count);
                district.setCellValue(mentalHealthScreening.getPatient().getPrimaryClinic().getDistrict().getName());
                Cell primaryClinic = mentalHealthScreeningRow.createCell(++count);
                primaryClinic.setCellValue(mentalHealthScreening.getPatient().getPrimaryClinic().getName());

                Cell screenedForMentalHealth = mentalHealthScreeningRow.createCell(++count);
                screenedForMentalHealth.setCellValue(mentalHealthScreening.getScreenedForMentalHealth() != null
                        ? mentalHealthScreening.getScreenedForMentalHealth().getName() : "");
                Cell dateScreened = mentalHealthScreeningRow.createCell(++count);
                if (mentalHealthScreening.getDateScreened() != null) {
                    dateScreened.setCellValue(mentalHealthScreening.getDateScreened());
                    dateScreened.setCellStyle(cellStyle);
                } else {
                    dateScreened.setCellValue("");
                }
                Cell screening = mentalHealthScreeningRow.createCell(++count);
                screening.setCellValue(mentalHealthScreening.getScreening() != null ? mentalHealthScreening.getScreening().getName() : "");
                Cell risk = mentalHealthScreeningRow.createCell(++count);
                risk.setCellValue(mentalHealthScreening.getRisk() != null ? mentalHealthScreening.getRisk().getName() : "");
                Cell identifiedRisks = mentalHealthScreeningRow.createCell(++count);
                identifiedRisks.setCellValue((mentalHealthScreening.getIdentifiedRisks() != null && !mentalHealthScreening.getIdentifiedRisks().isEmpty())
                        ? mentalHealthScreening.getIdentifiedRisks().toString() : "");
                Cell support = mentalHealthScreeningRow.createCell(++count);
                support.setCellValue(mentalHealthScreening.getSupport() != null
                        ? mentalHealthScreening.getSupport().getName() : "");
                Cell supports = mentalHealthScreeningRow.createCell(++count);
                supports.setCellValue((mentalHealthScreening.getSupports() != null && !mentalHealthScreening.getSupports().isEmpty())
                        ? mentalHealthScreening.getSupports().toString() : "");
                Cell referral = mentalHealthScreeningRow.createCell(++count);
                referral.setCellValue(mentalHealthScreening.getReferral() != null
                        ? mentalHealthScreening.getReferral().getName() : "");
                Cell referrals1 = mentalHealthScreeningRow.createCell(++count);
                referrals1.setCellValue((mentalHealthScreening.getReferrals() != null && !mentalHealthScreening.getReferrals().isEmpty())
                        ? mentalHealthScreening.getReferrals().toString() : "");
                Cell diagnosis = mentalHealthScreeningRow.createCell(++count);
                diagnosis.setCellValue(mentalHealthScreening.getDiagnosis() != null
                        ? mentalHealthScreening.getDiagnosis().getName() : "");
                Cell diagnoses = mentalHealthScreeningRow.createCell(++count);
                diagnoses.setCellValue((mentalHealthScreening.getDiagnoses() != null && !mentalHealthScreening.getDiagnoses().isEmpty())
                        ? mentalHealthScreening.getDiagnoses().toString() : "");
                Cell otherDiagnosis = mentalHealthScreeningRow.createCell(++count);
                otherDiagnosis.setCellValue(mentalHealthScreening.getOtherDiagnosis());
                Cell intervention = mentalHealthScreeningRow.createCell(++count);
                intervention.setCellValue(mentalHealthScreening.getIntervention() != null
                        ? mentalHealthScreening.getIntervention().getName() : "");
                Cell interventions = mentalHealthScreeningRow.createCell(++count);
                interventions.setCellValue((mentalHealthScreening.getInterventions() != null && !mentalHealthScreening.getInterventions().isEmpty())
                        ? mentalHealthScreening.getInterventions().toString() : "");
                Cell otherIntervention = mentalHealthScreeningRow.createCell(++count);
                otherIntervention.setCellValue(mentalHealthScreening.getOtherIntervention());
            }
            /* end here    */

            return workbook;

    }
}
