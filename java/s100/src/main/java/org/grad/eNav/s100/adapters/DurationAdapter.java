/*
 * Copyright (c) 2024 GLA Research and Development Directorate
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

package org.grad.eNav.s100.adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.Duration;

/**
 * The DateTime Adapter Class.
 *
 * This is used to translate between the java.time.LocalDateTime objects and the
 * XML dateTime elements.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class DurationAdapter extends XmlAdapter<String, Duration> {

    //private final DateTimeFormatter dateFormat = DurationAdapter;

    /**
     * Marshall a Java Date object into an XML element.
     *
     * @param duration  The java Duration object
     * @return The XML element
     */
    @Override
    public String marshal(Duration duration) {
        return duration.toString();
    }

    /**
     * Unmarshall an XML element into a Java Duration object.
     *
     * @param xml       The XML element
     * @return The Java Duration object
     */
    @Override
    public Duration unmarshal(String xml) {
        return Duration.parse(xml);
    }

}