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
package zw.org.zvandiri.portal.web.controller.process;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import zw.org.zvandiri.business.domain.Dependent;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.service.PatientService;

/**
 *
 * @author Judge Muzinda
 */
@Repository("patientCreationService")
public class PatientCreationServiceImpl implements PatientCreationService {
    
    @Resource
    private PatientService patientService;

    @Override
    public Patient createPatient() {
        return new Patient();
    }

    @Override
    public Dependent createDependant(Patient patient) {
        return new Dependent(patient);
    }

    @Override
    public Patient savePatient(Patient patient) {
        return patientService.save(patient);
    }
    
}