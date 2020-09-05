/*
 * Copyright 2016 Judge Muzinda.
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
package zw.org.zvandiri.portal.web.controller.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.report.api.GenericReportModel;
import zw.org.zvandiri.report.api.service.OfficeExportService;

/**
 *
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/report")
public class ReportIndexController extends BaseController {
    
    @Resource
    private OfficeExportService officeExportService;
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getReportIndex(ModelMap model){
        model.addAttribute("pageTitle", APP_PREFIX+"Reports Home");
        return "report/index";
    }
    
    @RequestMapping(value = "/catlist", method = RequestMethod.GET)
    public String getCatList(ModelMap model){
        model.addAttribute("pageTitle", APP_PREFIX+"CAT Province Age-Group Cross Tabulation");
        model.addAttribute("items", catLoc());
        model.addAttribute("excelExport", "/report/aggregate/catlist/excel");
        return "report/table";
    }
    
    @RequestMapping(value = "/catlist/excel", method = RequestMethod.GET)
    public void getExcelExport(HttpServletResponse response){
        String name = DateUtil.getFriendlyFileName("CAT_Age_Group_Province_Cross_Tabulation");
        forceDownLoadDatabase(officeExportService.exportExcelXLSXFile(catLoc(), name), name, response);
    }
    
    private List<GenericReportModel> catLoc(){
        List<GenericReportModel> list = new ArrayList<>();
        String [] t = {"","10-16","17-25","26-35","35-10","50-70", "71+"};
        list.add(new GenericReportModel(Arrays.asList(t)));
        String [] r = {"Harare","0","23","0","1","3", "23"};
        list.add(new GenericReportModel(Arrays.asList(r)));
        String [] p = {"Bulawayo","4","8","2","9","0", "1"};
        list.add(new GenericReportModel(Arrays.asList(p)));
        String [] c = {"Manicaland","0","0","9","1","1", "0"};
        list.add(new GenericReportModel(Arrays.asList(c)));
        String [] d = {"Mashonaland West","5","0","1","7","1", "0"};
        list.add(new GenericReportModel(Arrays.asList(d)));
        String [] v = {"Mashonaland East","3","4","5","10","0", "0"};
        list.add(new GenericReportModel(Arrays.asList(v)));
        String [] n = {"Mashonaland Central","3","1","0","0","0", "0"};
        list.add(new GenericReportModel(Arrays.asList(n)));
        String [] b = {"Midlands","7","25","4","0","12", "1"};
        list.add(new GenericReportModel(Arrays.asList(b)));
        String [] o = {"Matebeleland South","9","30","13","10","50", "0"};
        list.add(new GenericReportModel(Arrays.asList(o)));
        String [] q = {"Matebeleland North","0","4","8","12","15", "9"};
        list.add(new GenericReportModel(Arrays.asList(q)));
        String [] l = {"Masvingo","0","0","2","8","14", "6"};
        list.add(new GenericReportModel(Arrays.asList(l)));
        return list;
    }
}
