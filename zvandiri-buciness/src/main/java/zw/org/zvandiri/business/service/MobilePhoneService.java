package zw.org.zvandiri.business.service;

import org.springframework.stereotype.Service;
import zw.org.zvandiri.business.domain.Cadre;
import zw.org.zvandiri.business.domain.MobilePhone;
import zw.org.zvandiri.business.domain.Patient;


public interface MobilePhoneService {
    MobilePhone save(MobilePhone phone);
    MobilePhone getByCadre(Cadre cadre);
    MobilePhone get(String id);
}
