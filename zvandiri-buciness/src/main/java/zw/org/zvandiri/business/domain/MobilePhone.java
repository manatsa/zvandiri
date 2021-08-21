package zw.org.zvandiri.business.domain;

import lombok.ToString;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import zw.org.zvandiri.business.domain.util.Condition;
import zw.org.zvandiri.business.domain.util.PhoneStatus;

import javax.persistence.*;
import java.util.Date;

@ToString
@Entity
@Table(indexes = {
        @Index(name = "cat_patient_phone", columnList = "patient"),
        @Index(name = "cat_phone_imei1", columnList = "imei1"),
        @Index(name = "cat_phone_msisdn1", columnList = "msisdn1")
})
public class MobilePhone extends BaseEntity{
    private String msisdn1;
    private String msisdn2;
    private String imei1;
    private String imei2;
    private String phoneMake;
    private String phoneModel;
    private String phoneIssues;
    @Enumerated
    private Condition phoneCondition=Condition.NEW_ONE;
    @Enumerated
    private PhoneStatus phoneStatus=PhoneStatus.WORKING;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateIssued;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateRecovered;
    @OneToOne
    private Patient patient;

    public MobilePhone( String misdn1, String misdn2, String imei1, String imei2, String make, String model, Condition condition, PhoneStatus phoneStatus, Date dateIssued, Date dateRecovered, Patient cat) {
        super();
        this.msisdn1 = misdn1;
        this.msisdn2 = misdn2;
        this.imei1 = imei1;
        this.imei2 = imei2;
        this.phoneMake = make;
        this.phoneModel = model;
        this.phoneCondition = condition;
        this.phoneStatus = phoneStatus;
        this.dateIssued = dateIssued;
        this.dateRecovered = dateRecovered;
        this.patient=cat;
    }

    public MobilePhone() {
       super();
    }


    public String getMsisdn1() {
        return msisdn1;
    }

    public void setMsisdn1(String msisdn1) {
        this.msisdn1 = msisdn1;
    }

    public String getMsisdn2() {
        return msisdn2;
    }

    public void setMsisdn2(String msisdn2) {
        this.msisdn2 = msisdn2;
    }

    public String getImei1() {
        return imei1;
    }

    public void setImei1(String imei1) {
        this.imei1 = imei1;
    }

    public String getImei2() {
        return imei2;
    }

    public void setImei2(String imei2) {
        this.imei2 = imei2;
    }

    public String getPhoneMake() {
        return phoneMake;
    }

    public void setPhoneMake(String phoneMake) {
        this.phoneMake = phoneMake;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getPhoneIssues() {
        return phoneIssues;
    }

    public void setPhoneIssues(String phoneIssues) {
        this.phoneIssues = phoneIssues;
    }

    public Condition getPhoneCondition() {
        return phoneCondition;
    }

    public void setPhoneCondition(Condition phoneCondition) {
        this.phoneCondition = phoneCondition;
    }

    public PhoneStatus getPhoneStatus() {
        return phoneStatus;
    }

    public void setPhoneStatus(PhoneStatus phoneStatus) {
        this.phoneStatus = phoneStatus;
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
