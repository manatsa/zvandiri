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
 * @author tasu
 */
public class LabTestTask extends RecursiveTask<List>{
    
    private final PatientReportService reportService;
    private final SearchDTO searchData;
    private final List<Integer> arrCount;

    public LabTestTask(List<Integer> arrCount, PatientReportService reportService, SearchDTO searchData) {
        this.reportService = reportService;
        this.searchData = searchData;
        this.arrCount = arrCount;
    }

    @Override
    protected List compute() {
        if (arrCount.size() <= ReportGenConstants.SEQUENTIAL_THRESHOLD) {
            return process();
        } else {
            int mid = arrCount.size() / 2;
            LabTestTask task = new LabTestTask(arrCount.subList(0, mid), reportService, searchData);
            LabTestTask last = new LabTestTask(arrCount.subList(mid, arrCount.size()), reportService, searchData);
            task.fork();
            List list = last.compute();
            list.addAll(task.join());
            return list;
        }
    }

    private List process() {
        int first = arrCount.get(0);
        first = first > 0 ? first - 1 : first;
        searchData.setFirstResult(first);
        Integer pageSize = arrCount.get(arrCount.size() - 1) - searchData.getFirstResult();
        searchData.setPageSize(pageSize);
        return reportService.getPatientLabResultList(searchData.getInstance(searchData));
    }
}
