/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.portal.web.controller.report.parallel;

import java.util.List;
import java.util.concurrent.RecursiveTask;

import zw.org.zvandiri.business.domain.User;
import zw.org.zvandiri.business.service.GenericReportService;
import zw.org.zvandiri.business.service.UserService;
import zw.org.zvandiri.business.util.dto.SearchDTO;

/**
 *
 * @author tasu
 */
public class GenericCountReportTask extends RecursiveTask<List> {

    private GenericReportService reportService;
    private SearchDTO searchData;
    private List<Integer> arrCount;
    User user;

    public GenericCountReportTask(List<Integer> arrCount, GenericReportService reportService, SearchDTO searchData, User user) {
        this.reportService = reportService;
        this.searchData = searchData.getInstance(searchData);
        this.arrCount = arrCount;
        this.user=user;
    }

    @Override
    protected List compute() {
        if (arrCount.size() <= ReportGenConstants.SEQUENTIAL_THRESHOLD) {
            System.err.println("User :"+user.getUserName()+" <=> District::"+user.getDistrict()+"  >>>> Contacts Parts Data Size::"+arrCount.size());
            return process();
        } else {
            int mid = arrCount.size() / 2;
            GenericCountReportTask task = new GenericCountReportTask(arrCount.subList(0, mid), reportService, searchData, user);
            GenericCountReportTask last = new GenericCountReportTask(arrCount.subList(mid, arrCount.size()), reportService, searchData, user);
            task.fork();
            List list = last.compute();
            list.addAll(task.join());
            return list;
        }
    }

    private List process() {
        //System.err.println(searchData.getInstance(searchData).toString());
        int first = arrCount.get(0);
        first = first > 0 ? first - 1 : first;
        searchData.setFirstResult(first);
        Integer pageSize = arrCount.get(arrCount.size() - 1) - searchData.getFirstResult();
        searchData.setPageSize(pageSize);
        System.err.println( "User :"+user.getUserName()+" <=> District::"+user.getDistrict()+" >>> Contact Parts IDS ::"+arrCount.size()+"\n");
        return reportService.get(searchData.getInstance(searchData));
    }
}
