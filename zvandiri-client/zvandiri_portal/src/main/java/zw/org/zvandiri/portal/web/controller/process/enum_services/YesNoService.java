package zw.org.zvandiri.portal.web.controller.process.enum_services;


import org.springframework.stereotype.Service;
import zw.org.zvandiri.business.domain.util.YesNo;

import java.util.List;

/**
 * @author manatsachinyeruse@gmail.com
 */


@Service("yesNoService")
public class YesNoService {


    public YesNo[] getYesNoValues(){
        return YesNo.values();
    }
}
