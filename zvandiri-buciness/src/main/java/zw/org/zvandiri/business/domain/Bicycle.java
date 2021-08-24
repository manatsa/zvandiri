package zw.org.zvandiri.business.domain;

import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import zw.org.zvandiri.business.domain.util.Condition;
import zw.org.zvandiri.business.domain.util.PhoneStatus;

import javax.persistence.*;
import java.util.Date;

@ToString
@Entity
@Table(indexes = {
        @Index(name = "cat_patient_bicycle", columnList = "patient"),
        @Index(name = "cat_phone_type", columnList = "bikeType")
})
public class Bicycle extends BaseEntity{

    private String bikeType;
    private String bikeIssues;
    @Enumerated
    private Condition bikeCondition=Condition.NEW_ONE;
    @Enumerated
    private PhoneStatus bikeStatus=PhoneStatus.WORKING;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateIssued;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateRecovered;
    @OneToOne
    private Patient patient;

    public Bicycle(String type, Condition condition, PhoneStatus bikeStatus, Date dateIssued, Date dateRecovered, Patient cat) {
        super();
        this.bikeType = type;
        this.bikeCondition = condition;
        this.bikeStatus = bikeStatus;
        this.dateIssued = dateIssued;
        this.dateRecovered = dateRecovered;
        this.patient=cat;
    }

    public Bicycle() {
        super();
    }

    public String getBikeType() {
        return bikeType;
    }

    public void setBikeType(String bikeType) {
        this.bikeType = bikeType;
    }

    public String getBikeIssues() {
        return bikeIssues;
    }

    public void setBikeIssues(String bikeIssues) {
        this.bikeIssues = bikeIssues;
    }

    public Condition getBikeCondition() {
        return bikeCondition;
    }

    public void setBikeCondition(Condition bikeCondition) {
        this.bikeCondition = bikeCondition;
    }

    public PhoneStatus getBikeStatus() {
        return bikeStatus;
    }

    public void setBikeStatus(PhoneStatus bikeStatus) {
        this.bikeStatus = bikeStatus;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(Date dateIssued) {
        this.dateIssued = dateIssued;
    }

    public Date getDateRecovered() {
        return dateRecovered;
    }

    public void setDateRecovered(Date dateRecovered) {
        this.dateRecovered = dateRecovered;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
