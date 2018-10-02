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

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import org.hibernate.annotations.Formula;
import zw.org.zvandiri.business.domain.util.Gender;
import zw.org.zvandiri.business.domain.util.PatientChangeEvent;
import zw.org.zvandiri.business.util.DateUtil;

/**
 *
 * @author Judge Muzinda
 */
@Entity
public class Patient extends GenericPatient {

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "patient_disability_category", joinColumns = {
        @JoinColumn(name = "patient_id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "disability_category_id", nullable = false)})
    private Set<DisabilityCategory> disabilityCategorys;
    @OneToMany(mappedBy = "patient", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private Set<PatientHistory> patientHistories = new HashSet<>();
    @Transient
    private District district;
    @Transient
    private Province province;
    @Transient
    private District supportGroupDistrict;
    @Transient
    private int age = 0;
    @Transient
    private String name;
    @Formula("(Select c.id From cat_detail c where c.patient = id)")
    private String catId;
    @Transient
    private String pic;
    @Transient
    private String dateJoin;
    @Transient
    private String currentElement;
    @Transient
    private Boolean patientStatus;
    @Transient
    private String patientExist;
    @Transient
    private String mother;
    @Formula("(Select i.result From investigation_test i where i.patient = id and i.test_type = 0 order by i.date_created desc limit 0,1)")
    private Integer viralLoad;
    @Formula("(Select i.result From investigation_test i where i.patient = id and i.test_type = 1 order by i.date_created desc limit 0,1)")
    private Integer cd4Count;

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

    public String getCurrentElement() {
        return currentElement;
    }

    public void setCurrentElement(String currentElement) {
        this.currentElement = currentElement;
    }

    public District getSupportGroupDistrict() {
        return supportGroupDistrict;
    }

    public void setSupportGroupDistrict(District supportGroupDistrict) {
        this.supportGroupDistrict = supportGroupDistrict;
    }

    public String getName() {
        return getFirstName() + (getMiddleName() != null && !getMiddleName().equals("") ? " " + getMiddleName() : "") + " " + getLastName();
    }

    public Set<DisabilityCategory> getDisabilityCategorys() {
        return disabilityCategorys;
    }

    public void setDisabilityCategorys(Set<DisabilityCategory> disabilityCategorys) {
        this.disabilityCategorys = disabilityCategorys;
    }

    public int getAge() {
        if (getDateOfBirth() == null) {
            return 0;
        }
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(getDateOfBirth());
        Calendar todayCalendar = Calendar.getInstance();
        age = todayCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);
        if (todayCalendar.get(Calendar.MONTH) < birthCalendar.get(Calendar.MONTH)) {
            age--;
        } else if (todayCalendar.get(Calendar.MONTH) == birthCalendar.get(Calendar.MONTH)
                && todayCalendar.get(Calendar.DAY_OF_MONTH) < birthCalendar.get(Calendar.DAY_OF_MONTH)) {
            age--;
        }
        return age;
    }

    public String getPic() {
        if (getGender() == null) {
            return "/resources/images/noimage.gif";
        } else if (getGender().equals(Gender.MALE)) {
            return "/resources/images/male.gif";
        } else if (getGender().equals(Gender.FEMALE)) {
            return "/resources/images/female.gif";
        }
        return "/resources/images/noimage.gif";
    }

    public String getDateJoin() {
        if (getDateJoined() == null) {
            return "";
        }
        return DateUtil.getStringFromDate(getDateJoined());
    }

    public Boolean getPatientStatus() {
        if (getStatus() == null || getStatus().equals(PatientChangeEvent.ACTIVE)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatientExist() {
        return patientExist;
    }

    public void setPatientExist(String patientExist) {
        this.patientExist = patientExist;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getMother() {
        return getName() + " dob " + DateUtil.zimDate(getDateOfBirth());
    }

    public Set<PatientHistory> getPatientHistories() {
        return patientHistories;
    }

    public void setPatientHistories(Set<PatientHistory> patientHistories) {
        this.patientHistories = patientHistories;
    }

    public Integer getViralLoad() {
        return viralLoad != null ? viralLoad : 0;
    }

    public Integer getCd4Count() {
        return cd4Count != null ? cd4Count : 0;
    }
    
}
