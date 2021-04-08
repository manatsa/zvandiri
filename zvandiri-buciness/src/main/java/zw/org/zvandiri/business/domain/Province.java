package zw.org.zvandiri.business.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author Judge Muzinda
 */
@Entity 
@JsonIgnoreProperties(ignoreUnknown = true)
public class Province extends BaseName {

    @JsonIgnore
    @OneToMany(mappedBy = "province", cascade = CascadeType.REMOVE)
    private Set<District> districts = new HashSet<>();
    
    public Province() {
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
