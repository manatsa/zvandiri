/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.report.api.service.parallel;

import zw.org.zvandiri.business.domain.InvestigationTest;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.TestType;
import zw.org.zvandiri.business.repo.ContactRepo;
import zw.org.zvandiri.business.repo.InvestigationTestRepo;
import zw.org.zvandiri.business.repo.ReferralRepo;
import zw.org.zvandiri.business.util.dto.SearchDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @author tasu
 */
public class PatientVLsTask extends RecursiveTask<List<InvestigationTest>> {

    private final List<Patient> data;
    private final SearchDTO dto;
    private final InvestigationTestRepo investigationTestRepo;


    public PatientVLsTask(List<Patient> data, SearchDTO dto, InvestigationTestRepo investigationTestRepo) {
        this.data = data;
        this.dto = dto;
        this.investigationTestRepo = investigationTestRepo;
    }

    @Override
    protected List compute() {
        if (data == null || data.isEmpty()) {
        	return new ArrayList<Patient>();
        }
    	if (data.size() <= ReportGenConstants.SEQUENTIAL_THRESHOLD) {
            return process();
        } else {
            int mid = data.size() / 2;
            PatientVLsTask task = new PatientVLsTask(data.subList(0, mid), dto, investigationTestRepo);
            PatientVLsTask last = new PatientVLsTask(data.subList(mid, data.size()), dto, investigationTestRepo);
            task.fork();
            List<InvestigationTest> list = last.compute();
            list.addAll(task.join());
            return list;
        }
    }

    private List<InvestigationTest> process() {
        List<InvestigationTest> list = new ArrayList<>();
        System.err.println(">> DB VLs for patients :<>: "+data.size());
        for (Patient item : data) {

                list.addAll(investigationTestRepo.findByPatientAndTestType(item, TestType.VIRAL_LOAD));

        }
        return list;
    }

}
