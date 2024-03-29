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
package zw.org.zvandiri.business.domain;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import zw.org.zvandiri.business.domain.util.ReferalType;

/**
 *
 * @author Judge Muzinda
 */
@Entity @JsonIgnoreProperties(ignoreUnknown = true)
public class ServicesReferred extends BaseName {

    @Enumerated
    private ReferalType referalType;
    
    public ServicesReferred() {
    }

    public ServicesReferred(String id) {
        super(id);
    }

    public ReferalType getReferalType() {
        return referalType;
    }

    public void setReferalType(ReferalType referalType) {
        this.referalType = referalType;
    }
    
}
