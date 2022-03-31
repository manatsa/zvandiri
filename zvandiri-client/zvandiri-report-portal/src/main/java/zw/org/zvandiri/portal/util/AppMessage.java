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
 */
public class AppMessage {

    private final String message;
    private final MessageType messageType;
    private final Boolean exist;

    private AppMessage(MessageBuilder builder) {
        this.message = builder.message;
        this.messageType = builder.messageType;
        this.exist = builder.exist;
    }

    public static class MessageBuilder {

        private final Boolean exist;
        private MessageType messageType = MessageType.MESSAGE;
        private String message = "";

        public MessageBuilder() {
            this.exist = Boolean.FALSE;
        }

        public MessageBuilder(Boolean exist) {
            this.exist = exist;
        }

        public MessageBuilder messageType(MessageType messageType) {
            this.messageType = messageType;
            return this;
        }

        public MessageBuilder message(String message) {
            this.message = message;
            return this;
        }

        public AppMessage build() {
            return new AppMessage(this);
        }

    }

    public Boolean getExist() {
        return exist;
    }

    public String getMessage() {
        return message;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return message;
    }

    public String getMsgType() {
        return messageType.getMessageType();
    }

    public static AppMessage getMessage(Integer type) {
        switch (type) {
            case 1:
                return new AppMessage.MessageBuilder(Boolean.TRUE).message("Record saved").messageType(MessageType.MESSAGE).build();
            case 2:
                return new AppMessage.MessageBuilder(Boolean.TRUE).message("Record deleted").messageType(MessageType.MESSAGE).build();
            case 3:
                return new AppMessage.MessageBuilder(Boolean.TRUE).message("Operation cancelled").messageType(MessageType.MESSAGE).build();
            case 4:
                return new AppMessage.MessageBuilder(Boolean.TRUE).message("Password successfully changed").messageType(MessageType.MESSAGE).build();
            case 5:
                return new AppMessage.MessageBuilder(Boolean.TRUE).message("Erro occurred").messageType(MessageType.ERROR).build();
            default:
                throw new IllegalArgumentException("Parameter provided not recognised :" + type);
        }
    }
}