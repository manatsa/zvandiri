/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.portal.web.converter;

import org.springframework.core.convert.converter.Converter;
import zw.org.zvandiri.business.domain.util.TbIdentificationOutcome;

/**
 * @author tasu
 */
public class TbIdentificationOutcomeConverter implements Converter<String, TbIdentificationOutcome> {
    @Override
    public TbIdentificationOutcome convert(String s) {
        if (s.equals("")) {
            return null;
        }
        return TbIdentificationOutcome.get(Integer.valueOf(s));
    }
}
