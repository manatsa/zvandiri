/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.report.api.service.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import zw.org.zvandiri.business.domain.Facility;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.report.api.GenericReportModel;
import zw.org.zvandiri.report.api.service.ProblemReportService;

/**
 *
 * @author tasu
 */
public class CombinedReportTask extends RecursiveAction {

    private final List<Facility> data;
    private static final int SEQUENTIAL_THRESHOLD = 5;
    private final ProblemReportService reportService;
    private final SearchDTO dto;
    private final List<GenericReportModel> list;
    private final List<String> provinces;
    private final List<String> districts;

    public CombinedReportTask(List<Facility> data, ProblemReportService reportService, SearchDTO dto, List<GenericReportModel> list, List<String> provinces, List<String> districts) {
        this.data = data;
        this.reportService = reportService;
        this.dto = dto;
        this.list = list;
        this.provinces = provinces;
        this.districts = districts;
    }

    @Override
    protected void compute() {
        if (data.size() <= SEQUENTIAL_THRESHOLD) {
            process();
        } else {
            int mid = data.size() / 2;
            CombinedReportTask first = new CombinedReportTask(data.subList(0, mid), reportService, dto, list, provinces, districts);
            CombinedReportTask last = new CombinedReportTask(data.subList(mid, data.size()), reportService, dto, list, provinces, districts);
            first.fork();
            last.fork();
            first.join();
            last.join();
        }
    }

    private void process() {
        for (Facility facility : data) {
            dto.setPrimaryClinic(facility);
            GenericReportModel model = new GenericReportModel();
            List<String> row = new ArrayList<>();
            row.add(!contains(facility.getDistrict().getProvince().getName(), provinces) ? facility.getDistrict().getProvince().getName() : "");
            if(!contains(facility.getDistrict().getProvince().getName(), provinces)) {
                add(facility.getDistrict().getProvince().getName(), provinces);
            }
            row.add(!contains(facility.getDistrict().getName(), districts) ? facility.getDistrict().getName() : "");
            if(!contains(facility.getDistrict().getName(), districts)) {
                add(facility.getDistrict().getName(), districts);
            }
            row.add(facility.getName());
            model.setRow(reportService.getProblemReport(row, dto.getInstance(dto)));
            list.add(model);
        }
    }

    private void add(String item, List<String> items) {
        items.add(item);
    }

    private boolean contains(String item, List<String> items) {
        return items.contains(item);
    }
}
