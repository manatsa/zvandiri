/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.report.api.service.parallel;

import zw.org.zvandiri.business.service.ContactReportService;
import zw.org.zvandiri.business.util.dto.SearchDTO;

import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @author tasu
 */
public class NewContactReportTask extends RecursiveTask<List>{

    private final ContactReportService reportService;
    private final SearchDTO searchData;
    private final List<Integer> arrCount;

    public NewContactReportTask(List<Integer> arrCount, ContactReportService reportService, SearchDTO searchData) {
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
            NewContactReportTask task = new NewContactReportTask(arrCount.subList(0, mid), reportService, searchData);
            NewContactReportTask last = new NewContactReportTask(arrCount.subList(mid, arrCount.size()), reportService, searchData);
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
        return reportService.get(searchData.getInstance(searchData));
    }
}
