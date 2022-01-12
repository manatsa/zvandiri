package zw.org.zvandiri.portal.web.controller.process;


import org.springframework.stereotype.Service;
import zw.org.zvandiri.business.domain.ContactVisitOutcome;

/**
 * @author manatsachinyeruse@gmail.com
 */


@Service("contactVisitOutcomeService")
public class ContactVisitOutcomeServiceImpl implements ContactVisitOutcomeService {
    @Override
    public ContactVisitOutcome createContactVisitOutcome() {
        return new ContactVisitOutcome();
    }
}
