package zw.org.zvandiri.report.api.jasper.core;

import java.util.Collection;
import java.util.Date;

public class JasperReportDataSource<T> {

    private String name;

    private Date reportDate;

    private String layoutTemplateUrl;

    private String templateUrl;

    private Collection<JasperSubReportDataSource<T>> data;

   public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getLayoutTemplateUrl() {
        return layoutTemplateUrl;
    }

    public void setLayoutTemplateUrl(String layoutTemplateUrl) {
        this.layoutTemplateUrl = layoutTemplateUrl;
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    public Collection<JasperSubReportDataSource<T>> getData() {
        return data;
    }

    public void setData(Collection<JasperSubReportDataSource<T>> data) {
        this.data = data;
    }

}