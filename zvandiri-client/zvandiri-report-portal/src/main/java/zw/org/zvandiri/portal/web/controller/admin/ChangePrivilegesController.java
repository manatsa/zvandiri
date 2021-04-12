/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zw.org.zvandiri.portal.web.controller.admin;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import zw.org.zvandiri.business.domain.User;
import zw.org.zvandiri.business.service.UserRoleService;
import zw.org.zvandiri.business.service.UserService;
import zw.org.zvandiri.business.util.dto.ChangePrivilegesDTO;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.validator.UserValidator;

/**
 *
 * @author Edward Zengeni
 * @author Judge Muzinda
 */
@Controller
@RequestMapping("/admin")
public class ChangePrivilegesController extends BaseController {

    @Resource
    private UserService userService;
    @Resource
    private UserValidator userValidator;
    @Resource
    private UserRoleService userRoleService;

    @RequestMapping(method = RequestMethod.GET, value = "/changeprivileges")
    public String changeUserPrivileges(@RequestParam String id, ModelMap model) {
        User user = userService.get(id);
        ChangePrivilegesDTO userProfile = new ChangePrivilegesDTO(user);
        model.addAttribute("pageTitle", APP_PREFIX + user.getDisplayName() + " Change Privileges");
        model.addAttribute("roles", userRoleService.getAll());
        model.addAttribute("item", userProfile);
        return "admin/changePrivilegesForm";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/changeprivileges")
    public String changeUser(ModelMap model, @ModelAttribute("item") ChangePrivilegesDTO details, BindingResult result) throws Exception {
        userValidator.validateChangeprivileges(details, result);
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", APP_PREFIX+"Change Privileges");
            model.addAttribute("item", details);
            return "admin/changePrivilegesForm";
        }
        userService.save(details.getInstance(details));
        return "redirect:../admin/user/?type=1";
    }
}