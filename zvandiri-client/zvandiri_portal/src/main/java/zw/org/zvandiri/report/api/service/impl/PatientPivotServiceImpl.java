/*
 * Copyright 2017 Judge Muzinda.
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
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.AgeGroup;
import zw.org.zvandiri.business.domain.util.TbIdentificationOutcome;
import zw.org.zvandiri.business.service.DetailedPatientReportService;
import zw.org.zvandiri.business.service.TbIptService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.business.util.pivot.dto.BasePatientExitingProgramPivotDTO;
import zw.org.zvandiri.business.util.pivot.dto.BasePatientPivotDTO;
import zw.org.zvandiri.business.util.pivot.dto.PatientDistrictPivotDTO;
import zw.org.zvandiri.business.util.pivot.dto.PatientExitingProgramDistrictPivotDTO;
import zw.org.zvandiri.business.util.pivot.dto.PatientExitingProgramNationalPivotDTO;
import zw.org.zvandiri.business.util.pivot.dto.PatientExitingProgramProvincePivotDTO;
import zw.org.zvandiri.business.util.pivot.dto.PatientNationalPivotDTO;
import zw.org.zvandiri.business.util.pivot.dto.PatientProvincePivotDTO;
import zw.org.zvandiri.portal.web.controller.report.parallel.GenericCountReportTask;
import zw.org.zvandiri.report.api.service.PatientPivotService;

/**
 *
 * @author Judge Muzinda
 */
@Repository
public class PatientPivotServiceImpl implements PatientPivotService {

    @Resource
    private DetailedPatientReportService detailedPatientReportService;
    @Resource
    private TbIptService tbIptService;

    @Override
    public List<BasePatientPivotDTO> get(SearchDTO dto) {
        return convertList(dto);
    }

    @Override
    public List<BasePatientExitingProgramPivotDTO> getInactivePatients(SearchDTO dto) {
        return convertInactivePatientList(dto);
    }

    private List<BasePatientPivotDTO> convertList(SearchDTO dto) {
        
        List<BasePatientPivotDTO> items = new ArrayList<>();
        ForkJoinPool pool = ForkJoinPool.commonPool();
        List<Patient> patients = pool.invoke(new GenericCountReportTask(DateUtil.generateArray(detailedPatientReportService.getCount(dto)), detailedPatientReportService, dto));
        
        for (Patient p : patients) {
            String isCats = (p.getCatId() != null && !p.getCatId().isEmpty()) ? "Yes" : "No";
            boolean onTbTreatment = tbIptService.existsOnTbTreatment(p, TbIdentificationOutcome.ON_TB_TREATMENT);
            boolean hasChildren =  p.getMotherOfHei() != null;
            if (dto.getDistrict() != null) {
                // remove references to parent
                dto.setProvince(null);
                items.add(new PatientDistrictPivotDTO(
                        p.getPrimaryClinic().getName(),
                        AgeGroup.get(AgeGroup.getAgeRange(p.getAge())).getAltName(),
                        p.getGender().getName(),
                        p.getAge(),
                        p.getStatus().getName(),
                        p.getHivStatusKnown() != null ? p.getHivStatusKnown().getName() : "No Value",
                        p.getDisabilityCategorys() != null && !p.getDisabilityCategorys().isEmpty() ? "Yes" : "No",
                        p.getTransmissionMode() != null ? p.getTransmissionMode().getName() : "No Value",
                        p.getEducation() != null ? p.getEducation().getName() : "No Value",
                        p.getEducationLevel() != null ? p.getEducationLevel().getName() : "No Value",
                        p.getReferer() != null ? p.getReferer().getName() : "No Value",
                        isCats, onTbTreatment ? "Yes" : "No", hasChildren ? "Yes" : "No"));
            } else if (dto.getProvince() != null) {
                items.add(new PatientProvincePivotDTO(
                        p.getPrimaryClinic().getDistrict().getName(),
                        AgeGroup.get(AgeGroup.getAgeRange(p.getAge())).getAltName(),
                        p.getGender().getName(),
                        p.getAge(),
                        p.getStatus().getName(),
                        p.getHivStatusKnown() != null ? p.getHivStatusKnown().getName() : "No Value",
                        p.getDisabilityCategorys() != null && !p.getDisabilityCategorys().isEmpty() ? "Yes" : "No",
                        p.getTransmissionMode() != null ? p.getTransmissionMode().getName() : "No Value",
                        p.getEducation() != null ? p.getEducation().getName() : "No Value",
                        p.getEducationLevel() != null ? p.getEducationLevel().getName() : "No Value",
                        p.getReferer() != null ? p.getReferer().getName() : "No Value",
                        isCats, onTbTreatment ? "Yes" : "No", hasChildren ? "Yes" : "No"));
            } else {
                items.add(new PatientNationalPivotDTO(
                        p.getPrimaryClinic().getDistrict().getProvince().getName(),
                        AgeGroup.get(AgeGroup.getAgeRange(p.getAge())).getAltName(),
                        p.getGender().getName(),
                        p.getAge(),
                        p.getStatus().getName(),
                        p.getHivStatusKnown() != null ? p.getHivStatusKnown().getName() : "No Value",
                        p.getDisabilityCategorys() != null && !p.getDisabilityCategorys().isEmpty() ? "Yes" : "No",
                        p.getTransmissionMode() != null ? p.getTransmissionMode().getName() : "No Value",
                        p.getEducation() != null ? p.getEducation().getName() : "No Value",
                        p.getEducationLevel() != null ? p.getEducationLevel().getName() : "No Value",
                        p.getReferer() != null ? p.getReferer().getName() : "No Value",
                        isCats, onTbTreatment ? "Yes" : "No", hasChildren ? "Yes" : "No"));
            }
        }
        return items;
    }

    private List<BasePatientExitingProgramPivotDTO> convertInactivePatientList(SearchDTO dto) {
        List<BasePatientExitingProgramPivotDTO> items = new ArrayList<>();
        for (Patient p : detailedPatientReportService.get(dto)) {
            if (dto.getDistrict() != null) {
                // remove references to parent
                dto.setProvince(null);
                items.add(new PatientExitingProgramDistrictPivotDTO(
                        p.getPrimaryClinic().getName(),
                        AgeGroup.get(AgeGroup.getAgeRange(p.getAge())).getAltName(),
                        p.getGender().getName(),
                        p.getAge(),
                        p.getStatus().getName(),
                        p.getEducation().getName(),
                        p.getEducationLevel().getName(),
                        DateUtil.getYearMonthName(p.getDateModified())
                ));
            } else if (dto.getProvince() != null) {
                items.add(new PatientExitingProgramProvincePivotDTO(
                        p.getPrimaryClinic().getDistrict().getName(),
                        AgeGroup.get(AgeGroup.getAgeRange(p.getAge())).getAltName(),
                        p.getGender().getName(),
                        p.getAge(),
                        p.getStatus().getName(),
                        p.getEducation().getName(),
                        p.getEducationLevel().getName(),
                        DateUtil.getYearMonthName(p.getDateModified())
                ));
            } else {
                items.add(new PatientExitingProgramNationalPivotDTO(
                        p.getPrimaryClinic().getDistrict().getProvince().getName(),
                        AgeGroup.get(AgeGroup.getAgeRange(p.getAge())).getAltName(),
                        p.getGender().getName(),
                        p.getAge(),
                        p.getStatus().getName(),
                        p.getEducation().getName(),
                        p.getEducationLevel().getName(),
                        DateUtil.getYearMonthName(p.getDateModified())
                ));
            }
        }
        return items;
    }
}
