package zw.org.zvandiri.business.service.impl;

import org.springframework.stereotype.Service;
import zw.org.zvandiri.business.domain.Cadre;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.service.CadreReportService;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.Reportutil;
import zw.org.zvandiri.business.util.dto.SearchDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class CadreReportServiceImpl implements CadreReportService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Cadre> get(SearchDTO dto) {

        StringBuilder builder = Reportutil.commonQueryString(dto);

        builder.append(" order by p.lastName ASC");

        TypedQuery<Cadre> query = entityManager.createQuery(builder.toString(), Cadre.class);
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
        query.setFirstResult(dto.getFirstResult());
        query.setMaxResults(dto.getPageSize());
        return query.getResultList();
    }

    @Override
    public Long getCount(SearchDTO dto) {
        StringBuilder builder = Reportutil.commonQueryCount(dto);
        TypedQuery<Long> query = entityManager.createQuery(builder.toString(), Long.class);
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

}
