package zw.org.zvandiri.business.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import zw.org.zvandiri.business.util.dto.DistrictDTO;

/**
 *
 * @author Judge Muzinda
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(indexes = {
    @Index(name = "district_province", columnList = "province")
})
public class District extends BaseName {

    private static final long serialVersionUID = 1L;
    @ManyToOne
    private Province province;
    @OneToMany(mappedBy = "district", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Facility> facilitys = new HashSet<>();
    @OneToMany(mappedBy = "district", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<SupportGroup> supportGroups = new HashSet<>();

    public District() {
    }

    public DistrictDTO getDistrictDTO(){
        return new DistrictDTO(getId(), this.getName(), this.getVersion());
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

}
