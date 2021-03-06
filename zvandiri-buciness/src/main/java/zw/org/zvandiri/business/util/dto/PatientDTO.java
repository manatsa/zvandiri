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
package zw.org.zvandiri.business.util.dto;

import java.io.Serializable;
import zw.org.zvandiri.business.domain.Patient;

/**
 *
 * @author Judge Muzinda
 */
public class PatientDTO implements Serializable {
    
    private Patient patient;

    public PatientDTO() {
    }

    public PatientDTO(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public Patient getSupportGroupInstance(PatientDTO item){
        Patient p = item.getPatient();
        p.setSupportGroup(item.getPatient().getSupportGroup());
        return p;
    }
    
    public Patient getFacilityInstance(PatientDTO item){
        Patient p = item.getPatient();
        p.setPrimaryClinic(item.getPatient().getPrimaryClinic());
        return p;
    }
    
    public Patient getPatientStatusInstance(PatientDTO item){
        Patient p = item.getPatient();
        p.setStatus(item.getPatient().getStatus());
        return p;
    }
}