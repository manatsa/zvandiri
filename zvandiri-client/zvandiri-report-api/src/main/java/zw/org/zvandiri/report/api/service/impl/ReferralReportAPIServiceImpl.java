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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import zw.org.zvandiri.business.domain.Period;
import zw.org.zvandiri.business.domain.Referral;
import zw.org.zvandiri.business.domain.util.AgeGroup;
import zw.org.zvandiri.business.domain.util.DateRangeItem;
import zw.org.zvandiri.business.domain.util.PatientChangeEvent;
import zw.org.zvandiri.business.domain.util.Reason;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.CatDetailReportService;
import zw.org.zvandiri.business.service.ContactReportService;
import zw.org.zvandiri.business.service.PatientReportService;
import zw.org.zvandiri.business.service.PeriodService;
import zw.org.zvandiri.business.service.ReferalReportService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.report.api.BasicNameNumber;
import zw.org.zvandiri.report.api.GenericReportModel;
import zw.org.zvandiri.report.api.Notifications;
import zw.org.zvandiri.report.api.service.ReferralReportAPIService;
import zw.org.zvandiri.report.api.service.parallel.GenericCountReportTask;

/**
 * @author Judge Muzinda
 */
@Repository
public class ReferralReportAPIServiceImpl implements ReferralReportAPIService {

    @Resource
    private ReferalReportService referalReportService;
    @Resource
    private ContactReportService contactReportService;
    @Resource
    private PatientReportService patientReportService;
    @Resource
    private PeriodService periodService;
    @Resource
    private CatDetailReportService catDetailReportService;

