package zw.org.zvandiri.portal.web.controller.process.enum_services;


import zw.org.zvandiri.business.domain.Referral;
import zw.org.zvandiri.business.domain.ServicesReferred;

import java.util.List;

/**
 * @author manatsachinyeruse@gmail.com
 */


public interface ReferralListService {

    public List<ServicesReferred> getHiveSTIPrevention();
    public List<ServicesReferred> getLabDiagnosis();
    public List<ServicesReferred> getOIArtServices();
    public List<ServicesReferred> getTBServices();
    public List<ServicesReferred> getPsychoSocialSupoort();
    public List<ServicesReferred> getSRHServices();
    public List<ServicesReferred> getLegalSupport();
}
