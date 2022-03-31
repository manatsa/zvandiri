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
package zw.org.zvandiri.report.api.service;

import java.util.List;

import org.jfree.chart.JFreeChart;
import zw.org.zvandiri.report.api.BasicTrendModel;
import zw.org.zvandiri.report.api.ChartModelItem;
import zw.org.zvandiri.report.api.GenericReportModel;

/**
 * @author Judge Muzinda
 */
public interface AggregateVisualReportService {

    public JFreeChart getDefaultReport(ChartModelItem item, List<GenericReportModel> data, String... key);

    public JFreeChart getDefaultPieChart(ChartModelItem item, List<GenericReportModel> data, String... key);

    public JFreeChart getDefaultTrend(ChartModelItem item, List<GenericReportModel> data, String... key);

    public JFreeChart getTbStatusTrend(ChartModelItem item, List<GenericReportModel> data, String... key);

    public JFreeChart getTbOutcomeTrend(ChartModelItem item, List<GenericReportModel> data, String... key);

    public JFreeChart getDashReport(ChartModelItem item, List<GenericReportModel> data, String... key);

    public List<BasicTrendModel> initializeMupliple(List<GenericReportModel> data, String... key);
}
