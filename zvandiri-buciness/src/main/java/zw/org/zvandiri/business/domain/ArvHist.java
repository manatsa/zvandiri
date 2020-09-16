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

import java.util.Date;
import javax.persistence.Entity; import org.codehaus.jackson.annotate.JsonIgnoreProperties;;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Judge Muzinda
 */
@Entity @JsonIgnoreProperties(ignoreUnknown = true)
public class ArvHist extends BaseEntity {

    @ManyToOne
    @JsonIgnore
    private Patient patient;
    @ManyToOne
    private ArvMedicine arvMedicine;
    @ManyToOne
    private ArvMedicine arvMedicine2;
    @ManyToOne
    private ArvMedicine arvMedicine3;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date startDate;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date endDate;
    @Transient
    private String medicines;

    public ArvHist() {
    }

    public ArvHist(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public ArvMedicine getArvMedicine() {
        return arvMedicine;
    }

    public void setArvMedicine(ArvMedicine arvMedicine) {
        this.arvMedicine = arvMedicine;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

	public ArvMedicine getArvMedicine2() {
		return arvMedicine2;
	}

	public void setArvMedicine2(ArvMedicine arvMedicine2) {
		this.arvMedicine2 = arvMedicine2;
	}

	public ArvMedicine getArvMedicine3() {
		return arvMedicine3;
	}

	public void setArvMedicine3(ArvMedicine arvMedicine3) {
		this.arvMedicine3 = arvMedicine3;
	}
	
	public String getMedicines() {
		if (arvMedicine != null && StringUtils.isNotBlank(arvMedicine.getName())) {
			medicines = arvMedicine.getName();
		}
		if (arvMedicine2 != null && StringUtils.isNotBlank(arvMedicine2.getName())) {
			medicines += " + " + arvMedicine2.getName();
		}
		if (arvMedicine3 != null && StringUtils.isNotBlank(arvMedicine3.getName())) {
			medicines += " + " + arvMedicine3.getName();
		}
		return medicines;
	}
    
}
