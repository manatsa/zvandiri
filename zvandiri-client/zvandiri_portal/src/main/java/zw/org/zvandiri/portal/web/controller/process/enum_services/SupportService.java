package zw.org.zvandiri.portal.web.controller.process.enum_services;


import org.springframework.stereotype.Service;
import zw.org.zvandiri.business.domain.util.Support;

/**
 * @author manatsachinyeruse@gmail.com
 */


@Service("supportService")
public class SupportService {

    public Support[] getSupports() {
        return Support.values();
    }
}
