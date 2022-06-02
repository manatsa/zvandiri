/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.report.api.service.parallel;

import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.Referral;
import zw.org.zvandiri.business.repo.ContactRepo;
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
public class PatientReferralsTask extends RecursiveTask<List<Referral>> {

    private final List<Patient> data;
    private final SearchDTO dto;
    private final ReferralRepo referralRepo;


    public PatientReferralsTask(List<Patient> data, SearchDTO dto,  ReferralRepo referralRepo) {
        this.data = data;
        this.dto = dto;

        this.referralRepo = referralRepo;
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
            PatientReferralsTask task = new PatientReferralsTask(data.subList(0, mid), dto, referralRepo);
            PatientReferralsTask last = new PatientReferralsTask(data.subList(mid, data.size()), dto,  referralRepo);
            task.fork();
            List<Referral> list = last.compute();
            list.addAll(task.join());
            return list;
        }
    }

    private List<Referral> process() {
        List<Referral> list = new ArrayList<>();
        System.err.println(">> DB referrals for patients :<>: "+data.size());
        for (Patient item : data) {
            if (dto.getStartDate() != null && dto.getEndDate() != null) {
                list.addAll(referralRepo.findByPatientAndContactDate(item, dto.getStartDate(), dto.getEndDate()));
            } else {
                list.addAll(referralRepo.findByPatient(item));
            }
        }
        return list;
    }

}
