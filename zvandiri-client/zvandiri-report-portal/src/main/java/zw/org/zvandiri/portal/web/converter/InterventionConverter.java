/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.portal.web.converter;

import org.springframework.core.convert.converter.Converter;
import zw.org.zvandiri.business.domain.util.Intervention;

/**
 * @author tasu
 */
public class InterventionConverter implements Converter<String, Intervention> {
    @Override
    public Intervention convert(String s) {
        if (s.equals("")) return null;
        return Intervention.get(Integer.valueOf(s));
    }
}
