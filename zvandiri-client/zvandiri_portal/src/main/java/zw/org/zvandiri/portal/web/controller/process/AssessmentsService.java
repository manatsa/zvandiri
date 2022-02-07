package zw.org.zvandiri.portal.web.controller.process;


import zw.org.zvandiri.business.domain.Assessment;

import java.util.List;
import java.util.Set;

/**
 * @author manatsachinyeruse@gmail.com
 */


public interface AssessmentsService {
    public List<Assessment> getClinicalAssessments();

    public List<Assessment> getNonClinicalAssessments();
}


