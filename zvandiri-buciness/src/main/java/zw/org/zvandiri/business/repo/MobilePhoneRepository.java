package zw.org.zvandiri.business.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.org.zvandiri.business.domain.MobilePhone;
import zw.org.zvandiri.business.domain.Patient;

public interface MobilePhoneRepository extends JpaRepository<MobilePhone, String> {
    MobilePhone getByPatient(Patient patient);
}