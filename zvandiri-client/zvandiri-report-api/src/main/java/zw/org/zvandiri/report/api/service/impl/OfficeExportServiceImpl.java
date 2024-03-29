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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.domain.util.TestType;
import zw.org.zvandiri.business.repo.*;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.business.util.Reportutil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.report.api.DatabaseHeader;
import zw.org.zvandiri.report.api.GenericReportModel;
import zw.org.zvandiri.report.api.service.OfficeExportService;
import zw.org.zvandiri.report.api.service.parallel.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author Judge Muzinda
 */
@Repository
public class OfficeExportServiceImpl implements OfficeExportService {

    @Resource
    private DetailedPatientReportService detailedPatientReportService;
    @Autowired
    ContactReportService contactReportService;


    @Resource
    ContactService contactService;
    @Resource
    private ContactRepo contactRepo;
    @Resource
    private InvestigationTestService investigationTestService;
    @Resource
    private MentalHealthScreeningService mentalHealthScreeningService;
    @Resource 
    private ReferralRepo referralRepo;

    @Resource
    InvestigationTestRepo investigationTestRepo;

    @Resource
    MentalHealthScreeningRepo mentalHealthScreeningRepo;

    @Resource
    TbIptRepo tbIptRepo;

    @Resource
    ArvHistRepo arvHistRepo;

    @Resource
    MortalityRepo mortalityRepo;

    @Override
    public XSSFWorkbook exportExcelFile(List<GenericReportModel> XSSFRows, String name) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet XSSFSheet = workbook.createSheet(name);
        int XSSFRowNum = 0;
        for (GenericReportModel model : XSSFRows) {
            XSSFRow XSSFRow = XSSFSheet.createRow(XSSFRowNum++);
            int XSSFCellNum = 0;
            for (String item : model.getRow()) {
                XSSFCell XSSFCell = XSSFRow.createCell(XSSFCellNum++);
                XSSFCell.setCellValue(item);
            }
        }
        return workbook;
    }

    @Override
    public XSSFWorkbook exportExcelXLSXFile(List<GenericReportModel> XSSFRows, String name) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet XSSFSheet = workbook.createSheet(name);
        int XSSFRowNum = 0;
        for (GenericReportModel model : XSSFRows) {
            XSSFRow XSSFRow = XSSFSheet.createRow(XSSFRowNum++);
            int XSSFCellNum = 0;
            for (String item : model.getRow()) {
                XSSFCell XSSFCell = XSSFRow.createCell(XSSFCellNum++);
                XSSFCell.setCellValue(item);
            }
        }
        return workbook;
    }

