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
package zw.org.zvandiri.report.api;

import java.io.Serializable;

/**
 * @author Judge Muzinda
 */
public final class ChartModelItem implements Serializable {

    private final String chartName;
    private final String xAxisName;
    private final String yAxisName;
    private double topValue = 105.0;
    private boolean useCount = false;

    public ChartModelItem(String chartName, String xAxisName, String yAxisName) {
        this.chartName = chartName;
        this.xAxisName = xAxisName;
        this.yAxisName = yAxisName;
    }

    public ChartModelItem(String chartName, String xAxisName, String yAxisName, double topValue, boolean useCount) {
        this.chartName = chartName;
        this.xAxisName = xAxisName;
        this.yAxisName = yAxisName;
        this.topValue = topValue;
        this.useCount = useCount;
    }


    public String getChartName() {
        return chartName;
    }

    public String getxAxisName() {
        return xAxisName;
    }

    public String getyAxisName() {
        return yAxisName.isEmpty() ? "Percent" : yAxisName;
    }

    public double getTopValue() {
        return topValue;
    }

    public void setTopValue(double topValue) {
        this.topValue = topValue;
    }

    public boolean isUseCount() {
        return useCount;
    }

    public void setUseCount(boolean useCount) {
        this.useCount = useCount;
    }
}