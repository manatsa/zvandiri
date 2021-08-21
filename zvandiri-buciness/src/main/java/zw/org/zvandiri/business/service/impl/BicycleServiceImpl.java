package zw.org.zvandiri.business.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import zw.org.zvandiri.business.repo.BicycleRepo;
import zw.org.zvandiri.business.domain.Bicycle;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.service.BicycleService;
import zw.org.zvandiri.business.service.UserService;
import zw.org.zvandiri.business.util.UUIDGen;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class BicycleServiceImpl implements BicycleService {

    @Resource
    BicycleRepo bicycleRepo;
    @Resource
    UserService userService;

    @Override
    public Bicycle save(Bicycle t) {
        if (t.getId() == null || StringUtils.isBlank(t.getId())) {
            t.setId(UUIDGen.generateUUID());
            t.setCreatedBy(userService.getCurrentUser());
            t.setDateCreated(new Date());
            return bicycleRepo.save(t);
        }
        t.setModifiedBy(userService.getCurrentUser());
        t.setDateModified(new Date());
        return bicycleRepo.save(t);
    }

    @Override
    public Bicycle getByPatient(Patient patient) {
        if(patient!=null)
            return bicycleRepo.getByPatient(patient);
        throw new IllegalArgumentException("The value of Patient is not allowed to be null!");
    }
}
