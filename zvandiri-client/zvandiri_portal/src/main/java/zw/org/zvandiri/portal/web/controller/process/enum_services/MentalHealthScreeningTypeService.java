package zw.org.zvandiri.portal.web.controller.process.enum_services;


import org.springframework.stereotype.Service;
import zw.org.zvandiri.business.domain.util.MentalHealthScreeningType;

/**
 * @author manatsachinyeruse@gmail.com
 */


@Service("mentalHealthScreeningTypeService")
public class MentalHealthScreeningTypeService {

    public MentalHealthScreeningType[] mentalHealthScreeningTypes(){
        return MentalHealthScreeningType.values();
    }
}
