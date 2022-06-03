/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.report.api.service.parallel;

import java.util.List;
import java.util.concurrent.RecursiveTask;
import zw.org.zvandiri.business.service.PatientReportService;
import zw.org.zvandiri.business.util.dto.SearchDTO;

/**
 *
 * @author manatsachinyeruse@gmail.com
 */
public class UnContactedClientTask extends RecursiveTask<List>{
    
    private final PatientReportService reportService;
    private final SearchDTO searchData;
    private final List<String> patientIds;

    public UnContactedClientTask(List<String> patientIds, PatientReportService reportService, SearchDTO searchData) {
        this.reportService = reportService;
        this.searchData = searchData;
        this.patientIds = patientIds;

    }

    @Override
    protected List compute() {
        if (patientIds.size() <= ReportGenConstants.SEQUENTIAL_THRESHOLD) {
            return process();
        } else {
            int mid = patientIds.size() / 2;
            UnContactedClientTask task = new UnContactedClientTask(patientIds.subList(0, mid), reportService, searchData);
            UnContactedClientTask last = new UnContactedClientTask(patientIds.subList(mid, patientIds.size()), reportService, searchData);
            task.fork();
            List list = last.compute();
            list.addAll(task.join());
            return list;
        }
    }

    private List process() {
        return reportService.getUncontactedClients(patientIds, searchData);
    }
}
