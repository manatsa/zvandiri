package zw.org.zvandiri.portal.web.converter;

import org.springframework.core.convert.converter.Converter;
import zw.org.zvandiri.business.domain.util.Condition;



/**
 *
 * @author mana
 */
public class PhoneConditionConverter implements Converter<String, Condition> {

    @Override
    public Condition convert(String s) {
        if(s.equals("")) return null;
        return Condition.get(Integer.valueOf(s));
    }

}
