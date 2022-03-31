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

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.springframework.stereotype.Repository;

import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.report.api.BasicReportModel;
import zw.org.zvandiri.report.api.BasicTrendModel;
import zw.org.zvandiri.report.api.ChartModelItem;
import zw.org.zvandiri.report.api.GenericReportModel;
import zw.org.zvandiri.report.api.service.AggregateVisualReportService;
import zw.org.zvandiri.report.api.service.RandomNumGenService;

/**
 * @author Judge Muzinda
 * @version 1.0.1 patch to getItemRowByMapKey method
 */
@Repository
public class AggregateVisualReportServiceImpl implements AggregateVisualReportService {

    @Resource
    private RandomNumGenService randomNumGenService;

    @Override
    public JFreeChart getDefaultReport(ChartModelItem item, List<GenericReportModel> data, String... key) {
        if (data.size() <= 0) {
            throw new IllegalArgumentException("No data to disply in chart");
        }
        return generateChart(item, data, key);
    }

    @Override
    public JFreeChart getDefaultPieChart(ChartModelItem item, List<GenericReportModel> data, String... key) {
        if (data.size() <= 0) {
            throw new IllegalArgumentException("No data to disply in chart");
        }
        return generatePieChart(item.getChartName(), item.getxAxisName(), data, key);
    }

    @Override
    public JFreeChart getDefaultTrend(ChartModelItem item, List<GenericReportModel> data, String... key) {
        if (data.size() <= 0) {
            throw new IllegalArgumentException("No data to disply in chart");
        }
        return generateTrend(item, data, key);
    }

    @Override
    public JFreeChart getTbStatusTrend(ChartModelItem item, List<GenericReportModel> data, String... key) {
        if (data.size() <= 0) {
            throw new IllegalArgumentException("No data to disply in chart");
        }
        return generateTbStatusTrend(item, data, key);
    }

    @Override
    public JFreeChart getTbOutcomeTrend(ChartModelItem item, List<GenericReportModel> data, String... key) {
        if (data.size() <= 0) {
            throw new IllegalArgumentException("No data to disply in chart");
        }
        return generateTbOutcomeTrend(item, data, key);
    }

    @Override
    public JFreeChart getDashReport(ChartModelItem item, List<GenericReportModel> data, String... key) {
        if (data.size() <= 0) {
            throw new IllegalArgumentException("No data to disply in chart");
        }
        return generateDashChart(item, data, key);
    }

