package zw.org.zvandiri.portal.web.controller.process;


import org.springframework.stereotype.Service;
import zw.org.zvandiri.business.domain.ServiceOffered;
import zw.org.zvandiri.portal.web.controller.process.enum_services.ServiceOfferedsService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author manatsachinyeruse@gmail.com
 */

@Service("servicesOfferedService")
public class ServicesOfferedServiceImpl implements ServicesOfferedService {

    @Resource
    ServiceOfferedsService serviceOfferedsService;

    @Override
    public List<ServiceOffered> getServicesOffered() {
        return serviceOfferedsService.getServiceOffereds();
    }
}
