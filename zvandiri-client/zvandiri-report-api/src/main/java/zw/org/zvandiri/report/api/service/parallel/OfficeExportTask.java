package zw.org.zvandiri.report.api.service.parallel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import zw.org.zvandiri.report.api.GenericReportModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class OfficeExportTask extends RecursiveAction {

    List<GenericReportModel> model;
    XSSFSheet XSSFSheet;
    XSSFWorkbook workbook;

    int XSSFRowNum=0;

    public OfficeExportTask(List<GenericReportModel> model,XSSFSheet sheet) {
        this.model = model;
        this.XSSFSheet=sheet;
    }


    @Override
    protected void compute() {
        if(model.size()<=ReportGenConstants.SEQUENTIAL_THRESHOLD)
        {
           computeDirectly();
        }else{
            int mid=model.size() / 2;
            OfficeExportTask first=new OfficeExportTask(model.subList(0, mid), XSSFSheet);
            OfficeExportTask second=new OfficeExportTask(model.subList(mid, model.size()), XSSFSheet);

            first.fork();
            second.compute();
            first.join();
        }


    }

    public void computeDirectly(){
        List<XSSFRow> rows=new ArrayList<>();
        System.err.println("**************************************** Now size of model is :"+model.size());
        for (GenericReportModel model : model) {
            XSSFRow XSSFRow = XSSFSheet.createRow(XSSFRowNum++);
            int XSSFCellNum = 0;
            for (String item : model.getRow()) {
                XSSFCell XSSFCell = XSSFRow.createCell(XSSFCellNum++);
                XSSFCell.setCellValue(item);
            }
//         rows.add(XSSFRow);
        }

//        return rows;
    }
}
