/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.report.api.service.parallel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.repo.ContactRepo;
import zw.org.zvandiri.business.util.dto.SearchDTO;

/**
 *
 * @author tasu
 */
public class PatientReportTask extends RecursiveTask<List<Patient>> {

    private static final int SEQUENTIAL_THRESHOLD = 100;
    private final List<Patient> data;
    private final SearchDTO dto;
    private final ContactRepo contactRepo;
            

    public PatientReportTask(List<Patient> data, SearchDTO dto, ContactRepo contactRepo) {
        this.data = data;
        this.dto = dto;
        this.contactRepo = contactRepo;
    }

    @Override
    protected List compute() {
        if (data == null || data.isEmpty()) {
        	return new ArrayList<Patient>();
        }
    	if (data.size() <= SEQUENTIAL_THRESHOLD) {
            return process();
        } else {
            int mid = data.size() / 2;
            PatientReportTask task = new PatientReportTask(data.subList(0, mid), dto, contactRepo);
            PatientReportTask last = new PatientReportTask(data.subList(mid, data.size()), dto, contactRepo);
            task.fork();
            List<Patient> list = last.compute();
            list.addAll(task.join());
            return list;
        }
    }

    private List<Patient> process() {
        List<Patient> list = new ArrayList<>();
        for (Patient item : data) {
            if (dto.getStartDate() != null && dto.getEndDate() != null) {
                item.setContacts(new HashSet<>(contactRepo.findByPatientAndContactDate(item, dto.getStartDate(), dto.getEndDate())));
            } else {
                item.setContacts(new HashSet<>(contactRepo.findByPatient(item)));
            }
            list.add(item);
        }
        return list;
    }

}
