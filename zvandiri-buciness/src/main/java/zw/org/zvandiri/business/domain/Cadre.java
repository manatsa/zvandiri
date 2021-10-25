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

import lombok.ToString;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;
import zw.org.zvandiri.business.domain.util.*;
import zw.org.zvandiri.business.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Judge Muzinda
 */
@Entity @JsonIgnoreProperties(ignoreUnknown = true)
@Table(indexes = {
        @Index(name = "cadre_first_name", columnList = "firstName"),
        @Index(name = "cadre_last_name", columnList = "lastName")
})
@ToString
public class Cadre {


    @Id
    private String id;
    private String uuid;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User createdBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User modifiedBy;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateCreated;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateModified;
    @Version
    private Long version;
    private Boolean active = Boolean.TRUE;
    private Boolean deleted = Boolean.FALSE;
    @Enumerated
    private RecordSource recordSource = RecordSource.WEB_APP;
    private String firstName;
    private String middleName;
    private String lastName;
    @Enumerated
    private Gender gender;
    private String address;
    private String mobileNumber;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;
    @ManyToOne
    private Facility primaryClinic;
    @ManyToOne
    private District district;
    @ManyToOne
    private Province province;
    @ManyToOne
    private SupportGroup supportGroup;
    @Enumerated
    private CaderType caderType;
    @Enumerated
    private PatientChangeEvent status = PatientChangeEvent.ACTIVE;
    @Temporal(TemporalType.DATE)
    private Date statusChangeDate;
    @Enumerated
    private YesNo hasPatient;
    private String patientId;
    private int age;
    @OneToOne
    @Transient
    private MobilePhone mobilePhone;
    @OneToOne
    @Transient
    private Bicycle bicycle;

    public Cadre() {
    }

    public Cadre(String patient) {
        this.patientId = patient;
    }

    public Cadre(String firstName, String middleName, String lastName, Gender gender, String address,String mobileNumber, Date dateOfBirth, Facility primaryClinic, SupportGroup supportGroup,CaderType caderType) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.dateOfBirth = dateOfBirth;
        this.primaryClinic = primaryClinic;
        this.supportGroup = supportGroup;
        this.caderType = caderType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Facility getPrimaryClinic() {
        return primaryClinic;
    }

    public void setPrimaryClinic(Facility primaryClinic) {
        this.primaryClinic = primaryClinic;
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

    public SupportGroup getSupportGroup() {
        return supportGroup;
    }

    public void setSupportGroup(SupportGroup supportGroup) {
        this.supportGroup = supportGroup;
    }

    public CaderType getCaderType() {
        return caderType;
    }

    public void setCaderType(CaderType caderType) {
        this.caderType = caderType;
    }

    public PatientChangeEvent getStatus() {
        return status;
    }

    public void setStatus(PatientChangeEvent status) {
        this.status = status;
    }

    public Date getStatusChangeDate() {
        return statusChangeDate;
    }

    public void setStatusChangeDate(Date statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }

    public YesNo getHasPatient() {
        return hasPatient;
    }

    public void setHasPatient(YesNo hasPatient) {
        this.hasPatient = hasPatient;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public MobilePhone getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(MobilePhone mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Bicycle getBicycle() {
        return bicycle;
    }

    public void setBicycle(Bicycle bicycle) {
        this.bicycle = bicycle;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(User modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public RecordSource getRecordSource() {
        return recordSource;
    }

    public void setRecordSource(RecordSource recordSource) {
        this.recordSource = recordSource;
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


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BaseEntity)) {
            return false;
        }
        return this.getId().equals(((BaseEntity) obj).getId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    public String getName()
    {
        return firstName+" "+lastName;
    }
}
