package zw.org.zvandiri.portal.web.converter;

import org.springframework.core.convert.converter.Converter;
import zw.org.zvandiri.business.domain.util.CaderType;

/**
 * @author manatsachinyeruse@gmail.com
 */


public class CadreTypeConverter implements Converter<String, CaderType> {

    @Override
    public CaderType convert(String s) {
        if(s.equals("")) return null;
        return CaderType.get(Integer.valueOf(s));
    }

}