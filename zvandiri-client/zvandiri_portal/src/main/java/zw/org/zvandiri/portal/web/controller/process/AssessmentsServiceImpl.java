package zw.org.zvandiri.portal.web.controller.process;


import org.springframework.stereotype.Service;
import zw.org.zvandiri.business.domain.Assessment;
import zw.org.zvandiri.business.domain.util.ContactAssessment;
import zw.org.zvandiri.business.service.AssessmentService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author manatsachinyeruse@gmail.com
 */

@Service("assessmentsCreationService")
public class AssessmentsServiceImpl implements AssessmentsService {

    @Resource
    AssessmentService assessmentService;

    @Override
    public List<Assessment> getClinicalAssessments() {
        return assessmentService.getByAssessmentType(ContactAssessment.CLINICAL);
    }

    @Override
    public List<Assessment> getNonClinicalAssessments() {
        return assessmentService.getByAssessmentType(ContactAssessment.NON_CLINICAL);
    }
}
