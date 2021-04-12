/*
 * Copyright 2015 Judge Muzinda.
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
package zw.org.zvandiri.portal.web.controller;

import javax.servlet.http.HttpSession;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Judge Muzinda
 */
@Controller
public class IndexController extends BaseController {

    @RequestMapping(value = "/login")
    public String getLogin(ModelMap model) {
        model.addAttribute("pageTitle", APP_PREFIX+"Login");
        return "login";
    }

    @RequestMapping(value = "/loginfailed")
    public String getloginFailed() {
        return "redirect:loginerror";
    }

    @RequestMapping(value = "/loginerror", method = RequestMethod.GET)
    public String getRedirectError(ModelMap model) {
        model.addAttribute("pageTitle", APP_PREFIX+"Access Denied");
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Error worng username/ password").messageType(MessageType.ERROR).build());
        return "login";
    }

    @RequestMapping(value = "/sessiontimeout", method = RequestMethod.GET)
    public String sessionTimeout(ModelMap model) {
        model.addAttribute("pageTitle", APP_PREFIX+"Session Timed Out");
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Session has timed out, please login again").messageType(MessageType.ERROR).build());
        return "login";
    }
    
    @RequestMapping(value = "/success")
    public String getSuccess(ModelMap model) {
        model.addAttribute("message", APP_PREFIX+"Access accepted");
        return "index";
    }

    @RequestMapping(value = "/logout")
    public String logout(ModelMap model, HttpSession httpSession) {
        model.addAttribute("message", APP_PREFIX+"Access Failed");
        httpSession.invalidate();
        return "redirect:login";
    } 
}