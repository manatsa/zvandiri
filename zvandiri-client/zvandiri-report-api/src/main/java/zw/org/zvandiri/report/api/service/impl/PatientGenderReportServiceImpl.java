/*
 * Copyright 2017 User.
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
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import zw.org.zvandiri.business.domain.util.Gender;
import zw.org.zvandiri.business.service.PatientReportService;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.report.api.GenericReportModel;
import zw.org.zvandiri.report.api.service.PatientGenderReportService;

/**
 * @author Tasu Muzinda
 */
@Repository
public class PatientGenderReportServiceImpl implements PatientGenderReportService {

    @Resource
    private PatientReportService patientReportService;

    @Override
    public List<GenericReportModel> getDefaultPieData(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<String> items = new ArrayList<>();
        items.add("");
        for (Gender item : Gender.values()) {
            items.add(item.getName());
        }
        items.add("Total");
        list.add(new GenericReportModel(items));
        GenericReportModel model = new GenericReportModel();
        List<String> row = new ArrayList<>();
        Long rowCount = 0L;
        row.add("Counts");
        for (Gender item : Gender.values()) {
            dto.setGender(item);
            Long itemCount = patientReportService.getCount(dto);
            row.add(itemCount.toString());
            rowCount += itemCount;
        }
        row.add(rowCount.toString());
        model.setRow(row);
        list.add(model);
        return list;
    }


    @Override
    public List<GenericReportModel> getDefaultReport(SearchDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<GenericReportModel> getDefaultReportB(SearchDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
