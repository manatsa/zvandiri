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
package zw.org.zvandiri.portal.web.controller.report;

import java.util.Date;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.domain.util.DateRangeItem;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.ContactReportService;
import zw.org.zvandiri.business.service.PatientReportService;
import zw.org.zvandiri.business.service.ReferalReportService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;

/**
 * @author Judge Muzinda
 */
@RequestMapping("/report/notification")
@Controller
public class NotificationDetailedController extends BaseController {

    @Resource
    private ReferalReportService referalReportService;
    @Resource
    private ContactReportService contactReportService;
    @Resource
    private PatientReportService patientReportService;

    @RequestMapping(value = "/new-contacts-past-week", method = RequestMethod.GET)
    public String getNewContactsPastOneWeek(ModelMap model) {
        model.addAttribute("pageTitle", APP_PREFIX + "New Contacts Received Past 1 Week");
        SearchDTO dto = new SearchDTO();
        dto = getUserLevelObjectState(dto);
        dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_ONE_WEEK.getEnd()));
        dto.setEndDate(new Date());
        model.addAttribute("items", contactReportService.get(dto));
        return "report/notification/contact";
    }

    @RequestMapping(value = "/new-contacts-past-two-week", method = RequestMethod.GET)
    public String getNewContactsPastTwoWeek(ModelMap model) {
        model.addAttribute("pageTitle", APP_PREFIX + "New Contacts Received Past 2 Weeks");
        SearchDTO dto = new SearchDTO();
        dto = getUserLevelObjectState(dto);
        dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWO_WEEKS.getEnd()));
        dto.setEndDate(new Date());
        model.addAttribute("items", contactReportService.get(dto));
        return "report/notification/contact";
    }

    @RequestMapping(value = "/no-contacts-past-one-month", method = RequestMethod.GET)
    public String getNoContactsPastOneMonth(ModelMap model) {
        model.addAttribute("pageTitle", APP_PREFIX + "Patients with no Contact Past One Month");
        SearchDTO dto = new SearchDTO();
        dto = getUserLevelObjectState(dto);
        dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_ONE_MONTH.getEnd()));
        dto.setEndDate(new Date());
        model.addAttribute("items", patientReportService.getPatientWithNoContactList(dto));
        return "report/notification/patient";
    }

    @RequestMapping(value = "/no-contacts-past-three-months", method = RequestMethod.GET)
    public String getNoContactsPastThreeMonths(ModelMap model) {
        model.addAttribute("pageTitle", APP_PREFIX + "Patients with no Contact Past Three Months");
        SearchDTO dto = new SearchDTO();
        dto = getUserLevelObjectState(dto);
        dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_THREE_MONTHS.getEnd()));
        dto.setEndDate(new Date());
        model.addAttribute("items", patientReportService.getPatientWithNoContactList(dto));
        return "report/notification/patient";
    }

    @RequestMapping(value = "/no-contacts-past-twelve-months", method = RequestMethod.GET)
    public String getNoContactsPastTwelveMonths(ModelMap model) {
        model.addAttribute("pageTitle", APP_PREFIX + "Patients with no Contact Past Twelve Months");
        SearchDTO dto = new SearchDTO();
        dto = getUserLevelObjectState(dto);
        dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWELVE_MONTHS.getEnd()));
        dto.setEndDate(new Date());
        model.addAttribute("items", patientReportService.getPatientWithNoContactList(dto));
        return "report/notification/patient";
    }

    @RequestMapping(value = "/no-contacts-past-six-months", method = RequestMethod.GET)
    public String getNoContactsPastSixMonths(ModelMap model) {
        model.addAttribute("pageTitle", APP_PREFIX + "Patients with no Contact Past Six Months");
        SearchDTO dto = new SearchDTO();
        dto = getUserLevelObjectState(dto);
        dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_SIX_MONTHS.getEnd()));
        dto.setEndDate(new Date());
        model.addAttribute("items", patientReportService.getPatientWithNoContactList(dto));
        return "report/notification/patient";
    }

    @RequestMapping(value = "/patients-graduating-in-tweleve-months", method = RequestMethod.GET)
    public String getPatientTwelveMonthsbeforeGrad(ModelMap model) {
        model.addAttribute("pageTitle", APP_PREFIX + "Patients Graduating Within Twelve Months");
        SearchDTO dto = new SearchDTO();
        dto = getUserLevelObjectState(dto);
        dto.setStartDate(DateUtil.getDateFromAge(24));
        dto.setEndDate(DateUtil.getDateFromAge(23));
        model.addAttribute("items", patientReportService.getPatientAboutToGraduateList(dto));
        return "report/notification/patient";
    }

    @RequestMapping(value = "/referrals-with-no-feedback-past-one-week", method = RequestMethod.GET)
    public String getExternalReferralWithinPastWeek(ModelMap model) {
        model.addAttribute("pageTitle", APP_PREFIX + "External Referrals Send with no Feedback Received Past 1 Week");
        SearchDTO dto = new SearchDTO();
        dto = getUserLevelObjectState(dto);
        dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_ONE_WEEK.getEnd()));
        dto.setEndDate(new Date());
        dto.setYesNo(YesNo.NO);
        model.addAttribute("items", referalReportService.get(dto));
        return "report/notification/referral";
    }

    @RequestMapping(value = "/referrals-with-no-feedback-past-two-week", method = RequestMethod.GET)
    public String getExternalReferralWithinPastTwoWeek(ModelMap model) {
        model.addAttribute("pageTitle", APP_PREFIX + "External Referrals Send with no Feedback Received Past 2 Weeks");
        SearchDTO dto = new SearchDTO();
        dto = getUserLevelObjectState(dto);
        dto.setStartDate(DateUtil.getDateDiffDate(-DateRangeItem.PAST_TWO_WEEKS.getEnd()));
        dto.setEndDate(new Date());
        dto.setYesNo(YesNo.NO);
        model.addAttribute("items", referalReportService.get(dto));
        return "report/notification/referral";
    }
}