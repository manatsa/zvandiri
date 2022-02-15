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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;
import zw.org.zvandiri.business.domain.util.CareLevel;
import zw.org.zvandiri.business.domain.util.ContactPhoneOption;
import zw.org.zvandiri.business.domain.util.FollowUp;
import zw.org.zvandiri.business.domain.util.UserLevel;
import zw.org.zvandiri.business.domain.util.YesNo;

/**
 *
 * @author Judge Muzinda
 */
@ToString
@Entity @JsonIgnoreProperties(ignoreUnknown = true)
@Table(indexes = {
		@Index(name = "contact_patient", columnList = "patient"),
		@Index(name = "contact_contact_date", columnList = "contactDate"),
		@Index(name = "contact_location", columnList = "location"),
		@Index(name = "contact_position", columnList = "position")
})
public class Contact extends BaseEntity {

    @JsonIgnore
    @ManyToOne
    private Patient patient;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date contactDate;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date nextClinicAppointmentDate;
    @Enumerated
    private CareLevel careLevel;
    @Enumerated
    @Column(name = "follow_up")
    private FollowUp careLevelAfterAssessment;
    @Enumerated
    private FollowUp systemDeterminedCareLevel;
    @ManyToOne
    private Location location;
    @Enumerated
    private ContactPhoneOption contactPhoneOption;
    private Integer numberOfSms;
    @ManyToOne
    private Position position;
    /*@Enumerated
    private Reason reason;
    private String otherReason;
    @ManyToOne
    private Period period;
    @ManyToOne
    private InternalReferral internalReferral;
    @ManyToOne
    private ExternalReferral externalReferral;
    @Enumerated
    private FollowUp followUp;
    @Column(columnDefinition = "text")
    private String subjective;
    @Column(columnDefinition = "text")
    private String objective;
    @Enumerated
    private DifferentiatedService differentiatedService;
    @JsonIgnore
    @Column(columnDefinition = "text")
    private String plan;
    @ManyToOne
    private ActionTaken actionTaken;
    /*@Column(name = "`open`")
    private Boolean open = Boolean.FALSE;
    @Column(columnDefinition = "text")
    private String defaultMessage;
    @Transient
    private String currentElement;
    @Transient
    private District district;
    @Transient
    private Province province;
    @Formula("(Select c.care_level From contact c where c.patient = patient order by c.contact_date desc limit 1,1)")
    private Integer previousCareLevel;
    @Transient
    private CareLevel currentCareLevel;
    @Enumerated
    private VisitOutcome visitOutcome;
    private String referredPersonId;
    /*@ManyToOne
    @JsonIgnore
    private Contact parent;
    @ManyToOne
    @JsonIgnore
    private User referredPerson;*/
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "contact_lab_service", joinColumns = {
        @JoinColumn(name = "contact_id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "lab_service_id", nullable = false)})
    private Set<LabTask> labTasks = new HashSet<>();
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "contact_clinical_assessment", joinColumns = {
        @JoinColumn(name = "contact_id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "assessment_id", nullable = false)})
    private Set<Assessment> clinicalAssessments = new HashSet<>();
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "contact_non_clinical_assessment", joinColumns = {
        @JoinColumn(name = "contact_id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "assessment_id", nullable = false)})
    private Set<Assessment> nonClinicalAssessments = new HashSet<>();
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "contact_stable", joinColumns = {
        @JoinColumn(name = "contact_id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "stable_id", nullable = false)})
    private Set<Stable> stables = new HashSet<>();
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "contact_enhanced", joinColumns = {
        @JoinColumn(name = "contact_id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "enhanced_id", nullable = false)})
    private Set<Enhanced> enhanceds = new HashSet<>();
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "contact_service_offered", joinColumns = {
        @JoinColumn(name = "contact_id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "service_offered_id", nullable = false)})
    private Set<ServiceOffered> serviceOffereds = new HashSet<>();

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date lastClinicAppointmentDate;
    private YesNo attendedClinicAppointment;
    @Transient
    private InvestigationTest viralLoad;
    @Transient
    private InvestigationTest cd4Count;
    @Transient
    private UserLevel userLevel;
    @Enumerated
    private YesNo eac;
    private Integer eac1;
    private Integer eac2;
    private Integer eac3;
    private String contactMadeBy;



    public Contact(Patient patient) {
        this.patient = patient;
    }

    public Contact() {
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getContactDate() {
        return contactDate;
    }

    public void setContactDate(Date contactDate) {
        this.contactDate = contactDate;
    }

    public CareLevel getCareLevel() {
        return careLevel;
    }

    public FollowUp getCareLevelAfterAssessment() {
        return careLevelAfterAssessment;
    }

    public void setCareLevelAfterAssessment(FollowUp careLevelAfterAssessment) {
        this.careLevelAfterAssessment = careLevelAfterAssessment;
    }

    public void setCareLevel(CareLevel careLevel) {
        this.careLevel = careLevel;
    }

    public Date getNextClinicAppointmentDate() {
        return nextClinicAppointmentDate;
    }

    public void setNextClinicAppointmentDate(Date nextClinicAppointmentDate) {
        this.nextClinicAppointmentDate = nextClinicAppointmentDate;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

   /* public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public InternalReferral getInternalReferral() {
        return internalReferral;
    }

    public void setInternalReferral(InternalReferral internalReferral) {
        this.internalReferral = internalReferral;
    }

    public ExternalReferral getExternalReferral() {
        return externalReferral;
    }

    public void setExternalReferral(ExternalReferral externalReferral) {
        this.externalReferral = externalReferral;
    }

    public FollowUp getFollowUp() {
        return followUp;
    }

    public void setFollowUp(FollowUp followUp) {
        this.followUp = followUp;
    }

    public String getSubjective() {
        return subjective;
    }

    public void setSubjective(String subjective) {
        this.subjective = subjective;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }*/

    public Set<Assessment> getClinicalAssessments() {
        return clinicalAssessments;
    }

    public void setClinicalAssessments(Set<Assessment> clinicalAssessments) {
        this.clinicalAssessments = clinicalAssessments;
    }

    public Set<Assessment> getNonClinicalAssessments() {
        return nonClinicalAssessments;
    }

    public void setNonClinicalAssessments(Set<Assessment> nonClinicalAssessments) {
        this.nonClinicalAssessments = nonClinicalAssessments;
    }

   /* public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public ActionTaken getActionTaken() {
        return actionTaken;
    }

    public void setActionTaken(ActionTaken actionTaken) {
        this.actionTaken = actionTaken;
    }*/

    public Set<Stable> getStables() {
        return stables;
    }

    public void setStables(Set<Stable> stables) {
        this.stables = stables;
    }

    public Set<Enhanced> getEnhanceds() {
        return enhanceds;
    }

    public void setEnhanceds(Set<Enhanced> enhanceds) {
        this.enhanceds = enhanceds;
    }

    public Date getLastClinicAppointmentDate() {
        return lastClinicAppointmentDate;
    }

    public void setLastClinicAppointmentDate(Date lastClinicAppointmentDate) {
        this.lastClinicAppointmentDate = lastClinicAppointmentDate;
    }

    public YesNo getAttendedClinicAppointment() {
        return attendedClinicAppointment;
    }

    public void setAttendedClinicAppointment(YesNo attendedClinicAppointment) {
        this.attendedClinicAppointment = attendedClinicAppointment;
    }

    public ContactPhoneOption getContactPhoneOption() {
        return contactPhoneOption;
    }

    public void setContactPhoneOption(ContactPhoneOption contactPhoneOption) {
        this.contactPhoneOption = contactPhoneOption;
    }

    public Integer getNumberOfSms() {
        return numberOfSms;
    }

    public void setNumberOfSms(Integer numberOfSms) {
        this.numberOfSms = numberOfSms;
    }

    public Set<ServiceOffered> getServiceOffereds() {
        return serviceOffereds;
    }

    public void setServiceOffereds(Set<ServiceOffered> serviceOffereds) {
        this.serviceOffereds = serviceOffereds;
    }

    public UserLevel getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(UserLevel userLevel) {
        this.userLevel = userLevel;
    }

    /* public Contact getParent() {
        return parent;
    }

    public void setParent(Contact parent) {
        this.parent = parent;
    }

    public String getCurrentElement() {
        return currentElement;
    }

    public void setCurrentElement(String currentElement) {
        this.currentElement = currentElement;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public User getReferredPerson() {
        return referredPerson;
    }

    public void setReferredPerson(User referredPerson) {
        this.referredPerson = referredPerson;
    }

    public Boolean getOpen() {
        if (open == null) {
            return Boolean.TRUE;
        }
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
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

    public Integer getPreviousCareLevel() {
        return previousCareLevel;
    }

    public String getReferredPersonId() {
        return referredPersonId;
    }

    public void setReferredPersonId(String referredPersonId) {
        this.referredPersonId = referredPersonId;
    }

    public CareLevel getCurrentCareLevel() {
        if (careLevel != null) {
            return CareLevel.get(careLevel + 1);
        }
        return null;
    }

    public DifferentiatedService getDifferentiatedService() {
        return differentiatedService;
    }

    public void setDifferentiatedService(DifferentiatedService differentiatedService) {
        this.differentiatedService = differentiatedService;
    }

    public String getOtherReason() {
        return otherReason;
    }

    public void setOtherReason(String otherReason) {
        this.otherReason = otherReason;
    }
    public VisitOutcome getVisitOutcome() {
        return visitOutcome;
    }

    public void setVisitOutcome(VisitOutcome visitOutcome) {
        this.visitOutcome = visitOutcome;
    }*/

    public InvestigationTest getViralLoad() {
        return viralLoad;
    }

    public void setViralLoad(InvestigationTest viralLoad) {
        this.viralLoad = viralLoad;
    }

    public InvestigationTest getCd4Count() {
        return cd4Count;
    }

    public void setCd4Count(InvestigationTest cd4Count) {
        this.cd4Count = cd4Count;
    }

    public Set<LabTask> getLabTasks() {
        return labTasks;
    }

    public void setLabTasks(Set<LabTask> labTasks) {
        this.labTasks = labTasks;
    }

    public YesNo getEac() {
        return eac;
    }

    public void setEac(YesNo eac) {
        this.eac = eac;
    }

    public Integer getEac1() {
        return eac1;
    }

    public void setEac1(Integer eac1) {
        this.eac1 = eac1;
    }

    public Integer getEac2() {
        return eac2;
    }

    public void setEac2(Integer eac2) {
        this.eac2 = eac2;
    }

    public Integer getEac3() {
        return eac3;
    }

    public void setEac3(Integer eac3) {
        this.eac3 = eac3;
    }

    public String getContactMadeBy() {
        return contactMadeBy;
    }

    public void setContactMadeBy(String contactMadeBy) {
        this.contactMadeBy = contactMadeBy;
    }

    public FollowUp getSystemDeterminedCareLevel() {
        return systemDeterminedCareLevel;
    }

    public void setSystemDeterminedCareLevel(FollowUp systemDeterminedCareLevel) {
        this.systemDeterminedCareLevel = systemDeterminedCareLevel;
    }

    @Override
    public String toString() {
        return super.toString().concat("Contact{" + "patient=" + patient + ", contactDate=" + contactDate + ", nextClinicAppointmentDate=" +
                nextClinicAppointmentDate + ", careLevel=" + careLevel + ", location=" + location + ", contactPhoneOption=" +
                contactPhoneOption + ", numberOfSms=" + numberOfSms + ", position=" + position   +
                ", clinicalAssessments=" + clinicalAssessments + ", nonClinicalAssessments=" + nonClinicalAssessments + ", serviceOffereds=" + serviceOffereds
                + ", lastClinicAppointmentDate=" + lastClinicAppointmentDate + ", attendedClinicAppointment=" + attendedClinicAppointment +
                ", careLevelAfterAssessment ="+careLevelAfterAssessment+", EAC="+eac+", eac1="+eac1+",eac2="+eac2+",eac3="+eac3+",contactMadeBy="+contactMadeBy+
                ", SystemDeterminedCareLevel = "+systemDeterminedCareLevel+"}");
    }

}
