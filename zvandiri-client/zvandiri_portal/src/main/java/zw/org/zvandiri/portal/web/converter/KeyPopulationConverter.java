package zw.org.zvandiri.portal.web.converter;

import org.springframework.core.convert.converter.Converter;
import zw.org.zvandiri.business.domain.util.KeyPopulation;
import zw.org.zvandiri.business.domain.util.MaritalStatus;

/**
 * @author manatsachinyeruse@gmail.com
 */


public class KeyPopulationConverter implements Converter<String, KeyPopulation> {
    @Override
    public KeyPopulation convert(String s) {
        if (s.equals("")) return null;
        return KeyPopulation.get(Integer.valueOf(s));
    }
}
