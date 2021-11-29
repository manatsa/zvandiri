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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import zw.org.zvandiri.business.domain.Contact;
import zw.org.zvandiri.business.domain.InvestigationTest;
import zw.org.zvandiri.business.domain.MentalHealthScreening;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.FollowUp;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.Reportutil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.report.api.DatabaseHeader;
import zw.org.zvandiri.report.api.service.CaseloadManagementService;
import zw.org.zvandiri.report.api.service.parallel.*;


import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

/**
 *
 * @author manatsachinyeruse@gmail.com
 */
@Repository
public class CaseloadManagementServiceImpl implements CaseloadManagementService {

    @Resource
    private DetailedPatientReportService detailedPatientReportService;
    @Resource
    PatientReportService patientReportService;
    @Autowired
    ContactReportService contactReportService;
    @Autowired
    InvestigationTestService investigationTestService;
    @Resource
    ContactService contactService;
    @Resource
    MentalHealthScreeningService mentalHealthScreeningService;

    final static String [] CLIENTS_GENERAL_HEADER = {
            "Client Name","Date of Birth", "Age", "Gender","Status", "Address","Secondary Address", "Mobile Number", "Second Mobile Number", "Region",
            "District","Primary Clinic", "IS CATS", "In YMM Programme","In YMD Programme",
            "Last Contact Date","Current Care Level","LastVL Result",
            "Last VL Date Taken","Mental Health Risk","Mental Health Risks", "Date Screened"
    };




    @Override
    public XSSFWorkbook exportCaseload(String name, SearchDTO dto) {

       long initially= System.currentTimeMillis();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle XSSFCellStyle = workbook.createCellStyle();
        XSSFCreationHelper createHelper = workbook.getCreationHelper();
        XSSFCellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        dto.setFirstResult(0);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        List<Patient> patients = pool.invoke(new UnContactedClientTask(DateUtil.generateArray(patientReportService.getCount(dto)), patientReportService, dto));
        List<Patient> vlPatients = pool.invoke(new InvalidVLCandidatesTask(DateUtil.generateArray(patientReportService.getCount(dto)), patientReportService, dto));
        List<Patient> enhancedPatients = pool.invoke(new EnhancedClientsTask(DateUtil.generateArray(patientReportService.getCount(dto)), patientReportService, dto));
        List<Patient> mhPatients = pool.invoke(new MHScreeningCandidatesTask(DateUtil.generateArray(patientReportService.getCount(dto)), patientReportService, dto));
        List<Patient> tbPatients = pool.invoke(new TBScreeningCandidatesTask(DateUtil.generateArray(patientReportService.getCount(dto)), patientReportService, dto));

        pool.shutdown();

        addSheet(workbook, "Uncontacted Clients",patients,XSSFCellStyle);
        addSheet(workbook, "Enhanced Clients",enhancedPatients,XSSFCellStyle);
        addSheet(workbook, "Invalid VL Clients",vlPatients,XSSFCellStyle);
        addSheet(workbook, "MH Screening Candidates",mhPatients,XSSFCellStyle);
        addSheet(workbook, "TB Screening Candidates",tbPatients,XSSFCellStyle);


        long finall=System.currentTimeMillis();

        long timeTaken=(finall-initially)/1000;
        System.err.println("Caseload Mgt Time Taken :"+timeTaken);
        return workbook;

    }




