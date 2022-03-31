package zw.org.zvandiri.portal.web.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import zw.org.zvandiri.business.domain.Cadre;

/**
 * @author manatsachinyeruse@gmail.com
 */


@Component
public class CadreValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        System.err.println("Classname :: " + aClass.getName());
        return Cadre.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Cadre item = (Cadre) o;

        ValidationUtils.rejectIfEmpty(errors, "firstName", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "mobileNumber", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "gender", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "address", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "dateOfBirth", "field.empty");


    }
}
