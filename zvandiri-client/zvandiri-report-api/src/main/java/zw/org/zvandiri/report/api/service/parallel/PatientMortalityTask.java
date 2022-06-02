/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.report.api.service.parallel;

import zw.org.zvandiri.business.domain.Contact;
import zw.org.zvandiri.business.domain.Mortality;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.repo.MortalityRepo;
import zw.org.zvandiri.business.util.dto.SearchDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @author tasu
 */
public class PatientMortalityTask extends RecursiveTask<List<Mortality>> {

    private final List<Patient> data;
    private final SearchDTO dto;
    private final MortalityRepo mortalityRepo;


    public PatientMortalityTask(List<Patient> data, SearchDTO dto, MortalityRepo mortalityRepo) {
        this.data = data;
        this.dto = dto;
        this.mortalityRepo = mortalityRepo;
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
            PatientMortalityTask task = new PatientMortalityTask(data.subList(0, mid), dto, mortalityRepo);
            PatientMortalityTask last = new PatientMortalityTask(data.subList(mid, data.size()), dto, mortalityRepo);
            task.fork();
            List<Mortality> list = last.compute();
            list.addAll(task.join());
            return list;
        }
    }

    private List<Mortality> process() {
        List<Mortality> list = new ArrayList<>();
        System.err.println(">> DB Contacts for patients :<>: "+data.size());
        for (Patient item : data) {

                list.add(mortalityRepo.findByPatient(item));

        }
        return list;
    }

}
