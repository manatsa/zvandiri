/*
 * Copyright 2018 tasu.
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
import java.util.Set;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.Referral;
import zw.org.zvandiri.business.service.PatientReportService;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.report.api.GenericReportModel;
import zw.org.zvandiri.report.api.service.ContactServicesOfferedReportService;

/**
 * @author tasu
 */
@Repository
public class ContactServicesOfferedReportServiceImpl implements ContactServicesOfferedReportService {

    @Resource
    private PatientReportService reportService;

    @Override
    public List<GenericReportModel> getDefaultReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<String> topHeaderRow = new ArrayList<>();
        String[] topHeaderNames = {"Patient", "Province", "District", "Primary Clinic", "Services Offered"};
        topHeaderRow.addAll(Arrays.asList(topHeaderNames));
        list.add(new GenericReportModel(topHeaderRow));
        List<Patient> patients = reportService.getPatientWithContactList(dto);
        for (Patient item : patients) {
            List<String> row = new ArrayList<>();
            row.add(item.getFirstName() + " " + item.getLastName());
            row.add(item.getPrimaryClinic().getDistrict().getProvince().getName());
            row.add(item.getPrimaryClinic().getDistrict().getName());
            row.add(item.getPrimaryClinic().getName());
            Set<Referral> referrals = item.getReferrals();
            String referral = "[ ";
            for (Referral r : referrals) {
                Set<String> servicesReceived = r.getServicesReceived();
                for (String s : servicesReceived) {
                    referral += " [";
                    referral += s;
                    referral += " ] ";
                }
            }
            referral += " ]";
            row.add(referral);
            list.add(new GenericReportModel(row));
        }
        return list;
    }

    @Override
    public List<GenericReportModel> getDefaultReportB(SearchDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
