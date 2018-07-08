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
package zw.org.zvandiri.business.service.impl;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.HIVStatus;
import zw.org.zvandiri.business.service.PatientReportService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.PatientInnerJoin;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.business.util.dto.SearchNationalDTO;

/**
 *
 * @author Judge Muzinda
 */
@Repository
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class PatientReportServiceImpl implements PatientReportService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Integer get(SearchNationalDTO dto, HIVStatus hivStatus) {
        if (dto.getFacility() != null) {
            return 0;
        } else if (dto.getDistrict() != null) {
            return 0;
        } else if (dto.getProvince() != null) {
            return 0;
        }
        return 0;
    }

    @Override
    public Long getCount(SearchDTO dto) {
        StringBuilder builder = new StringBuilder("Select count(p) from Patient p");
        int position = 0;
        builder.append(" where ");
        if (dto.getProvince() != null) {
            if (position == 0) {
                builder.append("p.primaryClinic.district.province=:province");
                position++;
            } else {
                builder.append(" and p.primaryClinic.district.province=:province");
            }
        }
        if (dto.getDistrict() != null) {
            if (position == 0) {
                builder.append("p.primaryClinic.district=:district");
                position++;
            } else {
                builder.append(" and p.primaryClinic.district=:district");
            }

        }
        if (dto.getPrimaryClinic() != null) {
            if (position == 0) {
                builder.append("p.primaryClinic=:primaryClinic");
                position++;
            } else {
                builder.append(" and p.primaryClinic=:primaryClinic");
            }
        }
        if (dto.getSupportGroup() != null) {
            if (position == 0) {
                builder.append("p.supportGroup=:supportGroup");
                position++;
            } else {
                builder.append(" and p.supportGroup=:supportGroup");
            }
        }
        if (dto.getGender() != null) {
            if (position == 0) {
                builder.append("p.gender=:gender");
                position++;
            } else {
                builder.append(" and p.gender=:gender");
            }
        }
        if (dto.getAgeGroup() != null) {
            if (position == 0) {
                builder.append("p.dateOfBirth between :start and :end");
                position++;
            } else {
                builder.append(" and p.dateOfBirth between :start and :end");
            }
        }
        if (dto.getPeriod() != null) {
            if (position == 0) {
                builder.append("p.period=:period");
                position++;
            } else {
                builder.append(" and p.period=:period");
            }
        }
        if (dto.getStatus() != null) {
            if (position == 0) {
                builder.append("p.status=:status");
                position++;
            } else {
                builder.append(" and p.status=:status");
            }
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            if (position == 0) {
                builder.append("p.dateJoined between :startDate and :endDate");
                position++;
            } else {
                builder.append(" and (p.dateJoined between :startDate and :endDate)");
            }
        }
        TypedQuery query = entityManager.createQuery(builder.toString(), Long.class);
        if (dto.getProvince() != null) {
            query.setParameter("province", dto.getProvince());
        }
        if (dto.getDistrict() != null) {
            query.setParameter("district", dto.getDistrict());
        }
        if (dto.getPrimaryClinic() != null) {
            query.setParameter("primaryClinic", dto.getPrimaryClinic());
        }
        if (dto.getSupportGroup() != null) {
            query.setParameter("supportGroup", dto.getSupportGroup());
        }
        if (dto.getGender() != null) {
            query.setParameter("gender", dto.getGender());
        }
        if (dto.getAgeGroup() != null) {
            query.setParameter("start", DateUtil.getDateFromAge(dto.getAgeGroup().getEnd()));
            query.setParameter("end", DateUtil.getEndDate(dto.getAgeGroup().getStart()));
        }
        if (dto.getPeriod() != null) {
            query.setParameter("period", dto.getPeriod());
        }
        if (dto.getStatus() != null) {
            query.setParameter("status", dto.getStatus());
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            query.setParameter("startDate", dto.getStartDate());
            query.setParameter("endDate", dto.getEndDate());
        }
        return (Long) query.getSingleResult();
    }

    @Override
    public Long getNewlyRegistered(SearchDTO dto) {
        dto.setEndDate(new Date());
        StringBuilder builder = new StringBuilder("Select count(p) from Patient p");
        int position = 0;
        builder.append(" where ");
        if (dto.getProvince() != null) {
            if (position == 0) {
                builder.append("p.primaryClinic.district.province=:province");
                position++;
            } else {
                builder.append(" and p.primaryClinic.district.province=:province");
            }
        }
        if (dto.getDistrict() != null) {
            if (position == 0) {
                builder.append("p.primaryClinic.district=:district");
                position++;
            } else {
                builder.append(" and p.primaryClinic.district=:district");
            }
        }
        if (dto.getPrimaryClinic() != null) {
            if (position == 0) {
                builder.append("p.primaryClinic=:primaryClinic");
                position++;
            } else {
                builder.append(" and p.primaryClinic=:primaryClinic");
            }
        }
        if (dto.getSupportGroup() != null) {
            if (position == 0) {
                builder.append("p.supportGroup=:supportGroup");
                position++;
            } else {
                builder.append(" and p.supportGroup=:supportGroup");
            }
        }
        if (dto.getGender() != null) {
            if (position == 0) {
                builder.append("p.gender=:gender");
                position++;
            } else {
                builder.append(" and p.gender=:gender");
            }
        }
        if (dto.getAgeGroup() != null) {
            if (position == 0) {
                builder.append("p.dateOfBirth between :start and :end");
                position++;
            } else {
                builder.append(" and p.dateOfBirth between :start and :end");
            }
        }
        if (dto.getPeriod() != null) {
            if (position == 0) {
                builder.append("p.period=:period");
                position++;
            } else {
                builder.append(" and p.period=:period");
            }
        }
        if (dto.getStatus() != null) {
            if (position == 0) {
                builder.append("p.status=:status");
                position++;
            } else {
                builder.append(" and p.status=:status");
            }
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            if (position == 0) {
                builder.append("p.dateJoined between :startDate and :endDate");
                position++;
            } else {
                builder.append(" and (p.dateJoined between :startDate and :endDate)");
            }
        }

        TypedQuery query = entityManager.createQuery(builder.toString(), Long.class);
        if (dto.getProvince() != null) {
            query.setParameter("province", dto.getProvince());
        }
        if (dto.getDistrict() != null) {
            query.setParameter("district", dto.getDistrict());
        }
        if (dto.getPrimaryClinic() != null) {
            query.setParameter("primaryClinic", dto.getPrimaryClinic());
        }
        if (dto.getSupportGroup() != null) {
            query.setParameter("supportGroup", dto.getSupportGroup());
        }
        if (dto.getGender() != null) {
            query.setParameter("gender", dto.getGender());
        }
        if (dto.getAgeGroup() != null) {
            query.setParameter("start", DateUtil.getDateFromAge(dto.getAgeGroup().getEnd()));
            query.setParameter("end", DateUtil.getEndDate(dto.getAgeGroup().getStart()));
        }
        if (dto.getPeriod() != null) {
            query.setParameter("period", dto.getPeriod());
        }
        if (dto.getStatus() != null) {
            query.setParameter("status", dto.getStatus());
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            query.setParameter("startDate", dto.getStartDate());
            query.setParameter("endDate", dto.getEndDate());
        }
        return (Long) query.getSingleResult();
    }

    @Override
    public Long getPatientWithNoContact(SearchDTO dto) {
        StringBuilder builder = new StringBuilder("Select count(p) from Patient p");
        int position = 0;
        builder.append(" where ");
        if (dto.getProvince() != null) {
            if (position == 0) {
                builder.append("p.primaryClinic.district.province=:province");
                position++;
            } else {
                builder.append(" and p.primaryClinic.district.province=:province");
            }
        }
        if (dto.getDistrict() != null) {
            if (position == 0) {
                builder.append("p.primaryClinic.district=:district");
                position++;
            } else {
                builder.append(" and p.primaryClinic.district=:district");
            }
        }
        if (dto.getPrimaryClinic() != null) {
            if (position == 0) {
                builder.append("p.primaryClinic=:primaryClinic");
                position++;
            } else {
                builder.append(" and p.primaryClinic=:primaryClinic");
            }
        }
        if (dto.getSupportGroup() != null) {
            if (position == 0) {
                builder.append("p.supportGroup=:supportGroup");
                position++;
            } else {
                builder.append(" and p.supportGroup=:supportGroup");
            }
        }
        if (dto.getGender() != null) {
            if (position == 0) {
                builder.append("p.gender=:gender");
                position++;
            } else {
                builder.append(" and p.gender=:gender");
            }
        }
        if (dto.getAgeGroup() != null) {
            if (position == 0) {
                builder.append("p.dateOfBirth between :start and :end");
                position++;
            } else {
                builder.append(" and p.dateOfBirth between :start and :end");
            }
        }
        if (dto.getPeriod() != null) {
            if (position == 0) {
                builder.append("p.period=:period");
                position++;
            } else {
                builder.append(" and p.period=:period");
            }
        }
        if (dto.getStatus() != null) {
            if (position == 0) {
                builder.append("p.status=:status");
                position++;
            } else {
                builder.append(" and p.status=:status");
            }
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            if (position == 0) {
                builder.append("p not in (Select c.patient From Contact c where c.contactDate between :startDate and :endDate)");
                position++;
            } else {
                builder.append(" and p not in (Select c.patient From Contact c where c.contactDate between :startDate and :endDate)");
            }
        }

        TypedQuery query = entityManager.createQuery(builder.toString(), Long.class);
        if (dto.getProvince() != null) {
            query.setParameter("province", dto.getProvince());
        }
        if (dto.getDistrict() != null) {
            query.setParameter("district", dto.getDistrict());
        }
        if (dto.getPrimaryClinic() != null) {
            query.setParameter("primaryClinic", dto.getPrimaryClinic());
        }
        if (dto.getSupportGroup() != null) {
            query.setParameter("supportGroup", dto.getSupportGroup());
        }
        if (dto.getGender() != null) {
            query.setParameter("gender", dto.getGender());
        }
        if (dto.getAgeGroup() != null) {
            query.setParameter("start", DateUtil.getDateFromAge(dto.getAgeGroup().getEnd()));
            query.setParameter("end", DateUtil.getEndDate(dto.getAgeGroup().getStart()));
        }
        if (dto.getPeriod() != null) {
            query.setParameter("period", dto.getPeriod());
        }
        if (dto.getStatus() != null) {
            query.setParameter("status", dto.getStatus());
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            query.setParameter("startDate", dto.getStartDate());
            query.setParameter("endDate", dto.getEndDate());
        }
        return (Long) query.getSingleResult();
    }

    @Override
    public Long getPatientAboutToGraduate(SearchDTO dto) {
        StringBuilder builder = new StringBuilder("Select count(p) from Patient p");
        int position = 0;
        builder.append(" where ");
        if (dto.getProvince() != null) {
            if (position == 0) {
                builder.append("p.primaryClinic.district.province=:province");
                position++;
            } else {
                builder.append(" and p.primaryClinic.district.province=:province");
            }
        }
        if (dto.getDistrict() != null) {
            if (position == 0) {
                builder.append("p.primaryClinic.district=:district");
                position++;
            } else {
                builder.append(" and p.primaryClinic.district=:district");
            }
        }
        if (dto.getPrimaryClinic() != null) {
            if (position == 0) {
                builder.append("p.primaryClinic=:primaryClinic");
                position++;
            } else {
                builder.append(" and p.primaryClinic=:primaryClinic");
            }
        }
        if (dto.getSupportGroup() != null) {
            if (position == 0) {
                builder.append("p.supportGroup=:supportGroup");
                position++;
            } else {
                builder.append(" and p.supportGroup=:supportGroup");
            }
        }
        if (dto.getGender() != null) {
            if (position == 0) {
                builder.append("p.gender=:gender");
                position++;
            } else {
                builder.append(" and p.gender=:gender");
            }
        }
        if (dto.getStatus() != null) {
            if (position == 0) {
                builder.append("p.status=:status");
                position++;
            } else {
                builder.append(" and p.status=:status");
            }
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            if (position == 0) {
                builder.append("p.dateOfBirth between :startDate and :endDate");
                position++;
            } else {
                builder.append(" and p.dateOfBirth between :startDate and :endDate");
            }
        }

        TypedQuery query = entityManager.createQuery(builder.toString(), Long.class);
        if (dto.getProvince() != null) {
            query.setParameter("province", dto.getProvince());
        }
        if (dto.getDistrict() != null) {
            query.setParameter("district", dto.getDistrict());
        }
        if (dto.getPrimaryClinic() != null) {
            query.setParameter("primaryClinic", dto.getPrimaryClinic());
        }
        if (dto.getSupportGroup() != null) {
            query.setParameter("supportGroup", dto.getSupportGroup());
        }
        if (dto.getGender() != null) {
            query.setParameter("gender", dto.getGender());
        }
        if (dto.getStatus() != null) {
            query.setParameter("status", dto.getStatus());
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            query.setParameter("startDate", dto.getStartDate());
            query.setParameter("endDate", dto.getEndDate());
        }
        return (Long) query.getSingleResult();
    }

    @Override
    public List<Patient> getPatientWithNoContactList(SearchDTO dto) {
        StringBuilder builder = new StringBuilder("Select Distinct p from Patient p "+PatientInnerJoin.PATIENT_INNER_JOIN);
        int position = 0;
        builder.append(" where ");
        if (dto.getProvince() != null) {
            if (position == 0) {
                builder.append("p.primaryClinic.district.province=:province");
                position++;
            } else {
                builder.append(" and p.primaryClinic.district.province=:province");
            }
        }
        if (dto.getDistrict() != null) {
            if (position == 0) {
                builder.append("p.primaryClinic.district=:district");
                position++;
            } else {
                builder.append(" and p.primaryClinic.district=:district");
            }
        }
        if (dto.getPrimaryClinic() != null) {
            if (position == 0) {
                builder.append("p.primaryClinic=:primaryClinic");
                position++;
            } else {
                builder.append(" and p.primaryClinic=:primaryClinic");
            }
        }
        if (dto.getSupportGroup() != null) {
            if (position == 0) {
                builder.append("p.supportGroup=:supportGroup");
                position++;
            } else {
                builder.append(" and p.supportGroup=:supportGroup");
            }
        }
        if (dto.getGender() != null) {
            if (position == 0) {
                builder.append("p.gender=:gender");
                position++;
            } else {
                builder.append(" and p.gender=:gender");
            }
        }
        if (dto.getAgeGroup() != null) {
            if (position == 0) {
                builder.append("p.dateOfBirth between :start and :end");
                position++;
            } else {
                builder.append(" and p.dateOfBirth between :start and :end");
            }
        }
        if (dto.getPeriod() != null) {
            if (position == 0) {
                builder.append("p.period=:period");
                position++;
            } else {
                builder.append(" and p.period=:period");
            }
        }
        if (dto.getStatus() != null) {
            if (position == 0) {
                builder.append("p.status=:status");
                position++;
            } else {
                builder.append(" and p.status=:status");
            }
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            if (position == 0) {
                builder.append("p not in (Select c.patient From Contact c where c.contactDate between :startDate and :endDate)");
                position++;
            } else {
                builder.append(" and p not in (Select c.patient From Contact c where c.contactDate between :startDate and :endDate)");
            }
        }

        TypedQuery query = entityManager.createQuery(builder.toString(), Patient.class);
        if (dto.getProvince() != null) {
            query.setParameter("province", dto.getProvince());
        }
        if (dto.getDistrict() != null) {
            query.setParameter("district", dto.getDistrict());
        }
        if (dto.getPrimaryClinic() != null) {
            query.setParameter("primaryClinic", dto.getPrimaryClinic());
        }
        if (dto.getSupportGroup() != null) {
            query.setParameter("supportGroup", dto.getSupportGroup());
        }
        if (dto.getGender() != null) {
            query.setParameter("gender", dto.getGender());
        }
        if (dto.getAgeGroup() != null) {
            query.setParameter("start", DateUtil.getDateFromAge(dto.getAgeGroup().getEnd()));
            query.setParameter("end", DateUtil.getEndDate(dto.getAgeGroup().getStart()));
        }
        if (dto.getPeriod() != null) {
            query.setParameter("period", dto.getPeriod());
        }
        if (dto.getStatus() != null) {
            query.setParameter("status", dto.getStatus());
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            query.setParameter("startDate", dto.getStartDate());
            query.setParameter("endDate", dto.getEndDate());
        }
        return query.getResultList();
    }

    @Override
    public List<Patient> getPatientAboutToGraduateList(SearchDTO dto) {
        StringBuilder builder = new StringBuilder("Select Distinct p from Patient p "+PatientInnerJoin.PATIENT_INNER_JOIN);
        int position = 0;
        builder.append(" where ");
        if (dto.getProvince() != null) {
            if (position == 0) {
                builder.append("p.primaryClinic.district.province=:province");
                position++;
            } else {
                builder.append(" and p.primaryClinic.district.province=:province");
            }
        }
        if (dto.getDistrict() != null) {
            if (position == 0) {
                builder.append("p.primaryClinic.district=:district");
                position++;
            } else {
                builder.append(" and p.primaryClinic.district=:district");
            }
        }
        if (dto.getPrimaryClinic() != null) {
            if (position == 0) {
                builder.append("p.primaryClinic=:primaryClinic");
                position++;
            } else {
                builder.append(" and p.primaryClinic=:primaryClinic");
            }
        }
        if (dto.getSupportGroup() != null) {
            if (position == 0) {
                builder.append("p.supportGroup=:supportGroup");
                position++;
            } else {
                builder.append(" and p.supportGroup=:supportGroup");
            }
        }
        if (dto.getGender() != null) {
            if (position == 0) {
                builder.append("p.gender=:gender");
                position++;
            } else {
                builder.append(" and p.gender=:gender");
            }
        }
        if (dto.getStatus() != null) {
            if (position == 0) {
                builder.append("p.status=:status");
                position++;
            } else {
                builder.append(" and p.status=:status");
            }
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            if (position == 0) {
                builder.append("p.dateOfBirth between :startDate and :endDate");
                position++;
            } else {
                builder.append(" and p.dateOfBirth between :startDate and :endDate");
            }
        }

        TypedQuery query = entityManager.createQuery(builder.toString(), Patient.class);
        if (dto.getProvince() != null) {
            query.setParameter("province", dto.getProvince());
        }
        if (dto.getDistrict() != null) {
            query.setParameter("district", dto.getDistrict());
        }
        if (dto.getPrimaryClinic() != null) {
            query.setParameter("primaryClinic", dto.getPrimaryClinic());
        }
        if (dto.getSupportGroup() != null) {
            query.setParameter("supportGroup", dto.getSupportGroup());
        }
        if (dto.getGender() != null) {
            query.setParameter("gender", dto.getGender());
        }
        if (dto.getStatus() != null) {
            query.setParameter("status", dto.getStatus());
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            query.setParameter("startDate", dto.getStartDate());
            query.setParameter("endDate", dto.getEndDate());
        }
        return query.getResultList();
    }

    @Override
    public Long getPatientLabResults(SearchDTO dto) {
        StringBuilder builder = new StringBuilder("Select count(Distinct v) from InvestigationTest v");
        int position = 0;
        builder.append(" where ");
        if (dto.getMaxViralLoad()!= null) {
            if (position == 0) {
                builder.append("v.result >:result");
                position++;
            } else {
                builder.append(" and v.result >:result");
            }
        }
        if (dto.getProvince() != null) {
            if (position == 0) {
                builder.append("v.patient.primaryClinic.district.province=:province");
                position++;
            } else {
                builder.append(" and v.patient.primaryClinic.district.province=:province");
            }
        }
        if (dto.getDistrict() != null) {
            if (position == 0) {
                builder.append("v.patient.primaryClinic.district=:district");
                position++;
            } else {
                builder.append(" and v.patient.primaryClinic.district=:district");
            }

        }
        if (dto.getPrimaryClinic() != null) {
            if (position == 0) {
                builder.append("v.patient.primaryClinic=:primaryClinic");
                position++;
            } else {
                builder.append(" and v.patient.primaryClinic=:primaryClinic");
            }
        }
        if (dto.getSupportGroup() != null) {
            if (position == 0) {
                builder.append("v.patient.supportGroup=:supportGroup");
                position++;
            } else {
                builder.append(" and v.patient.supportGroup=:supportGroup");
            }
        }
        if (dto.getGender() != null) {
            if (position == 0) {
                builder.append("v.patient..gender=:gender");
                position++;
            } else {
                builder.append(" and v.patient.gender=:gender");
            }
        }
        if (dto.getAgeGroup() != null) {
            if (position == 0) {
                builder.append("v.patient.dateOfBirth between :start and :end");
                position++;
            } else {
                builder.append(" and v.patient.dateOfBirth between :start and :end");
            }
        }
        if (dto.getPeriod() != null) {
            if (position == 0) {
                builder.append("v.patient.period=:period");
                position++;
            } else {
                builder.append(" and v.patient.period=:period");
            }
        }
        if (dto.getStatus() != null) {
            if (position == 0) {
                builder.append("v.patient.status=:status");
                position++;
            } else {
                builder.append(" and v.patient.status=:status");
            }
        }
        if (dto.getTestType() != null) {
            if (position == 0) {
                builder.append("v.testType=:testType");
                position++;
            } else {
                builder.append(" and v.testType=:testType");
            }
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            if (position == 0) {
                builder.append("v.patient.dateJoined between :startDate and :endDate");
                position++;
            } else {
                builder.append(" and (v.patient.dateJoined between :startDate and :endDate)");
            }
        }
        builder.append(" order by v.dateTaken DESC");
        TypedQuery query = entityManager.createQuery(builder.toString(), Long.class);
        if (dto.getProvince() != null) {
            query.setParameter("province", dto.getProvince());
        }
        if (dto.getDistrict() != null) {
            query.setParameter("district", dto.getDistrict());
        }
        if (dto.getPrimaryClinic() != null) {
            query.setParameter("primaryClinic", dto.getPrimaryClinic());
        }
        if (dto.getSupportGroup() != null) {
            query.setParameter("supportGroup", dto.getSupportGroup());
        }
        if (dto.getGender() != null) {
            query.setParameter("gender", dto.getGender());
        }
        if (dto.getAgeGroup() != null) {
            query.setParameter("start", DateUtil.getDateFromAge(dto.getAgeGroup().getEnd()));
            query.setParameter("end", DateUtil.getEndDate(dto.getAgeGroup().getStart()));
        }
        if (dto.getPeriod() != null) {
            query.setParameter("period", dto.getPeriod());
        }
        if (dto.getStatus() != null) {
            query.setParameter("status", dto.getStatus());
        }
        if (dto.getTestType() != null) {
            query.setParameter("testType", dto.getTestType());
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            query.setParameter("startDate", dto.getStartDate());
            query.setParameter("endDate", dto.getEndDate());
        }
        if(dto.getMaxViralLoad() != null){
            query.setParameter("result", dto.getMaxViralLoad());
        }
        return (Long) query.getSingleResult();
    }

    @Override
    public List<Patient> getPatientWithViralLoadList(SearchDTO dto) {
        StringBuilder builder = new StringBuilder("Select Distinct v.patient from ViralLoad v");
        int position = 0;
        builder.append(" where ");
        if (dto.getMaxViralLoad()!= null) {
            if (position == 0) {
                builder.append("v.result >:result");
                position++;
            } else {
                builder.append(" and v.result >:result");
            }
        }
        if (dto.getProvince() != null) {
            if (position == 0) {
                builder.append("v.patient.primaryClinic.district.province=:province");
                position++;
            } else {
                builder.append(" and v.patient.primaryClinic.district.province=:province");
            }
        }
        if (dto.getDistrict() != null) {
            if (position == 0) {
                builder.append("v.patient.primaryClinic.district=:district");
                position++;
            } else {
                builder.append(" and v.patient.primaryClinic.district=:district");
            }

        }
        if (dto.getPrimaryClinic() != null) {
            if (position == 0) {
                builder.append("v.patient.primaryClinic=:primaryClinic");
                position++;
            } else {
                builder.append(" and v.patient.primaryClinic=:primaryClinic");
            }
        }
        if (dto.getSupportGroup() != null) {
            if (position == 0) {
                builder.append("v.patient.supportGroup=:supportGroup");
                position++;
            } else {
                builder.append(" and v.patient.supportGroup=:supportGroup");
            }
        }
        if (dto.getGender() != null) {
            if (position == 0) {
                builder.append("v.patient..gender=:gender");
                position++;
            } else {
                builder.append(" and v.patient.gender=:gender");
            }
        }
        if (dto.getAgeGroup() != null) {
            if (position == 0) {
                builder.append("v.patient.dateOfBirth between :start and :end");
                position++;
            } else {
                builder.append(" and v.patient.dateOfBirth between :start and :end");
            }
        }
        if (dto.getPeriod() != null) {
            if (position == 0) {
                builder.append("v.patient.period=:period");
                position++;
            } else {
                builder.append(" and v.patient.period=:period");
            }
        }
        if (dto.getStatus() != null) {
            if (position == 0) {
                builder.append("v.patient.status=:status");
                position++;
            } else {
                builder.append(" and v.patient.status=:status");
            }
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            if (position == 0) {
                builder.append("v.patient.dateJoined between :startDate and :endDate");
                position++;
            } else {
                builder.append(" and (v.patient.dateJoined between :startDate and :endDate)");
            }
        }
        //builder.append(" order by v.dateTaken DESC");        
        TypedQuery query = entityManager.createQuery(builder.toString(), Patient.class);
        if (dto.getProvince() != null) {
            query.setParameter("province", dto.getProvince());
        }
        if (dto.getDistrict() != null) {
            query.setParameter("district", dto.getDistrict());
        }
        if (dto.getPrimaryClinic() != null) {
            query.setParameter("primaryClinic", dto.getPrimaryClinic());
        }
        if (dto.getSupportGroup() != null) {
            query.setParameter("supportGroup", dto.getSupportGroup());
        }
        if (dto.getGender() != null) {
            query.setParameter("gender", dto.getGender());
        }
        if (dto.getAgeGroup() != null) {
            query.setParameter("start", DateUtil.getDateFromAge(dto.getAgeGroup().getEnd()));
            query.setParameter("end", DateUtil.getEndDate(dto.getAgeGroup().getStart()));
        }
        if (dto.getPeriod() != null) {
            query.setParameter("period", dto.getPeriod());
        }
        if (dto.getStatus() != null) {
            query.setParameter("status", dto.getStatus());
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            query.setParameter("startDate", dto.getStartDate());
            query.setParameter("endDate", dto.getEndDate());
        }
        if(dto.getMaxViralLoad() != null){
            query.setParameter("result", dto.getMaxViralLoad());
        }
        return query.getResultList();
    }

    @Override
    public Long getPatientWithCd4Count(SearchDTO dto) {
        StringBuilder builder = new StringBuilder("Select count(Distinct v.patient) from CD4Count v");
        int position = 0;
        builder.append(" where ");
        if (dto.getMinCd4Count()!= null) {
            if (position == 0) {
                builder.append("v.result <:result");
                position++;
            } else {
                builder.append(" and v.result <:result");
            }
        }
        if (dto.getProvince() != null) {
            if (position == 0) {
                builder.append("v.patient.primaryClinic.district.province=:province");
                position++;
            } else {
                builder.append(" and v.patient.primaryClinic.district.province=:province");
            }
        }
        if (dto.getDistrict() != null) {
            if (position == 0) {
                builder.append("v.patient.primaryClinic.district=:district");
                position++;
            } else {
                builder.append(" and v.patient.primaryClinic.district=:district");
            }

        }
        if (dto.getPrimaryClinic() != null) {
            if (position == 0) {
                builder.append("v.patient.primaryClinic=:primaryClinic");
                position++;
            } else {
                builder.append(" and v.patient.primaryClinic=:primaryClinic");
            }
        }
        if (dto.getSupportGroup() != null) {
            if (position == 0) {
                builder.append("v.patient.supportGroup=:supportGroup");
                position++;
            } else {
                builder.append(" and v.patient.supportGroup=:supportGroup");
            }
        }
        if (dto.getGender() != null) {
            if (position == 0) {
                builder.append("v.patient..gender=:gender");
                position++;
            } else {
                builder.append(" and v.patient.gender=:gender");
            }
        }
        if (dto.getAgeGroup() != null) {
            if (position == 0) {
                builder.append("v.patient.dateOfBirth between :start and :end");
                position++;
            } else {
                builder.append(" and v.patient.dateOfBirth between :start and :end");
            }
        }
        if (dto.getPeriod() != null) {
            if (position == 0) {
                builder.append("v.patient.period=:period");
                position++;
            } else {
                builder.append(" and v.patient.period=:period");
            }
        }
        if (dto.getStatus() != null) {
            if (position == 0) {
                builder.append("v.patient.status=:status");
                position++;
            } else {
                builder.append(" and v.patient.status=:status");
            }
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            if (position == 0) {
                builder.append("v.patient.dateJoined between :startDate and :endDate");
                position++;
            } else {
                builder.append(" and (v.patient.dateJoined between :startDate and :endDate)");
            }
        }
        builder.append(" order by v.dateTaken DESC");
        TypedQuery query = entityManager.createQuery(builder.toString(), Long.class);
        if (dto.getProvince() != null) {
            query.setParameter("province", dto.getProvince());
        }
        if (dto.getDistrict() != null) {
            query.setParameter("district", dto.getDistrict());
        }
        if (dto.getPrimaryClinic() != null) {
            query.setParameter("primaryClinic", dto.getPrimaryClinic());
        }
        if (dto.getSupportGroup() != null) {
            query.setParameter("supportGroup", dto.getSupportGroup());
        }
        if (dto.getGender() != null) {
            query.setParameter("gender", dto.getGender());
        }
        if (dto.getAgeGroup() != null) {
            query.setParameter("start", DateUtil.getDateFromAge(dto.getAgeGroup().getEnd()));
            query.setParameter("end", DateUtil.getEndDate(dto.getAgeGroup().getStart()));
        }
        if (dto.getPeriod() != null) {
            query.setParameter("period", dto.getPeriod());
        }
        if (dto.getStatus() != null) {
            query.setParameter("status", dto.getStatus());
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            query.setParameter("startDate", dto.getStartDate());
            query.setParameter("endDate", dto.getEndDate());
        }
        if(dto.getMinCd4Count()!= null){
            query.setParameter("result", dto.getMinCd4Count());
        }
        return (Long) query.getSingleResult();
    }

    @Override
    public List<Patient> getPatientWithCd4CountList(SearchDTO dto) {
        StringBuilder builder = new StringBuilder("Select Distinct v.patient from CD4Count v");
        int position = 0;
        builder.append(" where ");
        if (dto.getMinCd4Count()!= null) {
            if (position == 0) {
                builder.append("v.result <:result");
                position++;
            } else {
                builder.append(" and v.result <:result");
            }
        }
        if (dto.getProvince() != null) {
            if (position == 0) {
                builder.append("v.patient.primaryClinic.district.province=:province");
                position++;
            } else {
                builder.append(" and v.patient.primaryClinic.district.province=:province");
            }
        }
        if (dto.getDistrict() != null) {
            if (position == 0) {
                builder.append("v.patient.primaryClinic.district=:district");
                position++;
            } else {
                builder.append(" and v.patient.primaryClinic.district=:district");
            }

        }
        if (dto.getPrimaryClinic() != null) {
            if (position == 0) {
                builder.append("v.patient.primaryClinic=:primaryClinic");
                position++;
            } else {
                builder.append(" and v.patient.primaryClinic=:primaryClinic");
            }
        }
        if (dto.getSupportGroup() != null) {
            if (position == 0) {
                builder.append("v.patient.supportGroup=:supportGroup");
                position++;
            } else {
                builder.append(" and v.patient.supportGroup=:supportGroup");
            }
        }
        if (dto.getGender() != null) {
            if (position == 0) {
                builder.append("v.patient..gender=:gender");
                position++;
            } else {
                builder.append(" and v.patient.gender=:gender");
            }
        }
        if (dto.getAgeGroup() != null) {
            if (position == 0) {
                builder.append("v.patient.dateOfBirth between :start and :end");
                position++;
            } else {
                builder.append(" and v.patient.dateOfBirth between :start and :end");
            }
        }
        if (dto.getPeriod() != null) {
            if (position == 0) {
                builder.append("v.patient.period=:period");
                position++;
            } else {
                builder.append(" and v.patient.period=:period");
            }
        }
        if (dto.getStatus() != null) {
            if (position == 0) {
                builder.append("v.patient.status=:status");
                position++;
            } else {
                builder.append(" and v.patient.status=:status");
            }
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            if (position == 0) {
                builder.append("v.patient.dateJoined between :startDate and :endDate");
                position++;
            } else {
                builder.append(" and (v.patient.dateJoined between :startDate and :endDate)");
            }
        }
        //builder.append(" order by v.dateTaken DESC");
        TypedQuery query = entityManager.createQuery(builder.toString(), Patient.class);
        if (dto.getProvince() != null) {
            query.setParameter("province", dto.getProvince());
        }
        if (dto.getDistrict() != null) {
            query.setParameter("district", dto.getDistrict());
        }
        if (dto.getPrimaryClinic() != null) {
            query.setParameter("primaryClinic", dto.getPrimaryClinic());
        }
        if (dto.getSupportGroup() != null) {
            query.setParameter("supportGroup", dto.getSupportGroup());
        }
        if (dto.getGender() != null) {
            query.setParameter("gender", dto.getGender());
        }
        if (dto.getAgeGroup() != null) {
            query.setParameter("start", DateUtil.getDateFromAge(dto.getAgeGroup().getEnd()));
            query.setParameter("end", DateUtil.getEndDate(dto.getAgeGroup().getStart()));
        }
        if (dto.getPeriod() != null) {
            query.setParameter("period", dto.getPeriod());
        }
        if (dto.getStatus() != null) {
            query.setParameter("status", dto.getStatus());
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            query.setParameter("startDate", dto.getStartDate());
            query.setParameter("endDate", dto.getEndDate());
        }
        if(dto.getMinCd4Count()!= null){
            query.setParameter("result", dto.getMinCd4Count());
        }
        return query.getResultList();
    }
    
    @Override
    public Long getPatientWithContact(SearchDTO dto) {
        StringBuilder builder = new StringBuilder("Select count(p) from Patient p");
        int position = 0;
        builder.append(" where ");
        if (dto.getProvince() != null) {
            if (position == 0) {
                builder.append("p.primaryClinic.district.province=:province");
                position++;
            } else {
                builder.append(" and p.primaryClinic.district.province=:province");
            }
        }
        if (dto.getDistrict() != null) {
            if (position == 0) {
                builder.append("p.primaryClinic.district=:district");
                position++;
            } else {
                builder.append(" and p.primaryClinic.district=:district");
            }
        }
        if (dto.getPrimaryClinic() != null) {
            if (position == 0) {
                builder.append("p.primaryClinic=:primaryClinic");
                position++;
            } else {
                builder.append(" and p.primaryClinic=:primaryClinic");
            }
        }
        if (dto.getSupportGroup() != null) {
            if (position == 0) {
                builder.append("p.supportGroup=:supportGroup");
                position++;
            } else {
                builder.append(" and p.supportGroup=:supportGroup");
            }
        }
        if (dto.getGender() != null) {
            if (position == 0) {
                builder.append("p.gender=:gender");
                position++;
            } else {
                builder.append(" and p.gender=:gender");
            }
        }
        if (dto.getAgeGroup() != null) {
            if (position == 0) {
                builder.append("p.dateOfBirth between :start and :end");
                position++;
            } else {
                builder.append(" and p.dateOfBirth between :start and :end");
            }
        }
        if (dto.getPeriod() != null) {
            if (position == 0) {
                builder.append("p.period=:period");
                position++;
            } else {
                builder.append(" and p.period=:period");
            }
        }
        if (dto.getStatus() != null) {
            if (position == 0) {
                builder.append("p.status=:status");
                position++;
            } else {
                builder.append(" and p.status=:status");
            }
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            if (position == 0) {
                builder.append("p in (Select c.patient From Contact c where c.contactDate between :startDate and :endDate)");
                position++;
            } else {
                builder.append(" and p in (Select c.patient From Contact c where c.contactDate between :startDate and :endDate)");
            }
        }

        TypedQuery query = entityManager.createQuery(builder.toString(), Long.class);
        if (dto.getProvince() != null) {
            query.setParameter("province", dto.getProvince());
        }
        if (dto.getDistrict() != null) {
            query.setParameter("district", dto.getDistrict());
        }
        if (dto.getPrimaryClinic() != null) {
            query.setParameter("primaryClinic", dto.getPrimaryClinic());
        }
        if (dto.getSupportGroup() != null) {
            query.setParameter("supportGroup", dto.getSupportGroup());
        }
        if (dto.getGender() != null) {
            query.setParameter("gender", dto.getGender());
        }
        if (dto.getAgeGroup() != null) {
            query.setParameter("start", DateUtil.getDateFromAge(dto.getAgeGroup().getEnd()));
            query.setParameter("end", DateUtil.getEndDate(dto.getAgeGroup().getStart()));
        }
        if (dto.getPeriod() != null) {
            query.setParameter("period", dto.getPeriod());
        }
        if (dto.getStatus() != null) {
            query.setParameter("status", dto.getStatus());
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            query.setParameter("startDate", dto.getStartDate());
            query.setParameter("endDate", dto.getEndDate());
        }
        return (Long) query.getSingleResult();
    }
}