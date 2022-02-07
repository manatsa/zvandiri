package zw.org.zvandiri.portal.web.controller.process.enum_services;


import org.springframework.stereotype.Service;
import zw.org.zvandiri.business.domain.util.TbSymptom;

/**
 * @author manatsachinyeruse@gmail.com
 */


@Service("tbSymptomsService")
public class TBSystmptomsService {

    public TbSymptom[] getTBSymptoms(){
        return TbSymptom.values();
    }
}
