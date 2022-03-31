package zw.org.zvandiri.portal.web.controller.process.enum_services;


import org.springframework.stereotype.Service;
import zw.org.zvandiri.business.domain.Location;
import zw.org.zvandiri.business.service.LocationService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author manatsachinyeruse@gmail.com
 */


@Service("locationsService")
public class LocationsService {

    @Resource
    LocationService locationService;

    public List<Location> getLocations() {
        return locationService.getAll();
    }
}
