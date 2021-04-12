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

/**
 *
 * @author Judge Muzinda
 */
public class BasicReportModel extends BasicReportItem {
    
    private final String name;

    public BasicReportModel(String name, Integer count, Integer total) {
        super(count, total);
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public String getAltName(){
        return name+" [N="+getCount()+"]";
    }
    
    public String getPlainName(){
        return name+" [N="+getTotal()+"]";
    }

    @Override
    public String toString() {
        return "BasicReportModel{" + "name=" + name + ", count=" + getCount() + ", total=" + getCount() + '}';
    }   
}