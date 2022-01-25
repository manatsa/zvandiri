package zw.org.zvandiri.portal.web.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
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
public class ContactsValidator implements Validator {

    @Resource
    UserService userService;
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(Contact.class);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Contact item=(Contact)o;

        ValidationUtils.rejectIfEmpty(errors,"contactDate","field.empty");
        ValidationUtils.rejectIfEmpty(errors,"careLevel","field.empty");
        ValidationUtils.rejectIfEmpty(errors,"nextClinicAppointmentDate","field.empty");
        ValidationUtils.rejectIfEmpty(errors,"careLevelAfterAssessment","field.empty");
        ValidationUtils.rejectIfEmpty(errors,"location","field.empty");
        ValidationUtils.rejectIfEmpty(errors,"lastClinicAppointmentDate","field.empty");
        ValidationUtils.rejectIfEmpty(errors,"attendedClinicAppointment","field.empty");
        ValidationUtils.rejectIfEmpty(errors,"eac","field.empty");
        ValidationUtils.rejectIfEmpty(errors,"contactMadeBy","field.empty");

        if(item.getContactDate()!=null && item.getContactDate().before(item.getPatient().getDateOfBirth())){
            errors.rejectValue("contactDate","date.beforebirth");
        }
        if(item.getContactDate()!=null && item.getContactDate().after(new Date())){
            errors.rejectValue("contactDate","date.to.be.past");
        }
        if(item.getLastClinicAppointmentDate()!=null && item.getLastClinicAppointmentDate().before(item.getPatient().getDateOfBirth())){
            errors.rejectValue("lastClinicAppointmentDate","date.beforebirth");
        }
        if(item.getLastClinicAppointmentDate()!=null && item.getLastClinicAppointmentDate().after(new Date())){
            errors.rejectValue("lastClinicAppointmentDate","date.to.be.past");
        }
        if(item.getNextClinicAppointmentDate()!=null && item.getNextClinicAppointmentDate().before(item.getPatient().getDateOfBirth())){
            errors.rejectValue("nextClinicAppointmentDate","date.beforebirth");
        }
        if(item.getNextClinicAppointmentDate()!=null && item.getNextClinicAppointmentDate().before(new Date())){
            errors.rejectValue("nextClinicAppointmentDate","date.to.be.future");
        }

        if (item.getLocation() != null) {
            if (item.getLocation().getName().equalsIgnoreCase("Phone")) {
                if (item.getContactPhoneOption() == null) {
                    errors.rejectValue("contactPhoneOption", "field.empty");
                }
                if (item.getContactPhoneOption() != null && item.getContactPhoneOption().equals(ContactPhoneOption.SMS)) {
                    if (item.getNumberOfSms() == null || item.getNumberOfSms() == 0) {
                        errors.rejectValue("numberOfSms", "field.empty");
                    }
                }
            }
        }

        if(item.getEac()!=null && item.getEac().equals(YesNo.YES)){
            if(item.getEac1()==null && item.getEac2()==null && item.getEac3()==null){
                errors.rejectValue("eac","at.least.one.eac");
            }
        }

        if(errors.hasErrors()){
            User user=userService.getCurrentUser();
            System.err.println(" *** UserName : "+user.getUserName()+", FirstName : "+user.getFirstName()+", LastName : "+user.getLastName()+
                    ", District : "+user.getDistrict()+", Province : "+user.getProvince()+"\n"+errors);
        }
    }
}
