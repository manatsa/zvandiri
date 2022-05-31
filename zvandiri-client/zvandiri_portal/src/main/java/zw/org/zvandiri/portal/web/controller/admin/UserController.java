/*
 * Copyright 2014 Edward Zengeni.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package zw.org.zvandiri.portal.web.controller.admin;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import zw.org.zvandiri.business.domain.User;
import zw.org.zvandiri.business.domain.util.UserLevel;
import zw.org.zvandiri.business.service.DistrictService;
import zw.org.zvandiri.business.service.ProvinceService;
import zw.org.zvandiri.business.service.UserRoleService;
import zw.org.zvandiri.business.service.UserService;
import zw.org.zvandiri.business.util.dto.ItemDeleteDTO;
import zw.org.zvandiri.business.util.dto.UserDTO;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import zw.org.zvandiri.portal.web.controller.BaseController;
import zw.org.zvandiri.portal.web.validator.UserValidator;

/**
 *
 * @author Edward Zengeni
 * @author Judge Muzinda
 * 
 */
@Controller
@RequestMapping("/admin/user")
public class UserController extends BaseController{

    @Resource
    private UserService userService;
    @Resource
    private UserValidator userValidator;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private ProvinceService provinceService;
    @Resource
    private DistrictService districtService;
    
    private String setUpModel(ModelMap model, User item) {
        model.addAttribute("pageTitle", APP_PREFIX+ "Create/ Edit User");
        model.addAttribute("roles", userRoleService.getAll());
        model.addAttribute("showProvince", Boolean.FALSE);
        model.addAttribute("showDistrict", Boolean.FALSE);
        model.addAttribute("itemDelete", "user.list?type=3");
        model.addAttribute("item", item);
        if (item.getUserLevel() != null) {
            if (item.getUserLevel().equals(UserLevel.PROVINCE)) {
                model.addAttribute("provinces", provinceService.getAll());
                model.addAttribute("showProvince", Boolean.TRUE);
            } else if (item.getUserLevel().equals(UserLevel.DISTRICT)) {
                model.addAttribute("provinces", provinceService.getAll());
                /*if (item.getId() != null) {
                    item.setProvince(item.getDistrict().getProvince());
                }*/
                model.addAttribute("showProvince", Boolean.TRUE);
                model.addAttribute("showDistrict", Boolean.TRUE);
                model.addAttribute("districts", districtService.getDistrictByProvince(item.getProvince()));
            }
        }
        model.addAttribute("itemDelete", "user.list?type=3");
        return "admin/userForm";
    }

    @RequestMapping(value = "/user.form", method = RequestMethod.GET)
    public String userForm(ModelMap model, @RequestParam(required = false) String id) {
        User p = new User();
        if (id != null) {
            p = userService.get(id);
        }        
        return setUpModel(model, p);
    }

    @RequestMapping(value = "/user.form", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("item") @Valid User user, BindingResult result, ModelMap model) {
        userValidator.validate(user, result);
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        if (result.hasErrors()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return setUpModel(model, user);
        }
        userService.save(user);
        return "redirect:user.list?type=1";
    }
    
    @RequestMapping(value = "/reload-form", method = RequestMethod.POST)
    public String reloadForm(ModelMap model, @ModelAttribute("item") @Valid User user) {
        return setUpModel(model, user);
    }

    @RequestMapping(value = {"/user.list", "/"}, method = RequestMethod.GET)
    public String userList(ModelMap model, @RequestParam(required = false) Integer type) {
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        model.addAttribute("pageTitle", APP_PREFIX+"User List");
        model.addAttribute("items", userService.getAll());
        if(type != null){
            model.addAttribute("message", getMessage(type));
        }
        return "admin/userModList";
    }
    
    @RequestMapping(value = "/search-users", method = RequestMethod.GET)
    @ResponseBody
    public List<UserDTO> searchPatient(@RequestParam("search") String search) {
        String[] names = search.split(" ");
        List<UserDTO> users=new ArrayList<>();
        try {
            users = UserDTO.getInstance(userService.searchUsers(names));
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& : " + users);
        }catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }
    
    @RequestMapping(value = "user.delete", method = RequestMethod.GET)
    public String getUserDeleteForm(@RequestParam("id") String id, ModelMap model){
        User user = userService.get(id);
       ItemDeleteDTO dto = new ItemDeleteDTO(id, user.getUserName(), "user.list?type=3");
       model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
       model.addAttribute("pageTitle", APP_PREFIX+"Delete "+user.getUserName()+" User");
        return "admin/deleteItem";
    }
    
    @RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String deleteUser(@Valid ItemDeleteDTO dto){
        userService.delete(userService.get(dto.getId()));
        return "redirect:user.list?type=2";
    }
}