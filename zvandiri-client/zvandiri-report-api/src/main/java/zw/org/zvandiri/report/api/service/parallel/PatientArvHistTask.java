/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.report.api.service.parallel;

import zw.org.zvandiri.business.domain.ArvHist;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.repo.ArvHistRepo;
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
public class PatientArvHistTask extends RecursiveTask<List<ArvHist>> {

    private final List<Patient> data;
    private final SearchDTO dto;
    private final ArvHistRepo arvHistRepo;


    public PatientArvHistTask(List<Patient> data, SearchDTO dto, ArvHistRepo arvHistRepo) {
        this.data = data;
        this.dto = dto;

        this.arvHistRepo = arvHistRepo;
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
            PatientArvHistTask task = new PatientArvHistTask(data.subList(0, mid), dto, arvHistRepo);
            PatientArvHistTask last = new PatientArvHistTask(data.subList(mid, data.size()), dto,  arvHistRepo);
            task.fork();
            List<ArvHist> list = last.compute();
            list.addAll(task.join());
            return list;
        }
    }

    private List<ArvHist> process() {
        List<ArvHist> list = new ArrayList<>();
        System.err.println(">> DB referrals for patients :<>: "+data.size());
        for (Patient item : data) {
                list.addAll(arvHistRepo.findByPatient(item));

        }
        return list;
    }

}
