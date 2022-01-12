package zw.org.zvandiri.portal.web.controller.process.enum_services;


import org.springframework.stereotype.Service;
import zw.org.zvandiri.business.domain.LabTask;
import zw.org.zvandiri.business.service.LabTaskService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author manatsachinyeruse@gmail.com
 */


@Service("labTasksService")
public class LabTasksService {

    @Resource
    LabTaskService labTaskService;

    public List<LabTask> getLabTasks(){
        return labTaskService.getAll();
    }
}
