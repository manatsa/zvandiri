package zw.org.zvandiri.business.util.dto;

import zw.org.zvandiri.business.domain.District;

/**
 * @author manatsachinyeruse@gmail.com
 */


public class DistrictDTO {
    private String id;
    private String name;
    private Long version;

    public DistrictDTO() {
    }

    public DistrictDTO(String id, String name, Long version) {
        this.id = id;
        this.name = name;
        this.version = version;
    }

    public DistrictDTO(District district) {
        if(district!=null){
            this.id = district.getId();
            this.name = district.getName();
            this.version = district.getVersion();
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "DistrictDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", version=" + version +
                '}';
    }
}
