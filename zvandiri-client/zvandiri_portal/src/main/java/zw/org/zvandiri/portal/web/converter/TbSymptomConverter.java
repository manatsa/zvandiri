/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.portal.web.converter;

import org.springframework.core.convert.converter.Converter;
import zw.org.zvandiri.business.domain.util.TbSymptom;

/**
 * @author tasu
 */
public class TbSymptomConverter implements Converter<String, TbSymptom> {
    @Override
    public TbSymptom convert(String s) {
        if (s.equals("")) {
            return null;
        }
        return TbSymptom.get(Integer.valueOf(s));
    }
}