    private DefaultPieDataset createPieDataSet(List<BasicReportModel> data, String label) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (BasicReportModel temp : data) {
            dataset.setValue(temp.getName(), temp.getCount().doubleValue());
        }
        return dataset;
    }

    private CategoryDataset createDataSet(List<BasicReportModel> data, ChartModelItem item) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (BasicReportModel temp : data) {
            dataset.addValue(item.isUseCount() ? temp.getCount() : temp.getPercentage(), item.getxAxisName(), temp.getName());
        }
        return dataset;
    }

    private TimeSeriesCollection createTrendDataSet(List<BasicTrendModel> data, String label) {
        final TimeSeries serie = new TimeSeries("Monthly Rounds", Month.class);
        for (BasicTrendModel model : data) {
            serie.add(new Month(DateUtil.getMonthFromDate(model.getDate()) + 1, DateUtil.getYearFromDate(model.getDate())), model.getPercentage());
        }
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(serie);
        return dataset;
    }

    private TimeSeriesCollection createMultiTrendDataSet(List<BasicTrendModel> data, ChartModelItem item) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        final TimeSeries stable = new TimeSeries("Stable", Month.class);
        final TimeSeries enhanced = new TimeSeries("Enhanced", Month.class);
        for (BasicTrendModel model : data) {
            if (model.getName().equalsIgnoreCase("Stable")) {
                stable.add(new Month(DateUtil.getMonthFromDate(model.getDate()) + 1, DateUtil.getYearFromDate(model.getDate())), item.isUseCount() ? model.getCount() : model.getPercentage());
            } else if (model.getName().equalsIgnoreCase("Enhanced")) {
                enhanced.add(new Month(DateUtil.getMonthFromDate(model.getDate()) + 1, DateUtil.getYearFromDate(model.getDate())), item.isUseCount() ? model.getCount() : model.getPercentage());
            }
        }
        dataset.addSeries(stable);
        dataset.addSeries(enhanced);
        return dataset;
    }

    private TimeSeriesCollection createTbStatusMultiTrendDataSet(List<BasicTrendModel> data, ChartModelItem item) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        final TimeSeries active = new TimeSeries("Active On Tb Treatment", Month.class);
        final TimeSeries defaulted = new TimeSeries("Defaulted On Tb Treatment", Month.class);
        final TimeSeries completed = new TimeSeries("Completed Treatment", Month.class);
        for (BasicTrendModel model : data) {
            if (model.getName().equalsIgnoreCase("Active On Tb Treatment ")) {
                active.add(new Month(DateUtil.getMonthFromDate(model.getDate()) + 1, DateUtil.getYearFromDate(model.getDate())), item.isUseCount() ? model.getCount() : model.getPercentage());
            } else if (model.getName().equalsIgnoreCase("Defaulted On Tb Treatment ")) {
                defaulted.add(new Month(DateUtil.getMonthFromDate(model.getDate()) + 1, DateUtil.getYearFromDate(model.getDate())), item.isUseCount() ? model.getCount() : model.getPercentage());
            } else if (model.getName().equalsIgnoreCase("Completed Treatment ")) {
                completed.add(new Month(DateUtil.getMonthFromDate(model.getDate()) + 1, DateUtil.getYearFromDate(model.getDate())), item.isUseCount() ? model.getCount() : model.getPercentage());
            }
        }
        dataset.addSeries(active);
        dataset.addSeries(defaulted);
        dataset.addSeries(completed);
        return dataset;
    }

    private TimeSeriesCollection createTbOutcomeMultiTrendDataSet(List<BasicTrendModel> data, ChartModelItem item) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        final TimeSeries successful = new TimeSeries("Successful", Month.class);
        final TimeSeries failed = new TimeSeries("Failed", Month.class);
        for (BasicTrendModel model : data) {
            if (model.getName().equalsIgnoreCase("Successful")) {
                successful.add(new Month(DateUtil.getMonthFromDate(model.getDate()) + 1, DateUtil.getYearFromDate(model.getDate())), item.isUseCount() ? model.getCount() : model.getPercentage());
            } else if (model.getName().equalsIgnoreCase("Faled")) {
                failed.add(new Month(DateUtil.getMonthFromDate(model.getDate()) + 1, DateUtil.getYearFromDate(model.getDate())), item.isUseCount() ? model.getCount() : model.getPercentage());
            }
        }
        dataset.addSeries(successful);
        dataset.addSeries(failed);
        return dataset;
    }

    private Integer getItem(GenericReportModel data, int pos) {
        int outer = 0;
        for (String item : data.getRow()) {
            if (pos == outer) {
                return Integer.valueOf(item);
            }
            outer++;
        }
        throw new IllegalStateException("Item not found on selected position");
    }

    private Integer getItemCount(List<GenericReportModel> c, int pos) {
        int outer = 0;
        int count = 0;
        for (GenericReportModel data : c) {
            if (outer == 0) {
                outer++;
                continue;
            }
            int inner = 0;
            for (String item : data.getRow()) {
                if (inner == pos) {
                    count += Integer.valueOf(item);
                    break;
                }
                inner++;
            }
        }
        return count;
    }

    private List<BasicTrendModel> initialize(List<GenericReportModel> data, String... key) {
        List<BasicTrendModel> xAxis = new ArrayList<>();
        int outerPos = 0;
        /**
         * @param key get away with making key an array
         */
        List<GenericReportModel> c = new ArrayList<>();
        if (key.length == 1) {
            c = formatReportOutPut(getItemRowByMapKey(key[0], data));
        } else if (key.length > 1) {
            int pos = 0;
            for (String k : key) {
                if (pos == 0) {
                    c = formatReportOutPut(getItemRowByMapKey(k, data));
                } else if (pos >= 1) {
                    c = createMultiOptionTable(c, formatReportOutPut(getItemRowByMapKey(k, data)).get(1));
                }
                pos++;
            }
        }
        for (GenericReportModel model : c) {
            if (outerPos == 0) {
                int innerPos = 0;
                for (String item : model.getRow()) {
                    xAxis.add(new BasicTrendModel(DateUtil.getDateFromString(item), getItem(c.get(1), innerPos), getItemCount(formatReportOutPut(data), innerPos), "Yes"));
                    innerPos++;
                }
            }
            outerPos++;
            if (outerPos >= 1) {
                break;
            }
        }
        return xAxis;
    }

    private List<GenericReportModel> createMultiOptionTable(List<GenericReportModel> curr, GenericReportModel item) {
        int pos = 0;
        List<GenericReportModel> newlist = new ArrayList<>();
        Integer count = 0;
        for (GenericReportModel model : curr) {
            if (pos == 0) {
                newlist.add(model);
                pos++;
                continue;
            }
            List<String> row = new ArrayList<>();
            int innerPos = 0;
            for (String i : model.getRow()) {
                count = Integer.valueOf(i);
                count += Integer.valueOf(item.getRow().get(innerPos));
                row.add(count.toString());
                innerPos++;
            }
            newlist.add(new GenericReportModel(row));
        }
        return newlist;
    }

    private List<BasicReportModel> initializeArr(List<GenericReportModel> data, String... key) {
        List<BasicReportModel> xAxis = new ArrayList<>();
        int outerPos = 0;
        /**
         * @param key get away with making key an array
         */
        List<GenericReportModel> c = new ArrayList<>();
        if (key.length == 1) {
            c = formatReportOutPut(getItemRowByMapKey(key[0], data));
        } else if (key.length > 1) {
            int pos = 0;
            for (String k : key) {
                if (pos == 0) {
                    c = formatReportOutPut(getItemRowByMapKey(k, data));
                } else if (pos >= 1) {
                    c = createMultiOptionTable(c, formatReportOutPut(getItemRowByMapKey(k, data)).get(1));
                }
                pos++;
            }
        }
        for (GenericReportModel model : c) {
            if (outerPos == 0) {
                int innerPos = 0;
                for (String item : model.getRow()) {
                    xAxis.add(new BasicReportModel(item, getItem(c.get(1), innerPos), getItemCount(formatReportOutPut(data), innerPos)));
                    innerPos++;
                }
            }
            outerPos++;
            if (outerPos >= 1) {
                break;
            }
        }
        return xAxis;
    }

    private JFreeChart generatePieChart(String chartLabel, String domainAxisLabel, List<GenericReportModel> data, String... key) {
        JFreeChart chart = ChartFactory.createPieChart(
                chartLabel,
                createPieDataSet(initializeArr(data, key), domainAxisLabel),
                true,
                true,
                false);
        chart.setTitle(
                new org.jfree.chart.title.TextTitle(chartLabel,
                        new java.awt.Font("SansSerif", java.awt.Font.BOLD, 12)
                )
        );
        return chart;
    }

    private JFreeChart generateChart(ChartModelItem item, List<GenericReportModel> data, String... key) {
        final JFreeChart chart = ChartFactory.createBarChart3D(
                item.getChartName(),
                "",
                item.getyAxisName(),
                createDataSet(initializeArr(data, key), item),
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        chart.setBackgroundPaint(Color.white);
        final CategoryPlot plot = chart.getCategoryPlot();
        //plot.setBackgroundPaint(Color.lightGray);
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.BASELINE_CENTER));
        renderer.setBaseItemLabelsVisible(true);
        renderer.setMaximumBarWidth(.05);
        renderer.setItemLabelPaint(Color.WHITE);
        renderer.setItemLabelFont(new Font("Dialog", Font.BOLD, 10));
        renderer.setSeriesPaint(0, getColor());
        final CategoryAxis domainAxis = plot.getDomainAxis();
        ValueAxis xAxis = plot.getRangeAxis();
        xAxis.setRange(0.0, 105.0);
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 4.5)
        );
        return chart;
    }

    private JFreeChart generateTrend(ChartModelItem item, List<GenericReportModel> data, String... key) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                item.getChartName(),
                item.getyAxisName(),
                item.getxAxisName(),
                createMultiTrendDataSet(initializeMupliple(data, key), item),
                true,
                true,
                false);
        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        ValueAxis xAxis = plot.getRangeAxis();
        xAxis.setRange(0.0, item.getTopValue());
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
            renderer.setItemLabelFont(new Font("Dialog", Font.BOLD, 8));
        }
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setLabelFont(new Font("Dialog", Font.PLAIN, 12));
        axis.setTickLabelFont(new Font("Dialog", Font.PLAIN, 10));
        axis.setDateFormatOverride(new SimpleDateFormat("MMM-yy"));
        return chart;
    }

    private JFreeChart generateTbStatusTrend(ChartModelItem item, List<GenericReportModel> data, String... key) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                item.getChartName(),
                item.getyAxisName(),
                item.getxAxisName(),
                createTbStatusMultiTrendDataSet(initializeMupliple(data, key), item),
                true,
                true,
                false);
        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        ValueAxis xAxis = plot.getRangeAxis();
        xAxis.setRange(0.0, item.getTopValue());
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
            renderer.setItemLabelFont(new Font("Dialog", Font.BOLD, 8));
        }
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setLabelFont(new Font("Dialog", Font.PLAIN, 12));
        axis.setTickLabelFont(new Font("Dialog", Font.PLAIN, 10));
        axis.setDateFormatOverride(new SimpleDateFormat("MMM-yy"));
        return chart;
    }

    private JFreeChart generateTbOutcomeTrend(ChartModelItem item, List<GenericReportModel> data, String... key) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                item.getChartName(),
                item.getyAxisName(),
                item.getxAxisName(),
                createTbOutcomeMultiTrendDataSet(initializeMupliple(data, key), item),
                true,
                true,
                false);
        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        ValueAxis xAxis = plot.getRangeAxis();
        xAxis.setRange(0.0, item.getTopValue());
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
            renderer.setItemLabelFont(new Font("Dialog", Font.BOLD, 8));
        }
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setLabelFont(new Font("Dialog", Font.PLAIN, 12));
        axis.setTickLabelFont(new Font("Dialog", Font.PLAIN, 10));
        axis.setDateFormatOverride(new SimpleDateFormat("MMM-yy"));
        return chart;
    }

    private JFreeChart generateDashChart(ChartModelItem item, List<GenericReportModel> data, String... key) {
        final JFreeChart chart = ChartFactory.createBarChart3D(
                "",
                "",
                "",
                createDataSet(initializeArr(data, key), item),
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        chart.setBackgroundPaint(Color.white);
        final CategoryPlot plot = chart.getCategoryPlot();
        //plot.setBackgroundPaint(Color.lightGray);
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.BASELINE_CENTER));
        renderer.setBaseItemLabelsVisible(true);
        renderer.setMaximumBarWidth(.05);
        renderer.setItemLabelPaint(Color.WHITE);
        renderer.setItemLabelFont(new Font("Dialog", Font.BOLD, 8));
        renderer.setSeriesPaint(0, getColor());
        final CategoryAxis domainAxis = plot.getDomainAxis();
        ValueAxis xAxis = plot.getRangeAxis();
        xAxis.setTickLabelFont(new Font("Dialog", Font.BOLD, 8));
        xAxis.setRange(0.0, item.getTopValue());
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 4.5)
        );
        domainAxis.setTickLabelFont(new Font("Dialog", Font.PLAIN, 10));
        return chart;
    }

    private List<GenericReportModel> formatReportOutPut(List<GenericReportModel> model) {
        List<GenericReportModel> items = new ArrayList<>();
        for (GenericReportModel m : model) {
            List<String> item = new ArrayList<>();
            int pos = 0;
            for (String s : m.getRow()) {
                if (pos != 0 && pos != m.getRow().size() - 1) {
                    item.add(s);
                }
                pos++;
            }
            items.add(new GenericReportModel(item));
        }
        return items;
    }

    private List<GenericReportModel> getItemRowByMapKey(String key, List<GenericReportModel> data) {
        int outerPos = 0;
        List<GenericReportModel> items = new ArrayList<>();
        for (GenericReportModel model : data) {
            if (outerPos == 0) {
                items.add(model);
            }
            int innerPos = 0;
            for (String item : model.getRow()) {
                if (innerPos != 0) {
                    continue;
                }
                if (key.equalsIgnoreCase(item) && outerPos != 0) {
                    items.add(model);
                    return items;
                }
            }
            outerPos++;
        }
        throw new IllegalArgumentException("Error occurred item not found :" + key);
    }

    @Override
    public List<BasicTrendModel> initializeMupliple(List<GenericReportModel> data, String... key) {
        List<BasicTrendModel> xAxis = new ArrayList<>();
        int outerPos = 0;
        /**
         * @param key get away with making key an array
         */
        List<GenericReportModel> c = formatReportOutPut(data);
        int currentItemCount = 1;
        for (GenericReportModel model : c) {
            if (outerPos < c.size() - 1) {
                int innerPos = 0;
                for (String item : model.getRow()) {
                    xAxis.add(new BasicTrendModel(DateUtil.getDateFromString(c.get(0).getRow().get(innerPos)), getItem(c.get(currentItemCount), innerPos), getItemCount(formatReportOutPut(data), innerPos), data.get(currentItemCount).getRow().get(0)));
                    innerPos++;
                }
            }
            outerPos++;
            currentItemCount++;
        }
        return xAxis;
    }

    private Color getColor() {
        Integer num = randomNumGenService.getRandNum(0, 1);
        return num == 0 ? Color.BLUE : Color.GREEN;
    }
}
