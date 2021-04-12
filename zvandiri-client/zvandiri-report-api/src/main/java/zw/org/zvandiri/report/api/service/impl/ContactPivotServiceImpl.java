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

import zw.org.zvandiri.business.domain.Contact;
import zw.org.zvandiri.business.domain.util.AgeGroup;
import zw.org.zvandiri.business.service.ContactReportService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.business.util.pivot.dto.BaseContactPivotDTO;
import zw.org.zvandiri.business.util.pivot.dto.ContactDistrictPivotDTO;
import zw.org.zvandiri.business.util.pivot.dto.ContactNationalPivotDTO;
import zw.org.zvandiri.business.util.pivot.dto.ContactProvincePivotDTO;
import zw.org.zvandiri.report.api.service.ContactPivotService;
import zw.org.zvandiri.report.api.service.parallel.GenericCountReportTask;

/**
 *
 * @author Judge Muzinda
 */
@Repository
public class ContactPivotServiceImpl implements ContactPivotService {

    @Resource
    private ContactReportService contactReportService;

    @Override
    public List<BaseContactPivotDTO> get(SearchDTO dto) {
        return convertList(dto);
    }

    private List<BaseContactPivotDTO> convertList(SearchDTO dto) {
        List<BaseContactPivotDTO> items = new ArrayList<>();
        ForkJoinPool pool = ForkJoinPool.commonPool();
        List<Contact> contacts = pool.invoke(new GenericCountReportTask(DateUtil.generateArray(contactReportService.getCount(dto)), contactReportService, dto));
        for (Contact c : contacts) {
            if (dto.getDistrict() != null) {
                dto.setProvince(null);
                items.add(new ContactDistrictPivotDTO(
                        DateUtil.getYearMonthName(c.getContactDate()),
                        c.getCareLevel().getName(),
                        c.getFollowUp().getName(),
                        AgeGroup.get(AgeGroup.getAgeRange(c.getPatient().getAge())).getAltName(),
                        c.getPatient().getGender().getName(),
                        c.getPatient().getPrimaryClinic().getName()
                ));
            } else if (dto.getProvince() != null) {
                items.add(new ContactProvincePivotDTO(
                        DateUtil.getYearMonthName(c.getContactDate()),
                        c.getCareLevel().getName(),
                        c.getFollowUp().getName(),
                        AgeGroup.get(AgeGroup.getAgeRange(c.getPatient().getAge())).getAltName(),
                        c.getPatient().getGender().getName(),
                        c.getPatient().getPrimaryClinic().getDistrict().getName()));
            } else {
                items.add(new ContactNationalPivotDTO(
                        DateUtil.getYearMonthName(c.getContactDate()),
                        c.getCareLevel().getName(),
                        c.getFollowUp().getName(),
                        AgeGroup.get(AgeGroup.getAgeRange(c.getPatient().getAge())).getAltName(),
                        c.getPatient().getGender().getName(),
                        c.getPatient().getPrimaryClinic().getDistrict().getProvince().getName()));
            }
        }
        return items;
    }
}
