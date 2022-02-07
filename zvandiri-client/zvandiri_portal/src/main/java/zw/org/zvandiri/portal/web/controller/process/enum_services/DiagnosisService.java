package zw.org.zvandiri.portal.web.controller.process.enum_services;


import org.springframework.stereotype.Service;
import zw.org.zvandiri.business.domain.util.Diagnosis;

/**
 * @author manatsachinyeruse@gmail.com
 */


@Service("diagnosisService")
public class DiagnosisService {

    public Diagnosis[] getDiagnosis(){
        return Diagnosis.values();
    }
}
