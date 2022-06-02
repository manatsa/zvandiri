/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.report.api.service.parallel;

import zw.org.zvandiri.business.domain.MentalHealthScreening;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.repo.MentalHealthScreeningRepo;
import zw.org.zvandiri.business.repo.TbIptRepo;
import zw.org.zvandiri.business.util.dto.SearchDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @author tasu
 */
public class PatientMentalHealthScreeningTask extends RecursiveTask<List<MentalHealthScreening>> {

    private final List<Patient> data;
    private final SearchDTO dto;
    private final MentalHealthScreeningRepo mentalHealthScreeningRepo;


    public PatientMentalHealthScreeningTask(List<Patient> data, SearchDTO dto, MentalHealthScreeningRepo mentalHealthScreeningRepo) {
        this.data = data;
        this.dto = dto;

        this.mentalHealthScreeningRepo = mentalHealthScreeningRepo;
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
            PatientMentalHealthScreeningTask task = new PatientMentalHealthScreeningTask(data.subList(0, mid), dto, mentalHealthScreeningRepo);
            PatientMentalHealthScreeningTask last = new PatientMentalHealthScreeningTask(data.subList(mid, data.size()), dto,  mentalHealthScreeningRepo);
            task.fork();
            List<MentalHealthScreening> list = last.compute();
            list.addAll(task.join());
            return list;
        }
    }

    private List<MentalHealthScreening> process() {
        List<MentalHealthScreening> list = new ArrayList<>();
        System.err.println(">> DB referrals for patients :<>: "+data.size());
        for (Patient item : data) {

                list.addAll(mentalHealthScreeningRepo.findByPatient(item));

        }
        return list;
    }

}
