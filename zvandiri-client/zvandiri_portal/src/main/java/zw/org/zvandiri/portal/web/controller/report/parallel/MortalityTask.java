/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.portal.web.controller.report.parallel;

import java.util.List;
import java.util.concurrent.RecursiveTask;
import zw.org.zvandiri.business.service.MortalityService;
import zw.org.zvandiri.business.util.dto.SearchDTO;

/**
 *
 * @author tasu
 */
public class MortalityTask extends RecursiveTask<List>{
    
    private final MortalityService reportService;
    private static final int SEQUENTIAL_THRESHOLD = 500;
    private final SearchDTO searchData;
    private final List<Integer> arrCount;

    public MortalityTask(List<Integer> arrCount, MortalityService reportService, SearchDTO searchData) {
        this.reportService = reportService;
        this.searchData = searchData;
        this.arrCount = arrCount;
    }

    @Override
    protected List compute() {
        if (arrCount.size() <= SEQUENTIAL_THRESHOLD) {
            return process();
        } else {
            int mid = arrCount.size() / 2;
            MortalityTask task = new MortalityTask(arrCount.subList(0, mid), reportService, searchData);
            MortalityTask last = new MortalityTask(arrCount.subList(mid, arrCount.size()), reportService, searchData);
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
