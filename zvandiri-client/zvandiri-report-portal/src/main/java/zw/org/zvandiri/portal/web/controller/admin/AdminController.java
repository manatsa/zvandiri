package zw.org.zvandiri.portal.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.portal.web.controller.BaseController;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String showIndex(ModelMap model) {
        model.addAttribute("pageTitle", APP_PREFIX+"Administration Page Home");
        return "admin/index";
    }
}