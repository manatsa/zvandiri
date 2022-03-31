package zw.org.zvandiri.portal.web.controller.process;


import org.springframework.stereotype.Service;
import zw.org.zvandiri.business.domain.ServiceOffered;

import java.util.List;

/**
 * @author manatsachinyeruse@gmail.com
 */


public interface ServicesOfferedService {

    List<ServiceOffered> getServicesOffered();
}
