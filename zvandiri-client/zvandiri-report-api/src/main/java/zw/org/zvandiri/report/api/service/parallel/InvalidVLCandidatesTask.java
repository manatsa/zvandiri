/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.report.api.service.parallel;

import zw.org.zvandiri.business.service.PatientReportService;
import zw.org.zvandiri.business.util.dto.SearchDTO;

import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @author manatsachinyeruse@gmail.com
 */
public class InvalidVLCandidatesTask extends RecursiveTask<List>{

    private final PatientReportService reportService;
    private final SearchDTO searchData;
    private final List<String> patientIds;

    public InvalidVLCandidatesTask(List<String> patientIds, PatientReportService reportService, SearchDTO searchData) {
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
            InvalidVLCandidatesTask task = new InvalidVLCandidatesTask(patientIds.subList(0, mid), reportService, searchData);
            InvalidVLCandidatesTask last = new InvalidVLCandidatesTask(patientIds.subList(mid, patientIds.size()), reportService, searchData);
            task.fork();
            List list = last.compute();
            list.addAll(task.join());
            return list;
        }
    }

    private List process() {
//        int first = patientIds.get(0);
//        first = first > 0 ? first - 1 : first;
//        searchData.setFirstResult(first);
//        Integer pageSize = patientIds.get(patientIds.size() - 1) - searchData.getFirstResult();
//        searchData.setPageSize(pageSize);
        return reportService.getPatientsWithInvalidVL(patientIds, searchData);
    }
}
