package zw.org.zvandiri.business.domain;

import lombok.ToString;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;
import zw.org.zvandiri.business.domain.util.Condition;
import zw.org.zvandiri.business.domain.util.PhoneStatus;
import zw.org.zvandiri.business.domain.util.RecordSource;

import javax.persistence.*;
import java.util.Date;

@ToString
@Entity
@Table(indexes = {
        @Index(name = "cadre_bicycle", columnList = "cadre"),
        @Index(name = "cadre_bike_type", columnList = "bikeType")
})
public class Bicycle{

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
    @OneToOne(cascade = CascadeType.MERGE)
    private Cadre cadre;

    public Bicycle(String type, Condition condition, PhoneStatus bikeStatus, Date dateIssued, Date dateRecovered, Cadre cadre) {
        super();
        this.bikeType = type;
        this.bikeCondition = condition;
        this.bikeStatus = bikeStatus;
        this.dateIssued = dateIssued;
        this.dateRecovered = dateRecovered;
        this.cadre=cadre;
    }

    public Bicycle() {

    }

    public Bicycle(Cadre cadre) {
        this.cadre = cadre;
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

    public Cadre getCadre() {
        return cadre;
    }

    public void setCadre(Cadre cadre) {
        this.cadre = cadre;
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
}
