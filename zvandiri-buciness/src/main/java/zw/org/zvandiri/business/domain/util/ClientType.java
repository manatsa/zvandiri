package zw.org.zvandiri.business.domain.util;


import zw.org.zvandiri.business.util.StringUtils;

/**
 * @author manatsachinyeruse@gmail.com
 */


public enum ClientType {
    CAYPLHIV(1),YOUNG_MUM(2), OTHER(3);

    private final Integer code;

    ClientType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static ClientType get(String code){
        switch(code){
            case "1":
                return CAYPLHIV;
            case "2":
                return YOUNG_MUM;
            case "3":
                return OTHER;
            default:
                throw new IllegalArgumentException("Illegal parameter passed to method :"+code);
        }
    }

    public static final ClientType get(Integer code){
        for(ClientType item : values()){
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
