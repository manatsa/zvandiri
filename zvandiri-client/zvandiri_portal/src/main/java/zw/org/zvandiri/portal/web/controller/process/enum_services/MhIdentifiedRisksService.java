package zw.org.zvandiri.portal.web.controller.process.enum_services;


import org.springframework.stereotype.Service;
import zw.org.zvandiri.business.domain.util.IdentifiedRisk;

/**
 * @author manatsachinyeruse@gmail.com
 */


@Service("mhIdentifiedRisksService")
public class MhIdentifiedRisksService {

    public IdentifiedRisk[] getIdentifiedRisks() {
        return IdentifiedRisk.values();
    }
}
