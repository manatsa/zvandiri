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
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import zw.org.zvandiri.business.domain.util.Result;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.SelfTestingReportService;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.report.api.GenericReportModel;
import zw.org.zvandiri.report.api.service.HIVSelfTestingReportService;

/**
 * @author tasu
 */
@Repository
public class HIVSelfTestingReportServiceImpl implements HIVSelfTestingReportService {

    @Resource
    private SelfTestingReportService reportService;

    @Override
    public List<GenericReportModel> getDefaultReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<String> topHeaderRow = new ArrayList<>();
        String[] topHeaderNames = {" ", " ", " ", " ", "Number of individuals tested by different testing modalities", "", ""};
        topHeaderRow.addAll(Arrays.asList(topHeaderNames));
        list.add(new GenericReportModel(topHeaderRow));
        List<String> headerRow = new ArrayList<>();
        String[] headerNames = {"Number of individuals mobilised for testing", "Number of individuals tested for HIV", "Number of individuals testing HIV positive",
                "Number of individuals initiated on ART", "HIV Self-Testing", "Home based testing", "Facility Testing"};
        headerRow.addAll(Arrays.asList(headerNames));
        list.add(new GenericReportModel(headerRow));
        List<String> row = new ArrayList<>();
        row.add(reportService.countIndividualsMobilizedForTesting(dto.getInstance(dto)).toString());
        row.add(reportService.countIndividualsTestingForHIV(dto).toString());
        dto.setResult(Result.POSITIVE);
        row.add(reportService.countIndividualsTestingPostive(dto).toString());
        dto.setYesNo(YesNo.YES);
        row.add(reportService.countByArtInitiation(dto.getInstance(dto)).toString());
        row.add(reportService.countByHIvSelfTesting(dto.getInstance(dto)).toString());
        row.add(reportService.countHomeBased(dto.getInstance(dto)).toString());
        row.add(reportService.countFacilityBased(dto.getInstance(dto)).toString());
        list.add(new GenericReportModel(row));
        return list;
    }

    @Override
    public List<GenericReportModel> getDefaultReportB(SearchDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
