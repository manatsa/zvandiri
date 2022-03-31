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
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import zw.org.zvandiri.business.domain.District;
import zw.org.zvandiri.business.domain.Facility;
import zw.org.zvandiri.business.domain.Province;
import zw.org.zvandiri.business.domain.SupportGroup;
import zw.org.zvandiri.business.domain.util.Gender;
import zw.org.zvandiri.business.service.DetailedPatientReportService;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.service.SupportGroupService;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.report.api.GenericReportModel;
import zw.org.zvandiri.report.api.service.LocGenderReportService;

/**
 * @author Judge Muzinda
 */
@Repository
public class LocGenderReportServiceImpl implements LocGenderReportService {

    @Resource
    private DetailedPatientReportService detailedPatientReportService;
    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private FacilityService facilityService;
    @Resource
    private SupportGroupService supportGroupService;

    private List<GenericReportModel> getNationalReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<Province> provinces = provinceService.getAll();
        List<String> items = new ArrayList<>();
        items.add("");
        for (Gender element : Gender.values()) {
            items.add(element.getName());
        }
        items.add("Total");
        list.add(new GenericReportModel(items));
        for (Province province : provinces) {
            dto.setProvince(province);
            GenericReportModel model = new GenericReportModel();
            List<String> row = new ArrayList<>();
            row.add(province.getName());
            Long rowCount = 0L;
            for (Gender element : Gender.values()) {
                dto.setGender(element);
                Long itemCount = detailedPatientReportService.getCount(dto);
                row.add(itemCount.toString());
                rowCount += itemCount;
            }
            row.add(rowCount.toString());
            model.setRow(row);
            list.add(model);
        }
        return list;
    }

    private List<GenericReportModel> getProvincialReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<District> districts = districtService.getDistrictByProvince(dto.getProvince());
        //unset province
        dto.setProvince(null);
        List<String> items = new ArrayList<>();
        items.add("");
        for (Gender element : Gender.values()) {
            items.add(element.getName());
        }
        items.add("Total");
        list.add(new GenericReportModel(items));
        for (District district : districts) {
            dto.setDistrict(district);
            GenericReportModel model = new GenericReportModel();
            List<String> row = new ArrayList<>();
            row.add(district.getName());
            Long rowCount = 0L;
            for (Gender element : Gender.values()) {
                dto.setGender(element);
                Long itemCount = detailedPatientReportService.getCount(dto);
                row.add(itemCount.toString());
                rowCount += itemCount;
            }
            row.add(rowCount.toString());
            model.setRow(row);
            list.add(model);
        }
        return list;
    }

    private List<GenericReportModel> getDistrictReportFacility(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<Facility> facilitys = facilityService.getOptByDistrict(dto.getDistrict());
        //unset province and district to prevent un neccesary query joins down stream
        dto.setProvince(null);
        dto.setDistrict(null);
        List<String> items = new ArrayList<>();
        items.add("");
        for (Gender element : Gender.values()) {
            items.add(element.getName());
        }
        items.add("Total");
        list.add(new GenericReportModel(items));
        for (Facility facility : facilitys) {
            dto.setPrimaryClinic(facility);
            GenericReportModel model = new GenericReportModel();
            List<String> row = new ArrayList<>();
            row.add(facility.getName());
            Long rowCount = 0L;
            for (Gender element : Gender.values()) {
                dto.setGender(element);
                Long itemCount = detailedPatientReportService.getCount(dto);
                row.add(itemCount.toString());
                rowCount += itemCount;
            }
            row.add(rowCount.toString());
            model.setRow(row);
            list.add(model);
        }
        return list;
    }

    private List<GenericReportModel> getDistrictReportSupportGroup(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<SupportGroup> supportGroups = supportGroupService.getByDistrict(dto.getDistrict());
        //unset province and district to prevent un neccesary query joins down stream
        dto.setProvince(null);
        dto.setDistrict(null);
        List<String> items = new ArrayList<>();
        items.add("");
        for (Gender element : Gender.values()) {
            items.add(element.getName());
        }
        items.add("Total");
        list.add(new GenericReportModel(items));
        for (SupportGroup supportGroup : supportGroups) {
            dto.setSupportGroup(supportGroup);
            GenericReportModel model = new GenericReportModel();
            List<String> row = new ArrayList<>();
            row.add(supportGroup.getName());
            Long rowCount = 0L;
            for (Gender element : Gender.values()) {
                dto.setGender(element);
                Long itemCount = detailedPatientReportService.getCount(dto);
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
    public List<GenericReportModel> getDefaultReport(SearchDTO dto) {
        if (dto.getDistrict() != null) {
            return getDistrictReportFacility(dto);
        } else if (dto.getProvince() != null) {
            return getProvincialReport(dto);
        }
        return getNationalReport(dto);
    }

    @Override
    public List<GenericReportModel> getDefaultReportB(SearchDTO dto) {
        if (dto.getDistrict() != null) {
            return getDistrictReportSupportGroup(dto);
        } else if (dto.getProvince() != null) {
            return getProvincialReport(dto);
        }
        return getNationalReport(dto);
    }

}
