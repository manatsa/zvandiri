package zw.org.zvandiri.portal.web.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import zw.org.zvandiri.business.domain.InvestigationTest;
import zw.org.zvandiri.business.domain.util.YesNo;

import java.util.Date;

/**
 * @author manatsachinyeruse@gmail.com
 */

@Component
public class LabResultValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(InvestigationTest.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        InvestigationTest item=(InvestigationTest)o;
        ValidationUtils.rejectIfEmpty(errors, "testDone", "field.empty");

        if(item.getTestDone()!=null && item.getTestDone().equals(YesNo.YES))
        {
            ValidationUtils.rejectIfEmpty(errors,"dateTaken", "field.empty");
            ValidationUtils.rejectIfEmpty(errors,"testType", "field.empty");
            ValidationUtils.rejectIfEmpty(errors,"source", "field.empty");
            ValidationUtils.rejectIfEmpty(errors,"nextTestDate", "field.empty");
            ValidationUtils.rejectIfEmpty(errors,"haveResult", "field.empty");

        }

        if(item.getHaveResult()!=null && item.getHaveResult().equals(YesNo.YES)) {
            if (item.getResult() != null && item.getTnd() != null && item.getTnd() != "") {
                errors.rejectValue("result", "tnd.viralload.both");
                errors.rejectValue("tnd", "tnd.viralload.both");
            }
            if(item.getResult()==null && (item.getTnd()==null || item.getTnd()=="")){
                errors.rejectValue("result", "tnd.viralload.missing");
                errors.rejectValue("tnd", "tnd.viralload.missing");
            }
        }


        if(item.getNextTestDate() != null && item.getNextTestDate().before(new Date())){
            errors.rejectValue("nextTestDate","date.to.be.future");
        }

        if (item.getDateTaken() != null && item.getDateTaken().after(new Date())) {
            errors.rejectValue("dateTaken", "date.to.be.past");
        }
        if (item.getDateTaken() != null && item.getPatient().getDateOfBirth() != null && item.getDateTaken().before(item.getPatient().getDateOfBirth())) {
            errors.rejectValue("dateTaken", "date.beforebirth");
        }


        /*ValidationUtils.invokeValidator(this,item,errors);*/
        /*for(ObjectError error: errors.getAllErrors()){
            System.err.println("Error : "+error.getDefaultMessage());
        }*/
    }
}
