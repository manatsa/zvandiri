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
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import zw.org.zvandiri.business.domain.util.TbIdentificationOutcome;
import zw.org.zvandiri.business.domain.util.TbSymptom;
import zw.org.zvandiri.business.domain.util.TbTreatmentOutcome;
import zw.org.zvandiri.business.domain.util.YesNo;

/**
 *
 * @author tasu
 */
@Entity @JsonIgnoreProperties(ignoreUnknown = true)
public class TbIpt extends BaseEntity {

    @ManyToOne
    private Patient patient;
    @Enumerated
    private YesNo screenedForTb;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateScreened;
    @ElementCollection(targetClass = TbSymptom.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "tb_symptom",
            joinColumns = @JoinColumn(name = "tb_id"))
    @Column(name = "symptom_id")
    private Set<TbSymptom> tbSymptoms;
    @Enumerated
    private YesNo identifiedWithTb;
    @Enumerated
    private TbIdentificationOutcome tbIdentificationOutcome;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateStartedTreatment;
    //Need further info on this
    private String referralForSputum;
    @Enumerated
    private TbTreatmentOutcome tbTreatmentOutcome;
    @Enumerated
    private YesNo referredForIpt;
    @Enumerated
    private YesNo onIpt;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateStartedIpt;

    public TbIpt() {
    }
    
    public TbIpt(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public YesNo getScreenedForTb() {
        return screenedForTb;
    }

    public void setScreenedForTb(YesNo screenedForTb) {
        this.screenedForTb = screenedForTb;
    }

    public Date getDateScreened() {
        return dateScreened;
    }

    public void setDateScreened(Date dateScreened) {
        this.dateScreened = dateScreened;
    }

    public Set<TbSymptom> getTbSymptoms() {
        return tbSymptoms;
    }

    public void setTbSymptoms(Set<TbSymptom> tbSymptoms) {
        this.tbSymptoms = tbSymptoms;
    }

    public YesNo getIdentifiedWithTb() {
        return identifiedWithTb;
    }

    public void setIdentifiedWithTb(YesNo identifiedWithTb) {
        this.identifiedWithTb = identifiedWithTb;
    }

    public TbIdentificationOutcome getTbIdentificationOutcome() {
        return tbIdentificationOutcome;
    }

    public void setTbIdentificationOutcome(TbIdentificationOutcome tbIdentificationOutcome) {
        this.tbIdentificationOutcome = tbIdentificationOutcome;
    }

    public Date getDateStartedTreatment() {
        return dateStartedTreatment;
    }

    public void setDateStartedTreatment(Date dateStartedTreatment) {
        this.dateStartedTreatment = dateStartedTreatment;
    }

    public String getReferralForSputum() {
        return referralForSputum;
    }

    public void setReferralForSputum(String referralForSputum) {
        this.referralForSputum = referralForSputum;
    }

    public TbTreatmentOutcome getTbTreatmentOutcome() {
        return tbTreatmentOutcome;
    }

    public void setTbTreatmentOutcome(TbTreatmentOutcome tbTreatmentOutcome) {
        this.tbTreatmentOutcome = tbTreatmentOutcome;
    }

    public YesNo getReferredForIpt() {
        return referredForIpt;
    }

    public void setReferredForIpt(YesNo referredForIpt) {
        this.referredForIpt = referredForIpt;
    }

    public YesNo getOnIpt() {
        return onIpt;
    }

    public void setOnIpt(YesNo onIpt) {
        this.onIpt = onIpt;
    }

    public Date getDateStartedIpt() {
        return dateStartedIpt;
    }

    public void setDateStartedIpt(Date dateStartedIpt) {
        this.dateStartedIpt = dateStartedIpt;
    }
    
    public String getSymptoms() {
        if(tbSymptoms.isEmpty()){
            return "";
        }
        StringBuilder r = new StringBuilder();
        int pos = 1;
        for(TbSymptom role : tbSymptoms){
            if(pos < tbSymptoms.size()) {
                r.append(role.getName());
                r.append(" ,");
            }else{
                r.append(role.getName());
            }
            pos++;
        }
        return r.toString();
    }

	@Override
	public String toString() {
		return "TbIpt [patient=" + patient + ", screenedForTb=" + screenedForTb + ", dateScreened=" + dateScreened
				+ ", tbSymptoms=" + tbSymptoms + ", identifiedWithTb=" + identifiedWithTb + ", tbIdentificationOutcome="
				+ tbIdentificationOutcome + ", dateStartedTreatment=" + dateStartedTreatment + ", referralForSputum="
				+ referralForSputum + ", tbTreatmentOutcome=" + tbTreatmentOutcome + ", referredForIpt="
				+ referredForIpt + ", onIpt=" + onIpt + ", dateStartedIpt=" + dateStartedIpt + "]";
	}
    
}
