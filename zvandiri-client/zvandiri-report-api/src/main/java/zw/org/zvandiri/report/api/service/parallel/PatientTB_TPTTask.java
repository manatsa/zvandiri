/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.report.api.service.parallel;

import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.TbIpt;
import zw.org.zvandiri.business.repo.ReferralRepo;
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
public class PatientTB_TPTTask extends RecursiveTask<List<TbIpt>> {

    private final List<Patient> data;
    private final SearchDTO dto;
    private final TbIptRepo tbIptRepo;


    public PatientTB_TPTTask(List<Patient> data, SearchDTO dto, TbIptRepo tbIptRepo) {
        this.data = data;
        this.dto = dto;

        this.tbIptRepo = tbIptRepo;
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
            PatientTB_TPTTask task = new PatientTB_TPTTask(data.subList(0, mid), dto, tbIptRepo);
            PatientTB_TPTTask last = new PatientTB_TPTTask(data.subList(mid, data.size()), dto,  tbIptRepo);
            task.fork();
            List<TbIpt> list = last.compute();
            list.addAll(task.join());
            return list;
        }
    }

    private List<TbIpt> process() {
        List<TbIpt> list = new ArrayList<>();
        System.err.println(">> DB referrals for patients :<>: "+data.size());
        for (Patient item : data) {

                list.addAll(tbIptRepo.findByPatientOrderByDateCreatedDesc(item));

        }
        return list;
    }

}
