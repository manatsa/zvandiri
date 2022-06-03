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
public class MHScreeningCandidatesTask extends RecursiveTask<List>{

    private final PatientReportService reportService;
    private final SearchDTO searchData;
    private final List<String> patientIDS;

    public MHScreeningCandidatesTask(List<String> patientIDS, PatientReportService reportService, SearchDTO searchData) {
        this.reportService = reportService;
        this.searchData = searchData;
        this.patientIDS = patientIDS;

    }

    @Override
    protected List compute() {
        if (patientIDS.size() <= ReportGenConstants.SEQUENTIAL_THRESHOLD) {
            return process();
        } else {
            int mid = patientIDS.size() / 2;
            MHScreeningCandidatesTask task = new MHScreeningCandidatesTask(patientIDS.subList(0, mid), reportService, searchData);
            MHScreeningCandidatesTask last = new MHScreeningCandidatesTask(patientIDS.subList(mid, patientIDS.size()), reportService, searchData);
            task.fork();
            List list = last.compute();
            list.addAll(task.join());
            return list;
        }
    }

    private List process() {
//        int first = patientIDS.get(0);
//        first = first > 0 ? first - 1 : first;
//        searchData.setFirstResult(first);
//        Integer pageSize = patientIDS.get(patientIDS.size() - 1) - searchData.getFirstResult();
//        searchData.setPageSize(pageSize);
        return reportService.getMHScreeningCandidates(patientIDS,searchData);
    }
}
