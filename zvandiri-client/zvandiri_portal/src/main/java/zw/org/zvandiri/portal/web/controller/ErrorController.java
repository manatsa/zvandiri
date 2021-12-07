/*
 * Copyright 2017 User.
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

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zw.org.zvandiri.portal.util.AppMessage;
import zw.org.zvandiri.portal.util.MessageType;

/**
 *
 * @author User
 */
@Controller
public class ErrorController extends BaseController{
    
    @RequestMapping(value = "/errors", method = RequestMethod.GET)
    public String renderErrorPage(HttpServletRequest request, ModelMap map){
        String errorMsg = "";
        String pageTitle = APP_PREFIX;
        int errorCode = getErrorCode(request);
        Throwable throwable = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        final String referer = request.getHeader("referer");
        switch(errorCode){
            case 400:
                pageTitle += "User input error";
                errorMsg = "Application encountered an error due to user input:\n"+throwable.getMessage();
                break;
            case 401:
                pageTitle += "Access denied";
                errorMsg = "User is not authorised to access this resource please contact system administrator:\n"+throwable.getMessage();
                break;
            case 404:
                pageTitle += "Page/ Item not found or has expired";
                errorMsg = "Page or item being searched not found or has expired:\n"+throwable.getMessage();
                break;
            case 500:
                pageTitle += "Application/ System error";
                errorMsg = "Application encountered an internal error processing this request:\n"+throwable.getMessage();
                break;
        }
        map.addAttribute("pageTitle", pageTitle);
        map.addAttribute("referer", referer);
        map.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message(errorMsg).messageType(MessageType.ERROR).build());
        return "error";
    }
    
    public int getErrorCode(HttpServletRequest request){
        return (Integer) request.getAttribute("javax.servlet.error.status_code");
    }
}
