package zw.org.zvandiri.business.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Judge Muzinda
 */
@Entity
@Table(indexes = {
    @Index(name = "district_province", columnList = "province")
})
public class District extends BaseName {

    private static final long serialVersionUID = 1L;
    @ManyToOne
    //@JsonIgnoreProperties(value = { "uuid", "createdBy", "modifiedBy", "dateCreated","dateModified","version","deleted","description" })
    private Province province;
    @OneToMany(mappedBy = "district", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Facility> facilitys = new HashSet<>();
    @OneToMany(mappedBy = "district", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<SupportGroup> supportGroups = new HashSet<>();

    public District() {
    }

    public District(String id) {
        super(id);
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public Set<Facility> getFacilitys() {
        return facilitys;
    }

    public void setFacilitys(Set<Facility> facilitys) {
        this.facilitys = facilitys;
    }

    public Set<SupportGroup> getSupportGroups() {
        return supportGroups;
    }

    public void setSupportGroups(Set<SupportGroup> supportGroups) {
        this.supportGroups = supportGroups;
    }

    @Override
    public String toString() {
        return "District{" +
                "name=" + getName() +
                '}';
    }
}