    @Override
    public List<BasicNameNumber> getNotifications(SearchDTO dto) {
        List<BasicNameNumber> list = new ArrayList<>();
        int i = 0;
        Integer threeMonths = null;
        Integer sixMonths = null;
        Integer twelveMonths = null;
        dto.setStatus(PatientChangeEvent.ACTIVE);
        for (String item : Notifications.labels) {
            switch (i) {
                case 0:
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd()));
                    threeMonths = patientReportService.getNewlyRegistered(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_SIX_MONTHS.getEnd()));
                    sixMonths = patientReportService.getNewlyRegistered(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWELVE_MONTHS.getEnd()));
                    twelveMonths = patientReportService.getNewlyRegistered(dto).intValue();
                    list.add(new BasicNameNumber(item, threeMonths, sixMonths, twelveMonths));
                    i++;
                    continue;
                case 1:
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd()));
                    threeMonths = patientReportService.getPatientWithContact(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_SIX_MONTHS.getEnd()));
                    sixMonths = patientReportService.getPatientWithContact(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWELVE_MONTHS.getEnd()));
                    twelveMonths = patientReportService.getPatientWithContact(dto).intValue();
                    list.add(new BasicNameNumber(item, threeMonths, "/report/contact-services-offered/three-months", sixMonths, "/report/contact-services-offered/six-months", twelveMonths, "/report/contact-services-offered/twelve-months"));
                    i++;
                    continue;
                case 2:
                    dto.setEndDate(DateUtil.getDateFromAge(23));
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd(), DateUtil.getDateFromAge(24)));
                    threeMonths = patientReportService.getPatientAboutToGraduate(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_SIX_MONTHS.getEnd(), DateUtil.getDateFromAge(24)));
                    sixMonths = patientReportService.getPatientAboutToGraduate(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWELVE_MONTHS.getEnd(), DateUtil.getDateFromAge(24)));
                    twelveMonths = patientReportService.getPatientAboutToGraduate(dto).intValue();
                    list.add(new BasicNameNumber(item, threeMonths, sixMonths, twelveMonths));
                    i++;
                    continue;
                case 3:
                    dto.setReason(Reason.INTERNAL_REFERRAL);
                    //dto.setYesNo(YesNo.NO);
                    dto.setEndDate(new Date());
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd()));
                    threeMonths = contactReportService.getCount(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_SIX_MONTHS.getEnd()));
                    sixMonths = contactReportService.getCount(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWELVE_MONTHS.getEnd()));
                    twelveMonths = contactReportService.getCount(dto).intValue();
                    list.add(new BasicNameNumber(item, threeMonths, sixMonths, twelveMonths));
                    i++;
                    continue;
                case 4:
                    dto.setReason(Reason.INTERNAL_REFERRAL);
                    dto.setYesNo(YesNo.YES);
                    dto.setEndDate(new Date());
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd()));
                    threeMonths = contactReportService.getCount(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_SIX_MONTHS.getEnd()));
                    sixMonths = contactReportService.getCount(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWELVE_MONTHS.getEnd()));
                    twelveMonths = contactReportService.getCount(dto).intValue();
                    list.add(new BasicNameNumber(item, threeMonths, sixMonths, twelveMonths));
                    i++;
                    continue;
                case 5:
                    dto.setReason(Reason.EXTERNAL_REFERRAL);
                    //dto.setYesNo(YesNo.NO);
                    dto.setEndDate(new Date());
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd()));
                    threeMonths = contactReportService.getCount(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_SIX_MONTHS.getEnd()));
                    sixMonths = contactReportService.getCount(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWELVE_MONTHS.getEnd()));
                    twelveMonths = contactReportService.getCount(dto).intValue();
                    list.add(new BasicNameNumber(item, threeMonths, sixMonths, twelveMonths));
                    i++;
                    continue;
                case 6:
                    dto.setReason(Reason.EXTERNAL_REFERRAL);
                    dto.setYesNo(YesNo.YES);
                    dto.setEndDate(new Date());
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd()));
                    threeMonths = contactReportService.getCount(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_SIX_MONTHS.getEnd()));
                    sixMonths = contactReportService.getCount(dto).intValue();
                    dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWELVE_MONTHS.getEnd()));
                    twelveMonths = contactReportService.getCount(dto).intValue();
                    list.add(new BasicNameNumber(item, threeMonths, sixMonths, twelveMonths));
                    i++;
                    continue;
                default:
                    list.add(new BasicNameNumber(item, i, 0, 0));
                    i++;
            }
        }
        return list;
    }

    @Override
    public List<GenericReportModel> getPieDefaultData(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<String> items = new ArrayList<>();
        items.add("");
        for (AgeGroup item : AgeGroup.values()) {
            items.add(item.getName());
        }
        items.add("Total");
        list.add(new GenericReportModel(items));
        GenericReportModel model = new GenericReportModel();
        List<String> row = new ArrayList<>();
        Long rowCount = 0L;
        row.add("Counts");
        for (AgeGroup item : AgeGroup.values()) {
            dto.setAgeGroup(item);
            Long itemCount = referalReportService.getCount(dto);
            row.add(itemCount.toString());
            rowCount += itemCount;
        }
        row.add(rowCount.toString());
        model.setRow(row);
        list.add(model);
        return list;
    }

    @Override
    public List<GenericReportModel> getPeriodRange(SearchDTO dto) {
        List<GenericReportModel> list = new ArrayList<>();
        List<Period> periods = periodService.getPastPeriod(6);
        List<String> items = new ArrayList<>();
        items.add("");
        for (Period period : periods) {
            items.add(DateUtil.periodFriendly.format(period.getEndDate()));
        }
        items.add("Total");
        list.add(new GenericReportModel(items));
        GenericReportModel model = new GenericReportModel();
        List<String> row = new ArrayList<>();
        row.add("Counts");
        Integer rowCount = 0;
        for (Period period : periods) {
            dto.setStartDate(period.getStartDate());
            dto.setEndDate(period.getEndDate());
            Integer itemCount = referalReportService.getCount(dto) != null ? referalReportService.getCount(dto).intValue() : 0;
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
        String[] headers = {"Name", "Age", "Gender", "Phone No.", "IS CATS", "In YMM Programme", "In YMD Programme", "District", "Clinic", "Date Of Entry",
                "Referral Date", "Organisation", "Services Requested", "Services Received"};
        List<GenericReportModel> items = new ArrayList<>();
        items.add(new GenericReportModel(Arrays.asList(headers)));
        ForkJoinPool pool = ForkJoinPool.commonPool();
        List<Referral> result = pool.invoke(new GenericCountReportTask(DateUtil.generateArray(referalReportService.getCount(dto)), referalReportService, dto));
        for (Referral item : result) {
            String[] inner = {
                    item.getPatient().getName(),
                    item.getPatient().getAge() + "",
                    item.getPatient().getGender().getName(),
                    item.getPatient().getMobileNumber(),
                    item.getPatient().getCat() != null ? item.getPatient().getCat().getName() : "",
                    item.getPatient().getYoungMumGroup() != null ? item.getPatient().getYoungMumGroup().getName() : "",
                    item.getPatient().getYoungDadGroup() != null ? item.getPatient().getYoungDadGroup().getName() : "",
                    item.getPatient().getPrimaryClinic().getDistrict().getName(),
                    item.getPatient().getPrimaryClinic().getName(),
                    item.getDateCreated() != null ? item.getDateCreated().toString() : "",
                    DateUtil.getStringFromDate(item.getReferralDate()),
                    item.getOrganisation(),
                    item.getServicesRequested() != null && !item.getServicesRequested().isEmpty() ? item.getServicesRequested().toString() : null,
                    item.getServicesReceived() != null && !item.getServicesReceived().isEmpty() ? item.getServicesReceived().toString() : null
            };
            items.add(new GenericReportModel(Arrays.asList(inner)));
        }
        return items;
    }

    @Override
    public List<GenericReportModel> getDefaultReportB(SearchDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
