/*
 * Copyright 2018 jmuzinda.
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
package zw.org.zvandiri.portal.web.controller.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.service.DetailedPatientReportService;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.FacilityService;
import zw.org.zvandiri.business.service.PatientService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.PatientDuplicateDTO;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.service.parallel.GenericCountReportTask;

/**
 *
 * @author jmuzinda
 */
@Controller
@RequestMapping("/data")
public class DataCleaningController extends BaseController {
 
    @Resource
    private DetailedPatientReportService detailedPatientReportService;
    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    @Resource
    private FacilityService facilityService;
    @Resource
    private PatientService patientService;
    
    private Set<PatientDuplicateDTO> duplicates = new HashSet<>();

    public String setUpModel(ModelMap model, SearchDTO item, Boolean post) {
        item = getUserLevelObjectState(item);
        model.addAttribute("pageTitle", APP_PREFIX + "Data Cleaning Management Panel");
        model.addAttribute("provinces", provinceService.getAll());
        if (item.getProvince() != null) {
            model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            if (item.getDistrict() != null) {
                model.addAttribute("facilities", facilityService.getOptByDistrict(item.getDistrict()));
            }
        }
        model.addAttribute("item", item.getInstance(item));
        if (post) {
            ForkJoinPool pool = ForkJoinPool.commonPool();
            List<Patient> patients = pool.invoke(new GenericCountReportTask(DateUtil.generateArray(detailedPatientReportService.getCount(item)), detailedPatientReportService, item));
            duplicates = patientService.getAllPossibleDuplicates(patients);
            model.addAttribute("patients", duplicates);
        }
        return "data-cleaning/index";
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String getForm(ModelMap model) {
        return setUpModel(model, new SearchDTO(), Boolean.FALSE);
    }
    
    @RequestMapping(value = "/item.form", method = RequestMethod.POST)
    public String getListResults(ModelMap model, @ModelAttribute("item") @Valid SearchDTO item) {
        return setUpModel(model, item, Boolean.TRUE);
    }
    
    @RequestMapping(value = "/patient-duplicates", method = RequestMethod.GET)
    @ResponseBody
    public Set<PatientDuplicateDTO> getPatientDuplicates(@RequestParam String id) {
        return search(id);
    }
    
    private Set<PatientDuplicateDTO> search(String id) {
        PatientDuplicateDTO currentPatient = 
                PatientDuplicateDTO.getInstance(patientService.get(id));
        for (PatientDuplicateDTO dto : duplicates) {
            if (dto.equals(currentPatient)) {
                return dto.getMatches();
            }
        }
        throw new IllegalStateException("Patient is expected to have duplicates");
    }
    
    @RequestMapping(value = "/merge-patients", method = RequestMethod.GET)
    @ResponseBody
    public String mergePatients(@RequestParam String id, @RequestParam String patientId) {
        patientService.mergePatients(patientId, id);
        return id;
    }
}