/*
 * Copyright 2016 Judge Muzinda.
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
package zw.org.zvandiri.portal.web.controller.process;

import org.springframework.stereotype.Repository;
import org.springframework.validation.ObjectError;
import zw.org.zvandiri.business.domain.MentalHealthScreening;
import zw.org.zvandiri.business.service.MentalHealthScreeningService;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author manatsachinyeruse@gmail.com
 */
@Repository("mentalHealthCreationService")
public class MentalHealthScreeningCreationServiceImpl implements MentalHealthCreationService {
    
    @Resource
    private MentalHealthScreeningService mentalHealthScreeningService;

    @Override
    public MentalHealthScreening createMentalHealthScreening() {
        return new MentalHealthScreening();
    }


    @Override
    public MentalHealthScreening saveMentalHealthScreening(MentalHealthScreening mentalHealthScreening) {
        //System.err.println(mentalHealthScreening.getPatient());
        return mentalHealthScreeningService.save(mentalHealthScreening);
    }

    @Override
    public String isValid(MentalHealthScreening mentalHealthScreening){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        // now manually validating, which could be achieved by using @Valid annotation
        Set<ConstraintViolation<MentalHealthScreening>> constraintViolations = validator.validate(mentalHealthScreening);

        Iterator errorIterator=constraintViolations.iterator();
        while(errorIterator.hasNext()){
            //System.err.println(""+((ObjectError)(errorIterator.next())).getDefaultMessage());
        }
        return constraintViolations.size()<1?"success" : "failure";
    }
    
}