package zw.org.zvandiri.portal.web.controller.process.enum_services;


import org.springframework.stereotype.Service;
import zw.org.zvandiri.business.domain.Position;
import zw.org.zvandiri.business.service.PositionService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author manatsachinyeruse@gmail.com
 */


@Service("positionsService")
public class PositionsService {

    @Resource
    PositionService positionService;

    public List<Position> getPositions() {
        return positionService.getAll();
    }
}
