/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.report.api.service.parallel;

import zw.org.zvandiri.business.domain.Contact;
import zw.org.zvandiri.business.domain.Patient;
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
public class PatientContactsTask extends RecursiveTask<List<Contact>> {

    private final List<Patient> data;
    private final SearchDTO dto;
    private final ContactRepo contactRepo;


    public PatientContactsTask(List<Patient> data, SearchDTO dto, ContactRepo contactRepo) {
        this.data = data;
        this.dto = dto;
        this.contactRepo = contactRepo;
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
            PatientContactsTask task = new PatientContactsTask(data.subList(0, mid), dto, contactRepo);
            PatientContactsTask last = new PatientContactsTask(data.subList(mid, data.size()), dto, contactRepo);
            task.fork();
            List<Contact> list = last.compute();
            list.addAll(task.join());
            return list;
        }
    }

    private List<Contact> process() {
        List<Contact> list = new ArrayList<>();
        System.err.println(">> DB Contacts for patients :<>: "+data.size());
        for (Patient item : data) {
                list.addAll(contactRepo.findByPatient(item));
        }
        //System.err.println(">> DB Contacts for patients :<>: "+data.size()+" => "+list.size());
        return list;
    }

}
