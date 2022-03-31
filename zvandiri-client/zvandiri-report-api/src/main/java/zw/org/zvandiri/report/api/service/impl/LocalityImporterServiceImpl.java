/*
 * Copyright 2016 jmuzinda.
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

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import zw.org.zvandiri.business.domain.District;
import zw.org.zvandiri.business.domain.Facility;
import zw.org.zvandiri.business.domain.Province;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.report.api.Locality;
import zw.org.zvandiri.report.api.service.LocalityImporterService;

/**
 * @author jmuzinda
 */
@Repository
public class LocalityImporterServiceImpl implements LocalityImporterService {

    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private FacilityService facilityService;

    private List<Locality> getList(String file) throws FileNotFoundException {
        CsvToBean csv = new CsvToBean();
        CSVReader csvReader = new CSVReader(new FileReader(file));
        List list = csv.parse(setColumMapping(), csvReader);
        return (List<Locality>) list;
    }

    public static HeaderColumnNameTranslateMappingStrategy setColumMapping() {
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("Province", "province");
        columnMapping.put("District", "district");
        columnMapping.put("Facility", "facility");
        HeaderColumnNameTranslateMappingStrategy<Locality> strategy
                = new HeaderColumnNameTranslateMappingStrategy<>();
        strategy.setType(Locality.class);
        strategy.setColumnMapping(columnMapping);
        return strategy;
    }

    @Override
    public void importData(String file) throws FileNotFoundException, ParseException {
        List<Locality> items = getList(file);
        importProvinces(items);
    }

    private void importProvinces(List<Locality> data) {

        for (Locality d : data) {
            if (d.getProvince() == null) {
                continue;
            }
            Province province = provinceService.getByName(d.getProvince());
            if (province == null) {
                province = new Province();
                province.setName(d.getProvince());
                province = provinceService.save(province);

            }
            District district = districtService.getByNameAndProvince(d.getDistrict(), province);
            if (district == null) {
                district = new District();
                district.setName(d.getDistrict());
                district.setProvince(province);
                district = districtService.save(district);
            }
            Facility facility = facilityService.getByNameAndDistrict(d.getFacility(), district);
            if (facility == null) {
                facility = new Facility();
                facility.setName(d.getFacility());
                facility.setDistrict(district);
                facilityService.save(facility);
            }
        }
    }
}
