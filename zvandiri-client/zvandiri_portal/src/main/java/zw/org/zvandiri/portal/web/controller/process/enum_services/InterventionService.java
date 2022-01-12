package zw.org.zvandiri.portal.web.controller.process.enum_services;


import org.springframework.stereotype.Service;
import zw.org.zvandiri.business.domain.util.Intervention;

/**
 * @author manatsachinyeruse@gmail.com
 */


@Service("interventionService")
public class InterventionService {

    public Intervention[] getInterventions(){
        return Intervention.values();
    }
}
