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
package zw.org.zvandiri.portal.web.controller;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import zw.org.zvandiri.business.domain.ArvHist;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.PatientChangeEvent;
import zw.org.zvandiri.business.service.ArvHistService;
import zw.org.zvandiri.business.service.PatientReportService;
import zw.org.zvandiri.business.service.PatientService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.report.api.service.LocalityImporterService;

/**
 *
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/import")
public class ImportLocalitiesController extends BaseController {

    @Resource
    private LocalityImporterService localityImporterService;

    @Resource
    private PatientService patientService;

    @Resource
    private ArvHistService arvHistService;
    @Resource
    private PatientReportService patientReportService;

    @RequestMapping("/update")
    public void updateLocalities() throws FileNotFoundException, ParseException {
        localityImporterService.importData("/home/jmuzinda/localities.csv");
    }

    @RequestMapping("/update-arv")
    public void updatePatientArvHist() {

        for (Patient patient : patientService.getAll()) {
            List<ArvHist> hists = arvHistService.getByPatient(patient);
            if (hists.isEmpty()) {
                continue;
            }
            int count = 0;
            ArvHist firstRecord = null;
            for (Iterator<ArvHist> arvHistIterator = hists.iterator(); arvHistIterator.hasNext();) {
                if (count == 0) {
                    firstRecord = arvHistIterator.next();
                } else if (count == 1 && firstRecord != null) {
                    ArvHist secondRecord = arvHistIterator.next();
                    firstRecord.setArvMedicine2(secondRecord.getArvMedicine());
                    //arvHistService.delete(secondRecord);
                } else if (count == 2 && firstRecord != null) {
                    ArvHist thirdRecord = arvHistIterator.next();
                    firstRecord.setArvMedicine3(thirdRecord.getArvMedicine());
                   // arvHistService.delete(thirdRecord);
                }
                count++;
            }
            arvHistService.save(firstRecord);
        }
    }
    
    @RequestMapping("/graduate-patient")
    public void test() {

        SearchDTO dto = new SearchDTO();
        dto.setStatus(PatientChangeEvent.ACTIVE);
        dto.setEndDate(DateUtil.getDateDiffDate( -14, DateUtil.getDateFromAge(24)));
        dto.setStartDate(DateUtil.getDateFromAge(45));
        patientService.updatePatientStatus(patientReportService.getPatientAboutToGraduateList(dto));
    }
    
    @RequestMapping("/add-uac-patient")
    public void addUACToPatient() {

        patientService.updatePatientUAC();
    }
}
