package zw.org.zvandiri.portal.web.controller.process.enum_services;


import org.springframework.stereotype.Service;
import zw.org.zvandiri.business.domain.Referral;
import zw.org.zvandiri.business.domain.ServicesReferred;
import zw.org.zvandiri.business.domain.util.ReferalType;
import zw.org.zvandiri.business.service.ReferralService;
import zw.org.zvandiri.business.service.ServicesReferredService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author manatsachinyeruse@gmail.com
 */

@Service("referralListService")
public class ReferralListServiceImpl implements ReferralListService {

    @Resource
    ReferralService referralService;
    @Resource
    private ServicesReferredService servicesReferredService;



    @Override
    public List<ServicesReferred> getHiveSTIPrevention() {
        return servicesReferredService.getByType(ReferalType.HIV_STI_PREVENTION);
    }

    @Override
    public List<ServicesReferred> getLabDiagnosis() {
        return servicesReferredService.getByType(ReferalType.LABORATORY_DIAGNOSES);
    }

    @Override
    public List<ServicesReferred> getOIArtServices() {
        return servicesReferredService.getByType(ReferalType.OI_ART_SERVICES);
    }

    @Override
    public List<ServicesReferred> getTBServices() {
        return servicesReferredService.getByType(ReferalType.TB_SERVICES);
    }

    @Override
    public List<ServicesReferred> getPsychoSocialSupoort() {
        return servicesReferredService.getByType(ReferalType.PSYCHO_SOCIAL_SUPPORT);
    }

    @Override
    public List<ServicesReferred> getSRHServices() {
        return servicesReferredService.getByType(ReferalType.SRH_SERVICES);
    }

    @Override
    public List<ServicesReferred> getLegalSupport() {
        return servicesReferredService.getByType(ReferalType.LEGAL_SUPPORT);
    }
}