    public XSSFWorkbook  addSheet(XSSFWorkbook workbook, String sheetName, List<Patient> patients, XSSFCellStyle xssfCellStyle){

        XSSFSheet enhancedClientsDetails = workbook.createSheet(sheetName);
        int assessmentRowNum = 0;
        int assessmentCellNum = 0;
        XSSFRow enhancedRow = enhancedClientsDetails.createRow(assessmentRowNum++);

        for (String title : CLIENTS_GENERAL_HEADER) {
            Cell cell = enhancedRow.createCell(assessmentCellNum++);
            cell.setCellValue(title);
        }
        for (Patient patient : patients) {

            Contact lastContact=patient.getLastPatientContact(contactService);
            InvestigationTest vlTest=patient.getLastPatientVL(investigationTestService);
            MentalHealthScreening mentalHealthScreening=patient.getLastPatientMentalHealthScreening(mentalHealthScreeningService);
            int count = 0;

            enhancedRow = enhancedClientsDetails.createRow(assessmentRowNum++);

            Cell patientName = enhancedRow.createCell(count++);
            patientName.setCellValue(patient.getName());

            Cell dateOfBirth = enhancedRow.createCell(count++);
            dateOfBirth.setCellValue(patient.getDateOfBirth());
            dateOfBirth.setCellStyle(xssfCellStyle);

            Cell age = enhancedRow.createCell(count++);
            age.setCellValue(patient.getAge());

            Cell sex = enhancedRow.createCell(count++);
            sex.setCellValue(patient.getGender().getName());

            Cell status = enhancedRow.createCell(count++);
            status.setCellValue(patient.getStatus().getName());

            Cell address = enhancedRow.createCell(count++);
            address.setCellValue(patient.getAddress());

            Cell address1 = enhancedRow.createCell(count++);
            address1.setCellValue(patient.getAddress1());

            Cell phone = enhancedRow.createCell(count++);
            phone.setCellValue(patient.getMobileNumber() == null ? "" : patient.getMobileNumber());

            Cell phone1 = enhancedRow.createCell(count++);
            phone1.setCellValue(patient.getSecondaryMobileNumber() == null ? "" : patient.getSecondaryMobileNumber());

            Cell province = enhancedRow.createCell(count++);
            province.setCellValue(patient.getPrimaryClinic()==null|| patient.getPrimaryClinic().getDistrict()==null|| patient.getPrimaryClinic().getDistrict().getProvince()==null?"":patient.getPrimaryClinic().getDistrict().getProvince().getName());

            Cell district = enhancedRow.createCell(count++);
            district.setCellValue(patient.getPrimaryClinic()==null|| patient.getPrimaryClinic().getDistrict() == null ? "" : patient.getPrimaryClinic().getDistrict().getName());

            Cell primaryClinic = enhancedRow.createCell(count++);
            primaryClinic.setCellValue(patient.getPrimaryClinic() == null ? "" : patient.getPrimaryClinic().getName());

            Cell isCats = enhancedRow.createCell(count++);
            isCats.setCellValue(
                    patient.getCat() != null ? patient.getCat().getName() : null
            );
            Cell youngMumGroup = enhancedRow.createCell(count++);
            youngMumGroup.setCellValue(
                    patient.getYoungMumGroup() != null ? patient.getYoungMumGroup().getName() : null
            );
            Cell ymd = enhancedRow.createCell(count++);
            ymd.setCellValue(
                    patient.getYoungDadGroup() != null ? patient.getYoungDadGroup().getName() : null
            );
            XSSFCell contactDate = enhancedRow.createCell(count++);
            if (lastContact != null) {
                contactDate.setCellValue(lastContact.getContactDate());
                contactDate.setCellStyle(xssfCellStyle);
            } else {
                contactDate.setCellValue("");
            }

            XSSFCell careLevel = enhancedRow.createCell(count++);
            careLevel.setCellValue(lastContact!=null?lastContact.getFollowUp().getName():"");

            XSSFCell vlresult = enhancedRow.createCell(count++);
            if(vlTest!=null && vlTest.getResult()!=null)
            {
                vlresult.setCellValue(vlTest.getResult());
            }else{
                vlresult.setCellType(Cell.CELL_TYPE_BLANK);
            }

            XSSFCell vlDateTaken = enhancedRow.createCell(count++);
            if (vlTest != null) {
                vlDateTaken.setCellValue(vlTest.getDateTaken());
                vlDateTaken.setCellStyle(xssfCellStyle);
            } else {
                vlDateTaken.setCellValue("");
            }

            XSSFCell isMHRisk = enhancedRow.createCell(count++);
            isMHRisk.setCellValue(mentalHealthScreening!=null && mentalHealthScreening.getRisk()!=null?mentalHealthScreening.getRisk().getName():"");

            XSSFCell MHRisks = enhancedRow.createCell(count++);
            MHRisks.setCellValue(mentalHealthScreening!=null && mentalHealthScreening.getIdentifiedRisks()!=null? Reportutil.StringsFromList(mentalHealthScreening.getIdentifiedRisks()) :"");

            XSSFCell MHDateScreened = enhancedRow.createCell(count++);
            if (mentalHealthScreening != null) {
                MHDateScreened.setCellValue(mentalHealthScreening!=null? mentalHealthScreening.getDateScreened()!=null?mentalHealthScreening.getDateScreened().toString():"":"");
                MHDateScreened.setCellStyle(xssfCellStyle);
            } else {
                MHDateScreened.setCellValue("");
            }

        }
        return workbook;
    }
}
