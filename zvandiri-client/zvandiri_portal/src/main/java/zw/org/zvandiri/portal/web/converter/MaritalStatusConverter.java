package zw.org.zvandiri.portal.web.converter;


import org.springframework.core.convert.converter.Converter;
import zw.org.zvandiri.business.domain.util.MaritalStatus;

/**
 * @author manatsachinyeruse@gmail.com
 */


public class MaritalStatusConverter implements Converter<String, MaritalStatus> {
    @Override
    public MaritalStatus convert(String s) {
        if(s.equals("")) return null;
        return MaritalStatus.get(Integer.valueOf(s));
    }
}
