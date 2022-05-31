package zw.org.zvandiri.business.util.dto;


import zw.org.zvandiri.business.domain.Province;

/**
 * @author manatsachinyeruse@gmail.com
 */


public class ProvinceDTO {
    private String id;
    private String name;
    private Long version;

    public ProvinceDTO() {
    }

    public ProvinceDTO(String id, String name, Long version) {
        this.id = id;
        this.name = name;
        this.version = version;
    }

    public ProvinceDTO(Province province) {
        if(province!=null){
            this.id = province.getId();
            this.name = province.getName();
            this.version = province.getVersion();
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
        return "ProvinceDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", version=" + version +
                '}';
    }
}
