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

package zw.org.zvandiri.portal.util;

/**
 * @author Edward Zengeni
 * @author Judge Muzinda
 */
public enum MessageType {
    ERROR("alert alert-block alert-danger fade in"), WARNING("alert alert-warning fade in"), MESSAGE("alert alert-success fade in");

    private final String messageType;

    private MessageType(String type) {
        this.messageType = type;
    }

    public String getMessageType() {
        return messageType;
    }
}