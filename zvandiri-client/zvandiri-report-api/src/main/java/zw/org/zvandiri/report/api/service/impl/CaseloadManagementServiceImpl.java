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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.domain.util.FollowUp;
import zw.org.zvandiri.business.domain.util.TestType;
import zw.org.zvandiri.business.service.*;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.Reportutil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.report.api.DatabaseHeader;
import zw.org.zvandiri.report.api.service.CaseloadManagementService;
import zw.org.zvandiri.report.api.service.parallel.*;


import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ForkJoinPool;


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
    @Resource
    UserService userService;


    final static String [] CLIENTS_GENERAL_HEADER = {
            "Client Name","Date of Birth", "Age", "Gender","Status", "Address","Secondary Address", "Mobile Number", "Second Mobile Number", "Region",
            "District","Primary Clinic", "IS CATS", "In YMM Programme","In YMD Programme",
            "LastVL Result","Last VL Date Taken",
    };




    @Override
    public XSSFWorkbook exportCaseload(String name, SearchDTO dto) {



       long initially= System.currentTimeMillis();
        User currentUser=userService.getCurrentUser();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle XSSFCellStyle = workbook.createCellStyle();
        XSSFCreationHelper createHelper = workbook.getCreationHelper();
        XSSFCellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        dto.setFirstResult(0);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        //System.err.println(dto);
//        List<Patient> patientz=detailedPatientReportService.get(dto);
        List<String> patientIDS=detailedPatientReportService.getIds(dto);
        System.err.println("Total number of clients :: "+patientIDS.size());
        List<Patient> patients = pool.invoke(new UnContactedClientTask(patientIDS, patientReportService, dto));
        final long pat_end=System.currentTimeMillis();
        System.err.println("**** Caseload Uncontacted Patients (sec) :: "+Reportutil.df.format((double)(pat_end-initially)/1000));
        List<Patient> vlPatients = pool.invoke(new InvalidVLCandidatesTask(patientIDS, patientReportService, dto));
        final long vl_end=System.currentTimeMillis();
        System.err.println(" **** Caseload Invalid VLs (sec) :: "+Reportutil.df.format((double)(vl_end-pat_end)/1000));
        List<Patient> enhancedPatients = pool.invoke(new EnhancedClientsTask(patientIDS, patientReportService, dto));
        final long enhanced_end=System.currentTimeMillis();
        System.err.println(" **** Caseload Enhanced Patients (sec) :: "+Reportutil.df.format((double)(enhanced_end-vl_end)/1000));
        List<Patient> mhPatients = pool.invoke(new MHScreeningCandidatesTask(patientIDS, patientReportService, dto));
        final long mhs_end=System.currentTimeMillis();
        System.err.println(" **** Caseload Mental Health Screening Candidates (sec) :: "+Reportutil.df.format((double)(mhs_end-enhanced_end)/1000));
        List<Patient> tbPatients = pool.invoke(new TBScreeningCandidatesTask(patientIDS, patientReportService, dto));
        final long tbs_end=System.currentTimeMillis();
        System.err.println(" **** Caseload TB Screening Candidates (sec) :: "+Reportutil.df.format((double)(tbs_end-mhs_end)/1000));
        pool.shutdown();

        System.err.println("***** Now consolidating uncontacted data to excel sheet");

        addSheet(workbook, "Uncontacted Clients",patients,XSSFCellStyle);
        System.err.println("***** Now consolidating enhanced clients data to excel sheet");
        addSheet(workbook, "Enhanced Clients",enhancedPatients,XSSFCellStyle);
        System.err.println("***** Now consolidating invalid VL clients data to excel sheet");
        addSheet(workbook, "Invalid VL Clients",vlPatients,XSSFCellStyle);
        System.err.println("***** Now consolidating MH Screening candidates data to excel sheet");
        addSheet(workbook, "MH Screening Candidates",mhPatients,XSSFCellStyle);
        System.err.println("***** Now consolidating TB Screening candidates data to excel sheet");
        addSheet(workbook, "TB Screening Candidates",tbPatients,XSSFCellStyle);

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

            InvestigationTest vlTest=investigationTestService.getLatestTestByTestType(patient, TestType.VIRAL_LOAD);
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

            XSSFCell vlresult = enhancedRow.createCell(count++);
            if(vlTest!=null && vlTest.getResult()!=null)
            {
                vlresult.setCellValue(vlTest.getResult());
            }else{
                vlresult.setCellType(CellType.BLANK);
            }

            XSSFCell vlDateTaken = enhancedRow.createCell(count++);
            if (vlTest != null) {
                vlDateTaken.setCellValue(vlTest.getDateTaken());
                vlDateTaken.setCellStyle(xssfCellStyle);
            } else {
                vlDateTaken.setCellValue("");
            }


        }
        return workbook;
    }



}
