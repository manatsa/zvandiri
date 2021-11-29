/*
package zw.org.zvandiri.portal.config;


import org.springframework.stereotype.Controller;
import zw.org.zvandiri.business.domain.MentalHealthScreening;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.IdentifiedRisk;
import zw.org.zvandiri.business.service.MentalHealthScreeningService;
import zw.org.zvandiri.business.service.PatientService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

*/
/**
 * @author manatsachinyeruse@gmail.com
 *//*



@Controller
public class ExampleTester {

    @Resource
    MentalHealthScreeningService mentalHealthScreeningService;
    @Resource
    PatientService patientService;

    @PostConstruct
    public void createMentalHealthScreening(){
        Patient patient=patientService.get("0b77216c-b9d9-4b9a-900c-d1e7eacfd2f0");
        MentalHealthScreening mentalHealthScreening=new MentalHealthScreening();
        Set<IdentifiedRisk> risks=new HashSet();
        risks.addAll(Arrays.asList(IdentifiedRisk.SUICIDE,IdentifiedRisk.PSYCHOSIS));
        mentalHealthScreening.setIdentifiedRisks(risks);
        Set<MentalHealthScreening> mentalHealthScreenings=new HashSet<>();
        mentalHealthScreenings.add(mentalHealthScreening);
        patient.setMentalHealthScreenings(mentalHealthScreenings);
        mentalHealthScreening.setPatient(patient);

        MentalHealthScreening screening=mentalHealthScreeningService.save(mentalHealthScreening);
        System.err.println("MH : "+screening);
    }
}
*/
