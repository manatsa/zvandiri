package zw.org.zvandiri.business.service;

import zw.org.zvandiri.business.util.dto.LastContactedDTO;
import zw.org.zvandiri.business.util.dto.SearchDTO;

import java.util.List;

public interface LastContactedService {

    public List<LastContactedDTO> get(SearchDTO dto);
}
