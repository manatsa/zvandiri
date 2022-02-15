package zw.org.zvandiri.business.domain.util;


/**
 * @author manatsachinyeruse@gmail.com
 */


package zw.org.zvandiri.business.domain.util;


import zw.org.zvandiri.business.util.StringUtils;

/**
 * @author manatsachinyeruse@gmail.com
 */


public enum DisclosureType {
    NONE(1),DOUBLE(2), MATERNAL(3), PATERNAL(4);

    private final Integer code;

    DisclosureType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static final DisclosureType get(Integer code){
        for(DisclosureType item : values()){
            if(item.getCode().equals(code)){
                return item;
            }
        }
        throw new IllegalArgumentException("Parameter passed to method not recognized:" + code);
    }

    public String getName(){
        return StringUtils.toCamelCase3(super.name());
    }

    @Override
    public String toString() {
        return super.name().replace("_", " ");
    }
}
