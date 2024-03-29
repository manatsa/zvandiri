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
package zw.org.zvandiri.business.domain;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author Judge Muzinda
 */
@Entity @JsonIgnoreProperties(ignoreUnknown = true)
@Table(indexes = {
		@Index(name = "family_patient", columnList = "patient"),
		@Index(name = "family_orphan_status", columnList = "orphan_status")
})
public class Family extends BaseEntity {
    
    @ManyToOne
    @JsonIgnore
    private Patient patient;
    @ManyToOne
    private OrphanStatus orphanStatus;
    private Integer numberOfSiblings;

    public Family() {
    }

    public Family(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public OrphanStatus getOrphanStatus() {
        return orphanStatus;
    }

    public void setOrphanStatus(OrphanStatus orphanStatus) {
        this.orphanStatus = orphanStatus;
    }

    public Integer getNumberOfSiblings() {
        return numberOfSiblings;
    }

    public void setNumberOfSiblings(Integer numberOfSiblings) {
        this.numberOfSiblings = numberOfSiblings;
    }
    
}
