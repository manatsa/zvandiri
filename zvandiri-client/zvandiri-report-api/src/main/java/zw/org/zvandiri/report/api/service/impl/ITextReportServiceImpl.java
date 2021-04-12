/*
 * Copyright 2015 Judge Muzinda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package zw.org.zvandiri.report.api.service.impl;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.util.List;
import org.springframework.stereotype.Repository;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.report.api.GenericReportModel;
import zw.org.zvandiri.report.api.service.ITextReportService;

/**
 *
 * @author Judge Muzinda
 */
@Repository
public class ITextReportServiceImpl implements ITextReportService {

    @Override
    public PdfPTable getAggregateDefaultReport(List<GenericReportModel> items, String name) {
        int numCols = 0;
        if (items != null) {
            numCols = items.get(0).getRow().size();
        }
        PdfPTable modelTable = new PdfPTable(numCols);
        modelTable.setWidthPercentage(100); 
        modelTable.setSpacingBefore(10f); 
        modelTable.setSpacingAfter(10f); 
        int colCount = 0;
        for (GenericReportModel model : items) {
            int cellCount = 0;
            colCount++;
            for (String item : model.getRow()) {
                cellCount++;
                Font headerFont = new Font(Font.FontFamily.HELVETICA,10, Font.BOLD );
                Font generalFont = new Font(Font.FontFamily.HELVETICA,9 );
                PdfPCell cell;
                Integer [] colorCodes = DateUtil.hex2Rgb("#E7EFFA");
                if(colCount == 1 || cellCount ==1 || cellCount == numCols){
                    cell = new PdfPCell(new Paragraph(item, headerFont));
                    cell.setBackgroundColor(new BaseColor(colorCodes[0], colorCodes[1], colorCodes[2]));
                    modelTable.addCell(cell);
                } else{
                    modelTable.addCell(new PdfPCell(new Paragraph(item, generalFont)));
                }
            }
        }
        return modelTable;
    }
}