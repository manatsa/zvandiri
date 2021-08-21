package zw.org.zvandiri.business.service;

import zw.org.zvandiri.business.domain.Bicycle;
import zw.org.zvandiri.business.domain.Patient;

public interface BicycleService {
    Bicycle save(Bicycle bicycle);
    Bicycle getByPatient(Patient patient);
}
