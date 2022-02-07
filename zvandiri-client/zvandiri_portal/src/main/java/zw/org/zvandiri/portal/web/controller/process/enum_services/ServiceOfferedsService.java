package zw.org.zvandiri.portal.web.controller.process.enum_services;


import org.springframework.stereotype.Service;
import zw.org.zvandiri.business.domain.ServiceOffered;
import zw.org.zvandiri.business.service.ServiceOfferedService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author manatsachinyeruse@gmail.com
 */


@Service("serviceOfferedsService")
public class ServiceOfferedsService {

    @Resource
    ServiceOfferedService serviceOfferedService;

    public List<ServiceOffered> getServiceOffereds(){
        return serviceOfferedService.getAll();
    }
}
