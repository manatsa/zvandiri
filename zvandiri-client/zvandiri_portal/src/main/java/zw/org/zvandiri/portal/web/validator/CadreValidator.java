package zw.org.zvandiri.portal.web.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import zw.org.zvandiri.business.domain.Cadre;
import zw.org.zvandiri.business.domain.Contact;
import zw.org.zvandiri.business.domain.User;
import zw.org.zvandiri.business.domain.util.ContactPhoneOption;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.service.UserService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author manatsachinyeruse@gmail.com
 */

@Component
public class CadreValidator implements Validator {

    @Resource
    UserService userService;
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(Cadre.class);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Cadre item=(Cadre)o;

        ValidationUtils.rejectIfEmpty(errors,"firstName","field.empty");
        ValidationUtils.rejectIfEmpty(errors,"lastName","field.empty");
        ValidationUtils.rejectIfEmpty(errors,"gender","field.empty");
        ValidationUtils.rejectIfEmpty(errors,"address","field.empty");
        ValidationUtils.rejectIfEmpty(errors,"dateOfBirth","field.empty");
        ValidationUtils.rejectIfEmpty(errors,"primaryClinic","field.empty");
        ValidationUtils.rejectIfEmpty(errors,"caderType","field.empty");
        ValidationUtils.rejectIfEmpty(errors,"district","field.empty");
        ValidationUtils.rejectIfEmpty(errors,"province","field.empty");
        ValidationUtils.rejectIfEmpty(errors,"status","field.empty");
        ValidationUtils.rejectIfEmpty(errors,"active","field.empty");

        if(item.getDateOfBirth()!=null && item.getAge()<12){
            errors.rejectValue("dateOfBirth","cadre.too.young");
        }


        if(errors.hasErrors()){
            User user=userService.getCurrentUser();
            System.err.println(" *** UserName : "+user.getUserName()+", FirstName : "+user.getFirstName()+", LastName : "+user.getLastName()+
                    ", District : "+user.getDistrict()+", Province : "+user.getProvince()+"\n"+errors);
        }
    }
}
