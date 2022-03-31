/*
 * Copyright 2015 Edward Zengeni.
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
package zw.org.zvandiri.portal.web.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Edward Zengeni
 */
public class PasswordValidator {
    private final Pattern pattern;
    private Matcher matcher;
    /*
     * (            # Start of group
     (?=.*\d)       #   must contains one digit from 0-9
     (?=.*[a-z])    #   must contains one at least lowercase character
     (?=.*[A-Z])    #   must contain at least one uppercase character
     (?=.*[@#$%])   #   must contains one special symbols in the list "@#$%" -- removed
     .              #   match anything with previous condition checking
     {5,100}         #   length at least 5 characters and maximum of 100	
     )              #   End of group
     */
//    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,100})";

    public PasswordValidator() {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    /**
     * Validate password with regular expression
     *
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public boolean validate(final String password) {
        if (password == null || password.trim().equals("")) {
            return false;
        }
        matcher = pattern.matcher(password);
        return matcher.matches();

    }
}
