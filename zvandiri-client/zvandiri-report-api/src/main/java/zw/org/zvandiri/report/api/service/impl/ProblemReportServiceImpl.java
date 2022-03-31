/*
 * Copyright 2016 Tasunungurwa Muzinda.
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
import java.util.Date;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import zw.org.zvandiri.business.service.ProblemReportHeaderNames;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.report.api.GenericReportModel;
import zw.org.zvandiri.report.api.service.ProblemReportService;
import zw.org.zvandiri.business.domain.District;
import zw.org.zvandiri.business.domain.Facility;
import zw.org.zvandiri.business.domain.Province;
import zw.org.zvandiri.business.domain.util.AgeGroup;
import zw.org.zvandiri.business.domain.util.CrossTabOption;
import zw.org.zvandiri.business.domain.util.DateRangeItem;
import zw.org.zvandiri.business.domain.util.Gender;
import zw.org.zvandiri.business.service.ArvHistReportService;
import zw.org.zvandiri.business.service.ContactReportService;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.PatientReportService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.report.api.service.parallel.CombinedReportTask;

/**
 * @author Tasunungurwa Muzinda
 */
@Repository
public class ProblemReportServiceImpl implements ProblemReportService {

    @Resource
    private ContactReportService contactReportService;
    @Resource
    private PatientReportService patientReportService;
    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private FacilityService facilityService;
    @Resource
    private ArvHistReportService arvHistReportService;

    @Override
    public List<GenericReportModel> getDefaultReport(SearchDTO dto) {
        if (dto.getCrossTabOptions() == null || dto.getCrossTabOptions().isEmpty()) {
            return getNoCrossTab(dto);
        } else if (dto.getCrossTabOptions().size() > 1) {
            return getLocGenderAgeGroupReport(dto);
        } else if (dto.getCrossTabOptions().contains(CrossTabOption.GENDER)) {
            return getLocGenderReport(dto);
        }
        return getLocAgeGroupReport(dto);
    }

    private List<GenericReportModel> getLocGenderAgeGroupReport(SearchDTO dto) {
        if (dto.getDistrict() != null) {
            return getDistrictLocGenderAgeGroupReport(dto);
        } else if (dto.getProvince() != null) {
            return getProvinceLocGenderAgeGroupReport(dto);
        }
        return getNationalLocGenderAgeGroupReport(dto);
    }

    public List<GenericReportModel> getNoCrossTab(SearchDTO dto) {
        if (dto.getPrimaryClinic() != null) {
            return getFacilityReport(dto);
        } else if (dto.getDistrict() != null) {
            return getDistrictReport(dto);
        } else if (dto.getProvince() != null) {
            return getProvinceReport(dto);
        }
        return getNationalReport(dto);
    }

    private List<GenericReportModel> getLocGenderReport(SearchDTO dto) {
        if (dto.getDistrict() != null) {
            return getDistrictLocGenderReport(dto);
        } else if (dto.getProvince() != null) {
            return getProvincialLocGenderReport(dto);
        }
        return getNationalLocGenderReport(dto);
    }

    private List<GenericReportModel> getLocAgeGroupReport(SearchDTO dto) {
        if (dto.getDistrict() != null) {
            return getDistrictLocAgeGroupReport(dto);
        } else if (dto.getProvince() != null) {
            return getProvinceLocAgeGroupReport(dto);
        }
        return getNationalLocAgeGroupReport(dto);
    }

