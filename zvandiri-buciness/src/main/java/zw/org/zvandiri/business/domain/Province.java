package zw.org.zvandiri.business.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import zw.org.zvandiri.business.util.dto.DistrictDTO;
import zw.org.zvandiri.business.util.dto.ProvinceDTO;

/**
 *
 * @author Judge Muzinda
 */
@Entity 
@JsonIgnoreProperties({"modifiedBy"})
public class Province extends BaseName {

    @JsonIgnore
    @OneToMany(mappedBy = "province", cascade = CascadeType.REMOVE)
    private Set<District> districts = new HashSet<>();
    
    public Province() {
    }

    public ProvinceDTO getDistrictDTO(){
        return new ProvinceDTO(getId(), this.getName(), this.getVersion());
    }

    public Province(String id) {
        super(id);
    }

    public Set<District> getDistricts() {
        return districts;
    }

    public void setDistricts(Set<District> districts) {
        this.districts = districts;
    }
    
}
