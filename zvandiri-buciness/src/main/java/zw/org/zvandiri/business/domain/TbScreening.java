/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.business.domain;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import zw.org.zvandiri.business.domain.util.Result;
import zw.org.zvandiri.business.domain.util.TbTreatmentOutcome;
import zw.org.zvandiri.business.domain.util.TbTreatmentStatus;
import zw.org.zvandiri.business.domain.util.YesNo;

/**
 *
 * @author tasu
 */
@Entity
public class TbScreening extends BaseEntity {

    @Enumerated
    private YesNo coughing;
    @Enumerated
    private YesNo sweating;
    @Enumerated
    private YesNo weightLoss;
    @Enumerated
    private YesNo fever;
    @Enumerated
    private YesNo currentlyOnTreatment;
    private String typeOfDrug;
    @Enumerated
    private Result tbOutcome;
    @Enumerated
    private TbTreatmentStatus tbTreatmentStatus;
    @Enumerated
    private TbTreatmentOutcome tbTreatmentOutcome;
    @ManyToOne
    private Person person;

    public TbScreening(Person person) {
        this.person = person;
    }

    public TbScreening() {
    }

    public YesNo getCoughing() {
        return coughing;
    }

    public void setCoughing(YesNo coughing) {
        this.coughing = coughing;
    }

    public YesNo getSweating() {
        return sweating;
    }

    public void setSweating(YesNo sweating) {
        this.sweating = sweating;
    }

    public YesNo getWeightLoss() {
        return weightLoss;
    }

    public void setWeightLoss(YesNo weightLoss) {
        this.weightLoss = weightLoss;
    }

    public YesNo getFever() {
        return fever;
    }

    public void setFever(YesNo fever) {
        this.fever = fever;
    }

    public YesNo getCurrentlyOnTreatment() {
        return currentlyOnTreatment;
    }

    public void setCurrentlyOnTreatment(YesNo currentlyOnTreatment) {
        this.currentlyOnTreatment = currentlyOnTreatment;
    }

    public String getTypeOfDrug() {
        return typeOfDrug;
    }

    public void setTypeOfDrug(String typeOfDrug) {
        this.typeOfDrug = typeOfDrug;
    }

    public Result getTbOutcome() {
        return tbOutcome;
    }

    public void setTbOutcome(Result tbOutcome) {
        this.tbOutcome = tbOutcome;
    }

    public TbTreatmentStatus getTbTreatmentStatus() {
        return tbTreatmentStatus;
    }

    public void setTbTreatmentStatus(TbTreatmentStatus tbTreatmentStatus) {
        this.tbTreatmentStatus = tbTreatmentStatus;
    }

    public TbTreatmentOutcome getTbTreatmentOutcome() {
        return tbTreatmentOutcome;
    }

    public void setTbTreatmentOutcome(TbTreatmentOutcome tbTreatmentOutcome) {
        this.tbTreatmentOutcome = tbTreatmentOutcome;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
