package zw.org.zvandiri.portal.web.converter;


import org.springframework.core.convert.converter.Converter;
import zw.org.zvandiri.business.domain.util.MaritalStatus;
import zw.org.zvandiri.business.domain.util.OrphanageStatus;

/**
 * @author manatsachinyeruse@gmail.com
 */


public class OrphanageStatusConverter implements Converter<String, OrphanageStatus> {
    @Override
    public OrphanageStatus convert(String s) {
        if(s.equals("")) return null;
        return OrphanageStatus.get(Integer.valueOf(s));
    }
}
