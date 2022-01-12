package zw.org.zvandiri.portal.web.controller.process.enum_services;


import org.springframework.stereotype.Service;
import zw.org.zvandiri.business.domain.util.Referral;

/**
 * @author manatsachinyeruse@gmail.com
 */


@Service("referalEnumService")
public class ReferralService {

    public Referral[] getReferrals(){
        return Referral.values();
    }
}
