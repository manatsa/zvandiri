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
import zw.org.zvandiri.business.domain.District;
import zw.org.zvandiri.business.domain.Province;
import zw.org.zvandiri.business.domain.util.TbTreatmentOutcome;
import zw.org.zvandiri.business.domain.util.TbTreatmentStatus;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.service.TbScreeningReportService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.QuarterMod;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.report.api.GenericReportModel;
import zw.org.zvandiri.report.api.service.TbScreeningReportReportService;

/**
 *
 * @author tasu
 */
@Repository
public class TbScreeningReportReportServiceImpl implements TbScreeningReportReportService {

    @Resource
    private TbScreeningReportService reportService;
    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;

    @Override
    public List<GenericReportModel> getDefaultReport(SearchDTO dto) {
        if (dto.getDistrict() != null) {
            return getDistrictReport(dto);
        } else if (dto.getProvince() != null) {
            return getProvinceReport(dto);
        }
        return getNationalReport(dto);
    }

    public List<String> getTbScreen(List<String> row, SearchDTO dto) {
        Long count = reportService.get(dto.getInstance(dto));
        row.add(count.toString());
        dto.setYesNo(YesNo.YES);
        count = reportService.get(dto.getInstance(dto));
        row.add(count.toString());
        return row;
    }

    public List<GenericReportModel> getNationalReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<String> items = new ArrayList<>();
        String headerNames[] = {"Province", "District", "Individuals Screened For Tb", "Individuals On Tb Treatment"};
        items.addAll(Arrays.asList(headerNames));
        list.add(new GenericReportModel(items));
        for (Province province : provinceService.getAll()) {
            dto.setProvince(province);
            int pos = 0;
            for (District district : districtService.getDistrictByProvince(province)) {
                dto.setDistrict(district);
                int inner = 0;
                GenericReportModel model = new GenericReportModel();
                List<String> row = new ArrayList<>();
                row.add(pos == 0 ? province.getName() : "");
                row.add(inner == 0 ? district.getName() : "");
                model.setRow(getTbScreen(row, dto));
                list.add(model);
                inner++;
                pos++;
            }
        }
        return list;
    }

    public List<GenericReportModel> getProvinceReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<String> items = new ArrayList<>();
        String headerNames[] = {"Province", "District", "Individuals Screened For Tb", "Individuals On Tb Treatment"};
        items.addAll(Arrays.asList(headerNames));
        list.add(new GenericReportModel(items));
        Province province = dto.getProvince();
        int pos = 0;
        for (District district : districtService.getDistrictByProvince(province)) {
            dto.setDistrict(district);
            int inner = 0;
            GenericReportModel model = new GenericReportModel();
            List<String> row = new ArrayList<>();
            row.add(pos == 0 ? province.getName() : "");
            row.add(inner == 0 ? district.getName() : "");
            model.setRow(getTbScreen(row, dto));
            list.add(model);
            inner++;
            pos++;
        }
        return list;
    }

    public List<GenericReportModel> getDistrictReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<String> items = new ArrayList<>();
        String headerNames[] = {"Province", "District", "Individuals Screened For Tb", "Individuals On Tb Treatment"};
        items.addAll(Arrays.asList(headerNames));
        list.add(new GenericReportModel(items));
        District district = dto.getDistrict();
        int pos = 0;
        int inner = 0;
        GenericReportModel model = new GenericReportModel();
        List<String> row = new ArrayList<>();
        row.add(pos == 0 ? district.getProvince().getName() : "");
        row.add(inner == 0 ? district.getName() : "");
        model.setRow(getTbScreen(row, dto));
        list.add(model);
        inner++;
        pos++;
        return list;
    }

    @Override
    public List<GenericReportModel> getTbTreatmentStatusTrendReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        QuarterMod quarterMod = new QuarterMod(null, null);
        List<QuarterMod> periods = quarterMod.getPastSixQuarters();
        List<String> items = new ArrayList<>();
        items.add("");
        for (QuarterMod p : periods) {
            items.add(DateUtil.formatDate(p.getEndDate()));
        }
        items.add("Total");
        list.add(new GenericReportModel(items));
        for (TbTreatmentStatus element : TbTreatmentStatus.values()) {
            dto.setTbTreatmentStatus(element);
            GenericReportModel model = new GenericReportModel();
            List<String> row = new ArrayList<>();
            row.add(element.getName());
            Integer rowCount = 0;
            for (QuarterMod p : periods) {
                dto.setStartDate(p.getStartDate());
                dto.setEndDate(p.getEndDate());
                Integer itemCount = reportService.get(dto) != null ? reportService.get(dto).intValue() : 0;
                row.add(itemCount.toString());
                rowCount += itemCount;
            }
            row.add(rowCount.toString());
            model.setRow(row);
            list.add(model);
        }
        return list;
    }
    
    @Override
    public List<GenericReportModel> getTbTreatmentOutcomeTrendReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        QuarterMod quarterMod = new QuarterMod(null, null);
        List<QuarterMod> periods = quarterMod.getPastSixQuarters();
        List<String> items = new ArrayList<>();
        items.add("");
        for (QuarterMod p : periods) {
            items.add(DateUtil.formatDate(p.getEndDate()));
        }
        items.add("Total");
        list.add(new GenericReportModel(items));
        for (TbTreatmentOutcome element : TbTreatmentOutcome.values()) {
            dto.setTbTreatmentOutcome(element);
            GenericReportModel model = new GenericReportModel();
            List<String> row = new ArrayList<>();
            row.add(element.getName());
            Integer rowCount = 0;
            for (QuarterMod p : periods) {
                dto.setStartDate(p.getStartDate());
                dto.setEndDate(p.getEndDate());
                Integer itemCount = reportService.get(dto) != null ? reportService.get(dto).intValue() : 0;
                row.add(itemCount.toString());
                rowCount += itemCount;
            }
            row.add(rowCount.toString());
            model.setRow(row);
            list.add(model);
        }
        return list;
    }

    @Override
    public List<GenericReportModel> getDefaultReportB(SearchDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
