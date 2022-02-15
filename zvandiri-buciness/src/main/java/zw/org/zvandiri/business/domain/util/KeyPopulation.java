package zw.org.zvandiri.business.domain.util;


import zw.org.zvandiri.business.util.StringUtils;

/**
 * @author manatsachinyeruse@gmail.com
 */


public enum MaritalStatus {
    MARRIED(1), SINGLE_OR_CHILD(2);

    private final Integer code;

    MaritalStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static final MaritalStatus get(Integer code){
        for(MaritalStatus item : values()){
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