//    @Override
//    public XSSFWorkbook exportExcelXLSXFile(List<GenericReportModel> XSSFRows, String name) {
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet XSSFSheet = workbook.createSheet(name);
//        ForkJoinPool pool=ForkJoinPool.commonPool();
//        pool.invoke(new OfficeExportTask(XSSFRows,XSSFSheet));
//        return workbook;
//    }

    @Override
    public XWPFDocument exportWordFile(List<GenericReportModel> XSSFRows, String name) {
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraphOne = document.createParagraph();
        XWPFTable table = document.createTable();
        int XSSFRowNum = 0;
//        for (GenericReportModel model : XSSFRows) {
//            XWPFTableXSSFRow tableXSSFRowOne = table.getXSSFRow(XSSFRowNum++);
//            int XSSFCellNum = 0;
//            for (String item : model.getXSSFRow()) {
//                XWPFRun paragraphOneRunOne = paragraphOne.createRun();
//                paragraphOneRunOne.setText(item);
//            }
//
//        }
        return document;
    }

    /*private String printDistricts(List<District> districts){
        return  districts.stream().map(District::getName).collect(Collectors.joining(","));
    }*/

    @Override
    public XSSFWorkbook exportDatabase(String name, SearchDTO dto) {
        try {
            final long start = System.currentTimeMillis();
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFCellStyle XSSFCellStyle = workbook.createCellStyle();
            XSSFCreationHelper createHelper = workbook.getCreationHelper();
            XSSFCellStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
            // Patient details XSSFSheet
            XSSFSheet patientDetails = workbook.createSheet("Patient_Details");
            try {

                int XSSFRowNum = 0;
                XSSFRow header = patientDetails.createRow(XSSFRowNum++);
                int XSSFCellNum = 0;
                for (String title : DatabaseHeader.PATIENT_HEADER) {
                    XSSFCell XSSFCell = header.createCell(XSSFCellNum++);
                    XSSFCell.setCellValue(title);
                }


                dto.setFirstResult(0);
                dto.setPageSize(detailedPatientReportService.getCount(dto).intValue());
                List<String> ids = detailedPatientReportService.getIds(dto);
                ForkJoinPool pool = ForkJoinPool.commonPool();
                List<Patient> patients = pool.invoke(new PatientDatabaseExportTask(ids, detailedPatientReportService));
                //List<Patient> patients=detailedPatientReportService.get(ids);
                final long end = System.currentTimeMillis();
                final long time = end - start;
                System.err.println("User::" + dto.getCurrentUserName() + "==== User Level:" + dto.getUserLevel() + " ==>:: All Patientss (min)::" + (double) (time / 60000));

                List<Contact> contacts = pool.invoke(new PatientContactsTask(patients, dto, contactRepo));
                final long contact_end = System.currentTimeMillis();
                System.err.println("User::" + dto.getCurrentUserName() + "==== User Level:" + dto.getUserLevel() + " ==>:: Collation of DB Contacts Data (min)::" + (double) ((contact_end - end) / 60000));

                List<Referral> referrals = pool.invoke(new PatientReferralsTask(patients, dto, referralRepo));
                final long referral_end = System.currentTimeMillis();
                System.err.println("User::" + dto.getCurrentUserName() + "==== User Level:" + dto.getUserLevel() + " ==> :: Collation of DB Referrals Data (min)::" + (double) ((referral_end - contact_end) / 60000));

                List<InvestigationTest> investigationTests = pool.invoke(new PatientVLsTask(patients, dto, investigationTestRepo));
                final long vls_end = System.currentTimeMillis();
                System.err.println("User::" + dto.getCurrentUserName() + "==== User Level:" + dto.getUserLevel() + " ==> :: Collation of DB VLs Data (min)::" + (double) ((vls_end - referral_end) / 60000));

                List<MentalHealthScreening> mentalHealthScreenings = pool.invoke(new PatientMentalHealthScreeningTask(patients, dto, mentalHealthScreeningRepo));
                final long mhs_end = System.currentTimeMillis();
                System.err.println("User::" + dto.getCurrentUserName() + "==== User Level:" + dto.getUserLevel() + " ==> :: Collation of DB MHs Data (min)::" + (double) ((mhs_end - vls_end) / 60000));

                List<TbIpt> tbIpts = pool.invoke(new PatientTB_TPTTask(patients, dto, tbIptRepo));
                final long tbs_end = System.currentTimeMillis();
                System.err.println("User::" + dto.getCurrentUserName() + "==== User Level:" + dto.getUserLevel() + " ==>:: Collation of DB TBs Data (min)::" + (double) ((tbs_end - mhs_end) / 60000));

                List<ArvHist> arvHists = pool.invoke(new PatientArvHistTask(patients, dto, arvHistRepo));
                final long arv_end = System.currentTimeMillis();
                System.err.println("User::" + dto.getCurrentUserName() + "==== User Level:" + dto.getUserLevel() + " ==> :: Collation of DB ARV_HIST Data (min)::" + (double) ((arv_end - tbs_end) / 60000));

                pool.shutdown();

                final long start_patient = System.currentTimeMillis();
                if (patients != null) {
                    for (Patient patient : patients) {
                        int count = 0;
                        InvestigationTest vlTest = investigationTestService.getLatestTestByTestType(patient, TestType.VIRAL_LOAD);

                        Contact contact = contactService.findLatestContact(patient);

                        header = patientDetails.createRow(XSSFRowNum++);
                        XSSFCell id = header.createCell(count);
                        id.setCellValue(patient.getPatientNumber());
                        XSSFCell patientName = header.createCell(++count);
                        patientName.setCellValue(patient.getName());

                        XSSFCell clientType = header.createCell(++count);
                        clientType.setCellValue(patient.getClientType() != null ? patient.getClientType().getName() : null);

                        XSSFCell artNumber = header.createCell(++count);
                        artNumber.setCellValue(patient.getoINumber());

                        XSSFCell dateOfBirth = header.createCell(++count);
                        dateOfBirth.setCellValue(patient.getDateOfBirth());
                        dateOfBirth.setCellStyle(XSSFCellStyle);
                        XSSFCell age = header.createCell(++count);
                        age.setCellValue(patient.getAge());
                        XSSFCell dateEntered = header.createCell(++count);
                        if (patient.getDateCreated() != null) {
                            dateEntered.setCellValue(patient.getDateCreated());
                            dateEntered.setCellStyle(XSSFCellStyle);
                        } else {
                            dateEntered.setCellValue("");
                        }
                        XSSFCell dateJoined = header.createCell(++count);
                        if (patient.getDateJoined() != null) {
                            dateJoined.setCellValue(patient.getDateJoined());
                            dateJoined.setCellStyle(XSSFCellStyle);
                        } else {
                            dateJoined.setCellValue("");
                        }
                        dateJoined.setCellStyle(XSSFCellStyle);

                        XSSFCell gender = header.createCell(++count);
                        gender.setCellValue(patient.getGender().getName());

                        String addressDetails = patient.getAddress() != null ? patient.getAddress() : " , " + patient.getAddress1() != null ? patient.getAddress1() : "";
                        XSSFCell address = header.createCell(++count);
                        address.setCellValue(addressDetails);

                        XSSFCell mobileNumber = header.createCell(++count);
                        mobileNumber.setCellValue(patient.getMobileNumber());

                        XSSFCell consetToMHelaath = header.createCell(++count);
                        consetToMHelaath.setCellValue(patient.getConsentToMHealth() != null ? patient.getConsentToMHealth().getName() : null);

                        XSSFCell education = header.createCell(++count);
                        education.setCellValue(patient.getEducation() != null ? patient.getEducation().getName() : null);

                        XSSFCell highestEducation = header.createCell(++count);
                        highestEducation.setCellValue(patient.getEducationLevel() != null ? patient.getEducationLevel().getName() : null);

                        XSSFCell ctype = header.createCell(++count);
                        ctype.setCellValue(patient.getClientType() != null ? patient.getClientType().getName() : null);

                        XSSFCell regimen = header.createCell(++count);
                        regimen.setCellValue(patient.getArtRegimen() != null ? patient.getArtRegimen() : null);

                        XSSFCell refer = header.createCell(++count);
                        refer.setCellValue(patient.getReferer() != null ? patient.getReferer().getName() : null);

                        XSSFCell province = header.createCell(++count);
                        province.setCellValue(patient.getPrimaryClinic().getDistrict().getProvince().getName());

                        XSSFCell district = header.createCell(++count);
                        district.setCellValue(patient.getPrimaryClinic().getDistrict().getName());

                        XSSFCell primaryClinic = header.createCell(++count);
                        primaryClinic.setCellValue(patient.getPrimaryClinic().getName());

                        XSSFCell supportGroup = header.createCell(++count);
                        supportGroup.setCellValue(patient.getSupportGroup() != null ? patient.getSupportGroup().getName() : null);

                        XSSFCell dateTested = header.createCell(++count);
                        if (patient.getDateTested() != null) {
                            dateTested.setCellValue(patient.getDateTested());
                            dateTested.setCellStyle(XSSFCellStyle);
                        } else {
                            dateTested.setCellValue("");
                        }

                        XSSFCell hivDisclosureLoc = header.createCell(++count);
                        hivDisclosureLoc.setCellValue(patient.gethIVDisclosureLocation() != null ? patient.gethIVDisclosureLocation().getName() : null);

                        XSSFCell disclosureType = header.createCell(++count);
                        disclosureType.setCellValue(patient.getDisclosureType() != null ? patient.getDisclosureType().getName() : null);

                        XSSFCell isKey = header.createCell(++count);
                        isKey.setCellValue(patient.getIsKeypopulation() != null ? patient.getIsKeypopulation().getName() : null);

                        XSSFCell key = header.createCell(++count);
                        key.setCellValue(patient.getKeyPopulation() != null ? patient.getKeyPopulation().getName() : null);

                        XSSFCell birthCert = header.createCell(++count);
                        birthCert.setCellValue(patient.getHaveBirthCertificate() != null ? patient.getHaveBirthCertificate().getName() : null);

                        XSSFCell hasDisability = header.createCell(++count);
                        hasDisability.setCellValue(patient.getDisability() != null ? patient.getDisability().getName() : null);

                        XSSFCell isCats = header.createCell(++count);
                        isCats.setCellValue(patient.getCat() != null ? patient.getCat().getName() : null);

                        XSSFCell youngMumGroup = header.createCell(++count);
                        youngMumGroup.setCellValue(patient.getYoungMumGroup() != null ? patient.getYoungMumGroup().getName() : null);

                        XSSFCell ymd = header.createCell(++count);
                        ymd.setCellValue(patient.getYoungDadGroup() != null ? patient.getYoungDadGroup().getName() : null);

                        XSSFCell transMode = header.createCell(++count);
                        transMode.setCellValue(patient.getTransmissionMode() != null ? patient.getTransmissionMode().getName() : null);

                        XSSFCell drugRegimen = header.createCell(++count);
                        drugRegimen.setCellValue(patient.getCurrentArvRegimen() != null ? patient.getCurrentArvRegimen() : null);

                        XSSFCell hivStatusKnown = header.createCell(++count);
                        hivStatusKnown.setCellValue(patient.getHivStatusKnown() != null ? patient.getHivStatusKnown().getName() : null);

                        XSSFCell patientStatus = header.createCell(++count);
                        patientStatus.setCellValue(patient.getStatus() != null ? patient.getStatus().getName() : null);

                        XSSFCell contactDate = header.createCell(++count);
                        if (contact != null && contact.getContactDate() != null) {
                            contactDate.setCellValue(contact.getContactDate());
                            contactDate.setCellStyle(XSSFCellStyle);
                        } else {
                            contactDate.setCellType(CellType.BLANK);
                        }

                        XSSFCell careLevel = header.createCell(++count);
                        careLevel.setCellValue(contact != null && contact.getCareLevelAfterAssessment() != null ? contact.getCareLevelAfterAssessment().getName() : "");

                        XSSFCell vlresult = header.createCell(++count);
                        if (vlTest != null && vlTest.getResult() != null) {
                            vlresult.setCellValue(vlTest.getResult());
                        } else {
                            vlresult.setCellType(CellType.BLANK);
                        }

                        XSSFCell vlTnd = header.createCell(++count);
                        if (vlTest != null && vlTest.getTnd() != null) {
                            vlTnd.setCellValue(vlTest.getTnd());
                        } else {
                            vlTnd.setCellType(CellType.BLANK);
                        }


                        XSSFCell vlDateTaken = header.createCell(++count);
                        if (vlTest != null) {
                            vlDateTaken.setCellValue(vlTest.getDateTaken());
                            vlDateTaken.setCellStyle(XSSFCellStyle);
                        } else {
                            vlDateTaken.setCellValue("");
                        }

                    }
                }
                final long patient_end = System.currentTimeMillis();
                System.err.println("::Finished Collation of Patient Details (min)::" + (double) ((patient_end - start_patient) / 60000));

                // add contacts
                XSSFSheet contactDetails = workbook.createSheet("Patient_Contacts");
                int contactXSSFRowNum = 0;
                XSSFRow contactHeader = contactDetails.createRow(contactXSSFRowNum++);
                int contactXSSFCellNum = 0;
                for (String title : DatabaseHeader.CONTACT_HEADER) {
                    XSSFCell XSSFCell = contactHeader.createCell(contactXSSFCellNum++);
                    XSSFCell.setCellValue(title);
                }
                for (Contact contact : contacts) {
                    int count = 0;
                    contactHeader = contactDetails.createRow(contactXSSFRowNum++);
                    XSSFCell id = contactHeader.createCell(count);
                    id.setCellValue(contact.getPatient().getPatientNumber());
                    XSSFCell patientName = contactHeader.createCell(++count);
                    patientName.setCellValue(contact.getPatient().getName());
                    XSSFCell dateOfBirth = contactHeader.createCell(++count);
                    dateOfBirth.setCellValue(contact.getPatient().getDateOfBirth());
                    dateOfBirth.setCellStyle(XSSFCellStyle);
                    XSSFCell age = contactHeader.createCell(++count);
                    age.setCellValue(contact.getPatient().getAge());
                    XSSFCell sex = contactHeader.createCell(++count);
                    sex.setCellValue(contact.getPatient().getGender().getName());
                    XSSFCell province = contactHeader.createCell(++count);
                    province.setCellValue(contact.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                    XSSFCell district = contactHeader.createCell(++count);
                    district.setCellValue(contact.getPatient().getPrimaryClinic().getDistrict().getName());
                    XSSFCell primaryClinic = contactHeader.createCell(++count);
                    primaryClinic.setCellValue(contact.getPatient().getPrimaryClinic().getName());
                    XSSFCell entry = contactHeader.createCell(++count);
                    if (contact.getDateCreated() != null) {
                        entry.setCellValue(contact.getDateCreated());
                        entry.setCellStyle(XSSFCellStyle);
                    } else {
                        entry.setCellValue("");
                    }
                    entry.setCellStyle(XSSFCellStyle);
                    XSSFCell contactDate = contactHeader.createCell(++count);
                    contactDate.setCellValue(contact.getContactDate());
                    contactDate.setCellStyle(XSSFCellStyle);
                    XSSFCell careLevel = contactHeader.createCell(++count);
                    careLevel.setCellValue(contact.getCareLevel().getName());
                    XSSFCell location = contactHeader.createCell(++count);
                    location.setCellValue(contact.getLocation() != null ? contact.getLocation().getName() : null);
                    XSSFCell position = contactHeader.createCell(++count);
                    position.setCellValue(
                            contact.getPosition() != null ? contact.getPosition().getName() : null);
                    XSSFCell followUp = contactHeader.createCell(++count);
                    followUp.setCellValue(contact.getCareLevelAfterAssessment() != null ? contact.getCareLevelAfterAssessment().getName() : null);

                    XSSFCell lastClinicAppointmentDate = contactHeader.createCell(++count);
                    if (contact.getLastClinicAppointmentDate() != null) {
                        lastClinicAppointmentDate.setCellValue(contact.getLastClinicAppointmentDate());
                        lastClinicAppointmentDate.setCellStyle(XSSFCellStyle);
                    } else {
                        lastClinicAppointmentDate.setCellValue("");
                    }

                    XSSFCell attendedClinicAppointment = contactHeader.createCell(++count);
                    attendedClinicAppointment.setCellValue(contact.getAttendedClinicAppointment() != null ? contact.getAttendedClinicAppointment().getName() : "");

                    XSSFCell nextClinicAppointmentDate = contactHeader.createCell(++count);
                    if (contact.getNextClinicAppointmentDate() != null) {
                        nextClinicAppointmentDate.setCellValue(contact.getNextClinicAppointmentDate());
                        nextClinicAppointmentDate.setCellStyle(XSSFCellStyle);
                    } else {
                        nextClinicAppointmentDate.setCellValue("");
                    }

                    XSSFCell contacter = contactHeader.createCell(++count);
                    contacter.setCellValue(contact.getContactMadeBy());

                    XSSFCell eac = contactHeader.createCell(++count);
                    eac.setCellValue(contact.getEac() != null ? contact.getEac().getName() : null);

                    XSSFCell whichEac = contactHeader.createCell(++count);
                    whichEac.setCellValue(contact.getEac() != null && contact.getEac1() != null ? "eac1"
                            : contact.getEac() != null && contact.getEac2() != null ? "eac2"
                            : contact.getEac() != null && contact.getEac3() != null ? "eac3"
                            : null
                    );

                    XSSFCell isCats = contactHeader.createCell(++count);
                    isCats.setCellValue(
                            contact.getPatient().getCat() != null ? contact.getPatient().getCat().getName() : null
                    );
                    XSSFCell youngMumGroup = contactHeader.createCell(++count);
                    youngMumGroup.setCellValue(
                            contact.getPatient().getYoungMumGroup() != null ? contact.getPatient().getYoungMumGroup().getName() : null
                    );
                    XSSFCell ymd = contactHeader.createCell(++count);
                    ymd.setCellValue(
                            contact.getPatient().getYoungDadGroup() != null ? contact.getPatient().getYoungDadGroup().getName() : null
                    );
                }

                final long conts_end = System.currentTimeMillis();
                System.err.println(" :: Finished Collation of Contacts Details (min)::" + (double) ((conts_end - patient_end) / 60000));
                // add contact assessments
                XSSFSheet assessmentDetails = workbook.createSheet("Patient_Contact_Assessments");
                int assessmentXSSFRowNum = 0;
                XSSFRow assessmentHeader = assessmentDetails.createRow(assessmentXSSFRowNum++);
                int assessmentXSSFCellNum = 0;
                for (String title : DatabaseHeader.ASSESSMENT_HEADER) {
                    XSSFCell XSSFCell = assessmentHeader.createCell(assessmentXSSFCellNum++);
                    XSSFCell.setCellValue(title);
                }
                for (Contact contact : contacts) {
                    //if (!contact.getClinicalAssessments().isEmpty() || !contact.getNonClinicalAssessments().isEmpty()) {
                    Set<Assessment> clinicalAssessment = contact.getClinicalAssessments();
                    if (clinicalAssessment != null) {
                        clinicalAssessment.addAll(contact.getNonClinicalAssessments());
                    }
                    for (Assessment item : contact.getClinicalAssessments()) {
                        int count = 0;
                        assessmentHeader = assessmentDetails.createRow(assessmentXSSFRowNum++);
                        XSSFCell id = assessmentHeader.createCell(count);
                        id.setCellValue(contact.getPatient().getPatientNumber());
                        XSSFCell patientName = assessmentHeader.createCell(++count);
                        patientName.setCellValue(contact.getPatient().getName());
                        XSSFCell dateOfBirth = assessmentHeader.createCell(++count);
                        dateOfBirth.setCellValue(contact.getPatient().getDateOfBirth());
                        dateOfBirth.setCellStyle(XSSFCellStyle);
                        XSSFCell age = assessmentHeader.createCell(++count);
                        age.setCellValue(contact.getPatient().getAge());
                        XSSFCell sex = assessmentHeader.createCell(++count);
                        sex.setCellValue(contact.getPatient().getGender().getName());
                        XSSFCell province = assessmentHeader.createCell(++count);
                        province.setCellValue(contact.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                        XSSFCell district = assessmentHeader.createCell(++count);
                        district.setCellValue(contact.getPatient().getPrimaryClinic().getDistrict().getName());
                        XSSFCell primaryClinic = assessmentHeader.createCell(++count);
                        primaryClinic.setCellValue(contact.getPatient().getPrimaryClinic().getName());
                        XSSFCell entry = contactHeader.createCell(++count);
                        if (contact.getDateCreated() != null) {
                            entry.setCellValue(contact.getDateCreated());
                            entry.setCellStyle(XSSFCellStyle);
                        } else {
                            entry.setCellValue("");
                        }
                        XSSFCell contactDate = assessmentHeader.createCell(++count);
                        contactDate.setCellValue(contact.getContactDate());
                        contactDate.setCellStyle(XSSFCellStyle);
                        XSSFCell careLevel = assessmentHeader.createCell(++count);
                        careLevel.setCellValue(contact.getCareLevel().getName());
                        XSSFCell assessmentType = assessmentHeader.createCell(++count);
                        assessmentType.setCellValue(item.getContactAssessment().getName());
                        XSSFCell assessment = assessmentHeader.createCell(++count);
                        assessment.setCellValue(item.toString());

                        XSSFCell isCats = assessmentHeader.createCell(++count);
                        isCats.setCellValue(
                                contact.getPatient().getCat() != null ? contact.getPatient().getCat().getName() : null
                        );
                        XSSFCell youngMumGroup = assessmentHeader.createCell(++count);
                        youngMumGroup.setCellValue(
                                contact.getPatient().getYoungMumGroup() != null ? contact.getPatient().getYoungMumGroup().getName() : null
                        );
                        XSSFCell ymd = assessmentHeader.createCell(++count);
                        ymd.setCellValue(
                                contact.getPatient().getYoungDadGroup() != null ? contact.getPatient().getYoungDadGroup().getName() : null
                        );
                    }

                }
//
                final long assess_end = System.currentTimeMillis();
                System.err.println(" :: Finished Collation of Contacts Assessment (min)::" + (double) ((assess_end - contact_end) / 60000));
                // add referrals
                XSSFSheet referralDetails = workbook.createSheet("Patient_Referral");
                int referralXSSFRowNum = 0;
                XSSFRow referralXSSFRow = referralDetails.createRow(referralXSSFRowNum++);
                int referralXSSFCellNum = 0;
                for (String title : DatabaseHeader.REFERRAL_HEADER) {
                    XSSFCell XSSFCell = referralXSSFRow.createCell(referralXSSFCellNum++);
                    XSSFCell.setCellValue(title);
                }
                for (Referral referral : referrals) {
                    int count = 0;
                    referralXSSFRow = referralDetails.createRow(referralXSSFRowNum++);
                    XSSFCell id = referralXSSFRow.createCell(count);
                    id.setCellValue(referral.getPatient().getPatientNumber());
                    XSSFCell patientName = referralXSSFRow.createCell(++count);
                    patientName.setCellValue(referral.getPatient().getName());
                    XSSFCell dateOfBirth = referralXSSFRow.createCell(++count);
                    dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                    dateOfBirth.setCellStyle(XSSFCellStyle);
                    XSSFCell age = referralXSSFRow.createCell(++count);
                    age.setCellValue(referral.getPatient().getAge());
                    XSSFCell sex = referralXSSFRow.createCell(++count);
                    sex.setCellValue(referral.getPatient().getGender().getName());
                    XSSFCell province = referralXSSFRow.createCell(++count);
                    province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                    XSSFCell district = referralXSSFRow.createCell(++count);
                    district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                    XSSFCell primaryClinic = referralXSSFRow.createCell(++count);
                    primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                    //"Date Of Entry", "Referral Date", "Expected Visit Date", "Organisation",

                    XSSFCell entry = referralXSSFRow.createCell(++count);
                    if (referral.getDateCreated() != null) {
                        entry.setCellValue(referral.getDateCreated());
                        entry.setCellStyle(XSSFCellStyle);
                    } else {
                        entry.setCellValue("");
                    }
                    XSSFCell referralDate = referralXSSFRow.createCell(++count);
                    if (referral.getReferralDate() != null) {
                        referralDate.setCellValue(referral.getReferralDate());
                        referralDate.setCellStyle(XSSFCellStyle);
                    } else {
                        referralDate.setCellValue("");
                    }
                    XSSFCell expectedVisitDate = referralXSSFRow.createCell(++count);
                    if (referral.getExpectedVisitDate() != null) {
                        expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                        expectedVisitDate.setCellStyle(XSSFCellStyle);
                    } else {
                        expectedVisitDate.setCellValue("");
                    }
                    XSSFCell organisation = referralXSSFRow.createCell(++count);
                    organisation.setCellValue(referral.getOrganisation());
                    XSSFCell designation = referralXSSFRow.createCell(++count);
                    designation.setCellValue(referral.getDesignation());
                    XSSFCell attendingOfficer = referralXSSFRow.createCell(++count);
                    attendingOfficer.setCellValue(referral.getAttendingOfficer());
                    XSSFCell dateAttended = referralXSSFRow.createCell(++count);
                    if (referral.getDateAttended() != null) {
                        dateAttended.setCellValue(referral.getDateAttended());
                        dateAttended.setCellStyle(XSSFCellStyle);
                    } else {
                        dateAttended.setCellValue("");
                    }
                    XSSFCell actionTaken = referralXSSFRow.createCell(++count);
                    actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                    XSSFCell hivReq = referralXSSFRow.createCell(++count);
                    hivReq.setCellValue(!referral.getHivStiServicesReq().isEmpty()
                            ? referral.getHivStiServicesReq().toString() : null);
                    XSSFCell hivRec = referralXSSFRow.createCell(++count);
                    hivRec.setCellValue(!referral.getHivStiServicesAvailed().isEmpty()
                            ? referral.getHivStiServicesAvailed().toString() : null);
                    XSSFCell oiReq = referralXSSFRow.createCell(++count);
                    oiReq.setCellValue(!referral.getOiArtReq().isEmpty()
                            ? referral.getOiArtReq().toString() : null);
                    XSSFCell oiRec = referralXSSFRow.createCell(++count);
                    oiRec.setCellValue(!referral.getOiArtAvailed().isEmpty()
                            ? referral.getOiArtAvailed().toString() : null);
                    XSSFCell srhReq = referralXSSFRow.createCell(++count);
                    srhReq.setCellValue(!referral.getSrhReq().isEmpty()
                            ? referral.getSrhReq().toString() : null);
                    XSSFCell srhRec = referralXSSFRow.createCell(++count);
                    srhRec.setCellValue(!referral.getSrhAvailed().isEmpty()
                            ? referral.getSrhAvailed().toString() : null);
                    XSSFCell labReq = referralXSSFRow.createCell(++count);
                    labReq.setCellValue(!referral.getLaboratoryReq().isEmpty()
                            ? referral.getLaboratoryReq().toString() : null);
                    XSSFCell labRec = referralXSSFRow.createCell(++count);
                    labRec.setCellValue(!referral.getLaboratoryAvailed().isEmpty()
                            ? referral.getLaboratoryAvailed().toString() : null);
                    XSSFCell tbReq = referralXSSFRow.createCell(++count);
                    tbReq.setCellValue(!referral.getTbReq().isEmpty()
                            ? referral.getTbReq().toString() : null);
                    XSSFCell tbRec = referralXSSFRow.createCell(++count);
                    tbRec.setCellValue(!referral.getTbAvailed().isEmpty()
                            ? referral.getTbAvailed().toString() : null);
                    XSSFCell psReq = referralXSSFRow.createCell(++count);
                    psReq.setCellValue(!referral.getPsychReq().isEmpty()
                            ? referral.getPsychReq().toString() : null);
                    XSSFCell psRec = referralXSSFRow.createCell(++count);
                    psRec.setCellValue(!referral.getPsychAvailed().isEmpty()
                            ? referral.getPsychAvailed().toString() : null);
                    XSSFCell legalReq = referralXSSFRow.createCell(++count);
                    legalReq.setCellValue(!referral.getLegalReq().isEmpty()
                            ? referral.getLegalReq().toString() : null);
                    XSSFCell legalRec = referralXSSFRow.createCell(++count);
                    legalRec.setCellValue(!referral.getLegalAvailed().isEmpty()
                            ? referral.getLegalAvailed().toString() : null);

                    XSSFCell isCats = referralXSSFRow.createCell(++count);
                    isCats.setCellValue(
                            referral.getPatient().getCat() != null ? referral.getPatient().getCat().getName() : null
                    );
                    XSSFCell youngMumGroup = referralXSSFRow.createCell(++count);
                    youngMumGroup.setCellValue(
                            referral.getPatient().getYoungMumGroup() != null ? referral.getPatient().getYoungMumGroup().getName() : null
                    );
                    XSSFCell ymd = referralXSSFRow.createCell(++count);
                    ymd.setCellValue(
                            referral.getPatient().getYoungDadGroup() != null ? referral.getPatient().getYoungDadGroup().getName() : null
                    );
                }

                final long ref_end = System.currentTimeMillis();
                System.err.println(" :: Finished Collation of Referral Details (min)::" + (double) ((ref_end - assess_end) / 60000));



    // add hiv sti services referred +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
               /* XSSFSheet hivStiReferredDetails = workbook.createSheet("Hiv_And_Sti_Services_Referred");
                int hivStiReferredXSSFRowNum = 0;
                XSSFRow hivStiReferredXSSFRow = hivStiReferredDetails.createRow(hivStiReferredXSSFRowNum++);
                int hivStiReferredXSSFCellNum = 0;
                for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                    XSSFCell XSSFCell = hivStiReferredXSSFRow.createCell(hivStiReferredXSSFCellNum++);
                    XSSFCell.setCellValue(title);
                }
                for (Referral referral : referrals) {
                    if (!referral.getHivStiServicesReq().isEmpty()) {
                        for (ServicesReferred referred : referral.getHivStiServicesReq()) {
                            int count = 0;
                            hivStiReferredXSSFRow = hivStiReferredDetails.createRow(hivStiReferredXSSFRowNum++);
                            XSSFCell id = hivStiReferredXSSFRow.createCell(count);
                            id.setCellValue(referral.getPatient().getPatientNumber());
                            XSSFCell patientName = hivStiReferredXSSFRow.createCell(++count);
                            patientName.setCellValue(referral.getPatient().getName());
                            XSSFCell dateOfBirth = hivStiReferredXSSFRow.createCell(++count);
                            dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                            dateOfBirth.setCellStyle(XSSFCellStyle);
                            XSSFCell age = hivStiReferredXSSFRow.createCell(++count);
                            age.setCellValue(referral.getPatient().getAge());
                            XSSFCell sex = hivStiReferredXSSFRow.createCell(++count);
                            sex.setCellValue(referral.getPatient().getGender().getName());
                            XSSFCell province = hivStiReferredXSSFRow.createCell(++count);
                            province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                            XSSFCell district = hivStiReferredXSSFRow.createCell(++count);
                            district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                            XSSFCell primaryClinic = hivStiReferredXSSFRow.createCell(++count);
                            primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                            XSSFCell entry = hivStiReferredXSSFRow.createCell(++count);
                            if (referral.getDateCreated() != null) {
                                entry.setCellValue(referral.getDateCreated());
                                entry.setCellStyle(XSSFCellStyle);
                            } else {
                                entry.setCellValue("");
                            }
                            XSSFCell referralDate = hivStiReferredXSSFRow.createCell(++count);
                            referralDate.setCellValue(referral.getReferralDate());
                            referralDate.setCellStyle(XSSFCellStyle);
                            XSSFCell expectedVisitDate = hivStiReferredXSSFRow.createCell(++count);
                            if (referral.getExpectedVisitDate() != null) {
                                expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                                expectedVisitDate.setCellStyle(XSSFCellStyle);
                            } else {
                                expectedVisitDate.setCellValue("");
                            }
                            XSSFCell organisation = hivStiReferredXSSFRow.createCell(++count);
                            organisation.setCellValue(referral.getOrganisation());
                            XSSFCell designation = hivStiReferredXSSFRow.createCell(++count);
                            designation.setCellValue(referral.getDesignation());
                            XSSFCell attendingOfficer = hivStiReferredXSSFRow.createCell(++count);
                            attendingOfficer.setCellValue(referral.getAttendingOfficer());
                            XSSFCell dateAttended = hivStiReferredXSSFRow.createCell(++count);
                            if (referral.getDateAttended() != null) {
                                dateAttended.setCellValue(referral.getDateAttended());
                                dateAttended.setCellStyle(XSSFCellStyle);
                            } else {
                                dateAttended.setCellValue("");
                            }
                            XSSFCell actionTaken = hivStiReferredXSSFRow.createCell(++count);
                            actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                            XSSFCell hivReq = hivStiReferredXSSFRow.createCell(++count);
                            hivReq.setCellValue(referred.toString());

                            XSSFCell isCats = hivStiReferredXSSFRow.createCell(++count);
                            isCats.setCellValue(
                                    referral.getPatient().getCat() != null ? referral.getPatient().getCat().getName() : null
                            );
                            XSSFCell youngMumGroup = hivStiReferredXSSFRow.createCell(++count);
                            youngMumGroup.setCellValue(
                                    referral.getPatient().getYoungMumGroup() != null ? referral.getPatient().getYoungMumGroup().getName() : null
                            );
                            XSSFCell ymd = hivStiReferredXSSFRow.createCell(++count);
                            ymd.setCellValue(
                                    referral.getPatient().getYoungDadGroup() != null ? referral.getPatient().getYoungDadGroup().getName() : null
                            );
                        }
                    }

                }

                final long hiv_end = System.currentTimeMillis();
                System.err.println(":: Finished Collation of Referral(Hiv & STI referred) (min)::" + (double) ((hiv_end - ref_end) / 60000));

                // add hiv sti services provided
                XSSFSheet hivStiProvidedDetails = workbook.createSheet("Hiv_And_Sti_Services_Provided");
                int hivStiProvidedXSSFRowNum = 0;
                XSSFRow hivStiProvidedXSSFRow = hivStiProvidedDetails.createRow(hivStiProvidedXSSFRowNum++);
                int hivStiProvidedXSSFCellNum = 0;
                for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                    XSSFCell XSSFCell = hivStiProvidedXSSFRow.createCell(hivStiProvidedXSSFCellNum++);
                    XSSFCell.setCellValue(title);
                }
                for (Referral referral : referrals) {
                    if (!referral.getHivStiServicesAvailed().isEmpty()) {
                        for (ServicesReferred referred : referral.getHivStiServicesAvailed()) {
                            int count = 0;
                            hivStiProvidedXSSFRow = hivStiProvidedDetails.createRow(hivStiProvidedXSSFRowNum++);
                            XSSFCell id = hivStiProvidedXSSFRow.createCell(count);
                            id.setCellValue(referral.getPatient().getPatientNumber());
                            XSSFCell patientName = hivStiProvidedXSSFRow.createCell(++count);
                            patientName.setCellValue(referral.getPatient().getName());
                            XSSFCell dateOfBirth = hivStiProvidedXSSFRow.createCell(++count);
                            dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                            dateOfBirth.setCellStyle(XSSFCellStyle);
                            XSSFCell age = hivStiProvidedXSSFRow.createCell(++count);
                            age.setCellValue(referral.getPatient().getAge());
                            XSSFCell sex = hivStiProvidedXSSFRow.createCell(++count);
                            sex.setCellValue(referral.getPatient().getGender().getName());
                            sex.setCellValue(referral.getPatient().getGender().getName());
                            XSSFCell province = hivStiProvidedXSSFRow.createCell(++count);
                            province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                            XSSFCell district = hivStiProvidedXSSFRow.createCell(++count);
                            district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                            XSSFCell primaryClinic = hivStiProvidedXSSFRow.createCell(++count);
                            primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                            XSSFCell entry = hivStiProvidedXSSFRow.createCell(++count);
                            if (referral.getDateCreated() != null) {
                                entry.setCellValue(referral.getDateCreated());
                                entry.setCellStyle(XSSFCellStyle);
                            } else {
                                entry.setCellValue("");
                            }
                            XSSFCell referralDate = hivStiProvidedXSSFRow.createCell(++count);
                            referralDate.setCellValue(referral.getReferralDate());
                            referralDate.setCellStyle(XSSFCellStyle);
                            XSSFCell expectedVisitDate = hivStiProvidedXSSFRow.createCell(++count);
                            if (referral.getExpectedVisitDate() != null) {
                                expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                                expectedVisitDate.setCellStyle(XSSFCellStyle);
                            } else {
                                expectedVisitDate.setCellValue("");
                            }
                            XSSFCell organisation = hivStiProvidedXSSFRow.createCell(++count);
                            organisation.setCellValue(referral.getOrganisation());
                            XSSFCell designation = hivStiProvidedXSSFRow.createCell(++count);
                            designation.setCellValue(referral.getDesignation());
                            XSSFCell attendingOfficer = hivStiProvidedXSSFRow.createCell(++count);
                            attendingOfficer.setCellValue(referral.getAttendingOfficer());
                            XSSFCell dateAttended = hivStiProvidedXSSFRow.createCell(++count);
                            if (referral.getDateAttended() != null) {
                                dateAttended.setCellValue(referral.getDateAttended());
                                dateAttended.setCellStyle(XSSFCellStyle);
                            } else {
                                dateAttended.setCellValue("");
                            }
                            XSSFCell actionTaken = hivStiProvidedXSSFRow.createCell(++count);
                            actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                            XSSFCell hivReq = hivStiProvidedXSSFRow.createCell(++count);
                            hivReq.setCellValue(referred.toString());
                            XSSFCell isCats = hivStiProvidedXSSFRow.createCell(++count);
                            isCats.setCellValue(
                                    referral.getPatient().getCat() != null ? referral.getPatient().getCat().getName() : null
                            );
                            XSSFCell youngMumGroup = hivStiProvidedXSSFRow.createCell(++count);
                            youngMumGroup.setCellValue(
                                    referral.getPatient().getYoungMumGroup() != null ? referral.getPatient().getYoungMumGroup().getName() : null
                            );
                            XSSFCell ymd = hivStiProvidedXSSFRow.createCell(++count);
                            ymd.setCellValue(
                                    referral.getPatient().getYoungDadGroup() != null ? referral.getPatient().getYoungDadGroup().getName() : null
                            );
                        }
                    }

                }

                final long sti_end = System.currentTimeMillis();
                System.err.println(" :: Finished Collation of Referral(Hiv & STI availed) (min)::" + (double) ((sti_end - hiv_end) / 60000));

                // add oi art services referred
                XSSFSheet oiArtReferredDetails = workbook.createSheet("Oi_Art_Services_Referred");
                int oiArtReferredXSSFRowNum = 0;
                XSSFRow oiArtReferredXSSFRow = oiArtReferredDetails.createRow(oiArtReferredXSSFRowNum++);
                int oiArtReferredXSSFCellNum = 0;
                for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                    XSSFCell XSSFCell = oiArtReferredXSSFRow.createCell(oiArtReferredXSSFCellNum++);
                    XSSFCell.setCellValue(title);
                }
                for (Referral referral : referrals) {
                    if (!referral.getOiArtReq().isEmpty()) {
                        for (ServicesReferred referred : referral.getOiArtReq()) {
                            int count = 0;
                            oiArtReferredXSSFRow = oiArtReferredDetails.createRow(oiArtReferredXSSFRowNum++);
                            XSSFCell id = oiArtReferredXSSFRow.createCell(count);
                            id.setCellValue(referral.getPatient().getPatientNumber());
                            XSSFCell patientName = oiArtReferredXSSFRow.createCell(++count);
                            patientName.setCellValue(referral.getPatient().getName());
                            XSSFCell dateOfBirth = oiArtReferredXSSFRow.createCell(++count);
                            dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                            dateOfBirth.setCellStyle(XSSFCellStyle);
                            XSSFCell age = oiArtReferredXSSFRow.createCell(++count);
                            age.setCellValue(referral.getPatient().getAge());
                            XSSFCell sex = oiArtReferredXSSFRow.createCell(++count);
                            sex.setCellValue(referral.getPatient().getGender().getName());
                            XSSFCell province = oiArtReferredXSSFRow.createCell(++count);
                            province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                            XSSFCell district = oiArtReferredXSSFRow.createCell(++count);
                            district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                            XSSFCell primaryClinic = oiArtReferredXSSFRow.createCell(++count);
                            primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                            XSSFCell entry = oiArtReferredXSSFRow.createCell(++count);
                            if (referral.getDateCreated() != null) {
                                entry.setCellValue(referral.getDateCreated());
                                entry.setCellStyle(XSSFCellStyle);
                            } else {
                                entry.setCellValue("");
                            }
                            XSSFCell referralDate = oiArtReferredXSSFRow.createCell(++count);
                            referralDate.setCellValue(referral.getReferralDate());
                            referralDate.setCellStyle(XSSFCellStyle);
                            XSSFCell expectedVisitDate = oiArtReferredXSSFRow.createCell(++count);
                            if (referral.getExpectedVisitDate() != null) {
                                expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                                expectedVisitDate.setCellStyle(XSSFCellStyle);
                            } else {
                                expectedVisitDate.setCellValue("");
                            }
                            XSSFCell organisation = oiArtReferredXSSFRow.createCell(++count);
                            organisation.setCellValue(referral.getOrganisation());
                            XSSFCell designation = oiArtReferredXSSFRow.createCell(++count);
                            designation.setCellValue(referral.getDesignation());
                            XSSFCell attendingOfficer = oiArtReferredXSSFRow.createCell(++count);
                            attendingOfficer.setCellValue(referral.getAttendingOfficer());
                            XSSFCell dateAttended = oiArtReferredXSSFRow.createCell(++count);
                            if (referral.getDateAttended() != null) {
                                dateAttended.setCellValue(referral.getDateAttended());
                                dateAttended.setCellStyle(XSSFCellStyle);
                            } else {
                                dateAttended.setCellValue("");
                            }
                            XSSFCell actionTaken = oiArtReferredXSSFRow.createCell(++count);
                            actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                            XSSFCell hivReq = oiArtReferredXSSFRow.createCell(++count);
                            hivReq.setCellValue(referred.toString());

                            XSSFCell isCats = oiArtReferredXSSFRow.createCell(++count);
                            isCats.setCellValue(
                                    referral.getPatient().getCat() != null ? referral.getPatient().getCat().getName() : null
                            );
                            XSSFCell youngMumGroup = oiArtReferredXSSFRow.createCell(++count);
                            youngMumGroup.setCellValue(
                                    referral.getPatient().getYoungMumGroup() != null ? referral.getPatient().getYoungMumGroup().getName() : null
                            );
                            XSSFCell ymd = oiArtReferredXSSFRow.createCell(++count);
                            ymd.setCellValue(
                                    referral.getPatient().getYoungDadGroup() != null ? referral.getPatient().getYoungDadGroup().getName() : null
                            );
                        }
                    }

                }
                final long oi_end = System.currentTimeMillis();
                System.err.println(" :: Finished Collation of Referral(OI_ART referred) (min)::" + (double) ((oi_end - sti_end) / 60000));

                // add oi art services provided
                XSSFSheet oiArtProvidedDetails = workbook.createSheet("Oi_Art_Services_Provided");
                int oiArtProvidedXSSFRowNum = 0;
                XSSFRow oiArtProvidedXSSFRow = oiArtProvidedDetails.createRow(oiArtProvidedXSSFRowNum++);
                int oiArtProvidedXSSFCellNum = 0;
                for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                    XSSFCell XSSFCell = oiArtProvidedXSSFRow.createCell(oiArtProvidedXSSFCellNum++);
                    XSSFCell.setCellValue(title);
                }
                for (Referral referral : referrals) {
                    if (!referral.getOiArtAvailed().isEmpty()) {
                        for (ServicesReferred referred : referral.getOiArtAvailed()) {
                            int count = 0;
                            oiArtProvidedXSSFRow = oiArtProvidedDetails.createRow(oiArtProvidedXSSFRowNum++);
                            XSSFCell id = oiArtProvidedXSSFRow.createCell(count);
                            id.setCellValue(referral.getPatient().getPatientNumber());
                            XSSFCell patientName = oiArtProvidedXSSFRow.createCell(++count);
                            patientName.setCellValue(referral.getPatient().getName());
                            XSSFCell dateOfBirth = oiArtProvidedXSSFRow.createCell(++count);
                            dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                            dateOfBirth.setCellStyle(XSSFCellStyle);
                            XSSFCell age = oiArtProvidedXSSFRow.createCell(++count);
                            age.setCellValue(referral.getPatient().getAge());
                            XSSFCell sex = oiArtProvidedXSSFRow.createCell(++count);
                            sex.setCellValue(referral.getPatient().getGender().getName());
                            XSSFCell province = oiArtProvidedXSSFRow.createCell(++count);
                            province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                            XSSFCell district = oiArtProvidedXSSFRow.createCell(++count);
                            district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                            XSSFCell primaryClinic = oiArtProvidedXSSFRow.createCell(++count);
                            primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                            XSSFCell entry = oiArtProvidedXSSFRow.createCell(++count);
                            if (referral.getDateCreated() != null) {
                                entry.setCellValue(referral.getDateCreated());
                                entry.setCellStyle(XSSFCellStyle);
                            } else {
                                entry.setCellValue("");
                            }
                            XSSFCell referralDate = oiArtProvidedXSSFRow.createCell(++count);
                            referralDate.setCellValue(referral.getReferralDate());
                            referralDate.setCellStyle(XSSFCellStyle);
                            XSSFCell expectedVisitDate = oiArtProvidedXSSFRow.createCell(++count);
                            if (referral.getExpectedVisitDate() != null) {
                                expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                                expectedVisitDate.setCellStyle(XSSFCellStyle);
                            } else {
                                expectedVisitDate.setCellValue("");
                            }
                            XSSFCell organisation = oiArtProvidedXSSFRow.createCell(++count);
                            organisation.setCellValue(referral.getOrganisation());
                            XSSFCell designation = oiArtProvidedXSSFRow.createCell(++count);
                            designation.setCellValue(referral.getDesignation());
                            XSSFCell attendingOfficer = oiArtProvidedXSSFRow.createCell(++count);
                            attendingOfficer.setCellValue(referral.getAttendingOfficer());
                            XSSFCell dateAttended = oiArtProvidedXSSFRow.createCell(++count);
                            if (referral.getDateAttended() != null) {
                                dateAttended.setCellValue(referral.getDateAttended());
                                dateAttended.setCellStyle(XSSFCellStyle);
                            } else {
                                dateAttended.setCellValue("");
                            }
                            XSSFCell actionTaken = oiArtProvidedXSSFRow.createCell(++count);
                            actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                            XSSFCell hivReq = oiArtProvidedXSSFRow.createCell(++count);
                            hivReq.setCellValue(referred.toString());

                            XSSFCell isCats = oiArtProvidedXSSFRow.createCell(++count);
                            isCats.setCellValue(
                                    referral.getPatient().getCat() != null ? referral.getPatient().getCat().getName() : null
                            );
                            XSSFCell youngMumGroup = oiArtProvidedXSSFRow.createCell(++count);
                            youngMumGroup.setCellValue(
                                    referral.getPatient().getYoungMumGroup() != null ? referral.getPatient().getYoungMumGroup().getName() : null
                            );
                            XSSFCell ymd = oiArtProvidedXSSFRow.createCell(++count);
                            ymd.setCellValue(
                                    referral.getPatient().getYoungDadGroup() != null ? referral.getPatient().getYoungDadGroup().getName() : null
                            );
                        }
                    }

                }
                final long art_end = System.currentTimeMillis();
                System.err.println(":: Finished Collation of Referral(OI_ART availed) (min)::" + (double) ((art_end - oi_end) / 60000));

                // add srh services referred
                XSSFSheet srhReferredDetails = workbook.createSheet("Srh_Services_Referred");
                int srhReferredXSSFRowNum = 0;
                XSSFRow srhReferredXSSFRow = srhReferredDetails.createRow(srhReferredXSSFRowNum++);
                int srhReferredXSSFCellNum = 0;
                for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                    XSSFCell XSSFCell = srhReferredXSSFRow.createCell(srhReferredXSSFCellNum++);
                    XSSFCell.setCellValue(title);
                }
                for (Referral referral : referrals) {
                    if (!referral.getSrhReq().isEmpty()) {
                        for (ServicesReferred referred : referral.getSrhReq()) {
                            int count = 0;
                            srhReferredXSSFRow = srhReferredDetails.createRow(srhReferredXSSFRowNum++);
                            XSSFCell id = srhReferredXSSFRow.createCell(count);
                            id.setCellValue(referral.getPatient().getPatientNumber());
                            XSSFCell patientName = srhReferredXSSFRow.createCell(++count);
                            patientName.setCellValue(referral.getPatient().getName());
                            XSSFCell dateOfBirth = srhReferredXSSFRow.createCell(++count);
                            dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                            dateOfBirth.setCellStyle(XSSFCellStyle);
                            XSSFCell age = srhReferredXSSFRow.createCell(++count);
                            age.setCellValue(referral.getPatient().getAge());
                            XSSFCell sex = srhReferredXSSFRow.createCell(++count);
                            sex.setCellValue(referral.getPatient().getGender().getName());
                            XSSFCell province = srhReferredXSSFRow.createCell(++count);
                            province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                            XSSFCell district = srhReferredXSSFRow.createCell(++count);
                            district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                            XSSFCell primaryClinic = srhReferredXSSFRow.createCell(++count);
                            primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                            XSSFCell entry = srhReferredXSSFRow.createCell(++count);
                            if (referral.getDateCreated() != null) {
                                entry.setCellValue(referral.getDateCreated());
                                entry.setCellStyle(XSSFCellStyle);
                            } else {
                                entry.setCellValue("");
                            }
                            XSSFCell referralDate = srhReferredXSSFRow.createCell(++count);
                            referralDate.setCellValue(referral.getReferralDate());
                            referralDate.setCellStyle(XSSFCellStyle);
                            XSSFCell expectedVisitDate = srhReferredXSSFRow.createCell(++count);
                            if (referral.getExpectedVisitDate() != null) {
                                expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                                expectedVisitDate.setCellStyle(XSSFCellStyle);
                            } else {
                                expectedVisitDate.setCellValue("");
                            }
                            XSSFCell organisation = srhReferredXSSFRow.createCell(++count);
                            organisation.setCellValue(referral.getOrganisation());
                            XSSFCell designation = srhReferredXSSFRow.createCell(++count);
                            designation.setCellValue(referral.getDesignation());
                            XSSFCell attendingOfficer = srhReferredXSSFRow.createCell(++count);
                            attendingOfficer.setCellValue(referral.getAttendingOfficer());
                            XSSFCell dateAttended = srhReferredXSSFRow.createCell(++count);
                            if (referral.getDateAttended() != null) {
                                dateAttended.setCellValue(referral.getDateAttended());
                                dateAttended.setCellStyle(XSSFCellStyle);
                            } else {
                                dateAttended.setCellValue("");
                            }
                            XSSFCell actionTaken = srhReferredXSSFRow.createCell(++count);
                            actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                            XSSFCell hivReq = srhReferredXSSFRow.createCell(++count);
                            hivReq.setCellValue(referred.toString());

                            XSSFCell isCats = srhReferredXSSFRow.createCell(++count);
                            isCats.setCellValue(
                                    referral.getPatient().getCat() != null ? referral.getPatient().getCat().getName() : null
                            );
                            XSSFCell youngMumGroup = srhReferredXSSFRow.createCell(++count);
                            youngMumGroup.setCellValue(
                                    referral.getPatient().getYoungMumGroup() != null ? referral.getPatient().getYoungMumGroup().getName() : null
                            );
                            XSSFCell ymd = srhReferredXSSFRow.createCell(++count);
                            ymd.setCellValue(
                                    referral.getPatient().getYoungDadGroup() != null ? referral.getPatient().getYoungDadGroup().getName() : null
                            );
                        }
                    }

                }
                final long srh_start = System.currentTimeMillis();
                System.err.println(" :: Finished Collation of Referral(SRH referred) (min)::" + (double) ((srh_start - art_end) / 60000));

                // add srh services provided
                XSSFSheet srhProvidedDetails = workbook.createSheet("Srh_Services_Provided");
                int srhProvidedXSSFRowNum = 0;
                XSSFRow srhProvidedXSSFRow = srhProvidedDetails.createRow(srhProvidedXSSFRowNum++);
                int srhProvidedXSSFCellNum = 0;
                for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                    XSSFCell XSSFCell = srhProvidedXSSFRow.createCell(srhProvidedXSSFCellNum++);
                    XSSFCell.setCellValue(title);
                }
                for (Referral referral : referrals) {
                    if (!referral.getSrhAvailed().isEmpty()) {
                        for (ServicesReferred referred : referral.getSrhAvailed()) {
                            int count = 0;
                            srhProvidedXSSFRow = srhProvidedDetails.createRow(srhProvidedXSSFRowNum++);
                            XSSFCell id = srhProvidedXSSFRow.createCell(count);
                            id.setCellValue(referral.getPatient().getPatientNumber());
                            XSSFCell patientName = srhProvidedXSSFRow.createCell(++count);
                            patientName.setCellValue(referral.getPatient().getName());
                            XSSFCell dateOfBirth = srhProvidedXSSFRow.createCell(++count);
                            dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                            dateOfBirth.setCellStyle(XSSFCellStyle);
                            XSSFCell age = srhProvidedXSSFRow.createCell(++count);
                            age.setCellValue(referral.getPatient().getAge());
                            XSSFCell sex = srhProvidedXSSFRow.createCell(++count);
                            sex.setCellValue(referral.getPatient().getGender().getName());
                            XSSFCell province = srhProvidedXSSFRow.createCell(++count);
                            province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                            XSSFCell district = srhProvidedXSSFRow.createCell(++count);
                            district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                            XSSFCell primaryClinic = srhProvidedXSSFRow.createCell(++count);
                            primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                            XSSFCell entry = srhProvidedXSSFRow.createCell(++count);
                            if (referral.getDateCreated() != null) {
                                entry.setCellValue(referral.getDateCreated());
                                entry.setCellStyle(XSSFCellStyle);
                            } else {
                                entry.setCellValue("");
                            }
                            XSSFCell referralDate = srhProvidedXSSFRow.createCell(++count);
                            referralDate.setCellValue(referral.getReferralDate());
                            referralDate.setCellStyle(XSSFCellStyle);
                            XSSFCell expectedVisitDate = srhProvidedXSSFRow.createCell(++count);
                            if (referral.getExpectedVisitDate() != null) {
                                expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                                expectedVisitDate.setCellStyle(XSSFCellStyle);
                            } else {
                                expectedVisitDate.setCellValue("");
                            }
                            XSSFCell organisation = srhProvidedXSSFRow.createCell(++count);
                            organisation.setCellValue(referral.getOrganisation());
                            XSSFCell designation = srhProvidedXSSFRow.createCell(++count);
                            designation.setCellValue(referral.getDesignation());
                            XSSFCell attendingOfficer = srhProvidedXSSFRow.createCell(++count);
                            attendingOfficer.setCellValue(referral.getAttendingOfficer());
                            XSSFCell dateAttended = srhProvidedXSSFRow.createCell(++count);
                            if (referral.getDateAttended() != null) {
                                dateAttended.setCellValue(referral.getDateAttended());
                                dateAttended.setCellStyle(XSSFCellStyle);
                            } else {
                                dateAttended.setCellValue("");
                            }
                            XSSFCell actionTaken = srhProvidedXSSFRow.createCell(++count);
                            actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                            XSSFCell hivReq = srhProvidedXSSFRow.createCell(++count);
                            hivReq.setCellValue(referred.toString());

                            XSSFCell isCats = srhProvidedXSSFRow.createCell(++count);
                            isCats.setCellValue(
                                    referral.getPatient().getCat() != null ? referral.getPatient().getCat().getName() : null
                            );
                            XSSFCell youngMumGroup = srhProvidedXSSFRow.createCell(++count);
                            youngMumGroup.setCellValue(
                                    referral.getPatient().getYoungMumGroup() != null ? referral.getPatient().getYoungMumGroup().getName() : null
                            );
                            XSSFCell ymd = srhProvidedXSSFRow.createCell(++count);
                            ymd.setCellValue(
                                    referral.getPatient().getYoungDadGroup() != null ? referral.getPatient().getYoungDadGroup().getName() : null
                            );
                        }
                    }

                }
                final long srh_end = System.currentTimeMillis();
                System.err.println(" :: Finished Collation of Referral(SRH availed) (min)::" + (double) ((srh_end - srh_start) / 60000));

                // add laboratory services referred
                XSSFSheet laboratoryReferredDetails = workbook.createSheet("Laboratory_Services_Referred");
                int laboratoryReferredXSSFRowNum = 0;
                XSSFRow laboratoryReferredXSSFRow = laboratoryReferredDetails.createRow(laboratoryReferredXSSFRowNum++);
                int laboratoryReferredXSSFCellNum = 0;
                for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                    XSSFCell XSSFCell = laboratoryReferredXSSFRow.createCell(laboratoryReferredXSSFCellNum++);
                    XSSFCell.setCellValue(title);
                }
                for (Referral referral : referrals) {
                    if (!referral.getLaboratoryReq().isEmpty()) {
                        for (ServicesReferred referred : referral.getLaboratoryReq()) {
                            int count = 0;
                            laboratoryReferredXSSFRow = laboratoryReferredDetails.createRow(laboratoryReferredXSSFRowNum++);
                            XSSFCell id = laboratoryReferredXSSFRow.createCell(count);
                            id.setCellValue(referral.getPatient().getPatientNumber());
                            XSSFCell patientName = laboratoryReferredXSSFRow.createCell(++count);
                            patientName.setCellValue(referral.getPatient().getName());
                            XSSFCell dateOfBirth = laboratoryReferredXSSFRow.createCell(++count);
                            dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                            dateOfBirth.setCellStyle(XSSFCellStyle);
                            XSSFCell age = laboratoryReferredXSSFRow.createCell(++count);
                            age.setCellValue(referral.getPatient().getAge());
                            XSSFCell sex = laboratoryReferredXSSFRow.createCell(++count);
                            sex.setCellValue(referral.getPatient().getGender().getName());
                            XSSFCell province = laboratoryReferredXSSFRow.createCell(++count);
                            province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                            XSSFCell district = laboratoryReferredXSSFRow.createCell(++count);
                            district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                            XSSFCell primaryClinic = laboratoryReferredXSSFRow.createCell(++count);
                            primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                            XSSFCell entry = laboratoryReferredXSSFRow.createCell(++count);
                            if (referral.getDateCreated() != null) {
                                entry.setCellValue(referral.getDateCreated());
                                entry.setCellStyle(XSSFCellStyle);
                            } else {
                                entry.setCellValue("");
                            }
                            XSSFCell referralDate = laboratoryReferredXSSFRow.createCell(++count);
                            referralDate.setCellValue(referral.getReferralDate());
                            referralDate.setCellStyle(XSSFCellStyle);
                            XSSFCell expectedVisitDate = laboratoryReferredXSSFRow.createCell(++count);
                            if (referral.getExpectedVisitDate() != null) {
                                expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                                expectedVisitDate.setCellStyle(XSSFCellStyle);
                            } else {
                                expectedVisitDate.setCellValue("");
                            }
                            XSSFCell organisation = laboratoryReferredXSSFRow.createCell(++count);
                            organisation.setCellValue(referral.getOrganisation());
                            XSSFCell designation = laboratoryReferredXSSFRow.createCell(++count);
                            designation.setCellValue(referral.getDesignation());
                            XSSFCell attendingOfficer = laboratoryReferredXSSFRow.createCell(++count);
                            attendingOfficer.setCellValue(referral.getAttendingOfficer());
                            XSSFCell dateAttended = laboratoryReferredXSSFRow.createCell(++count);
                            if (referral.getDateAttended() != null) {
                                dateAttended.setCellValue(referral.getDateAttended());
                                dateAttended.setCellStyle(XSSFCellStyle);
                            } else {
                                dateAttended.setCellValue("");
                            }
                            XSSFCell actionTaken = laboratoryReferredXSSFRow.createCell(++count);
                            actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                            XSSFCell hivReq = laboratoryReferredXSSFRow.createCell(++count);
                            hivReq.setCellValue(referred.toString());

                            XSSFCell isCats = laboratoryReferredXSSFRow.createCell(++count);
                            isCats.setCellValue(
                                    referral.getPatient().getCat() != null ? referral.getPatient().getCat().getName() : null
                            );
                            XSSFCell youngMumGroup = laboratoryReferredXSSFRow.createCell(++count);
                            youngMumGroup.setCellValue(
                                    referral.getPatient().getYoungMumGroup() != null ? referral.getPatient().getYoungMumGroup().getName() : null
                            );
                            XSSFCell ymd = laboratoryReferredXSSFRow.createCell(++count);
                            ymd.setCellValue(
                                    referral.getPatient().getYoungDadGroup() != null ? referral.getPatient().getYoungDadGroup().getName() : null
                            );
                        }
                    }

                }
                final long lab_start = System.currentTimeMillis();
                System.err.println(":: Finished Collation of Referral(LAB referred) (min)::" + (double) ((lab_start - srh_end) / 60000));

                // add laboratory services provided
                XSSFSheet laboratoryProvidedDetails = workbook.createSheet("Laboratory_Services_Provided");
                int laboratoryProvidedXSSFRowNum = 0;
                XSSFRow laboratoryProvidedXSSFRow = laboratoryProvidedDetails.createRow(laboratoryProvidedXSSFRowNum++);
                int laboratoryProvidedXSSFCellNum = 0;
                for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                    XSSFCell XSSFCell = laboratoryProvidedXSSFRow.createCell(laboratoryProvidedXSSFCellNum++);
                    XSSFCell.setCellValue(title);
                }
                for (Referral referral : referrals) {
                    if (!referral.getLaboratoryAvailed().isEmpty()) {
                        for (ServicesReferred referred : referral.getLaboratoryAvailed()) {
                            int count = 0;
                            laboratoryProvidedXSSFRow = laboratoryProvidedDetails.createRow(laboratoryProvidedXSSFRowNum++);
                            XSSFCell id = laboratoryProvidedXSSFRow.createCell(count);
                            id.setCellValue(referral.getPatient().getPatientNumber());
                            XSSFCell patientName = laboratoryProvidedXSSFRow.createCell(++count);
                            patientName.setCellValue(referral.getPatient().getName());
                            XSSFCell dateOfBirth = laboratoryProvidedXSSFRow.createCell(++count);
                            dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                            dateOfBirth.setCellStyle(XSSFCellStyle);
                            XSSFCell age = laboratoryProvidedXSSFRow.createCell(++count);
                            age.setCellValue(referral.getPatient().getAge());
                            XSSFCell sex = laboratoryProvidedXSSFRow.createCell(++count);
                            sex.setCellValue(referral.getPatient().getGender().getName());
                            XSSFCell province = laboratoryProvidedXSSFRow.createCell(++count);
                            province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                            XSSFCell district = laboratoryProvidedXSSFRow.createCell(++count);
                            district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                            XSSFCell primaryClinic = laboratoryProvidedXSSFRow.createCell(++count);
                            primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                            XSSFCell entry = laboratoryProvidedXSSFRow.createCell(++count);
                            if (referral.getDateCreated() != null) {
                                entry.setCellValue(referral.getDateCreated());
                                entry.setCellStyle(XSSFCellStyle);
                            } else {
                                entry.setCellValue("");
                            }
                            XSSFCell referralDate = laboratoryProvidedXSSFRow.createCell(++count);
                            referralDate.setCellValue(referral.getReferralDate());
                            referralDate.setCellStyle(XSSFCellStyle);
                            XSSFCell expectedVisitDate = laboratoryProvidedXSSFRow.createCell(++count);
                            if (referral.getExpectedVisitDate() != null) {
                                expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                                expectedVisitDate.setCellStyle(XSSFCellStyle);
                            } else {
                                expectedVisitDate.setCellValue("");
                            }
                            XSSFCell organisation = laboratoryProvidedXSSFRow.createCell(++count);
                            organisation.setCellValue(referral.getOrganisation());
                            XSSFCell designation = laboratoryProvidedXSSFRow.createCell(++count);
                            designation.setCellValue(referral.getDesignation());
                            XSSFCell attendingOfficer = laboratoryProvidedXSSFRow.createCell(++count);
                            attendingOfficer.setCellValue(referral.getAttendingOfficer());
                            XSSFCell dateAttended = laboratoryProvidedXSSFRow.createCell(++count);
                            if (referral.getDateAttended() != null) {
                                dateAttended.setCellValue(referral.getDateAttended());
                                dateAttended.setCellStyle(XSSFCellStyle);
                            } else {
                                dateAttended.setCellValue("");
                            }
                            XSSFCell actionTaken = laboratoryProvidedXSSFRow.createCell(++count);
                            actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                            XSSFCell hivReq = laboratoryProvidedXSSFRow.createCell(++count);
                            hivReq.setCellValue(referred.toString());

                            XSSFCell isCats = laboratoryProvidedXSSFRow.createCell(++count);
                            isCats.setCellValue(
                                    referral.getPatient().getCat() != null ? referral.getPatient().getCat().getName() : null
                            );
                            XSSFCell youngMumGroup = laboratoryProvidedXSSFRow.createCell(++count);
                            youngMumGroup.setCellValue(
                                    referral.getPatient().getYoungMumGroup() != null ? referral.getPatient().getYoungMumGroup().getName() : null
                            );
                            XSSFCell ymd = laboratoryProvidedXSSFRow.createCell(++count);
                            ymd.setCellValue(
                                    referral.getPatient().getYoungDadGroup() != null ? referral.getPatient().getYoungDadGroup().getName() : null
                            );
                        }
                    }

                }
                final long lab_end = System.currentTimeMillis();
                System.err.println(":: Finished Collation of Referral(LAB availed) (min)::" + (double) ((lab_end - lab_start) / 60000));

                // add tb services referred
                XSSFSheet tbReferredDetails = workbook.createSheet("Tb_Services_Referred");
                int tbReferredXSSFRowNum = 0;
                XSSFRow tbReferredXSSFRow = tbReferredDetails.createRow(tbReferredXSSFRowNum++);
                int tbReferredXSSFCellNum = 0;
                for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                    XSSFCell XSSFCell = tbReferredXSSFRow.createCell(tbReferredXSSFCellNum++);
                    XSSFCell.setCellValue(title);
                }
                for (Referral referral : referrals) {
                    if (!referral.getTbReq().isEmpty()) {
                        for (ServicesReferred referred : referral.getTbReq()) {
                            int count = 0;
                            tbReferredXSSFRow = tbReferredDetails.createRow(tbReferredXSSFRowNum++);
                            XSSFCell id = tbReferredXSSFRow.createCell(count);
                            id.setCellValue(referral.getPatient().getPatientNumber());
                            XSSFCell patientName = tbReferredXSSFRow.createCell(++count);
                            patientName.setCellValue(referral.getPatient().getName());
                            XSSFCell dateOfBirth = tbReferredXSSFRow.createCell(++count);
                            dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                            dateOfBirth.setCellStyle(XSSFCellStyle);
                            XSSFCell age = tbReferredXSSFRow.createCell(++count);
                            age.setCellValue(referral.getPatient().getAge());
                            XSSFCell sex = tbReferredXSSFRow.createCell(++count);
                            sex.setCellValue(referral.getPatient().getGender().getName());
                            XSSFCell province = tbReferredXSSFRow.createCell(++count);
                            province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                            XSSFCell district = tbReferredXSSFRow.createCell(++count);
                            district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                            XSSFCell primaryClinic = tbReferredXSSFRow.createCell(++count);
                            primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                            XSSFCell entry = tbReferredXSSFRow.createCell(++count);
                            if (referral.getDateCreated() != null) {
                                entry.setCellValue(referral.getDateCreated());
                                entry.setCellStyle(XSSFCellStyle);
                            } else {
                                entry.setCellValue("");
                            }
                            XSSFCell referralDate = tbReferredXSSFRow.createCell(++count);
                            referralDate.setCellValue(referral.getReferralDate());
                            referralDate.setCellStyle(XSSFCellStyle);
                            XSSFCell expectedVisitDate = tbReferredXSSFRow.createCell(++count);
                            if (referral.getExpectedVisitDate() != null) {
                                expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                                expectedVisitDate.setCellStyle(XSSFCellStyle);
                            } else {
                                expectedVisitDate.setCellValue("");
                            }
                            XSSFCell organisation = tbReferredXSSFRow.createCell(++count);
                            organisation.setCellValue(referral.getOrganisation());
                            XSSFCell designation = tbReferredXSSFRow.createCell(++count);
                            designation.setCellValue(referral.getDesignation());
                            XSSFCell attendingOfficer = tbReferredXSSFRow.createCell(++count);
                            attendingOfficer.setCellValue(referral.getAttendingOfficer());
                            XSSFCell dateAttended = tbReferredXSSFRow.createCell(++count);
                            if (referral.getDateAttended() != null) {
                                dateAttended.setCellValue(referral.getDateAttended());
                                dateAttended.setCellStyle(XSSFCellStyle);
                            } else {
                                dateAttended.setCellValue("");
                            }
                            XSSFCell actionTaken = tbReferredXSSFRow.createCell(++count);
                            actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                            XSSFCell hivReq = tbReferredXSSFRow.createCell(++count);
                            hivReq.setCellValue(referred.toString());

                            XSSFCell isCats = tbReferredXSSFRow.createCell(++count);
                            isCats.setCellValue(
                                    referral.getPatient().getCat() != null ? referral.getPatient().getCat().getName() : null
                            );
                            XSSFCell youngMumGroup = tbReferredXSSFRow.createCell(++count);
                            youngMumGroup.setCellValue(
                                    referral.getPatient().getYoungMumGroup() != null ? referral.getPatient().getYoungMumGroup().getName() : null
                            );
                            XSSFCell ymd = tbReferredXSSFRow.createCell(++count);
                            ymd.setCellValue(
                                    referral.getPatient().getYoungDadGroup() != null ? referral.getPatient().getYoungDadGroup().getName() : null
                            );
                        }
                    }

                }
                final long tb_start = System.currentTimeMillis();
                System.err.println(" :: Finished Collation of Referral(TB referred) (min)::" + (double) ((tb_start - lab_end) / 60000));

                // add tb services provided
                XSSFSheet tbProvidedDetails = workbook.createSheet("Tb_Services_Provided");
                int tbProvidedXSSFRowNum = 0;
                XSSFRow tbProvidedXSSFRow = tbProvidedDetails.createRow(tbProvidedXSSFRowNum++);
                int tbProvidedXSSFCellNum = 0;
                for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                    XSSFCell XSSFCell = tbProvidedXSSFRow.createCell(tbProvidedXSSFCellNum++);
                    XSSFCell.setCellValue(title);
                }
                for (Referral referral : referrals) {
                    if (!referral.getTbAvailed().isEmpty()) {
                        for (ServicesReferred referred : referral.getTbAvailed()) {
                            int count = 0;
                            tbProvidedXSSFRow = tbProvidedDetails.createRow(tbProvidedXSSFRowNum++);
                            XSSFCell id = tbProvidedXSSFRow.createCell(count);
                            id.setCellValue(referral.getPatient().getPatientNumber());
                            XSSFCell patientName = tbProvidedXSSFRow.createCell(++count);
                            patientName.setCellValue(referral.getPatient().getName());
                            XSSFCell dateOfBirth = tbProvidedXSSFRow.createCell(++count);
                            dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                            dateOfBirth.setCellStyle(XSSFCellStyle);
                            XSSFCell age = tbProvidedXSSFRow.createCell(++count);
                            age.setCellValue(referral.getPatient().getAge());
                            XSSFCell sex = tbProvidedXSSFRow.createCell(++count);
                            sex.setCellValue(referral.getPatient().getGender().getName());
                            XSSFCell province = tbProvidedXSSFRow.createCell(++count);
                            province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                            XSSFCell district = tbProvidedXSSFRow.createCell(++count);
                            district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                            XSSFCell primaryClinic = tbProvidedXSSFRow.createCell(++count);
                            primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                            XSSFCell entry = tbProvidedXSSFRow.createCell(++count);
                            if (referral.getDateCreated() != null) {
                                entry.setCellValue(referral.getDateCreated());
                                entry.setCellStyle(XSSFCellStyle);
                            } else {
                                entry.setCellValue("");
                            }
                            XSSFCell referralDate = tbProvidedXSSFRow.createCell(++count);
                            referralDate.setCellValue(referral.getReferralDate());
                            referralDate.setCellStyle(XSSFCellStyle);
                            XSSFCell expectedVisitDate = tbProvidedXSSFRow.createCell(++count);
                            if (referral.getExpectedVisitDate() != null) {
                                expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                                expectedVisitDate.setCellStyle(XSSFCellStyle);
                            } else {
                                expectedVisitDate.setCellValue("");
                            }
                            XSSFCell organisation = tbProvidedXSSFRow.createCell(++count);
                            organisation.setCellValue(referral.getOrganisation());
                            XSSFCell designation = tbProvidedXSSFRow.createCell(++count);
                            designation.setCellValue(referral.getDesignation());
                            XSSFCell attendingOfficer = tbProvidedXSSFRow.createCell(++count);
                            attendingOfficer.setCellValue(referral.getAttendingOfficer());
                            XSSFCell dateAttended = tbProvidedXSSFRow.createCell(++count);
                            if (referral.getDateAttended() != null) {
                                dateAttended.setCellValue(referral.getDateAttended());
                                dateAttended.setCellStyle(XSSFCellStyle);
                            } else {
                                dateAttended.setCellValue("");
                            }
                            XSSFCell actionTaken = tbProvidedXSSFRow.createCell(++count);
                            actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                            XSSFCell hivReq = tbProvidedXSSFRow.createCell(++count);
                            hivReq.setCellValue(referred.toString());

                            XSSFCell isCats = tbProvidedXSSFRow.createCell(++count);
                            isCats.setCellValue(
                                    referral.getPatient().getCat() != null ? referral.getPatient().getCat().getName() : null
                            );
                            XSSFCell youngMumGroup = tbProvidedXSSFRow.createCell(++count);
                            youngMumGroup.setCellValue(
                                    referral.getPatient().getYoungMumGroup() != null ? referral.getPatient().getYoungMumGroup().getName() : null
                            );
                            XSSFCell ymd = tbProvidedXSSFRow.createCell(++count);
                            ymd.setCellValue(
                                    referral.getPatient().getYoungDadGroup() != null ? referral.getPatient().getYoungDadGroup().getName() : null
                            );
                        }
                    }

                }
                final long tb_end = System.currentTimeMillis();
                System.err.println(":: Finished Collation of Referral(TB availed) (min)::" + (double) ((tb_end - tb_start) / 60000));

                // add psych services referred
                XSSFSheet psychReferredDetails = workbook.createSheet("Psych_Services_Referred");
                int psychReferredXSSFRowNum = 0;
                XSSFRow psychReferredXSSFRow = psychReferredDetails.createRow(psychReferredXSSFRowNum++);
                int psychReferredXSSFCellNum = 0;
                for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                    XSSFCell XSSFCell = psychReferredXSSFRow.createCell(psychReferredXSSFCellNum++);
                    XSSFCell.setCellValue(title);
                }
                for (Referral referral : referrals) {
                    if (!referral.getPsychReq().isEmpty()) {
                        for (ServicesReferred referred : referral.getPsychReq()) {
                            int count = 0;
                            psychReferredXSSFRow = psychReferredDetails.createRow(psychReferredXSSFRowNum++);
                            XSSFCell id = psychReferredXSSFRow.createCell(count);
                            id.setCellValue(referral.getPatient().getPatientNumber());
                            XSSFCell patientName = psychReferredXSSFRow.createCell(++count);
                            patientName.setCellValue(referral.getPatient().getName());
                            XSSFCell dateOfBirth = psychReferredXSSFRow.createCell(++count);
                            dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                            dateOfBirth.setCellStyle(XSSFCellStyle);
                            XSSFCell age = psychReferredXSSFRow.createCell(++count);
                            age.setCellValue(referral.getPatient().getAge());
                            XSSFCell sex = psychReferredXSSFRow.createCell(++count);
                            sex.setCellValue(referral.getPatient().getGender().getName());
                            XSSFCell province = psychReferredXSSFRow.createCell(++count);
                            province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                            XSSFCell district = psychReferredXSSFRow.createCell(++count);
                            district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                            XSSFCell primaryClinic = psychReferredXSSFRow.createCell(++count);
                            primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                            XSSFCell entry = psychReferredXSSFRow.createCell(++count);
                            if (referral.getDateCreated() != null) {
                                entry.setCellValue(referral.getDateCreated());
                                entry.setCellStyle(XSSFCellStyle);
                            } else {
                                entry.setCellValue("");
                            }
                            XSSFCell referralDate = psychReferredXSSFRow.createCell(++count);
                            referralDate.setCellValue(referral.getReferralDate());
                            referralDate.setCellStyle(XSSFCellStyle);
                            XSSFCell expectedVisitDate = psychReferredXSSFRow.createCell(++count);
                            if (referral.getExpectedVisitDate() != null) {
                                expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                                expectedVisitDate.setCellStyle(XSSFCellStyle);
                            } else {
                                expectedVisitDate.setCellValue("");
                            }
                            XSSFCell organisation = psychReferredXSSFRow.createCell(++count);
                            organisation.setCellValue(referral.getOrganisation());
                            XSSFCell designation = psychReferredXSSFRow.createCell(++count);
                            designation.setCellValue(referral.getDesignation());
                            XSSFCell attendingOfficer = psychReferredXSSFRow.createCell(++count);
                            attendingOfficer.setCellValue(referral.getAttendingOfficer());
                            XSSFCell dateAttended = psychReferredXSSFRow.createCell(++count);
                            if (referral.getDateAttended() != null) {
                                dateAttended.setCellValue(referral.getDateAttended());
                                dateAttended.setCellStyle(XSSFCellStyle);
                            } else {
                                dateAttended.setCellValue("");
                            }
                            XSSFCell actionTaken = psychReferredXSSFRow.createCell(++count);
                            actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                            XSSFCell hivReq = psychReferredXSSFRow.createCell(++count);
                            hivReq.setCellValue(referred.toString());

                            XSSFCell isCats = psychReferredXSSFRow.createCell(++count);
                            isCats.setCellValue(
                                    referral.getPatient().getCat() != null ? referral.getPatient().getCat().getName() : null
                            );
                            XSSFCell youngMumGroup = psychReferredXSSFRow.createCell(++count);
                            youngMumGroup.setCellValue(
                                    referral.getPatient().getYoungMumGroup() != null ? referral.getPatient().getYoungMumGroup().getName() : null
                            );
                            XSSFCell ymd = psychReferredXSSFRow.createCell(++count);
                            ymd.setCellValue(
                                    referral.getPatient().getYoungDadGroup() != null ? referral.getPatient().getYoungDadGroup().getName() : null
                            );
                        }
                    }

                }
                final long ps_start = System.currentTimeMillis();
                System.err.println(":: Finished Collation of Referral(PSYCHO referred) (min)::" + (double) ((ps_start - tb_end) / 60000));

                // add psych services provided
                XSSFSheet psychProvidedDetails = workbook.createSheet("Psych_Services_Provided");
                int psychProvidedXSSFRowNum = 0;
                XSSFRow psychProvidedXSSFRow = psychProvidedDetails.createRow(psychProvidedXSSFRowNum++);
                int psychProvidedXSSFCellNum = 0;
                for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                    XSSFCell XSSFCell = psychProvidedXSSFRow.createCell(psychProvidedXSSFCellNum++);
                    XSSFCell.setCellValue(title);
                }
                for (Referral referral : referrals) {
                    if (!referral.getPsychAvailed().isEmpty()) {
                        for (ServicesReferred referred : referral.getPsychAvailed()) {
                            int count = 0;
                            psychProvidedXSSFRow = psychProvidedDetails.createRow(psychProvidedXSSFRowNum++);
                            XSSFCell id = psychProvidedXSSFRow.createCell(count);
                            id.setCellValue(referral.getPatient().getPatientNumber());
                            XSSFCell patientName = psychProvidedXSSFRow.createCell(++count);
                            patientName.setCellValue(referral.getPatient().getName());
                            XSSFCell dateOfBirth = psychProvidedXSSFRow.createCell(++count);
                            dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                            dateOfBirth.setCellStyle(XSSFCellStyle);
                            XSSFCell age = psychProvidedXSSFRow.createCell(++count);
                            age.setCellValue(referral.getPatient().getAge());
                            XSSFCell sex = psychProvidedXSSFRow.createCell(++count);
                            sex.setCellValue(referral.getPatient().getGender().getName());
                            XSSFCell province = psychProvidedXSSFRow.createCell(++count);
                            province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                            XSSFCell district = psychProvidedXSSFRow.createCell(++count);
                            district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                            XSSFCell primaryClinic = psychProvidedXSSFRow.createCell(++count);
                            primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                            XSSFCell entry = psychProvidedXSSFRow.createCell(++count);
                            if (referral.getDateCreated() != null) {
                                entry.setCellValue(referral.getDateCreated());
                                entry.setCellStyle(XSSFCellStyle);
                            } else {
                                entry.setCellValue("");
                            }
                            XSSFCell referralDate = psychProvidedXSSFRow.createCell(++count);
                            referralDate.setCellValue(referral.getReferralDate());
                            referralDate.setCellStyle(XSSFCellStyle);
                            XSSFCell expectedVisitDate = psychProvidedXSSFRow.createCell(++count);
                            if (referral.getExpectedVisitDate() != null) {
                                expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                                expectedVisitDate.setCellStyle(XSSFCellStyle);
                            } else {
                                expectedVisitDate.setCellValue("");
                            }
                            XSSFCell organisation = psychProvidedXSSFRow.createCell(++count);
                            organisation.setCellValue(referral.getOrganisation());
                            XSSFCell designation = psychProvidedXSSFRow.createCell(++count);
                            designation.setCellValue(referral.getDesignation());
                            XSSFCell attendingOfficer = psychProvidedXSSFRow.createCell(++count);
                            attendingOfficer.setCellValue(referral.getAttendingOfficer());
                            XSSFCell dateAttended = psychProvidedXSSFRow.createCell(++count);
                            if (referral.getDateAttended() != null) {
                                dateAttended.setCellValue(referral.getDateAttended());
                                dateAttended.setCellStyle(XSSFCellStyle);
                            } else {
                                dateAttended.setCellValue("");
                            }
                            XSSFCell actionTaken = psychProvidedXSSFRow.createCell(++count);
                            actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                            XSSFCell hivReq = psychProvidedXSSFRow.createCell(++count);
                            hivReq.setCellValue(referred.toString());

                            XSSFCell isCats = psychProvidedXSSFRow.createCell(++count);
                            isCats.setCellValue(
                                    referral.getPatient().getCat() != null ? referral.getPatient().getCat().getName() : null
                            );
                            XSSFCell youngMumGroup = psychProvidedXSSFRow.createCell(++count);
                            youngMumGroup.setCellValue(
                                    referral.getPatient().getYoungMumGroup() != null ? referral.getPatient().getYoungMumGroup().getName() : null
                            );
                            XSSFCell ymd = psychProvidedXSSFRow.createCell(++count);
                            ymd.setCellValue(
                                    referral.getPatient().getYoungDadGroup() != null ? referral.getPatient().getYoungDadGroup().getName() : null
                            );
                        }
                    }

                }
                final long ps_end = System.currentTimeMillis();
                System.err.println(" :: Finished Collation of Referral(PSYCHO availed) (min)::" + (double) ((ps_end - ps_start) / 60000));

                // add legal services referred
                XSSFSheet legalReferredDetails = workbook.createSheet("Legal_Services_Referred");
                int legalReferredXSSFRowNum = 0;
                XSSFRow legalReferredXSSFRow = legalReferredDetails.createRow(legalReferredXSSFRowNum++);
                int legalReferredXSSFCellNum = 0;
                for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                    XSSFCell XSSFCell = legalReferredXSSFRow.createCell(legalReferredXSSFCellNum++);
                    XSSFCell.setCellValue(title);
                }
                for (Referral referral : referrals) {
                    if (!referral.getLegalReq().isEmpty()) {
                        for (ServicesReferred referred : referral.getLegalReq()) {
                            int count = 0;
                            legalReferredXSSFRow = legalReferredDetails.createRow(legalReferredXSSFRowNum++);
                            XSSFCell id = legalReferredXSSFRow.createCell(count);
                            id.setCellValue(referral.getPatient().getPatientNumber());
                            XSSFCell patientName = legalReferredXSSFRow.createCell(++count);
                            patientName.setCellValue(referral.getPatient().getName());
                            XSSFCell dateOfBirth = legalReferredXSSFRow.createCell(++count);
                            dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                            dateOfBirth.setCellStyle(XSSFCellStyle);
                            XSSFCell age = legalReferredXSSFRow.createCell(++count);
                            age.setCellValue(referral.getPatient().getAge());
                            XSSFCell sex = legalReferredXSSFRow.createCell(++count);
                            sex.setCellValue(referral.getPatient().getGender().getName());
                            XSSFCell province = legalReferredXSSFRow.createCell(++count);
                            province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                            XSSFCell district = legalReferredXSSFRow.createCell(++count);
                            district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                            XSSFCell primaryClinic = legalReferredXSSFRow.createCell(++count);
                            primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                            XSSFCell entry = legalReferredXSSFRow.createCell(++count);
                            if (referral.getDateCreated() != null) {
                                entry.setCellValue(referral.getDateCreated());
                                entry.setCellStyle(XSSFCellStyle);
                            } else {
                                entry.setCellValue("");
                            }
                            XSSFCell referralDate = legalReferredXSSFRow.createCell(++count);
                            referralDate.setCellValue(referral.getReferralDate());
                            referralDate.setCellStyle(XSSFCellStyle);
                            XSSFCell expectedVisitDate = legalReferredXSSFRow.createCell(++count);
                            if (referral.getExpectedVisitDate() != null) {
                                expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                                expectedVisitDate.setCellStyle(XSSFCellStyle);
                            } else {
                                expectedVisitDate.setCellValue("");
                            }
                            XSSFCell organisation = legalReferredXSSFRow.createCell(++count);
                            organisation.setCellValue(referral.getOrganisation());
                            XSSFCell designation = legalReferredXSSFRow.createCell(++count);
                            designation.setCellValue(referral.getDesignation());
                            XSSFCell attendingOfficer = legalReferredXSSFRow.createCell(++count);
                            attendingOfficer.setCellValue(referral.getAttendingOfficer());
                            XSSFCell dateAttended = legalReferredXSSFRow.createCell(++count);
                            if (referral.getDateAttended() != null) {
                                dateAttended.setCellValue(referral.getDateAttended());
                                dateAttended.setCellStyle(XSSFCellStyle);
                            } else {
                                dateAttended.setCellValue("");
                            }
                            XSSFCell actionTaken = legalReferredXSSFRow.createCell(++count);
                            actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                            XSSFCell hivReq = legalReferredXSSFRow.createCell(++count);
                            hivReq.setCellValue(referred.toString());

                            XSSFCell isCats = legalReferredXSSFRow.createCell(++count);
                            isCats.setCellValue(
                                    referral.getPatient().getCat() != null ? referral.getPatient().getCat().getName() : null
                            );
                            XSSFCell youngMumGroup = legalReferredXSSFRow.createCell(++count);
                            youngMumGroup.setCellValue(
                                    referral.getPatient().getYoungMumGroup() != null ? referral.getPatient().getYoungMumGroup().getName() : null
                            );
                            XSSFCell ymd = legalReferredXSSFRow.createCell(++count);
                            ymd.setCellValue(
                                    referral.getPatient().getYoungDadGroup() != null ? referral.getPatient().getYoungDadGroup().getName() : null
                            );
                        }
                    }

                }
                final long legal_start = System.currentTimeMillis();
                System.err.println(":: Finished Collation of Referral(LEGAL referred) (min)::" + (double) ((legal_start - ps_end) / 60000));

                // add legal services provided
                XSSFSheet legalProvidedDetails = workbook.createSheet("Legal_Services_Provided");
                int legalProvidedXSSFRowNum = 0;
                XSSFRow legalProvidedXSSFRow = legalProvidedDetails.createRow(legalProvidedXSSFRowNum++);
                int legalProvidedXSSFCellNum = 0;
                for (String title : DatabaseHeader.REFERRAL_SPECIFI_HEADER) {
                    XSSFCell XSSFCell = legalProvidedXSSFRow.createCell(legalProvidedXSSFCellNum++);
                    XSSFCell.setCellValue(title);
                }
                for (Referral referral : referrals) {
                    if (!referral.getLegalAvailed().isEmpty()) {
                        for (ServicesReferred referred : referral.getLegalAvailed()) {
                            int count = 0;
                            legalProvidedXSSFRow = legalProvidedDetails.createRow(legalProvidedXSSFRowNum++);
                            XSSFCell id = legalProvidedXSSFRow.createCell(count);
                            id.setCellValue(referral.getPatient().getPatientNumber());
                            XSSFCell patientName = legalProvidedXSSFRow.createCell(++count);
                            patientName.setCellValue(referral.getPatient().getName());
                            XSSFCell dateOfBirth = legalProvidedXSSFRow.createCell(++count);
                            dateOfBirth.setCellValue(referral.getPatient().getDateOfBirth());
                            dateOfBirth.setCellStyle(XSSFCellStyle);
                            XSSFCell age = legalProvidedXSSFRow.createCell(++count);
                            age.setCellValue(referral.getPatient().getAge());
                            XSSFCell sex = legalProvidedXSSFRow.createCell(++count);
                            sex.setCellValue(referral.getPatient().getGender().getName());
                            XSSFCell province = legalProvidedXSSFRow.createCell(++count);
                            province.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                            XSSFCell district = legalProvidedXSSFRow.createCell(++count);
                            district.setCellValue(referral.getPatient().getPrimaryClinic().getDistrict().getName());
                            XSSFCell primaryClinic = legalProvidedXSSFRow.createCell(++count);
                            primaryClinic.setCellValue(referral.getPatient().getPrimaryClinic().getName());
                            XSSFCell entry = legalProvidedXSSFRow.createCell(++count);
                            if (referral.getDateCreated() != null) {
                                entry.setCellValue(referral.getDateCreated());
                                entry.setCellStyle(XSSFCellStyle);
                            } else {
                                entry.setCellValue("");
                            }
                            XSSFCell referralDate = legalProvidedXSSFRow.createCell(++count);
                            referralDate.setCellValue(referral.getReferralDate());
                            referralDate.setCellStyle(XSSFCellStyle);
                            XSSFCell expectedVisitDate = legalProvidedXSSFRow.createCell(++count);
                            if (referral.getExpectedVisitDate() != null) {
                                expectedVisitDate.setCellValue(referral.getExpectedVisitDate());
                                expectedVisitDate.setCellStyle(XSSFCellStyle);
                            } else {
                                expectedVisitDate.setCellValue("");
                            }
                            XSSFCell organisation = legalProvidedXSSFRow.createCell(++count);
                            organisation.setCellValue(referral.getOrganisation());
                            XSSFCell designation = legalProvidedXSSFRow.createCell(++count);
                            designation.setCellValue(referral.getDesignation());
                            XSSFCell attendingOfficer = legalProvidedXSSFRow.createCell(++count);
                            attendingOfficer.setCellValue(referral.getAttendingOfficer());
                            XSSFCell dateAttended = legalProvidedXSSFRow.createCell(++count);
                            if (referral.getDateAttended() != null) {
                                dateAttended.setCellValue(referral.getDateAttended());
                                dateAttended.setCellStyle(XSSFCellStyle);
                            } else {
                                dateAttended.setCellValue("");
                            }
                            XSSFCell actionTaken = legalProvidedXSSFRow.createCell(++count);
                            actionTaken.setCellValue(referral.getActionTaken() != null ? referral.getActionTaken().getName() : "");
                            XSSFCell hivReq = legalProvidedXSSFRow.createCell(++count);
                            hivReq.setCellValue(referred.toString());

                            XSSFCell isCats = legalProvidedXSSFRow.createCell(++count);
                            isCats.setCellValue(
                                    referral.getPatient().getCat() != null ? referral.getPatient().getCat().getName() : null
                            );
                            XSSFCell youngMumGroup = legalProvidedXSSFRow.createCell(++count);
                            youngMumGroup.setCellValue(
                                    referral.getPatient().getYoungMumGroup() != null ? referral.getPatient().getYoungMumGroup().getName() : null
                            );
                            XSSFCell ymd = legalProvidedXSSFRow.createCell(++count);
                            ymd.setCellValue(
                                    referral.getPatient().getYoungDadGroup() != null ? referral.getPatient().getYoungDadGroup().getName() : null
                            );
                        }
                    }

                }
                final long legal_end = System.currentTimeMillis();*/




//        System.err.println("User:"+dto2.getCurrentUser().getUserName()+" <=> District:"+dto2.getCurrentUser().getDistrict()+" :: Finished Collation of Referral(LEGAL availed) (min)::" + (double)((legal_end-legal_start)/60000));
////
////        // add patient dependants
////       /* XSSFSheet dependantDetails = workbook.createSheet("Patient_Dependants");
////        int dependantXSSFRowNum = 0;
////        XSSFRow dependantXSSFRow = dependantDetails.createRow(dependantXSSFRowNum++);
////        int dependantXSSFCellNum = 0;
////        for (String title : DatabaseHeader.DEPENDANT_HEADER) {
////            XSSFCell XSSFCell = dependantXSSFRow.createCell(dependantXSSFCellNum++);
////            XSSFCell.setCellValue(title);
////        }
////        for (Dependent dependent : dependents) {
////            int count = 0;
////            dependantXSSFRow = dependantDetails.createRow(dependantXSSFRowNum++);
////            XSSFCell id = dependantXSSFRow.createCell(count);
////            id.setCellValue(dependent.getPatient().getPatientNumber());
////            XSSFCell patientName = dependantXSSFRow.createCell(++count);
////            patientName.setCellValue(dependent.getPatient().getName());
////            XSSFCell patientDateOfBirth = dependantXSSFRow.createCell(++count);
////            patientDateOfBirth.setCellValue(dependent.getPatient().getDateOfBirth());
////            patientDateOfBirth.setCellStyle(XSSFCellStyle);
////            XSSFCell age = dependantXSSFRow.createCell(++count);
////            age.setCellValue(dependent.getPatient().getAge());
////            XSSFCell sex = dependantXSSFRow.createCell(++count);
////            sex.setCellValue(dependent.getPatient().getGender().getName());
////            XSSFCell dependantName = dependantXSSFRow.createCell(++count);
////            dependantName.setCellValue(dependent.getName());
////            XSSFCell province = dependantXSSFRow.createCell(++count);
////            province.setCellValue(dependent.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
////            XSSFCell district = dependantXSSFRow.createCell(++count);
////            district.setCellValue(dependent.getPatient().getPrimaryClinic().getDistrict().getName());
////            XSSFCell primaryClinic = dependantXSSFRow.createCell(++count);
////            primaryClinic.setCellValue(dependent.getPatient().getPrimaryClinic().getName());
////            XSSFCell gender = dependantXSSFRow.createCell(++count);
////            gender.setCellValue(dependent.getGender().getName());
////            XSSFCell dateOfBirth = dependantXSSFRow.createCell(++count);
////            dateOfBirth.setCellValue(dependent.getDateOfBirth());
////            dateOfBirth.setCellStyle(XSSFCellStyle);
////            XSSFCell hivStatus = dependantXSSFRow.createCell(++count);
////            hivStatus.setCellValue(dependent.getHivStatus() != null ? dependent.getHivStatus().getName() : "");
////            XSSFCell isCats = dependantXSSFRow.createCell(++count);
////            isCats.setCellValue(
////            		dependent.getPatient().getCat() != null ? dependent.getPatient().getCat().getName() : null
////            );
////            XSSFCell youngMumGroup = dependantXSSFRow.createCell(++count);
////            youngMumGroup.setCellValue(
////            		dependent.getPatient().getYoungMumGroup() != null ? dependent.getPatient().getYoungMumGroup().getName() : null
////            );
////
////            XSSFCell ymd = dependantXSSFRow.createCell(++count);
////            ymd.setCellValue(
////                    dependent.getPatient().getYoungDadGroup() != null ? dependent.getPatient().getYoungDadGroup().getName() : null
////            );
////
////        }
////        // add patient opportunistic infections
////        XSSFSheet opportunisticInfectionDetails = workbook.createSheet("Patient_Opportunistic_Infections");
////        int opportunisticInfectionXSSFRowNum = 0;
////        XSSFRow opportunisticInfectionXSSFRow = opportunisticInfectionDetails.createRow(opportunisticInfectionXSSFRowNum++);
////        int opportunisticInfectionXSSFCellNum = 0;
////        for (String title : DatabaseHeader.OPPORTUNISTIC_INFECTION_HEADER) {
////            XSSFCell XSSFCell = opportunisticInfectionXSSFRow.createCell(opportunisticInfectionXSSFCellNum++);
////            XSSFCell.setCellValue(title);
////        }
////        for (ChronicInfectionItem chronicInfectionItem : chronicInfectionItems) {
////            int count = 0;
////            opportunisticInfectionXSSFRow = opportunisticInfectionDetails.createRow(opportunisticInfectionXSSFRowNum++);
////            XSSFCell id = opportunisticInfectionXSSFRow.createCell(count);
////            id.setCellValue(chronicInfectionItem.getPatient().getPatientNumber());
////            XSSFCell patientName = opportunisticInfectionXSSFRow.createCell(++count);
////            XSSFCell dateOfBirth = opportunisticInfectionXSSFRow.createCell(++count);
////            dateOfBirth.setCellValue(chronicInfectionItem.getPatient().getDateOfBirth());
////            dateOfBirth.setCellStyle(XSSFCellStyle);
////            XSSFCell age = opportunisticInfectionXSSFRow.createCell(++count);
////            age.setCellValue(chronicInfectionItem.getPatient().getAge());
////            XSSFCell sex = opportunisticInfectionXSSFRow.createCell(++count);
////            sex.setCellValue(chronicInfectionItem.getPatient().getGender().getName());
////            XSSFCell province = opportunisticInfectionXSSFRow.createCell(++count);
////            province.setCellValue(chronicInfectionItem.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
////            XSSFCell district = opportunisticInfectionXSSFRow.createCell(++count);
////            district.setCellValue(chronicInfectionItem.getPatient().getPrimaryClinic().getDistrict().getName());
////            XSSFCell primaryClinic = opportunisticInfectionXSSFRow.createCell(++count);
////            primaryClinic.setCellValue(chronicInfectionItem.getPatient().getPrimaryClinic().getName());
////            XSSFCell entry = opportunisticInfectionXSSFRow.createCell(++count);
////            if (chronicInfectionItem.getDateCreated() != null) {
////                entry.setCellValue(chronicInfectionItem.getDateCreated());
////                entry.setCellStyle(XSSFCellStyle);
////            } else {
////                entry.setCellValue("");
////            }
////            patientName.setCellValue(chronicInfectionItem.getPatient().getName());
////            XSSFCell infection = opportunisticInfectionXSSFRow.createCell(++count);
////            infection.setCellValue(chronicInfectionItem.getChronicInfection().getName());
////            XSSFCell dateDiagnosed = opportunisticInfectionXSSFRow.createCell(++count);
////            if (chronicInfectionItem.getInfectionDate() != null) {
////                dateDiagnosed.setCellValue(chronicInfectionItem.getInfectionDate());
////                dateDiagnosed.setCellStyle(XSSFCellStyle);
////            } else {
////                dateDiagnosed.setCellValue("");
////            }
////            XSSFCell medication = opportunisticInfectionXSSFRow.createCell(++count);
////            medication.setCellValue(chronicInfectionItem.getMedication());
////            XSSFCell currentStatus = opportunisticInfectionXSSFRow.createCell(++count);
////            currentStatus.setCellValue(chronicInfectionItem.getCurrentStatus() != null ? chronicInfectionItem.getCurrentStatus().getName() : "");
////            XSSFCell isCats = opportunisticInfectionXSSFRow.createCell(++count);
////            isCats.setCellValue(
////            		chronicInfectionItem.getPatient().getCat() != null ? chronicInfectionItem.getPatient().getCat().getName() : null
////            );
////            XSSFCell youngMumGroup = opportunisticInfectionXSSFRow.createCell(++count);
////            youngMumGroup.setCellValue(
////            		chronicInfectionItem.getPatient().getYoungMumGroup() != null ? chronicInfectionItem.getPatient().getYoungMumGroup().getName() : null
////            );
////
////            XSSFCell ymd = opportunisticInfectionXSSFRow.createCell(++count);
////            ymd.setCellValue(
////                    chronicInfectionItem.getPatient().getYoungDadGroup() != null ? chronicInfectionItem.getPatient().getYoungDadGroup().getName() : null
////            );
////        }
////        // add patient hiv-coinfections
////        XSSFSheet hivCoInfectionDetails = workbook.createSheet("Patient_HIV_CO_Infections");
////        int hivCoInfectionXSSFRowNum = 0;
////        XSSFRow hivCoInfectionXSSFRow = hivCoInfectionDetails.createRow(hivCoInfectionXSSFRowNum++);
////        int hivCoInfectionXSSFCellNum = 0;
////        for (String title : DatabaseHeader.HIV_CO_INFECTION_HEADER) {
////            XSSFCell XSSFCell = hivCoInfectionXSSFRow.createCell(hivCoInfectionXSSFCellNum++);
////            XSSFCell.setCellValue(title);
////        }
////        for (HivConInfectionItem hivConInfectionItem : hivConInfectionItems) {
////            int count = 0;
////            hivCoInfectionXSSFRow = hivCoInfectionDetails.createRow(hivCoInfectionXSSFRowNum++);
////            XSSFCell id = hivCoInfectionXSSFRow.createCell(count);
////            id.setCellValue(hivConInfectionItem.getPatient().getPatientNumber());
////            XSSFCell patientName = hivCoInfectionXSSFRow.createCell(++count);
////            patientName.setCellValue(hivConInfectionItem.getPatient().getName());
////            XSSFCell dateOfBirth = hivCoInfectionXSSFRow.createCell(++count);
////            dateOfBirth.setCellValue(hivConInfectionItem.getPatient().getDateOfBirth());
////            dateOfBirth.setCellStyle(XSSFCellStyle);
////            XSSFCell age = hivCoInfectionXSSFRow.createCell(++count);
////            age.setCellValue(hivConInfectionItem.getPatient().getAge());
////            XSSFCell sex = hivCoInfectionXSSFRow.createCell(++count);
////            sex.setCellValue(hivConInfectionItem.getPatient().getGender().getName());
////            XSSFCell province = hivCoInfectionXSSFRow.createCell(++count);
////            province.setCellValue(hivConInfectionItem.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
////            XSSFCell district = hivCoInfectionXSSFRow.createCell(++count);
////            district.setCellValue(hivConInfectionItem.getPatient().getPrimaryClinic().getDistrict().getName());
////            XSSFCell primaryClinic = hivCoInfectionXSSFRow.createCell(++count);
////            primaryClinic.setCellValue(hivConInfectionItem.getPatient().getPrimaryClinic().getName());
////            XSSFCell entry = hivCoInfectionXSSFRow.createCell(++count);
////            if (hivConInfectionItem.getDateCreated() != null) {
////                entry.setCellValue(hivConInfectionItem.getDateCreated());
////                entry.setCellStyle(XSSFCellStyle);
////            } else {
////                entry.setCellValue("");
////            }
////            XSSFCell infection = hivCoInfectionXSSFRow.createCell(++count);
////            infection.setCellValue(hivConInfectionItem.getHivCoInfection().getName());
////            XSSFCell dateDiagnosed = hivCoInfectionXSSFRow.createCell(++count);
////            if (hivConInfectionItem.getInfectionDate() != null) {
////                dateDiagnosed.setCellValue(hivConInfectionItem.getInfectionDate());
////                dateDiagnosed.setCellStyle(XSSFCellStyle);
////            } else {
////                dateDiagnosed.setCellValue("");
////            }
////            XSSFCell medication = hivCoInfectionXSSFRow.createCell(++count);
////            medication.setCellValue(hivConInfectionItem.getMedication());
////            XSSFCell resolution = hivCoInfectionXSSFRow.createCell(++count);
////            resolution.setCellValue(hivConInfectionItem.getResolution());
////            XSSFCell isCats = hivCoInfectionXSSFRow.createCell(++count);
////            isCats.setCellValue(
////            		hivConInfectionItem.getPatient().getCat() != null ? hivConInfectionItem.getPatient().getCat().getName() : null
////            );
////            XSSFCell youngMumGroup = hivCoInfectionXSSFRow.createCell(++count);
////            youngMumGroup.setCellValue(
////            		hivConInfectionItem.getPatient().getYoungMumGroup() != null ? hivConInfectionItem.getPatient().getYoungMumGroup().getName() : null
////            );
////            XSSFCell ymd = hivCoInfectionXSSFRow.createCell(++count);
////            ymd.setCellValue(
////                    hivConInfectionItem.getPatient().getYoungDadGroup() != null ? hivConInfectionItem.getPatient().getYoungDadGroup().getName() : null
////            );
////        }
////        // add patient mental health
////        XSSFSheet mentalHealthDetails = workbook.createSheet("Patient_Mental_Health");
////        int mentalHealthXSSFRowNum = 0;
////        XSSFRow mentalHealthXSSFRow = mentalHealthDetails.createRow(mentalHealthXSSFRowNum++);
////        int mentalHealthXSSFCellNum = 0;
////        for (String title : DatabaseHeader.MENTAL_HIST_HEADER) {
////            XSSFCell XSSFCell = mentalHealthXSSFRow.createCell(mentalHealthXSSFCellNum++);
////            XSSFCell.setCellValue(title);
////        }
////        for (MentalHealthItem mentalHealthItem : mentalHealthItems) {
////            int count = 0;
////            mentalHealthXSSFRow = mentalHealthDetails.createRow(mentalHealthXSSFRowNum++);
////            XSSFCell id = mentalHealthXSSFRow.createCell(count);
////            id.setCellValue(mentalHealthItem.getPatient().getPatientNumber());
////            XSSFCell patientName = mentalHealthXSSFRow.createCell(++count);
////            XSSFCell dateOfBirth = mentalHealthXSSFRow.createCell(++count);
////            dateOfBirth.setCellValue(mentalHealthItem.getPatient().getDateOfBirth());
////            dateOfBirth.setCellStyle(XSSFCellStyle);
////            XSSFCell age = mentalHealthXSSFRow.createCell(++count);
////            age.setCellValue(mentalHealthItem.getPatient().getAge());
////            XSSFCell sex = mentalHealthXSSFRow.createCell(++count);
////            sex.setCellValue(mentalHealthItem.getPatient().getGender().getName());
////            patientName.setCellValue(mentalHealthItem.getPatient().getName());
////            XSSFCell province = mentalHealthXSSFRow.createCell(++count);
////            province.setCellValue(mentalHealthItem.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
////            XSSFCell district = mentalHealthXSSFRow.createCell(++count);
////            district.setCellValue(mentalHealthItem.getPatient().getPrimaryClinic().getDistrict().getName());
////            XSSFCell primaryClinic = mentalHealthXSSFRow.createCell(++count);
////            primaryClinic.setCellValue(mentalHealthItem.getPatient().getPrimaryClinic().getName());
////            XSSFCell entry = mentalHealthXSSFRow.createCell(++count);
////            if (mentalHealthItem.getDateCreated() != null) {
////                entry.setCellValue(mentalHealthItem.getDateCreated());
////                entry.setCellStyle(XSSFCellStyle);
////            } else {
////                entry.setCellValue("");
////            }
////            XSSFCell mentalHealth = mentalHealthXSSFRow.createCell(++count);
////            mentalHealth.setCellValue(mentalHealthItem.getMentalHealth().getName());
////            XSSFCell past = mentalHealthXSSFRow.createCell(++count);
////            past.setCellValue(mentalHealthItem.getPast());
////            XSSFCell current = mentalHealthXSSFRow.createCell(++count);
////            current.setCellValue(mentalHealthItem.getCurrent());
////            XSSFCell receiveProfessionalHelp = mentalHealthXSSFRow.createCell(++count);
////            receiveProfessionalHelp.setCellValue(
////                    mentalHealthItem.getReceivedProfessionalHelp() != null
////                    ? mentalHealthItem.getReceivedProfessionalHelp().getName() : ""
////            );
////            XSSFCell helpStartDate = mentalHealthXSSFRow.createCell(++count);
////            if (mentalHealthItem.getProfHelpStart() != null) {
////                helpStartDate.setCellValue(mentalHealthItem.getProfHelpStart());
////                helpStartDate.setCellStyle(XSSFCellStyle);
////            } else {
////                helpStartDate.setCellValue("");
////            }
////            XSSFCell helpEndDate = mentalHealthXSSFRow.createCell(++count);
////            if (mentalHealthItem.getProfHelpEnd() != null) {
////                helpEndDate.setCellValue(mentalHealthItem.getProfHelpEnd());
////                helpEndDate.setCellStyle(XSSFCellStyle);
////            } else {
////                helpEndDate.setCellValue("");
////            }
////            XSSFCell medication = mentalHealthXSSFRow.createCell(++count);
////            medication.setCellValue(mentalHealthItem.getMedication());
////
////            XSSFCell hosp = mentalHealthXSSFRow.createCell(++count);
////            hosp.setCellValue(
////                    mentalHealthItem.getBeenHospitalized() != null
////                    ? mentalHealthItem.getBeenHospitalized().getName() : ""
////            );
////            XSSFCell desc = mentalHealthXSSFRow.createCell(++count);
////            desc.setCellValue(mentalHealthItem.getMentalHistText());
////            XSSFCell isCats = mentalHealthXSSFRow.createCell(++count);
////            isCats.setCellValue(
////            		mentalHealthItem.getPatient().getCat() != null ? mentalHealthItem.getPatient().getCat().getName() : null
////            );
////            XSSFCell youngMumGroup = mentalHealthXSSFRow.createCell(++count);
////            youngMumGroup.setCellValue(
////            		mentalHealthItem.getPatient().getYoungMumGroup() != null ? mentalHealthItem.getPatient().getYoungMumGroup().getName() : null
////            );
////            XSSFCell ymd = mentalHealthXSSFRow.createCell(++count);
////            ymd.setCellValue(
////                    mentalHealthItem.getPatient().getYoungDadGroup() != null ? mentalHealthItem.getPatient().getYoungDadGroup().getName() : null
////            );
////        }
////        // add patient obstetrics
////        XSSFSheet obsDetails = workbook.createSheet("Patient_Obstetric_Hist");
////        int obsXSSFRowNum = 0;
////        XSSFRow obsXSSFRow = obsDetails.createRow(obsXSSFRowNum++);
////        int obsXSSFCellNum = 0;
////        for (String title : DatabaseHeader.OBSTERIC_HIST_HEADER) {
////            XSSFCell XSSFCell = obsXSSFRow.createCell(obsXSSFCellNum++);
////            XSSFCell.setCellValue(title);
////        }
////        for (ObstercHist obstercHist : obstercHists) {
////            int count = 0;
////            obsXSSFRow = obsDetails.createRow(obsXSSFRowNum++);
////            XSSFCell id = obsXSSFRow.createCell(count);
////            id.setCellValue(obstercHist.getPatient().getPatientNumber());
////            XSSFCell patientName = obsXSSFRow.createCell(++count);
////            patientName.setCellValue(obstercHist.getPatient().getName());
////            XSSFCell dateOfBirth = obsXSSFRow.createCell(++count);
////            dateOfBirth.setCellValue(obstercHist.getPatient().getDateOfBirth());
////            dateOfBirth.setCellStyle(XSSFCellStyle);
////            XSSFCell age = obsXSSFRow.createCell(++count);
////            age.setCellValue(obstercHist.getPatient().getAge());
////            XSSFCell sex = obsXSSFRow.createCell(++count);
////            sex.setCellValue(obstercHist.getPatient().getGender().getName());
////            XSSFCell province = obsXSSFRow.createCell(++count);
////            province.setCellValue(obstercHist.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
////            XSSFCell district = obsXSSFRow.createCell(++count);
////            district.setCellValue(obstercHist.getPatient().getPrimaryClinic().getDistrict().getName());
////            XSSFCell primaryClinic = obsXSSFRow.createCell(++count);
////            primaryClinic.setCellValue(obstercHist.getPatient().getPrimaryClinic().getName());
////            XSSFCell entry = obsXSSFRow.createCell(++count);
////            if (obstercHist.getDateCreated() != null) {
////                entry.setCellValue(obstercHist.getDateCreated());
////                entry.setCellStyle(XSSFCellStyle);
////            } else {
////                entry.setCellValue("");
////            }
////            XSSFCell liveWith = obsXSSFRow.createCell(++count);
////            liveWith.setCellValue(obstercHist.getPregnant().getName());
////            XSSFCell breatFeeding = obsXSSFRow.createCell(++count);
////            breatFeeding.setCellValue(obstercHist.getBreafFeedingCurrent() != null ? obstercHist.getBreafFeedingCurrent().getName() : "");
////            XSSFCell currentPreg = obsXSSFRow.createCell(++count);
////            currentPreg.setCellValue(
////                    obstercHist.getPregCurrent() != null
////                    ? obstercHist.getPregCurrent().getName() : ""
////            );
////            XSSFCell numAncVisit = obsXSSFRow.createCell(++count);
////            numAncVisit.setCellValue(
////                    obstercHist.getNumberOfANCVisit() != null
////                    ? obstercHist.getNumberOfANCVisit().getName() : ""
////            );
////            XSSFCell gestAge = obsXSSFRow.createCell(++count);
////            gestAge.setCellValue(
////                    obstercHist.getGestationalAge() != null
////                    ? obstercHist.getGestationalAge().getName() : ""
////            );
////            XSSFCell artStarted = obsXSSFRow.createCell(++count);
////            artStarted.setCellValue(
////                    obstercHist.getArtStarted() != null
////                    ? obstercHist.getArtStarted().getName() : ""
////            );
////            XSSFCell numChildren = obsXSSFRow.createCell(++count);
////            numChildren.setCellValue(obstercHist.getChildren() != null ? obstercHist.getChildren() : 0);
////            XSSFCell isCats = obsXSSFRow.createCell(++count);
////            isCats.setCellValue(
////            		obstercHist.getPatient().getCat() != null ? obstercHist.getPatient().getCat().getName() : null
////            );
////            XSSFCell youngMumGroup = obsXSSFRow.createCell(++count);
////            youngMumGroup.setCellValue(
////            		obstercHist.getPatient().getYoungMumGroup() != null ? obstercHist.getPatient().getYoungMumGroup().getName() : null
////            );
////            XSSFCell ymd = obsXSSFRow.createCell(++count);
////            ymd.setCellValue(
////                    obstercHist.getPatient().getYoungDadGroup() != null ? obstercHist.getPatient().getYoungDadGroup().getName() : null
////            );
////        }
////        // add patient social history
////        XSSFSheet socialHistDetails = workbook.createSheet("Patient_Social_Hist");
////        int socialHistXSSFRowNum = 0;
////        XSSFRow socialHistXSSFRow = socialHistDetails.createRow(socialHistXSSFRowNum++);
////        int socialHistXSSFCellNum = 0;
////        for (String title : DatabaseHeader.SOCIAL_HISTORY_HEADER) {
////            XSSFCell XSSFCell = socialHistXSSFRow.createCell(socialHistXSSFCellNum++);
////            XSSFCell.setCellValue(title);
////        }
////        for (SocialHist socialHist : socialHists) {
////            int count = 0;
////            socialHistXSSFRow = socialHistDetails.createRow(socialHistXSSFRowNum++);
////            XSSFCell id = socialHistXSSFRow.createCell(count);
////            id.setCellValue(socialHist.getPatient().getPatientNumber());
////            XSSFCell patientName = socialHistXSSFRow.createCell(++count);
////            XSSFCell dateOfBirth = socialHistXSSFRow.createCell(++count);
////            dateOfBirth.setCellValue(socialHist.getPatient().getDateOfBirth());
////            dateOfBirth.setCellStyle(XSSFCellStyle);
////            XSSFCell age = socialHistXSSFRow.createCell(++count);
////            age.setCellValue(socialHist.getPatient().getAge());
////            XSSFCell sex = socialHistXSSFRow.createCell(++count);
////            sex.setCellValue(socialHist.getPatient().getGender().getName());
////            patientName.setCellValue(socialHist.getPatient().getName());
////            XSSFCell province = socialHistXSSFRow.createCell(++count);
////            province.setCellValue(socialHist.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
////            XSSFCell district = socialHistXSSFRow.createCell(++count);
////            district.setCellValue(socialHist.getPatient().getPrimaryClinic().getDistrict().getName());
////            XSSFCell primaryClinic = socialHistXSSFRow.createCell(++count);
////            primaryClinic.setCellValue(socialHist.getPatient().getPrimaryClinic().getName());
////            XSSFCell entry = socialHistXSSFRow.createCell(++count);
////            if (socialHist.getDateCreated() != null) {
////                entry.setCellValue(socialHist.getDateCreated());
////                entry.setCellStyle(XSSFCellStyle);
////            } else {
////                entry.setCellValue("");
////            }
////            XSSFCell liveWith = socialHistXSSFRow.createCell(++count);
////            liveWith.setCellValue(socialHist.getLiveWith());
////            XSSFCell relationship = socialHistXSSFRow.createCell(++count);
////            relationship.setCellValue(
////                    socialHist.getRelationship() != null
////                    ? socialHist.getRelationship().getName() : ""
////            );
////            XSSFCell abused = socialHistXSSFRow.createCell(++count);
////            abused.setCellValue(
////                    socialHist.getAbuse() != null ? socialHist.getAbuse().getName() : ""
////            );
////            XSSFCell disclosed = socialHistXSSFRow.createCell(++count);
////            disclosed.setCellValue(
////                    socialHist.getDosclosure() != null ? socialHist.getDosclosure().getName() : ""
////            );
////            XSSFCell feelSafe = socialHistXSSFRow.createCell(++count);
////            feelSafe.setCellValue(
////                    socialHist.getFeelSafe() != null ? socialHist.getFeelSafe().getName() : ""
////            );
////            XSSFCell abuseType = socialHistXSSFRow.createCell(++count);
////            abuseType.setCellValue((socialHist.getAbuseTypes() != null && !socialHist.getAbuseTypes().isEmpty()) ? socialHist.getAbuseTypes().toString() : "");
////            XSSFCell abuseOutcome = socialHistXSSFRow.createCell(++count);
////            abuseOutcome.setCellValue(socialHist.getAbuseOutcome() != null ? socialHist.getAbuseOutcome().getName() : "");
////
////            XSSFCell isCats = socialHistXSSFRow.createCell(++count);
////            isCats.setCellValue(
////            		socialHist.getPatient().getCat() != null ? socialHist.getPatient().getCat().getName() : null
////            );
////            XSSFCell youngMumGroup = socialHistXSSFRow.createCell(++count);
////            youngMumGroup.setCellValue(
////            		socialHist.getPatient().getYoungMumGroup() != null ? socialHist.getPatient().getYoungMumGroup().getName() : null
////            );
////            XSSFCell ymd = socialHistXSSFRow.createCell(++count);
////            ymd.setCellValue(
////                    socialHist.getPatient().getYoungDadGroup() != null ? socialHist.getPatient().getYoungDadGroup().getName() : null
////            );
////
////        }
////        // add patient substance use history
////        XSSFSheet substanceUseDetails = workbook.createSheet("Patient_Substace_Use_Hist");
////        int substanceUseXSSFRowNum = 0;
////        XSSFRow substanceUseXSSFRow = substanceUseDetails.createRow(substanceUseXSSFRowNum++);
////        int substanceUseXSSFCellNum = 0;
////        for (String title : DatabaseHeader.SUBSTANCE_USE_HEADER) {
////            XSSFCell XSSFCell = substanceUseXSSFRow.createCell(substanceUseXSSFCellNum++);
////            XSSFCell.setCellValue(title);
////        }
////        for (SubstanceItem substanceItem : substanceItems) {
////            int count = 0;
////            substanceUseXSSFRow = substanceUseDetails.createRow(substanceUseXSSFRowNum++);
////            XSSFCell id = substanceUseXSSFRow.createCell(count);
////            id.setCellValue(substanceItem.getPatient().getPatientNumber());
////            XSSFCell patientName = substanceUseXSSFRow.createCell(++count);
////            patientName.setCellValue(substanceItem.getPatient().getName());
////            XSSFCell dateOfBirth = substanceUseXSSFRow.createCell(++count);
////            dateOfBirth.setCellValue(substanceItem.getPatient().getDateOfBirth());
////            dateOfBirth.setCellStyle(XSSFCellStyle);
////            XSSFCell age = substanceUseXSSFRow.createCell(++count);
////            age.setCellValue(substanceItem.getPatient().getAge());
////            XSSFCell sex = substanceUseXSSFRow.createCell(++count);
////            sex.setCellValue(substanceItem.getPatient().getGender().getName());
////            XSSFCell province = substanceUseXSSFRow.createCell(++count);
////            province.setCellValue(substanceItem.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
////            XSSFCell district = substanceUseXSSFRow.createCell(++count);
////            district.setCellValue(substanceItem.getPatient().getPrimaryClinic().getDistrict().getName());
////            XSSFCell primaryClinic = substanceUseXSSFRow.createCell(++count);
////            primaryClinic.setCellValue(substanceItem.getPatient().getPrimaryClinic().getName());
////            XSSFCell entry = substanceUseXSSFRow.createCell(++count);
////            if (substanceItem.getDateCreated() != null) {
////                entry.setCellValue(substanceItem.getDateCreated());
////                entry.setCellStyle(XSSFCellStyle);
////            } else {
////                entry.setCellValue("");
////            }
////            XSSFCell substance = substanceUseXSSFRow.createCell(++count);
////            substance.setCellValue(substanceItem.getSubstance().getName());
////            XSSFCell curent = substanceUseXSSFRow.createCell(++count);
////            curent.setCellValue(substanceItem.getCurrent() != null ? substanceItem.getCurrent().getName() : "");
////            XSSFCell past = substanceUseXSSFRow.createCell(++count);
////            past.setCellValue(substanceItem.getPast() != null ? substanceItem.getPast().getName() : "");
////            XSSFCell startDate = substanceUseXSSFRow.createCell(++count);
////            if (substanceItem.getStartDate() != null) {
////                startDate.setCellValue(substanceItem.getStartDate());
////                startDate.setCellStyle(XSSFCellStyle);
////            } else {
////                startDate.setCellValue("");
////            }
////            XSSFCell endDate = substanceUseXSSFRow.createCell(++count);
////            if (substanceItem.getEndDate() != null) {
////                endDate.setCellValue(substanceItem.getEndDate());
////                endDate.setCellStyle(XSSFCellStyle);
////            } else {
////                endDate.setCellValue("");
////            }
////
////            XSSFCell drugIntervention = substanceUseXSSFRow.createCell(++count);
////            drugIntervention.setCellValue(substanceItem.getDrugIntervention() != null ? substanceItem.getDrugIntervention().getName() : "");
////            XSSFCell duration = substanceUseXSSFRow.createCell(++count);
////            duration.setCellValue(substanceItem.getDuration());
////
////            XSSFCell isCats = substanceUseXSSFRow.createCell(++count);
////            isCats.setCellValue(
////            		substanceItem.getPatient().getCat() != null ? substanceItem.getPatient().getCat().getName() : null
////            );
////            XSSFCell youngMumGroup = substanceUseXSSFRow.createCell(++count);
////            youngMumGroup.setCellValue(
////            		substanceItem.getPatient().getYoungMumGroup() != null ? substanceItem.getPatient().getYoungMumGroup().getName() : null
////            );
////            XSSFCell ymd = substanceUseXSSFRow.createCell(++count);
////            ymd.setCellValue(
////                    substanceItem.getPatient().getYoungDadGroup() != null ? substanceItem.getPatient().getYoungDadGroup().getName() : null
////            );
////        }*/
////
                /* start here **/
                // add patient lab Results
                XSSFSheet cd4CountDetails = workbook.createSheet("Client_Lab_RESULTS");
                int cd4XSSFRowNum = 0;
                XSSFRow cd4XSSFRow = cd4CountDetails.createRow(cd4XSSFRowNum++);
                int cd4XSSFCellNum = 0;
                for (String title : DatabaseHeader.CD4_COUNT_HEADER) {
                    XSSFCell XSSFCell = cd4XSSFRow.createCell(cd4XSSFCellNum++);
                    XSSFCell.setCellValue(title);
                }
                for (InvestigationTest cd4Count : investigationTests) {
                    int count = 0;
                    cd4XSSFRow = cd4CountDetails.createRow(cd4XSSFRowNum++);
                    XSSFCell id = cd4XSSFRow.createCell(count);
                    id.setCellValue(cd4Count.getPatient().getPatientNumber());
                    XSSFCell patientName = cd4XSSFRow.createCell(++count);
                    patientName.setCellValue(cd4Count.getPatient().getName());
                    XSSFCell dateOfBirth = cd4XSSFRow.createCell(++count);
                    dateOfBirth.setCellValue(cd4Count.getPatient().getDateOfBirth());
                    dateOfBirth.setCellStyle(XSSFCellStyle);
                    XSSFCell age = cd4XSSFRow.createCell(++count);
                    age.setCellValue(cd4Count.getPatient().getAge());
                    XSSFCell sex = cd4XSSFRow.createCell(++count);
                    sex.setCellValue(cd4Count.getPatient().getGender().getName());
                    XSSFCell province = cd4XSSFRow.createCell(++count);
                    province.setCellValue(cd4Count.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                    XSSFCell district = cd4XSSFRow.createCell(++count);
                    district.setCellValue(cd4Count.getPatient().getPrimaryClinic().getDistrict().getName());
                    XSSFCell primaryClinic = cd4XSSFRow.createCell(++count);
                    primaryClinic.setCellValue(cd4Count.getPatient().getPrimaryClinic().getName());
                    XSSFCell entry = cd4XSSFRow.createCell(++count);
                    if (cd4Count.getDateCreated() != null) {
                        entry.setCellValue(cd4Count.getDateCreated());
                        entry.setCellStyle(XSSFCellStyle);
                    } else {
                        entry.setCellValue("");
                    }
                    XSSFCell testType = cd4XSSFRow.createCell(++count);
                    testType.setCellValue(cd4Count.getTestType().getName());
                    XSSFCell dateTaken = cd4XSSFRow.createCell(++count);
                    if (cd4Count.getDateTaken() != null) {
                        dateTaken.setCellValue(cd4Count.getDateTaken());
                        dateTaken.setCellStyle(XSSFCellStyle);
                    } else {
                        dateTaken.setCellValue("");
                    }
                    XSSFCell cd4Load = cd4XSSFRow.createCell(++count);
                    cd4Load.setCellValue(cd4Count.getResult() != null ? cd4Count.getResult() : 0);
                    cd4Load.setCellType(CellType.NUMERIC);
                    XSSFCell source = cd4XSSFRow.createCell(++count);
                    source.setCellValue(cd4Count.getSource() != null ? cd4Count.getSource().getName() : "");
                    XSSFCell nextLabDate = cd4XSSFRow.createCell(++count);
                    if (cd4Count.getNextTestDate() != null) {
                        nextLabDate.setCellValue(cd4Count.getNextTestDate());
                        nextLabDate.setCellStyle(XSSFCellStyle);
                    } else {
                        nextLabDate.setCellValue("");
                    }
                    XSSFCell suppressionStatus = cd4XSSFRow.createCell(++count);
                    if (cd4Count.getViralLoadSuppressionStatus() != null) {
                        suppressionStatus.setCellValue(cd4Count.getViralLoadSuppressionStatus());
                        suppressionStatus.setCellStyle(XSSFCellStyle);
                    } else {
                        suppressionStatus.setCellValue("");
                    }

                    XSSFCell resultsTaken = cd4XSSFRow.createCell(++count);
                    if (cd4Count.getResultTaken() != null) {
                        resultsTaken.setCellValue(cd4Count.getResultTaken().getName());
                        resultsTaken.setCellStyle(XSSFCellStyle);
                    } else {
                        resultsTaken.setCellValue("");
                    }

                    XSSFCell tnd = cd4XSSFRow.createCell(++count);
                    if (cd4Count.getTnd() != null) {
                        tnd.setCellValue(cd4Count.getTnd());
                        tnd.setCellStyle(XSSFCellStyle);
                    } else {
                        tnd.setCellValue("");
                    }

                    XSSFCell recordSource = cd4XSSFRow.createCell(++count);
                    if (cd4Count.getRecordSource() != null) {
                        recordSource.setCellValue(cd4Count.getRecordSource().getName());
                        recordSource.setCellStyle(XSSFCellStyle);
                    } else {
                        recordSource.setCellValue("");
                    }

                    XSSFCell isCats = cd4XSSFRow.createCell(++count);
                    isCats.setCellValue(
                            cd4Count.getPatient().getCat() != null ? cd4Count.getPatient().getCat().getName() : null
                    );
                    XSSFCell youngMumGroup = cd4XSSFRow.createCell(++count);
                    youngMumGroup.setCellValue(
                            cd4Count.getPatient().getYoungMumGroup() != null ? cd4Count.getPatient().getYoungMumGroup().getName() : null
                    );
                    XSSFCell ymd = cd4XSSFRow.createCell(++count);
                    ymd.setCellValue(
                            cd4Count.getPatient().getYoungDadGroup() != null ? cd4Count.getPatient().getYoungDadGroup().getName() : null
                    );

                }
                final long vl = System.currentTimeMillis();
                System.err.println(" :: Finished Collation of LAB RESULTS  (min)::" + (double) ((vl - ref_end) / 60000));
//
                //arv history
                XSSFSheet arvHistDetails = workbook.createSheet("Patient_ARV_HISTORY");
                int arvHistXSSFRowNum = 0;
                XSSFRow arvHistXSSFRow = arvHistDetails.createRow(arvHistXSSFRowNum++);
                int arvHistXSSFCellNum = 0;
                for (String title : DatabaseHeader.ARV_HISTORY_HEADER) {
                    XSSFCell XSSFCell = arvHistXSSFRow.createCell(arvHistXSSFCellNum++);
                    XSSFCell.setCellValue(title);
                }
                for (ArvHist arvHist : arvHists) {
                    int count = 0;
                    arvHistXSSFRow = arvHistDetails.createRow(arvHistXSSFRowNum++);
                    XSSFCell id = arvHistXSSFRow.createCell(count);
                    id.setCellValue(arvHist.getPatient().getPatientNumber());
                    XSSFCell patientName = arvHistXSSFRow.createCell(++count);
                    patientName.setCellValue(arvHist.getPatient().getName());
                    XSSFCell dateOfBirth = arvHistXSSFRow.createCell(++count);
                    dateOfBirth.setCellValue(arvHist.getPatient().getDateOfBirth());
                    dateOfBirth.setCellStyle(XSSFCellStyle);
                    XSSFCell age = arvHistXSSFRow.createCell(++count);
                    age.setCellValue(arvHist.getPatient().getAge());
                    XSSFCell sex = arvHistXSSFRow.createCell(++count);
                    sex.setCellValue(arvHist.getPatient().getGender().getName());
                    XSSFCell province = arvHistXSSFRow.createCell(++count);
                    province.setCellValue(arvHist.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                    XSSFCell district = arvHistXSSFRow.createCell(++count);
                    district.setCellValue(arvHist.getPatient().getPrimaryClinic().getDistrict().getName());
                    XSSFCell primaryClinic = arvHistXSSFRow.createCell(++count);
                    primaryClinic.setCellValue(arvHist.getPatient().getPrimaryClinic().getName());
                    XSSFCell entry = arvHistXSSFRow.createCell(++count);
                    if (arvHist.getDateCreated() != null) {
                        entry.setCellValue(arvHist.getDateCreated());
                        entry.setCellStyle(XSSFCellStyle);
                    } else {
                        entry.setCellValue("");
                    }
                    XSSFCell arvHistMedicine = arvHistXSSFRow.createCell(++count);
                    arvHistMedicine.setCellValue(arvHist.getMedicines());
                    XSSFCell startDate = arvHistXSSFRow.createCell(++count);
                    if (arvHist.getStartDate() != null) {
                        startDate.setCellValue(arvHist.getStartDate());
                        startDate.setCellStyle(XSSFCellStyle);
                    } else {
                        startDate.setCellValue("");
                    }
                    XSSFCell endDate = arvHistXSSFRow.createCell(++count);
                    if (arvHist.getEndDate() != null) {
                        endDate.setCellValue(arvHist.getEndDate());
                        endDate.setCellStyle(XSSFCellStyle);
                    } else {
                        endDate.setCellValue("");
                    }

                    XSSFCell isCats = arvHistXSSFRow.createCell(++count);
                    isCats.setCellValue(
                            arvHist.getPatient().getCat() != null ? arvHist.getPatient().getCat().getName() : null
                    );
                    XSSFCell youngMumGroup = arvHistXSSFRow.createCell(++count);
                    youngMumGroup.setCellValue(
                            arvHist.getPatient().getYoungMumGroup() != null ? arvHist.getPatient().getYoungMumGroup().getName() : null
                    );
                    XSSFCell ymd = arvHistXSSFRow.createCell(++count);
                    ymd.setCellValue(
                            arvHist.getPatient().getYoungDadGroup() != null ? arvHist.getPatient().getYoungDadGroup().getName() : null
                    );
                }
                final long arv = System.currentTimeMillis();
                System.err.println(" :: Finished Collation of ARV HIST (min)::" + (double) ((arv - vl) / 60000));

                // add mortality here
//            XSSFSheet mortalityDetails = workbook.createSheet("Patient_Mortality");
//            int mortalityXSSFRowNum = 0;
//            XSSFRow mortalityXSSFRow = mortalityDetails.createRow(mortalityXSSFRowNum++);
//            int mortalityXSSFCellNum = 0;
//            for (String title : DatabaseHeader.MORTALITY_HEADER) {
//                XSSFCell XSSFCell = mortalityXSSFRow.createCell(mortalityXSSFCellNum++);
//                XSSFCell.setCellValue(title);
//            }
//
//            for (Mortality mortality : mortalitys) {
//                System.err.println("########## Mortality: "+mortality);
//                if (mortality != null) {
//                    int count = 0;
//                    mortalityXSSFRow = mortalityDetails.createRow(mortalityXSSFRowNum++);
//                    XSSFCell id = mortalityXSSFRow.createCell(count);
//                    id
//                            .setCellValue(mortality != null && mortality.getPatient() != null ? mortality.getPatient().getPatientNumber() : "");
//                    XSSFCell patientName = mortalityXSSFRow.createCell(++count);
//                    patientName.setCellValue(mortality != null && mortality.getPatient() != null ? mortality.getPatient().getName() : "");
//                    XSSFCell dateOfBirth = mortalityXSSFRow.createCell(++count);
//                    if (mortality != null && mortality.getPatient() != null && mortality.getPatient().getDateOfBirth() != null) {
//                        dateOfBirth.setCellValue(mortality.getPatient().getDateOfBirth());
//                        dateOfBirth.setCellStyle(XSSFCellStyle);
//                    } else {
//                        dateOfBirth.setCellValue("");
//                    }
//
//                    XSSFCell age = mortalityXSSFRow.createCell(++count);
//                    if (mortality != null && mortality.getPatient() != null) {
//                        age.setCellValue(mortality.getPatient().getAge());
//                    } else {
//                        age.setCellValue("");
//                    }
//
//                    XSSFCell sex = mortalityXSSFRow.createCell(++count);
//                    sex.setCellValue(mortality != null && mortality.getPatient() != null ? mortality.getPatient().getGender().getName() : "");
//                    XSSFCell province = mortalityXSSFRow.createCell(++count);
//                    province.setCellValue(mortality != null && mortality.getPatient() != null ? mortality.getPatient().getPrimaryClinic().getDistrict().getProvince().getName() : "");
//                    XSSFCell district = mortalityXSSFRow.createCell(++count);
//                    district.setCellValue(mortality != null && mortality.getPatient() != null ? mortality.getPatient().getPrimaryClinic().getDistrict().getName() : "");
//                    XSSFCell primaryClinic = mortalityXSSFRow.createCell(++count);
//                    primaryClinic.setCellValue(mortality != null && mortality.getPatient() != null ? mortality.getPatient().getPrimaryClinic().getName() : "");
//                    XSSFCell entry = mortalityXSSFRow.createCell(++count);
//                    if (mortality != null && mortality.getDateCreated() != null) {
//                        entry.setCellValue(mortality.getDateCreated());
//                        entry.setCellStyle(XSSFCellStyle);
//                    } else {
//                        entry.setCellValue("");
//                    }
//                    XSSFCell dateOfDeath = mortalityXSSFRow.createCell(++count);
//                    if (mortality != null && mortality.getDateOfDeath() != null) {
//                        dateOfDeath.setCellValue(mortality.getDateOfDeath());
//                        dateOfDeath.setCellStyle(XSSFCellStyle);
//                    } else {
//                        dateOfDeath.setCellValue("");
//                    }
//                    XSSFCell causeOfDeath = mortalityXSSFRow.createCell(++count);
//                    causeOfDeath.setCellValue(mortality != null && mortality.getCauseOfDeath() != null ? mortality.getCauseOfDeath().getName() : "");
//                    XSSFCell causeOfDeathDetails = mortalityXSSFRow.createCell(++count);
//                    causeOfDeathDetails.setCellValue(mortality != null ? mortality.getCauseOfDeathDetails() : "");
//                    XSSFCell receivingEnhancedCare = mortalityXSSFRow.createCell(++count);
//                    receivingEnhancedCare.setCellValue(mortality != null && mortality.getReceivingEnhancedCare() != null ? mortality.getReceivingEnhancedCare().getName() : "");
//                    XSSFCell datePutOnEnhancedCare = mortalityXSSFRow.createCell(++count);
//                    if (mortality != null && mortality.getDatePutOnEnhancedCare() != null) {
//                        datePutOnEnhancedCare.setCellValue(mortality.getDatePutOnEnhancedCare());
//                        datePutOnEnhancedCare.setCellStyle(XSSFCellStyle);
//                    } else {
//                        datePutOnEnhancedCare.setCellValue("");
//                    }
//                    XSSFCell caseBackground = mortalityXSSFRow.createCell(++count);
//                    caseBackground.setCellValue(mortality != null ? mortality.getCaseBackground() : "");
//                    XSSFCell careProvided = mortalityXSSFRow.createCell(++count);
//                    careProvided.setCellValue(mortality != null ? mortality.getCareProvided() : "");
//                    XSSFCell home = mortalityXSSFRow.createCell(++count);
//                    home.setCellValue(mortality != null ? mortality.getHome() : "");
//
//                    XSSFCell beneficiary = mortalityXSSFRow.createCell(++count);
//                    beneficiary.setCellValue(mortality != null ? mortality.getBeneficiary() : "");
//                    XSSFCell facility = mortalityXSSFRow.createCell(++count);
//                    facility.setCellValue(mortality != null ? mortality.getFacility() : "");
//                    XSSFCell cats = mortalityXSSFRow.createCell(++count);
//                    cats.setCellValue(mortality != null ? mortality.getCats() : "");
//                    XSSFCell zm = mortalityXSSFRow.createCell(++count);
//                    zm.setCellValue(mortality != null ? mortality.getZm() : "");
//                    XSSFCell other = mortalityXSSFRow.createCell(++count);
//                    other.setCellValue(mortality != null ? mortality.getOther() : "");
//                    XSSFCell contactWithZM = mortalityXSSFRow.createCell(++count);
//                    contactWithZM.setCellValue(mortality != null && mortality.getContactWithZM() != null ? mortality.getContactWithZM().getName() : "");
//                    XSSFCell dateOfContactWithZim = mortalityXSSFRow.createCell(++count);
//                    if (mortality != null && mortality.getDateOfContactWithZim() != null) {
//                        dateOfContactWithZim.setCellValue(mortality.getDateOfContactWithZim());
//                        dateOfContactWithZim.setCellStyle(XSSFCellStyle);
//                    } else {
//                        dateOfContactWithZim.setCellValue("");
//                    }
//                    XSSFCell descriptionOfCase = mortalityXSSFRow.createCell(++count);
//                    descriptionOfCase.setCellValue(mortality != null ? mortality.getDescriptionOfCase() : "");
//                    XSSFCell learningPoints = mortalityXSSFRow.createCell(++count);
//                    learningPoints.setCellValue(mortality != null ? mortality.getLearningPoints() : "");
//                    XSSFCell actionPlan = mortalityXSSFRow.createCell(++count);
//                    actionPlan.setCellValue(mortality != null ? mortality.getActionPlan() : "");
//
//                    XSSFCell isCats = mortalityXSSFRow.createCell(++count);
//                    isCats.setCellValue(
//                            mortality != null && mortality.getPatient().getCat() != null ? mortality.getPatient().getCat().getName() : null
//                    );
//                    XSSFCell youngMumGroup = mortalityXSSFRow.createCell(++count);
//                    youngMumGroup.setCellValue(
//                            mortality != null && mortality.getPatient().getYoungMumGroup() != null ? mortality.getPatient().getYoungMumGroup().getName() : null
//                    );
//                    XSSFCell ymd = mortalityXSSFRow.createCell(++count);
//                    ymd.setCellValue(
//                            mortality != null && mortality.getPatient().getYoungDadGroup() != null ? mortality.getPatient().getYoungDadGroup().getName() : null
//                    );
//
//                }
//            }
//            final long mort=System.currentTimeMillis();
//            System.err.println(":: Finished Collation of MORTALITY (min)::" + (double)((mort-arv)/60000));

                // tb Ipt here
                XSSFSheet tbIptDetails = workbook.createSheet("Patient_TBIPT");
                int tbIptXSSFRowNum = 0;
                XSSFRow tbIptXSSFRow = tbIptDetails.createRow(tbIptXSSFRowNum++);
                int tbIptXSSFCellNum = 0;
                for (String title : DatabaseHeader.TB_IPT_HEADER) {
                    XSSFCell XSSFCell = tbIptXSSFRow.createCell(tbIptXSSFCellNum++);
                    XSSFCell.setCellValue(title);
                }

                for (TbIpt tbIpt : tbIpts) {
                    int count = 0;
                    tbIptXSSFRow = tbIptDetails.createRow(tbIptXSSFRowNum++);
                    XSSFCell id = tbIptXSSFRow.createCell(count);
                    id.setCellValue(tbIpt.getPatient().getPatientNumber());
                    XSSFCell patientName = tbIptXSSFRow.createCell(++count);
                    patientName.setCellValue(tbIpt.getPatient().getName());
                    XSSFCell dateOfBirth = tbIptXSSFRow.createCell(++count);
                    dateOfBirth.setCellValue(tbIpt.getPatient().getDateOfBirth());
                    dateOfBirth.setCellStyle(XSSFCellStyle);
                    XSSFCell age = tbIptXSSFRow.createCell(++count);
                    age.setCellValue(tbIpt.getPatient().getAge());
                    XSSFCell sex = tbIptXSSFRow.createCell(++count);
                    sex.setCellValue(tbIpt.getPatient().getGender().getName());
                    XSSFCell province = tbIptXSSFRow.createCell(++count);
                    province.setCellValue(tbIpt.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                    XSSFCell district = tbIptXSSFRow.createCell(++count);
                    district.setCellValue(tbIpt.getPatient().getPrimaryClinic().getDistrict().getName());
                    XSSFCell primaryClinic = tbIptXSSFRow.createCell(++count);
                    primaryClinic.setCellValue(tbIpt.getPatient().getPrimaryClinic().getName());
                    XSSFCell entry = tbIptXSSFRow.createCell(++count);
                    if (tbIpt.getDateCreated() != null) {
                        entry.setCellValue(tbIpt.getDateCreated());
                        entry.setCellStyle(XSSFCellStyle);
                    } else {
                        entry.setCellValue("");
                    }

                    XSSFCell screenedForTb = tbIptXSSFRow.createCell(++count);
                    screenedForTb.setCellValue(tbIpt.getScreenedForTb() != null ? tbIpt.getScreenedForTb().getName() : "");
                    XSSFCell dateScreened = tbIptXSSFRow.createCell(++count);
                    if (tbIpt.getDateScreened() != null) {
                        dateScreened.setCellValue(tbIpt.getDateScreened());
                        dateScreened.setCellStyle(XSSFCellStyle);
                    } else {
                        dateScreened.setCellValue("");
                    }
                    XSSFCell identifiedWithTb = tbIptXSSFRow.createCell(++count);
                    identifiedWithTb.setCellValue(tbIpt.getIdentifiedWithTb() != null ? tbIpt.getIdentifiedWithTb().getName() : "");

                    XSSFCell dateStartedTreatment = tbIptXSSFRow.createCell(++count);
                    if (tbIpt.getDateStartedTreatment() != null) {
                        dateStartedTreatment.setCellValue(tbIpt.getDateStartedTreatment());
                        dateStartedTreatment.setCellStyle(XSSFCellStyle);
                    } else {
                        dateStartedTreatment.setCellValue("");
                    }

                    XSSFCell referredForIpt = tbIptXSSFRow.createCell(++count);
                    referredForIpt.setCellValue(tbIpt.getReferredForIpt() != null ? tbIpt.getReferredForIpt().getName() : "");
                    XSSFCell onIpt = tbIptXSSFRow.createCell(++count);
                    onIpt.setCellValue(tbIpt.getOnIpt() != null ? tbIpt.getOnIpt().getName() : "");
                    XSSFCell dateStartedIpt = tbIptXSSFRow.createCell(++count);
                    if (tbIpt.getDateStartedTreatment() != null) {
                        dateStartedIpt.setCellValue(tbIpt.getDateStartedTreatment());
                        dateStartedIpt.setCellStyle(XSSFCellStyle);
                    } else {
                        dateStartedIpt.setCellValue("");
                    }

                    XSSFCell isCats = tbIptXSSFRow.createCell(++count);
                    isCats.setCellValue(
                            tbIpt.getPatient().getCat() != null ? tbIpt.getPatient().getCat().getName() : null
                    );
                    XSSFCell youngMumGroup = tbIptXSSFRow.createCell(++count);
                    youngMumGroup.setCellValue(
                            tbIpt.getPatient().getYoungMumGroup() != null ? tbIpt.getPatient().getYoungMumGroup().getName() : null
                    );

                    XSSFCell ymd = tbIptXSSFRow.createCell(++count);
                    ymd.setCellValue(
                            tbIpt.getPatient().getYoungDadGroup() != null ? tbIpt.getPatient().getYoungDadGroup().getName() : null
                    );
                }
                final long tbs = System.currentTimeMillis();
                System.err.println(" :: Finished Collation of TB/TPT (min)::" + (double) ((tbs - arv) / 60000));

                // mental health screening
                XSSFSheet mentalHealthScreeningDetails = workbook.createSheet("Patient_Mental_Health_Screening");
                int mentalHealthScreeningXSSFRowNum = 0;
                XSSFRow mentalHealthScreeningXSSFRow = mentalHealthScreeningDetails.createRow(mentalHealthScreeningXSSFRowNum++);
                int mentalHealthScreeningXSSFCellNum = 0;
                for (String title : DatabaseHeader.MENTAL_HEALTH_SCREENING_HEADER) {
                    XSSFCell XSSFCell = mentalHealthScreeningXSSFRow.createCell(mentalHealthScreeningXSSFCellNum++);
                    XSSFCell.setCellValue(title);
                }

                for (MentalHealthScreening mentalHealthScreening : mentalHealthScreenings) {
                    int count = 0;
                    mentalHealthScreeningXSSFRow = mentalHealthScreeningDetails.createRow(mentalHealthScreeningXSSFRowNum++);
                    XSSFCell id = mentalHealthScreeningXSSFRow.createCell(count);
                    id.setCellValue(mentalHealthScreening.getPatient().getPatientNumber());
                    XSSFCell patientName = mentalHealthScreeningXSSFRow.createCell(++count);
                    patientName.setCellValue(mentalHealthScreening.getPatient().getName());
                    XSSFCell dateOfBirth = mentalHealthScreeningXSSFRow.createCell(++count);
                    dateOfBirth.setCellValue(mentalHealthScreening.getPatient().getDateOfBirth());
                    dateOfBirth.setCellStyle(XSSFCellStyle);
                    XSSFCell age = mentalHealthScreeningXSSFRow.createCell(++count);
                    age.setCellValue(mentalHealthScreening.getPatient().getAge());
                    XSSFCell sex = mentalHealthScreeningXSSFRow.createCell(++count);
                    sex.setCellValue(mentalHealthScreening.getPatient().getGender().getName());
                    XSSFCell province = mentalHealthScreeningXSSFRow.createCell(++count);
                    province.setCellValue(mentalHealthScreening.getPatient().getPrimaryClinic().getDistrict().getProvince().getName());
                    XSSFCell district = mentalHealthScreeningXSSFRow.createCell(++count);
                    district.setCellValue(mentalHealthScreening.getPatient().getPrimaryClinic().getDistrict().getName());
                    XSSFCell primaryClinic = mentalHealthScreeningXSSFRow.createCell(++count);
                    primaryClinic.setCellValue(mentalHealthScreening.getPatient().getPrimaryClinic().getName());
                    XSSFCell entryDate = mentalHealthScreeningXSSFRow.createCell(++count);
                    if (mentalHealthScreening.getDateCreated() != null) {
                        entryDate.setCellValue(mentalHealthScreening.getDateCreated());
                        entryDate.setCellStyle(XSSFCellStyle);
                    } else {
                        entryDate.setCellValue("");
                    }

                    XSSFCell screenedForMentalHealth = mentalHealthScreeningXSSFRow.createCell(++count);
                    screenedForMentalHealth.setCellValue(mentalHealthScreening.getScreenedForMentalHealth() != null
                            ? mentalHealthScreening.getScreenedForMentalHealth().getName() : "");
                    XSSFCell dateScreened = mentalHealthScreeningXSSFRow.createCell(++count);
                    if (mentalHealthScreening.getDateScreened() != null) {
                        dateScreened.setCellValue(mentalHealthScreening.getDateScreened());
                        dateScreened.setCellStyle(XSSFCellStyle);
                    } else {
                        dateScreened.setCellValue("");
                    }
                    XSSFCell screening = mentalHealthScreeningXSSFRow.createCell(++count);
                    screening.setCellValue(mentalHealthScreening.getScreening() != null ? mentalHealthScreening.getScreening().getName() : "");
                    XSSFCell risk = mentalHealthScreeningXSSFRow.createCell(++count);
                    risk.setCellValue(mentalHealthScreening.getRisk() != null ? mentalHealthScreening.getRisk().getName() : "");
                    XSSFCell identifiedRisks = mentalHealthScreeningXSSFRow.createCell(++count);
                    identifiedRisks.setCellValue((mentalHealthScreening.getIdentifiedRisks() != null && !mentalHealthScreening.getIdentifiedRisks().isEmpty())
                            ? mentalHealthScreening.getIdentifiedRisks().toString() : "");
                    XSSFCell support = mentalHealthScreeningXSSFRow.createCell(++count);
                    support.setCellValue(mentalHealthScreening.getSupport() != null
                            ? mentalHealthScreening.getSupport().getName() : "");
                    XSSFCell supports = mentalHealthScreeningXSSFRow.createCell(++count);
                    supports.setCellValue((mentalHealthScreening.getSupports() != null && !mentalHealthScreening.getSupports().isEmpty())
                            ? mentalHealthScreening.getSupports().toString() : "");
                    XSSFCell referral = mentalHealthScreeningXSSFRow.createCell(++count);
                    referral.setCellValue(mentalHealthScreening.getReferral() != null
                            ? mentalHealthScreening.getReferral().getName() : "");


                    XSSFCell isCats = mentalHealthScreeningXSSFRow.createCell(++count);
                    isCats.setCellValue(
                            mentalHealthScreening.getPatient().getCat() != null ? mentalHealthScreening.getPatient().getCat().getName() : null
                    );
                    XSSFCell youngMumGroup = mentalHealthScreeningXSSFRow.createCell(++count);
                    youngMumGroup.setCellValue(
                            mentalHealthScreening.getPatient().getYoungMumGroup() != null ? mentalHealthScreening.getPatient().getYoungMumGroup().getName() : null
                    );
                    XSSFCell ymd = mentalHealthScreeningXSSFRow.createCell(++count);
                    ymd.setCellValue(
                            mentalHealthScreening.getPatient().getYoungDadGroup() != null ? mentalHealthScreening.getPatient().getYoungDadGroup().getName() : null
                    );
                }
                final long mhs = System.currentTimeMillis();
                System.err.println(":: Finished Collation of MHScreening (min)::" + (double) ((mhs - tbs) / 60000));
                /* end here    */
                final long finish = System.currentTimeMillis();
                final double taken = (double) ((finish - start) / 60000);
                System.err.println("Provinces::" + dto.getProvinces() + "\nDistricts:: " + dto.getDistricts() + "\nFacilities:: " + dto.getFacilities() + "\n" +
                        "District::" + dto.getDistrict() + "\n" + "Province::" + dto.getProvince() +
                        "\nFinal Time Taken(mins)::" + taken);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return workbook;
        }catch (Exception e){
            System.err.println("Error during database export :: "+e.getMessage());
        }

        return null;
    }
}
