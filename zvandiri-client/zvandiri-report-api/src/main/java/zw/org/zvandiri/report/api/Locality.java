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
package zw.org.zvandiri.report.api;

import java.io.Serializable;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * @author Judge Muzinda
 */
public class Locality implements Serializable {

    private String province;
    private String district;
    private String facility;

    public String getProvince() {
        return sanitizeStrings(province);
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return sanitizeStrings(district);
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getFacility() {
        return sanitizeStrings(facility);
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String sanitizeStrings(String str) {
        return StringUtils.toCamelCase3(str.trim());
    }
}