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
package zw.org.zvandiri.portal.util;

import java.io.Serializable;

/**
 * @author Edward Zengeni
 */
public final class MobileNumberFormat implements Serializable {

    public static String ZIMBABWE = "\\d{10}";

    private MobileNumberFormat() {
        throw new IllegalStateException("Class not meant to be initialized");
    }
}
