/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.report.api.service.parallel;

import java.util.List;
import java.util.concurrent.RecursiveTask;

import zw.org.zvandiri.business.service.InvestigationTestService;
import zw.org.zvandiri.business.util.dto.SearchDTO;

/**
 *
 * @author manatsachinyeruse@gmail.com
 */
public class InvalidVLTask extends RecursiveTask<List>{

    private final InvestigationTestService investigationTestService;
    private final SearchDTO searchData;
    private final List<Integer> arrCount;

    public InvalidVLTask(List<Integer> arrCount, InvestigationTestService reportService, SearchDTO searchData) {
        this.investigationTestService = reportService;
        this.searchData = searchData;
        this.arrCount = arrCount;
    }

    @Override
    protected List compute() {
        if (arrCount.size() <= ReportGenConstants.SEQUENTIAL_THRESHOLD) {
            return computeDirectly();
        } else {
            int mid = arrCount.size() / 2;
            InvalidVLTask first = new InvalidVLTask(arrCount.subList(0, mid), investigationTestService, searchData);
            InvalidVLTask last = new InvalidVLTask(arrCount.subList(mid, arrCount.size()), investigationTestService, searchData);
            first.fork();
            List list = last.compute();
            list.addAll(first.join());
            return list;
        }
    }

    private List computeDirectly() {
        int first = arrCount.get(0);
        first = first > 0 ? first - 1 : first;
        searchData.setFirstResult(first);
        Integer pageSize = arrCount.get(arrCount.size() - 1) - searchData.getFirstResult();
        searchData.setPageSize(pageSize);
        return investigationTestService.getInvalidViralLoad(searchData.getInstance(searchData));
    }
}