    public List<GenericReportModel> getDistrictReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<String> provinces = new ArrayList<>();
        List<String> districts = new ArrayList<>();
        List<String> items = new ArrayList<>();
        items.addAll(Arrays.asList(ProblemReportHeaderNames.headerNames));
        list.add(new GenericReportModel(items));
        District district = dto.getDistrict();
        List<Facility> facilitys = facilityService.getOptByDistrict(district);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.invoke(new CombinedReportTask(facilitys, this, dto, list, provinces, districts));
        return list;
    }

    public List<GenericReportModel> getNationalReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<String> provinces = new ArrayList<>();
        List<String> districts = new ArrayList<>();
        List<String> items = new ArrayList<>();
        items.addAll(Arrays.asList(ProblemReportHeaderNames.headerNames));
        list.add(new GenericReportModel(items));

        for (Province province : provinceService.getAll()) {
            dto.setProvince(province);
            int pos = 0;
            for (District district : districtService.getDistrictByProvince(province)) {
                dto.setDistrict(district);
                int inner = 0;
                List<Facility> facilitys = facilityService.getOptByDistrict(district);
                ForkJoinPool pool = ForkJoinPool.commonPool();
                pool.invoke(new CombinedReportTask(facilitys, this, dto, list, provinces, districts));
            }
        }
        return list;
    }

    public List<GenericReportModel> getFacilityReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<String> items = new ArrayList<>();
        items.addAll(Arrays.asList(ProblemReportHeaderNames.headerNames));
        list.add(new GenericReportModel(items));
        int inner = 0;
        int pos = 0;
        GenericReportModel model = new GenericReportModel();
        List<String> row = new ArrayList<>();
        row.add(pos == 0 ? dto.getPrimaryClinic().getDistrict().getProvince().getName() : "");
        row.add(inner == 0 ? dto.getPrimaryClinic().getDistrict().getName() : "");
        row.add(dto.getPrimaryClinic().getName());
        model.setRow(getProblemReport(row, dto.getInstance(dto)));
        list.add(model);
        inner++;
        pos++;
        return list;
    }

    public List<GenericReportModel> getProvinceReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<String> provinces = new ArrayList<>();
        List<String> districts = new ArrayList<>();
        List<String> items = new ArrayList<>();
        items.addAll(Arrays.asList(ProblemReportHeaderNames.headerNames));
        list.add(new GenericReportModel(items));
        Province province = dto.getProvince();
        int pos = 0;
        for (District district : districtService.getDistrictByProvince(province)) {
            dto.setDistrict(district);
            int inner = 0;
            List<Facility> facilitys = facilityService.getOptByDistrict(district);
            ForkJoinPool pool = ForkJoinPool.commonPool();
            pool.invoke(new CombinedReportTask(facilitys, this, dto, list, provinces, districts));
        }
        return list;
    }

    @Override
    public List<GenericReportModel> getDefaultReportB(SearchDTO dto) {
        throw new UnsupportedOperationException("Operation currently not supported");
    }

    private List<GenericReportModel> getNationalLocGenderReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<String> items = new ArrayList<>();
        items.add("");
        int topCount = 0;
        for (String indicator : ProblemReportHeaderNames.coreheaderNames) {
            items.add(indicator);
            items.add("");
            items.add("");
            topCount++;
        }
        items.add("");
        list.add(new GenericReportModel(items));
        //add second level items
        items = new ArrayList<>();
        items.add("");
        for (int i = 0; i < topCount; i++) {
            for (Gender gender : Gender.getItems()) {
                items.add(gender.getName());
            }
        }
        //items.add("Total");
        list.add(new GenericReportModel(items));
        for (Province province : provinceService.getAll()) {
            dto.setProvince(province);
            GenericReportModel model = new GenericReportModel();
            List<String> row = new ArrayList<>();
            row.add(province.getName());
            for (int i = 0; i < topCount; i++) {
                for (Gender gender : Gender.getItems()) {
                    dto.setGender(gender);
                    row.add(getItem(dto.getInstance(dto), i));
                }
            }
            model.setRow(row);
            list.add(model);
        }
        return list;
    }

    private List<GenericReportModel> getProvincialLocGenderReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<String> items = new ArrayList<>();
        items.add("");
        int topCount = 0;
        for (String indicator : ProblemReportHeaderNames.coreheaderNames) {
            items.add(indicator);
            items.add("");
            items.add("");
            topCount++;
        }
        items.add("");
        list.add(new GenericReportModel(items));
        //add second level items
        items = new ArrayList<>();
        items.add("");
        for (int i = 0; i < topCount; i++) {
            for (Gender gender : Gender.getItems()) {
                items.add(gender.getName());
            }
        }
        //items.add("Total");
        list.add(new GenericReportModel(items));
        for (District district : districtService.getDistrictByProvince(dto.getProvince())) {
            dto.setProvince(null);
            dto.setDistrict(district);
            GenericReportModel model = new GenericReportModel();
            List<String> row = new ArrayList<>();
            row.add(district.getName());
            for (int i = 0; i < topCount; i++) {
                for (Gender gender : Gender.getItems()) {
                    dto.setGender(gender);
                    row.add(getItem(dto.getInstance(dto), i));
                }
            }
            model.setRow(row);
            list.add(model);
        }
        return list;
    }

    private List<GenericReportModel> getDistrictLocGenderReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<String> items = new ArrayList<>();
        items.add("");
        int topCount = 0;
        for (String indicator : ProblemReportHeaderNames.coreheaderNames) {
            items.add(indicator);
            items.add("");
            items.add("");
            topCount++;
        }
        items.add("");
        list.add(new GenericReportModel(items));
        //add second level items
        items = new ArrayList<>();
        items.add("");
        for (int i = 0; i < topCount; i++) {
            for (Gender gender : Gender.getItems()) {
                items.add(gender.getName());
            }
        }
        //items.add("Total");
        list.add(new GenericReportModel(items));
        for (Facility facility : facilityService.getOptByDistrict(dto.getDistrict())) {
            dto.setProvince(null);
            dto.setDistrict(null);
            dto.setPrimaryClinic(facility);
            GenericReportModel model = new GenericReportModel();
            List<String> row = new ArrayList<>();
            row.add(facility.getName());
            for (int i = 0; i < topCount; i++) {
                for (Gender gender : Gender.getItems()) {
                    dto.setGender(gender);
                    row.add(getItem(dto.getInstance(dto), i));
                }
            }
            model.setRow(row);
            list.add(model);
        }
        return list;
    }

    private List<GenericReportModel> getNationalLocAgeGroupReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<String> items = new ArrayList<>();
        items.add("");
        int topCount = 0;
        for (String indicator : ProblemReportHeaderNames.coreheaderNames) {
            items.add(indicator);
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            topCount++;
        }
        items.add("");
        list.add(new GenericReportModel(items));
        //add second level items
        items = new ArrayList<>();
        items.add("");
        for (int i = 0; i < topCount; i++) {
            for (AgeGroup ageGroup : AgeGroup.values()) {
                items.add(ageGroup.getName());
            }
        }
        //items.add("Total");
        list.add(new GenericReportModel(items));
        for (Province province : provinceService.getAll()) {
            dto.setProvince(province);
            GenericReportModel model = new GenericReportModel();
            List<String> row = new ArrayList<>();
            row.add(province.getName());
            for (int i = 0; i < topCount; i++) {
                for (AgeGroup ageGroup : AgeGroup.values()) {
                    dto.setAgeGroup(ageGroup);
                    row.add(getItem(dto.getInstance(dto), i));
                }
            }
            model.setRow(row);
            list.add(model);
        }
        return list;
    }

    private List<GenericReportModel> getProvinceLocAgeGroupReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<String> items = new ArrayList<>();
        items.add("");
        int topCount = 0;
        for (String indicator : ProblemReportHeaderNames.coreheaderNames) {
            items.add(indicator);
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            topCount++;
        }
        items.add("");
        list.add(new GenericReportModel(items));
        //add second level items
        items = new ArrayList<>();
        items.add("");
        for (int i = 0; i < topCount; i++) {
            for (AgeGroup ageGroup : AgeGroup.values()) {
                items.add(ageGroup.getName());
            }
        }
        //items.add("Total");
        list.add(new GenericReportModel(items));
        for (District district : districtService.getDistrictByProvince(dto.getProvince())) {
            dto.setProvince(null);
            dto.setDistrict(district);
            GenericReportModel model = new GenericReportModel();
            List<String> row = new ArrayList<>();
            row.add(district.getName());
            for (int i = 0; i < topCount; i++) {
                for (AgeGroup ageGroup : AgeGroup.values()) {
                    dto.setAgeGroup(ageGroup);
                    row.add(getItem(dto.getInstance(dto), i));
                }
            }
            model.setRow(row);
            list.add(model);
        }
        return list;
    }

    private List<GenericReportModel> getDistrictLocAgeGroupReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<String> items = new ArrayList<>();
        items.add("");
        int topCount = 0;
        for (String indicator : ProblemReportHeaderNames.coreheaderNames) {
            items.add(indicator);
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            topCount++;
        }
        items.add("");
        list.add(new GenericReportModel(items));
        //add second level items
        items = new ArrayList<>();
        items.add("");
        for (int i = 0; i < topCount; i++) {
            for (AgeGroup ageGroup : AgeGroup.values()) {
                items.add(ageGroup.getName());
            }
        }
        //items.add("Total");
        list.add(new GenericReportModel(items));
        for (Facility facility : facilityService.getOptByDistrict(dto.getDistrict())) {
            dto.setProvince(null);
            dto.setDistrict(null);
            dto.setPrimaryClinic(facility);
            GenericReportModel model = new GenericReportModel();
            List<String> row = new ArrayList<>();
            row.add(facility.getName());
            for (int i = 0; i < topCount; i++) {
                for (AgeGroup ageGroup : AgeGroup.values()) {
                    dto.setAgeGroup(ageGroup);
                    row.add(getItem(dto.getInstance(dto), i));
                }
            }
            model.setRow(row);
            list.add(model);
        }
        return list;
    }

    private List<GenericReportModel> getNationalLocGenderAgeGroupReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<String> items = new ArrayList<>();
        items.add("");
        int topCount = 0;
        for (String indicator : ProblemReportHeaderNames.coreheaderNames) {
            items.add(indicator);
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            topCount++;
        }
        items.add("");
        list.add(new GenericReportModel(items));
        //add second level items
        items = new ArrayList<>();
        items.add("");
        int secondLevelCount = 0;
        for (int i = 0; i < topCount; i++) {
            for (AgeGroup ageGroup : AgeGroup.values()) {
                items.add(ageGroup.getName());
                items.add("");
                items.add("");
                secondLevelCount++;
            }
        }
        list.add(new GenericReportModel(items));
        //add third level items
        items = new ArrayList<>();
        items.add("");
        for (int i = 0; i < secondLevelCount; i++) {
            for (Gender gender : Gender.getItems()) {
                items.add(gender.getAltName());
            }
        }
        //items.add("Total");
        list.add(new GenericReportModel(items));
        for (Province province : provinceService.getAll()) {
            dto.setProvince(province);
            GenericReportModel model = new GenericReportModel();
            List<String> row = new ArrayList<>();
            row.add(province.getName());
            for (int i = 0; i < topCount; i++) {
                for (AgeGroup ageGroup : AgeGroup.values()) {
                    dto.setAgeGroup(ageGroup);
                    for (Gender gender : Gender.getItems()) {
                        dto.setGender(gender);
                        row.add(getItem(dto.getInstance(dto), i));
                    }
                }
            }
            model.setRow(row);
            list.add(model);
        }
        return list;
    }

    private List<GenericReportModel> getProvinceLocGenderAgeGroupReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<String> items = new ArrayList<>();
        items.add("");
        int topCount = 0;
        for (String indicator : ProblemReportHeaderNames.coreheaderNames) {
            items.add(indicator);
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            topCount++;
        }
        items.add("");
        list.add(new GenericReportModel(items));
        //add second level items
        items = new ArrayList<>();
        items.add("");
        int secondLevelCount = 0;
        for (int i = 0; i < topCount; i++) {
            for (AgeGroup ageGroup : AgeGroup.values()) {
                items.add(ageGroup.getName());
                items.add("");
                items.add("");
                secondLevelCount++;
            }
        }
        list.add(new GenericReportModel(items));
        //add third level items
        items = new ArrayList<>();
        items.add("");
        for (int i = 0; i < secondLevelCount; i++) {
            for (Gender gender : Gender.getItems()) {
                items.add(gender.getAltName());
            }
        }
        //items.add("Total");
        list.add(new GenericReportModel(items));
        for (District district : districtService.getDistrictByProvince(dto.getProvince())) {
            dto.setProvince(null);
            dto.setDistrict(district);
            GenericReportModel model = new GenericReportModel();
            List<String> row = new ArrayList<>();
            row.add(district.getName());
            for (int i = 0; i < topCount; i++) {
                for (AgeGroup ageGroup : AgeGroup.values()) {
                    dto.setAgeGroup(ageGroup);
                    for (Gender gender : Gender.getItems()) {
                        dto.setGender(gender);
                        row.add(getItem(dto.getInstance(dto), i));
                    }
                }
            }
            model.setRow(row);
            list.add(model);
        }
        return list;
    }

    private List<GenericReportModel> getDistrictLocGenderAgeGroupReport(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<String> items = new ArrayList<>();
        items.add("");
        int topCount = 0;
        for (String indicator : ProblemReportHeaderNames.coreheaderNames) {
            items.add(indicator);
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            items.add("");
            topCount++;
        }
        items.add("");
        list.add(new GenericReportModel(items));
        //add second level items
        items = new ArrayList<>();
        items.add("");
        int secondLevelCount = 0;
        for (int i = 0; i < topCount; i++) {
            for (AgeGroup ageGroup : AgeGroup.values()) {
                items.add(ageGroup.getName());
                items.add("");
                items.add("");
                secondLevelCount++;
            }
        }
        list.add(new GenericReportModel(items));
        //add third level items
        items = new ArrayList<>();
        items.add("");
        for (int i = 0; i < secondLevelCount; i++) {
            for (Gender gender : Gender.getItems()) {
                items.add(gender.getName());
            }
        }
        //items.add("Total");
        list.add(new GenericReportModel(items));
        for (Facility facility : facilityService.getOptByDistrict(dto.getDistrict())) {
            dto.setProvince(null);
            dto.setDistrict(null);
            dto.setPrimaryClinic(facility);
            GenericReportModel model = new GenericReportModel();
            List<String> row = new ArrayList<>();
            row.add(facility.getName());
            dto.setPrimaryClinic(facility);
            for (int i = 0; i < topCount; i++) {
                for (AgeGroup ageGroup : AgeGroup.values()) {
                    dto.setAgeGroup(ageGroup);
                    for (Gender gender : Gender.getItems()) {
                        dto.setGender(gender);
                        row.add(getItem(dto.getInstance(dto), i));
                    }
                }
            }
            model.setRow(row);
            list.add(model);
        }
        return list;
    }

    private String getItem(SearchDTO dto, int pos) {
        pos++;
        switch (pos) {
            case 1:
                return contactReportService.getCount(dto.getInstance(dto)).toString();
            case 2:
                return patientReportService.getCount(dto.getInstance(dto)).toString();
            case 3:
                dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd()));
                return patientReportService.getNewlyRegistered(dto.getInstance(dto)).toString();
            case 4:
                dto.setEndDate(DateUtil.getDateFromAge(23));
                dto.setStartDate(DateUtil.getDateFromAge(24));
                return patientReportService.getPatientAboutToGraduate(dto.getInstance(dto)).toString();
            case 5:
                dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd()));
                return arvHistReportService.getNewlyInitiatedOnART(dto.getInstance(dto)).toString();
            case 6:
                dto.setStartDate(null);
                return arvHistReportService.getNumberCurrentlyOnART(dto.getInstance(dto)).toString();
            case 7:
                dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd()));
                dto.setEndDate(DateUtil.getDateDiffDate(-(DateRangeItem.PAST_SIX_MONTHS.getEnd()) - 1));
                return arvHistReportService.getOnARTForGivenPeriod(dto.getInstance(dto)).toString();
            case 8:
                dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_SIX_MONTHS.getEnd()));
                dto.setEndDate(DateUtil.getDateDiffDate(-(DateRangeItem.PAST_TWELVE_MONTHS.getEnd()) - 1));
                return arvHistReportService.getOnARTForGivenPeriod(dto.getInstance(dto)).toString();
            case 9:
                dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWELVE_MONTHS.getEnd()));
                dto.setEndDate(DateUtil.getDateDiffDate(-(DateRangeItem.PAST_TWENTY_FOUR_MONTHS.getEnd()) - 1));
                return arvHistReportService.getOnARTForGivenPeriod(dto.getInstance(dto)).toString();
            case 10:
                dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWENTY_FOUR_MONTHS.getEnd()));
                dto.setEndDate(DateUtil.getDateDiffDate(-(DateRangeItem.PAST_THIRTY_SIX_MONTHS.getEnd()) - 1));
                return arvHistReportService.getOnARTForGivenPeriod(dto.getInstance(dto)).toString();
            case 11:
                dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THIRTY_SIX_MONTHS.getEnd()));
                return arvHistReportService.getOnARTForGivenPeriod(dto.getInstance(dto)).toString();
            case 12:
                Date startDate = DateUtil.getDateDiffDate(-DateRangeItem.ABOVE_THIRTY_SIX_MONTHS.getEnd());
                Date endDate = DateUtil.getDateDiffDate(-DateRangeItem.ABOVE_THIRTY_SIX_MONTHS.getStart());
                dto.setStartDate(startDate);
                dto.setEndDate(endDate);
                return arvHistReportService.getOnARTForGivenPeriod(dto.getInstance(dto)).toString();
            default:
                throw new IllegalArgumentException("Illegal position parameter passed to method");
        }
    }

    @Override
    public List<String> getProblemReport(List<String> row, SearchDTO dto) {
        Long count = contactReportService.getCount(dto.getInstance(dto));
        row.add(count.toString());
        count = patientReportService.getCount(dto.getInstance(dto));
        row.add(count.toString());
        dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd()));
        count = patientReportService.getNewlyRegistered(dto.getInstance(dto));
        row.add(count.toString());
        dto.setEndDate(DateUtil.getDateFromAge(23));
        dto.setStartDate(DateUtil.getDateFromAge(24));
        count = patientReportService.getPatientAboutToGraduate(dto.getInstance(dto));
        row.add(count.toString());
        dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd()));
        count = arvHistReportService.getNewlyInitiatedOnART(dto.getInstance(dto));
        row.add(count.toString());
        dto.setStartDate(null);
        count = arvHistReportService.getNumberCurrentlyOnART(dto.getInstance(dto));
        row.add(count.toString());
        dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd()));
        dto.setEndDate(DateUtil.getDateDiffDate(-(DateRangeItem.PAST_SIX_MONTHS.getEnd() - 1)));
        count = arvHistReportService.getOnARTForGivenPeriod(dto.getInstance(dto));
        row.add(count.toString());
        dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_SIX_MONTHS.getEnd()));
        dto.setEndDate(DateUtil.getDateDiffDate(-(DateRangeItem.PAST_TWELVE_MONTHS.getEnd() - 1)));
        count = arvHistReportService.getOnARTForGivenPeriod(dto.getInstance(dto));
        row.add(count.toString());
        dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWELVE_MONTHS.getEnd()));
        dto.setEndDate(DateUtil.getDateDiffDate(-(DateRangeItem.PAST_TWENTY_FOUR_MONTHS.getEnd() - 1)));
        count = arvHistReportService.getOnARTForGivenPeriod(dto.getInstance(dto));
        row.add(count.toString());
        dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWENTY_FOUR_MONTHS.getEnd()));
        dto.setEndDate(DateUtil.getDateDiffDate(-(DateRangeItem.PAST_THIRTY_SIX_MONTHS.getEnd() - 1)));
        count = arvHistReportService.getOnARTForGivenPeriod(dto.getInstance(dto));
        row.add(count.toString());
        dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THIRTY_SIX_MONTHS.getEnd()));
        dto.setEndDate(DateUtil.getDateDiffDate(-(DateRangeItem.ABOVE_THIRTY_SIX_MONTHS.getEnd() - 1)));
        count = arvHistReportService.getOnARTForGivenPeriod(dto.getInstance(dto));
        row.add(count.toString());
        Date startDate = DateUtil.getDateDiffDate(-DateRangeItem.ABOVE_THIRTY_SIX_MONTHS.getEnd());
        Date endDate = DateUtil.getDateDiffDate(-(DateRangeItem.ABOVE_THIRTY_SIX_MONTHS.getStart() - 1));
        dto.setStartDate(startDate);
        dto.setEndDate(endDate);
        count = arvHistReportService.getOnARTForGivenPeriod(dto.getInstance(dto));
        row.add(count.toString());
        return row;
    }
}
