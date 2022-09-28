/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.report.api.service.parallel;

import java.util.List;
import java.util.concurrent.RecursiveTask;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.service.DetailedPatientReportService;

/**
 *
 * @author tasu
 */
public class PatientDatabaseExportTask extends RecursiveTask<List<Patient>> {
    private final List<String> data;
    private final DetailedPatientReportService reportService;
//    private final static int SEQUENTIAL_THRESHOLD = 200;
    
    public PatientDatabaseExportTask(List<String> data, DetailedPatientReportService reportService) {
        this.data = data;
        this.reportService = reportService;
    }

    @Override
    protected List<Patient> compute() {
        if (data.size() <= ReportGenConstants.SEQUENTIAL_THRESHOLD) {
            return process();
        } else {
            int mid = data.size() / 2;
            PatientDatabaseExportTask task = new PatientDatabaseExportTask(data.subList(0, mid), reportService);
            PatientDatabaseExportTask last = new PatientDatabaseExportTask(data.subList(mid, data.size()), reportService);
            task.fork();
            List<Patient> list = last.compute();
            list.addAll(task.join());
            return list;
        }
    }
    
    private List<Patient> process() {
        return reportService.get(data);
    }
    
}
