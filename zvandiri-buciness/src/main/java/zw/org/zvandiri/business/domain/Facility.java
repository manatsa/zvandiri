/*
 * Copyright 2015 Judge Muzinda.
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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author Judge Muzinda
 */
@Entity 
@Table(indexes = {
		@Index(name = "facility_district", columnList = "district")
})
@JsonIgnoreProperties(value= {"patients"}, ignoreUnknown = true)
public class Facility extends BaseName {

    @ManyToOne
    private District district;
    @Transient
    private Province province;
    @JsonIgnore
    @OneToMany(mappedBy = "primaryClinic", cascade = CascadeType.REMOVE)
    private List<Patient> patients = new ArrayList<>();

    public Facility() {
    }

    public Facility(String id) {
        super(id);
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
    
    
    public List<Patient> getCatPatients()
    {
        return patients;
    }
    
}
