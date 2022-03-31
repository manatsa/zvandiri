package zw.org.zvandiri.report.api.jasper.core;

import java.util.Collection;

public interface JasperSubReportDataSource<T> {
    Collection<T> getData();
}
