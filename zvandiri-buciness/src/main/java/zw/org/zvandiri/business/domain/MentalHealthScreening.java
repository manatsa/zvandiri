/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import zw.org.zvandiri.business.domain.util.Diagnosis;
import zw.org.zvandiri.business.domain.util.IdentifiedRisk;
import zw.org.zvandiri.business.domain.util.Intervention;
import zw.org.zvandiri.business.domain.util.MentalHealthScreeningType;
import zw.org.zvandiri.business.domain.util.Referral;
import zw.org.zvandiri.business.domain.util.Support;
import zw.org.zvandiri.business.domain.util.YesNo;

/**
 *
 * @author tasu
 */
@Entity 
@Table(indexes = {
		@Index(name = "mental_health_screening_patient", columnList = "patient")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class MentalHealthScreening extends BaseEntity {

    @ManyToOne(optional = false)
    @JsonIgnore
    @NotNull
    private Patient patient;
    @Enumerated
    private YesNo screenedForMentalHealth;
    @Enumerated
    private MentalHealthScreeningType screening;
    @Enumerated
    private YesNo risk;
    @ElementCollection(targetClass = IdentifiedRisk.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "mental_health_screening_risk",
            joinColumns = @JoinColumn(name = "screening_id"))
    @Column(name = "risk_id")
    private Set<IdentifiedRisk> identifiedRisks;
    @Enumerated
    private YesNo support;
    @ElementCollection(targetClass = Support.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "mental_health_screening_support",
            joinColumns = @JoinColumn(name = "screening_id"))
    @Column(name = "support_id")
    private Set<Support> supports;
    @Enumerated
    private YesNo referral;
    @ElementCollection(targetClass = zw.org.zvandiri.business.domain.util.Referral.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "mental_health_screening_referral",
            joinColumns = @JoinColumn(name = "screening_id"))
    @Column(name = "referral_id")
    private Set<zw.org.zvandiri.business.domain.util.Referral> referrals;
    @Enumerated
    private YesNo diagnosis;
    @ElementCollection(targetClass = Diagnosis.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "mental_health_screening_diagnosis",
            joinColumns = @JoinColumn(name = "screening_id"))
    @Column(name = "diagnosis_id")
    private Set<Diagnosis> diagnoses;
    private String otherDiagnosis;
    @Enumerated
    private YesNo intervention;
    @ElementCollection(targetClass = Intervention.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "mental_health_screening_intervention",
            joinColumns = @JoinColumn(name = "screening_id"))
    @Column(name = "intervention_id")
    private Set<Intervention> interventions;
    private String otherIntervention;
    @Transient
    private String patientId;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateScreened;
    @Enumerated
    private YesNo referralComplete;
    @Transient
    private String currentElement;

    public MentalHealthScreening(Patient patient) {
        this.patient = patient;
    }

    public MentalHealthScreening() {
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public YesNo getRisk() {
        return risk;
    }

    public void setRisk(YesNo risk) {
        this.risk = risk;
    }

    public YesNo getSupport() {
        return support;
    }

    public void setSupport(YesNo support) {
        this.support = support;
    }

    public YesNo getReferral() {
        return referral;
    }

    public void setReferral(YesNo referral) {
        this.referral = referral;
    }

    public YesNo getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(YesNo diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getOtherDiagnosis() {
        return otherDiagnosis;
    }

    public void setOtherDiagnosis(String otherDiagnosis) {
        this.otherDiagnosis = otherDiagnosis;
    }

    public YesNo getIntervention() {
        return intervention;
    }

    public void setIntervention(YesNo intervention) {
        this.intervention = intervention;
    }

    public String getOtherIntervention() {
        return otherIntervention;
    }

    public void setOtherIntervention(String otherIntervention) {
        this.otherIntervention = otherIntervention;
    }

    public Set<IdentifiedRisk> getIdentifiedRisks() {
        return identifiedRisks;
    }

    public void setIdentifiedRisks(Set<IdentifiedRisk> identifiedRisks) {
        this.identifiedRisks = identifiedRisks;
    }

    public Set<Support> getSupports() {
        return supports;
    }

    public void setSupports(Set<Support> supports) {
        this.supports = supports;
    }

    public Set<Referral> getReferrals() {
        return referrals;
    }

    public void setReferrals(Set<Referral> referrals) {
        this.referrals = referrals;
    }

    public Set<Diagnosis> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(Set<Diagnosis> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public Set<Intervention> getInterventions() {
        return interventions;
    }

    public void setInterventions(Set<Intervention> interventions) {
        this.interventions = interventions;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public YesNo getScreenedForMentalHealth() {
        return screenedForMentalHealth;
    }

    public void setScreenedForMentalHealth(YesNo screenedForMentalHealth) {
        this.screenedForMentalHealth = screenedForMentalHealth;
    }

    public MentalHealthScreeningType getScreening() {
        return screening;
    }

    public void setScreening(MentalHealthScreeningType screening) {
        this.screening = screening;
    }

    public Date getDateScreened() {
        return dateScreened;
    }

    public void setDateScreened(Date dateScreened) {
        this.dateScreened = dateScreened;
    }

    public YesNo getReferralComplete() {
        return referralComplete;
    }

    public void setReferralComplete(YesNo referralComplete) {
        this.referralComplete = referralComplete;
    }
    
    public String getCurrentElement() {
        return currentElement;
    }

    public void setCurrentElement(String currentElement) {
        this.currentElement = currentElement;
    }
    
}
