package zw.org.zvandiri.business.service.impl;

import org.springframework.stereotype.Service;
import zw.org.zvandiri.business.service.LastContactedService;
import zw.org.zvandiri.business.service.impl.parallel.LastContactedDTOTask;
import zw.org.zvandiri.business.util.dto.LastContactedDTO;
import zw.org.zvandiri.business.util.dto.SearchDTO;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

@Service
public class LastContactedServiceImpl implements LastContactedService {

    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public List<LastContactedDTO> get(SearchDTO dto) {
        List<LastContactedDTO> udto = new ArrayList<>();
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PATIENTLASTCONTACT");

        //Declare the parameters in the same order
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);


        //Pass the parameter values

        query.setParameter(1, dto.getProvince()!=null ? dto.getProvince().getId(): "");
        query.setParameter(2, dto.getDistrict() !=null ? dto.getDistrict().getId() : "");
        query.setParameter(3, dto.getPrimaryClinic()!=null ? dto.getPrimaryClinic().getId() : "");


//        //Execute query
//        query.execute();
//
//        //Get output parameters
//        Integer outCode = (Integer) query.getOutputParameterValue(3);
//        String outMessage = (String) query.getOutputParameterValue(4);

        List<Object[]> results=query.getResultList();
        ForkJoinPool forkJoinPool=ForkJoinPool.commonPool();
        List<LastContactedDTO> list=forkJoinPool.invoke(new LastContactedDTOTask(results));

//        System.err.println("<><><><><><><><><><><><> Last Contacted Items "+ list.size());
//        System.err.println("<><><><><><><><><><><><> Last Contacted Item 1 "+ list.get(0).toString());


        return list;
    }
}
