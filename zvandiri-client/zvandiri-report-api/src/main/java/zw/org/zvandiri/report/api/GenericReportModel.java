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
import java.util.List;

/**
 * @author Judge Muzinda
 * @version 1.00
 * Magical 2D data structure to represent any tabular data ready for std.out
 */
public final class GenericReportModel implements Serializable {

    private List<String> row;

    public GenericReportModel() {
    }

    public GenericReportModel(List<String> row) {
        this.row = row;
    }

    public List<String> getRow() {
        return row;
    }

    public void setRow(List<String> row) {
        this.row = row;
    }

    public Boolean noData(List<?> items) {
        if (items.size() <= 0) return Boolean.TRUE;
        return Boolean.FALSE;
    }

    @Override
    public String toString() {
        return "GenericReportModel{" + "row=" + row + '}';
    }
}