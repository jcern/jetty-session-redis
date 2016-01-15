/**
 * Copyright (C) 2011 Ovea <dev@ovea.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ovea.jetty.session.serializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.ovea.jetty.session.SerializerException;

import java.io.IOException;


/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class JsonSerializer extends SerializerSkeleton {

    private ObjectMapper mapper;

    @Override
    public void start() {
        mapper = new ObjectMapper();

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, false);
//        mapper.configure(SerializationFeature.AUTO_DETECT_GETTERS, false);
//        mapper.configure(SerializationFeature.AUTO_DETECT_IS_GETTERS, false);
//        mapper.configure(SerializationFeature.AUTO_DETECT_FIELDS, true);
//        mapper.configure(SerializationFeature.CAN_OVERRIDE_ACCESS_MODIFIERS, true);
//        mapper.configure(SerializationFeature.USE_STATIC_TYPING, false);
        mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, false);
//        mapper.configure(SerializationFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
//        mapper.configure(SerializationFeature.USE_ANNOTATIONS, true);
//        mapper.configure(DeserializationFeature.USE_ANNOTATIONS, true);
//        mapper.configure(DeserializationFeature.AUTO_DETECT_SETTERS, false);
//        mapper.configure(DeserializationFeature.AUTO_DETECT_CREATORS, true);
//        mapper.configure(DeserializationFeature.AUTO_DETECT_FIELDS, true);
//        mapper.configure(DeserializationFeature.USE_GETTERS_AS_SETTERS, false);
//        mapper.configure(DeserializationFeature.CAN_OVERRIDE_ACCESS_MODIFIERS, true);
        mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

        mapper.setVisibilityChecker(new VisibilityChecker.Std(JsonAutoDetect.Visibility.ANY, JsonAutoDetect.Visibility.ANY, JsonAutoDetect.Visibility.ANY, JsonAutoDetect.Visibility.ANY, JsonAutoDetect.Visibility.ANY));

        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        mapper = null;
    }

    @Override
    public String serialize(Object o) throws SerializerException {
        try {
            return mapper.writeValueAsString(o);
        } catch (IOException e) {
            throw new SerializerException(e);
        }
    }

    @Override
    public <T> T deserialize(String o, Class<T> targetType) throws SerializerException {
        try {
            return mapper.readValue(o, targetType);
        } catch (IOException e) {
            throw new SerializerException(e);
        }
    }
}
